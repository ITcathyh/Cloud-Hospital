package top.itcat.rpc.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.itcat.action.CollectStatisticalDataAction;
import top.itcat.entity.BillingCategory;
import top.itcat.entity.Department;
import top.itcat.entity.NonmedicalCharge;
import top.itcat.entity.RegistrationLevel;
import top.itcat.exception.InternalException;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.hospital.*;
import top.itcat.rpc.service.model.*;
import top.itcat.rpc.service.user.*;
import top.itcat.rpc.thrift.ThriftUtil;
import top.itcat.service.BillingCategoryService;
import top.itcat.service.DepartmentService;
import top.itcat.service.NonmedicalChargeService;
import top.itcat.service.RegistrationLevelService;
import top.itcat.util.IDGeneratorUtil;
import top.itcat.util.PinYinUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@SuppressWarnings("unchecked")
public class ServiceServer implements HospitalService.Iface {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RegistrationLevelService registrationLevelService;
    @Autowired
    private NonmedicalChargeService nonmedicalChargeService;
    @Autowired
    private BillingCategoryService billingCategoryService;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private CollectStatisticalDataAction collectStatisticalDataAction;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public GetDepartmentResponse getDepartment(GetDepartmentRequest req) throws TException {
        GetDepartmentResponse rsp = new GetDepartmentResponse();
        List<Department> list;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1).orderBy("id");
        int offset = 0;
        int limit = 300;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetIds()) {
            wrapper = wrapper.in("id", req.getIds());
        }

        if (req.isSetCatalog()) {
            wrapper = wrapper.eq("category", req.getCatalog());
        }


        if (req.isSetClassification()) {
            wrapper = wrapper.eq("classification", req.getClassification().getValue());
        }

        if (req.isSetCatalog()) {
            wrapper = wrapper.eq("category", req.getClassification().getValue());
        }

        if (req.isSetSearchKey()) {
            wrapper = wrapper.andNew()
                    .like("code", req.getSearchKey())
                    .or()
                    .like("name", req.getSearchKey());
        }

        Page<Department> page = null;
        try {
            page = departmentService.selectPage(
                    new Page<>(offset, limit), wrapper);
            page.setTotal(departmentService.selectCount(wrapper));
        } catch (Exception e) {
            log.error("getDepartment err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        list = page.getRecords();
        List<top.itcat.rpc.service.model.Department> rpcList =
                list.parallelStream().map(Department::convertRPCBean).collect(Collectors.toList());

        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateDepartmentResponse addOrUpdateDepartment(AddOrUpdateDepartmentRequest req) throws TException {
        AddOrUpdateDepartmentResponse rsp = new AddOrUpdateDepartmentResponse();

        try {
            if (!req.getBeanList().get(0).isSetId()) {
                req.getBeanList().forEach(item -> item.setCode(
                        PinYinUtil.getFirstPinYin(item.getName())
                ));
            }
        } catch (Exception e) {
            log.error("department set code err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        List<Department> list = req.getBeanList().parallelStream().map(Department::convert).collect(Collectors.toList());
        try {
//            if (list.get(0).getValid() != null && list.get(0).getValid() == -1) {
//                DepartClassificationEnum classification = DepartClassificationEnum.findByValue(list.get(0).getClassification());
//
//                if (classification == DepartClassificationEnum.Medical) {
//
//                }
//            }

            departmentService.insertOrUpdateBatch(list);
        } catch (Exception e) {
            log.error("addOrUpdateDepartment err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetBillingCategoryResponse getBillingCategory(GetBillingCategoryRequest getBillingCategoryRequest) throws TException {
        GetBillingCategoryResponse rsp = new GetBillingCategoryResponse();
        List<BillingCategory> list;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1).orderBy("id");
        int offset = 0;
        int limit = 100;

        if (getBillingCategoryRequest.isSetIds()) {
            wrapper = wrapper.in("id", getBillingCategoryRequest.getIds());
        }

        Page<BillingCategory> page = null;
        try {
            page = billingCategoryService.selectPage(
                    new Page<>(offset, limit), wrapper);
            page.setTotal(billingCategoryService.selectCount(wrapper));
        } catch (Exception e) {
            log.error("getBillingCategory err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        list = page.getRecords();
        List<top.itcat.rpc.service.model.BillingCategory> rpcList =
                list.parallelStream().map(BillingCategory::convertRPCBean).collect(Collectors.toList());

        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateBillingCategoryResponse addOrUpdateBillingCategory(AddOrUpdateBillingCategoryRequest addOrUpdateBillingCategoryRequest) throws TException {
        AddOrUpdateBillingCategoryResponse rsp = new AddOrUpdateBillingCategoryResponse();
        List<BillingCategory> list =
                addOrUpdateBillingCategoryRequest.getBeanList().parallelStream().map(BillingCategory::convert).collect(Collectors.toList());
        try {
            billingCategoryService.insertOrUpdateBatch(list);
        }catch (Exception e) {
            log.error("addOrUpdateBillingCategory err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetRegisterationLevelResponse getRegisterationLevel(GetRegisterationLevelRequest getRegisterationLevelRequest) throws TException {
        GetRegisterationLevelResponse rsp = new GetRegisterationLevelResponse();
        List<RegistrationLevel> list;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1);
        int offset = 0;
        int limit = 100;

        if (getRegisterationLevelRequest.isSetIds()) {
            wrapper = wrapper.in("id", getRegisterationLevelRequest.getIds());
        }

        if (getRegisterationLevelRequest.isSetCode()) {
            wrapper = wrapper.eq("code", getRegisterationLevelRequest.getCode());
        }

        Page<RegistrationLevel> page = null;
        try {
            page = registrationLevelService.selectPage(
                    new Page<>(offset, limit), wrapper);
            page.setTotal(registrationLevelService.selectCount(wrapper));
        } catch (Exception e) {
            log.error("getRegisterationLevel err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        list = page.getRecords();
        List<top.itcat.rpc.service.model.RegisterationLevel> rpcList =
                list.parallelStream().map(RegistrationLevel::convertRPCBean).collect(Collectors.toList());

        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateRegisterationLevelResponse addOrUpdateRegisterationLevel(AddOrUpdateRegisterationLevelRequest req) throws TException {
        AddOrUpdateRegisterationLevelResponse rsp = new AddOrUpdateRegisterationLevelResponse();
        try {
            for (RegisterationLevel level: req.getBeanList()) {
                if (level.isSetName()) {
                    level.setCode(PinYinUtil.getFirstPinYin(level.getName()));
                }
            }
        } catch (Exception e) {
            log.error("addOrUpdateRegisterationLevel err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        List<RegistrationLevel> list = req.getBeanList()
                .parallelStream()
                .map(RegistrationLevel::convert)
                .collect(Collectors.toList());
        try {
            registrationLevelService.insertOrUpdateBatch(list);
        } catch (Exception e) {
            log.error("addOrUpdateRegisterationLevel err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetNonmedicalChargeResponse getNonmedicalCharge(GetNonmedicalChargeRequest req) throws TException {
        GetNonmedicalChargeResponse rsp = new GetNonmedicalChargeResponse();
        List<NonmedicalCharge> list;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1).orderBy("id");
        int offset = 0;
        int limit = 100;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetIds()) {
            wrapper = wrapper.in("id", req.getIds());
        }

        if (req.isSetDepartmentId()) {
            wrapper = wrapper.eq("department_id", req.getDepartmentId());
        }

        if (req.isSetChargeSubjectIds()) {
            wrapper = wrapper.in("charge_subject_id", req.getChargeSubjectIds());
        }

        if (req.isSetSearchKey()) {
            wrapper = wrapper.like("code", req.getSearchKey())
                    .or()
                    .like("name", req.getSearchKey())
                    .or()
                    .like("phonetic", req.getSearchKey());
        }

        if (req.isSetCategory()) {
            wrapper = wrapper.eq("category", req.getCategory().getValue());
        }

        Page<NonmedicalCharge> page = null;
        try {
            page = nonmedicalChargeService.selectPage(
                    new Page<>(offset, limit), wrapper);
            page.setTotal(nonmedicalChargeService.selectCount(wrapper));
        } catch (Exception e) {
            log.error("getNonmedicalCharge err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        list = page.getRecords();
        List<top.itcat.rpc.service.model.NonmedicalCharge> rpcList =
                list.parallelStream().map(NonmedicalCharge::convertRPCBean).collect(Collectors.toList());

        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateNonmedicalChargeResponse addOrUpdateNonmedicalCharge(AddOrUpdateNonmedicalChargeRequest req) throws TException {
        AddOrUpdateNonmedicalChargeResponse rsp = new AddOrUpdateNonmedicalChargeResponse();

        try {
            if (!req.getBeanList().get(0).isSetId()) {
                for (top.itcat.rpc.service.model.NonmedicalCharge item : req.getBeanList()) {
                    item.setCode(IDGeneratorUtil.genCode("NMD", redisTemplate, "nonmedical_code_incr"));
                    item.setPinyin(PinYinUtil.getFirstPinYin(item.getName()));
                }
            }

            log.warn(req.getBeanList().toString());

            List<NonmedicalCharge> list = req.getBeanList()
                    .parallelStream()
                    .map(NonmedicalCharge::convert)
                    .collect(Collectors.toList());

            log.warn(list.toString());
            nonmedicalChargeService.insertOrUpdateBatch(list);
        } catch (Exception e) {
            log.error("addOrUpdateNonmedicalCharge err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    /**
     * 科室工作量统计
     *
     * @throws TException RPC框架错误
     * @see GetDepartStatisticalDataResponse
     * @see GetDepartStatisticalDataRequest
     */
    @Override
    public GetDepartStatisticalDataResponse getDepartStatistical(GetDepartStatisticalDataRequest req) throws TException {
        List<Long> departIds;
        GetDepartStatisticalDataResponse rsp = new GetDepartStatisticalDataResponse();

        if (!req.isSetDepartIds() || req.getDepartIds().isEmpty()) {
            // 查询全门诊科室
            departIds = departmentService.selectList(new EntityWrapper<Department>()
                    .eq("valid", 1)
                    .eq("classification", DepartClassificationEnum.Clinical.getValue()))
                    .parallelStream()
                    .map(Department::getId)
                    .collect(Collectors.toList());
        } else {
            departIds = req.getDepartIds();
        }

        List<DepartStatisticalData> departStatisticalDatas =
                collectStatisticalDataAction.CollectDepartStatisticalData(departIds,
                        req.getStartTime(),
                        req.getEndTime());

        if (departStatisticalDatas == null) {
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        rsp.setBeanList(departStatisticalDatas);
        return rsp;
    }

    /**
     * 医生工作量统计
     *
     * @throws TException RPC框架错误
     * @see GetDoctorStatisticalDataRequest
     * @see GetDoctorStatisticalDataResponse
     */
    @Override
    public GetDoctorStatisticalDataResponse getDoctorStatistical(GetDoctorStatisticalDataRequest req) throws TException {
        List<Long> doctorIds;
        GetDoctorStatisticalDataResponse rsp = new GetDoctorStatisticalDataResponse();
        boolean isMedical = false;

        if (!req.isSetDoctorIds() || req.getDoctorIds().isEmpty()) {
            // 查询全门诊科室
            GetUserIdsRequest getUserIdsRequest = new GetUserIdsRequest();
            getUserIdsRequest.setRoles(Collections.singletonList(RoleEnum.Doctor));
            GetUserIdsResponse getUserIdsResponse = userServiceClient.getUserIds(getUserIdsRequest);

            if (getUserIdsResponse == null) {
                rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
                return rsp;
            }

            doctorIds = new ArrayList<>();

            for (List<Long> ids : getUserIdsResponse.getIds().values()) {
                doctorIds.addAll(ids);
            }
        } else {
            doctorIds = req.getDoctorIds();

            if (doctorIds.size() == 1) {
                MGetMedicalDoctorRequest getDoctorRequest = new MGetMedicalDoctorRequest();
                getDoctorRequest.setUids(doctorIds);
                MGetMedicalDoctorResponse getDoctorResponse = userServiceClient.mGetMedicalDoctor(getDoctorRequest);

                // 判断是否为医技医生，不影响主要流程
                if (getDoctorResponse != null && getDoctorResponse.getUsersSize() != 0) {
                    isMedical = true;
                }
            }
        }

        List<DoctorStatisticalData> doctorStatisticalDatas = collectStatisticalDataAction.
                collectDoctorStatisticalData(doctorIds, req.getStartTime(), req.getEndTime(), isMedical);

        if (doctorStatisticalDatas == null) {
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        rsp.setBeanList(doctorStatisticalDatas);
        return rsp;
    }
}
