package top.itcat.controller.charge.analysis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.charge.statistics.*;
import top.itcat.concurrent.CommonThreadPoolFactory;
import top.itcat.entity.charge.ChargeSubject;
import top.itcat.entity.hospital.Department;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.hospital.*;
import top.itcat.rpc.service.model.*;
import top.itcat.rpc.service.order.GetChargeSubjectRequest;
import top.itcat.rpc.service.order.GetChargeSubjectResponse;
import top.itcat.rpc.service.user.*;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 工作量统计Controller
 * <p> 主要包括 医生工作量统计、科室工作量统计
 */
@RestController
@RequestMapping("/charge/statistics")
@Slf4j
public class StatisticsController {
    @Autowired
    private HospitalServiceClient hospitalServiceClient;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private OrderServiceClient orderServiceClient;

    /**
     * 医生工作量统计
     * 请求方法：Any
     *
     * @return 获取到的医生工作量
     * @see GetDoctorStatisticsReq
     * @see DoctorStatistics
     */
    @RequestMapping("/doctor/get")
    @RoleCheck(roles = {RoleEnum.Account_Clerk, RoleEnum.Doctor, RoleEnum.Medical_Doctor})
    public String getDoctorStatistics(GetDoctorStatisticsReq req,
                                      HttpServletRequest request) {
        if (req.getStartTime() == null || req.getEndTime() == null) {
            return GetBaseResponUtil.getBaseRspStr(400, "参数有误");
        }

        List<Long> doctorIds = null;
        Claims claims = ((Claims) request.getAttribute("claims"));
        Map map = request.getParameterMap();

        if ((int) claims.get("role") == RoleEnum.Medical_Doctor.getValue()) {
            doctorIds = Collections.singletonList(Long.valueOf(String.valueOf(claims.get("id"))));
        } else if (req.getAll() == null || !req.getAll()) {
            if (req.getIds() != null && !req.getIds().isEmpty()) {
                doctorIds = req.getIds();
            } else if (req.getDepartmentId() != null) {
                GetUserIdsRequest getUserIdsRequest = new GetUserIdsRequest();
                getUserIdsRequest.setDepartId(req.getDepartmentId());
                GetUserIdsResponse getUserIdsResponse = userServiceClient.getUserIds(getUserIdsRequest);
                doctorIds = new ArrayList<>();

                for (List<Long> ids : getUserIdsResponse.getIds().values()) {
                    doctorIds.addAll(ids);
                }
            } else {
                doctorIds = Collections.singletonList(Long.valueOf(String.valueOf(claims.get("id"))));
            }
        }

        GetDoctorStatisticalDataRequest getDoctorStatisticalDataRequest = new GetDoctorStatisticalDataRequest();
        getDoctorStatisticalDataRequest.setDoctorIds(doctorIds);
        getDoctorStatisticalDataRequest.setEndTime(req.getEndTime());
        getDoctorStatisticalDataRequest.setStartTime(req.getStartTime());
        GetDoctorStatisticalDataResponse getDoctorStatisticalDataResponse = hospitalServiceClient.getDoctorStatistical(getDoctorStatisticalDataRequest);
        Map<Long, User> userMap;

        if ((int) claims.get("role") == RoleEnum.Medical_Doctor.getValue()) {
            // 获取医技医生信息
            // 医技医生只能查询个人工作量，不可批量查询
            MGetMedicalDoctorRequest getMedicalDoctorRequest = new MGetMedicalDoctorRequest();
            getMedicalDoctorRequest.setUids(doctorIds);
            MGetMedicalDoctorResponse getMedicalDoctorResponse = userServiceClient.mGetMedicalDoctor(getMedicalDoctorRequest);
            userMap = new HashMap<>(1);

            userMap.put(Long.valueOf((String) claims.get("id")), getMedicalDoctorResponse.getUsers().get(0).getUser());
        } else {
            // 获取并过滤医生ID
            doctorIds = getDoctorStatisticalDataResponse.getBeanList()
                    .parallelStream()
                    .map(DoctorStatisticalData::getDoctorId)
                    .distinct()
                    .collect(Collectors.toList());
            MGetDoctorRequest getDoctorRequest = new MGetDoctorRequest();
            getDoctorRequest.setUids(doctorIds);
            MGetDoctorResponse getDoctorResponse = userServiceClient.mGetDoctor(getDoctorRequest);
            userMap = getDoctorResponse.getUsers()
                    .parallelStream()
                    .collect(Collectors.toMap(item -> item.getUser().getId(), OutpatientDoctor::getUser));
        }

        // 获取费用科目
        GetChargeSubjectRequest getChargeSubjectRequest = new GetChargeSubjectRequest();
        GetChargeSubjectResponse getChargeSubjectResponse = orderServiceClient.getChargeSubject(getChargeSubjectRequest);
        Map<Long, ChargeSubject> chargeSubjectMap = getChargeSubjectResponse.getBeanList()
                .parallelStream()
                .map(ChargeSubject::convert)
                .collect(Collectors.toMap(ChargeSubject::getId, chargeSubject -> chargeSubject));
        List<DoctorStatistics> doctorStatisticsList = new ArrayList<>();

        // 根据费用科目打包信息
        for (DoctorStatisticalData doctorStatisticalData : getDoctorStatisticalDataResponse.getBeanList()) {
            DoctorStatistics doctorStatistics = new DoctorStatistics();
            doctorStatistics.setDoctorInfo(userMap.get(doctorStatisticalData.getDoctorId()));
            doctorStatistics.setRegistrationCount(doctorStatisticalData.getRegistrationCount());

            List<StatisticsDetail> statisticsDetails = new ArrayList<>();

            // 各费用科目对应的数据
            for (Map.Entry<Long, Double> en :
                    doctorStatisticalData.getChargeDetail().entrySet()) {
                StatisticsDetail statisticsDetail = new StatisticsDetail();
                statisticsDetail.setAmount(en.getValue());
                statisticsDetail.setChargeSubject(chargeSubjectMap.get(en.getKey()));
                statisticsDetails.add(statisticsDetail);
            }

            doctorStatistics.setStatisticsDetail(statisticsDetails);
            doctorStatisticsList.add(doctorStatistics);
        }

        JSONObject jsonObject = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = doctorStatisticsList.parallelStream()
                .sorted((o1, o2) -> {
                    long amount1 = 0;
                    long amount2 = 0;

                    for (StatisticsDetail detail : o1.getStatisticsDetail()) {
                        amount1 += detail.getAmount();
                    }

                    for (StatisticsDetail detail : o2.getStatisticsDetail()) {
                        amount2 += detail.getAmount();
                    }

                    return (int) (amount2 - amount1);
                })
                .collect(Collectors.toCollection(JSONArray::new));

        jsonObject.put("list", jsonArray);
        jsonObject.put("count", doctorStatisticsList.size());
        jsonObject.put("total", doctorStatisticsList.size());
        return JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 科室工作量统计
     * 请求方法：Any
     *
     * @return 获取到的科室工作量
     * @see GetDepartStatisticsReq
     * @see DepartStatistics
     */
    @RequestMapping("/depart/get")
    @RoleCheck(roles = {RoleEnum.Account_Clerk})
    public String getDepartStatistics(GetDepartStatisticsReq req,
                                      HttpServletRequest request) {
        List<Long> departIds = null;

        if (req.getAll() == null || !req.getAll()) {
            if (req.getIds() != null && !req.getIds().isEmpty()) {
                departIds = req.getIds();
            }
        }

        GetDepartStatisticalDataRequest getDepartStatisticalDataRequest = new GetDepartStatisticalDataRequest();
        getDepartStatisticalDataRequest.setDepartIds(departIds);
        getDepartStatisticalDataRequest.setEndTime(req.getEndTime());
        getDepartStatisticalDataRequest.setStartTime(req.getStartTime());
        GetDepartStatisticalDataResponse getDepartStatisticalDataResponse = hospitalServiceClient.getDepartStatistical(getDepartStatisticalDataRequest);

        // 获取统计数据
        GetChargeSubjectRequest getChargeSubjectRequest = new GetChargeSubjectRequest();
        GetChargeSubjectResponse getChargeSubjectResponse = orderServiceClient.getChargeSubject(getChargeSubjectRequest);
        Map<Long, ChargeSubject> chargeSubjectMap = getChargeSubjectResponse.getBeanList()
                .parallelStream()
                .map(ChargeSubject::convert)
                .collect(Collectors.toMap(ChargeSubject::getId, chargeSubject -> chargeSubject));

        // 获取并过滤科室ID
        departIds = getDepartStatisticalDataResponse.getBeanList()
                .parallelStream()
                .map(DepartStatisticalData::getDepartId)
                .distinct()
                .collect(Collectors.toList());

        GetDepartmentRequest getDepartmentRequest = new GetDepartmentRequest();
        getDepartmentRequest.setIds(departIds);
        GetDepartmentResponse getDepartmentResponse = hospitalServiceClient.getDepartment(getDepartmentRequest);
        Map<Long, Department> departmentMap = getDepartmentResponse.getBeanList()
                .parallelStream()
                .map(Department::convert)
                .collect(Collectors.toMap(Department::getId, department -> department));
        List<DepartStatistics> departStatisticsList = new ArrayList<>();

        for (DepartStatisticalData departStatisticalData : getDepartStatisticalDataResponse.getBeanList()) {
            DepartStatistics departStatistics = new DepartStatistics();
            departStatistics.setDepartment(departmentMap.get(departStatisticalData.getDepartId()));
            departStatistics.setRegistrationCount(departStatisticalData.getRegistrationCount());

            List<StatisticsDetail> statisticsDetails = new ArrayList<>();

            for (Map.Entry<Long, Double> en :
                    departStatisticalData.getChargeDetail().entrySet()) {
                StatisticsDetail statisticsDetail = new StatisticsDetail();
                statisticsDetail.setAmount(en.getValue());
                statisticsDetail.setChargeSubject(chargeSubjectMap.get(en.getKey()));
                statisticsDetails.add(statisticsDetail);
            }

            departStatistics.setStatisticsDetail(statisticsDetails);
            departStatisticsList.add(departStatistics);
        }

        JSONObject jsonObject = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = departStatisticsList.parallelStream()
                .sorted((o1, o2) -> {
                    long amount1 = 0;
                    long amount2 = 0;

                    for (StatisticsDetail detail : o1.getStatisticsDetail()) {
                        amount1 += detail.getAmount();
                    }

                    for (StatisticsDetail detail : o2.getStatisticsDetail()) {
                        amount2 += detail.getAmount();
                    }

                    return (int) (amount2 - amount1);
                })
                .collect(Collectors.toCollection(JSONArray::new));

        jsonObject.put("list", jsonArray);
        jsonObject.put("count", departStatisticsList.size());
        jsonObject.put("total", departStatisticsList.size());
        return JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
    }
}
