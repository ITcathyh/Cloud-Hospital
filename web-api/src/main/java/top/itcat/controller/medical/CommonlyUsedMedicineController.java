package top.itcat.controller.medical;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.LineConvertHump;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.CommonDelReq;
import top.itcat.bean.medical.AddCommonlyUsedMedicineReq;
import top.itcat.bean.medical.QueryCommonlyUsedMedicineReq;
import top.itcat.bean.medical.UpdateCommonlyUsedMedicineReq;
import top.itcat.entity.medical.CommonlyUsedMedicine;
import top.itcat.entity.medical.Medicine;
import top.itcat.rpc.client.MedicalServiceClient;
import top.itcat.rpc.service.medical.*;
import top.itcat.rpc.service.model.MedicineCategoryEnum;
import top.itcat.rpc.service.model.SuitableRangeEnum;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 常用药品Controller
 * <p> 主要包括 常用药品的增删改查
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/medical/medicine/common")
@Slf4j
@RoleCheck
public class CommonlyUsedMedicineController {
    @Autowired
    private MedicalServiceClient medicalServerClient;

    /**
     * 增加常用药品
     * 请求方法：Post
     *
     * @return 状态
     * @see AddCommonlyUsedMedicineReq
     */
    @PostMapping("/manage/add")
    public String addMedicine(@LineConvertHump AddCommonlyUsedMedicineReq req,
                              HttpServletRequest request) {
        Claims claims = ((Claims) request.getAttribute("claims"));

        for (CommonlyUsedMedicine commonlyUsedMedicine : req.getList()) {
            commonlyUsedMedicine.setDoctorId(Long.valueOf((String) claims.get("id")));
            commonlyUsedMedicine.setDepartmentId(Long.valueOf((String) claims.get("departmentId")));
        }

        AddOrUpdateCommonlyUsedMedicineRequest rpcReq = new AddOrUpdateCommonlyUsedMedicineRequest();
        rpcReq.setBeanList(req.getList().
                parallelStream().map(CommonlyUsedMedicine::convertRPCBean).
                collect(Collectors.toList()));

        if (medicalServerClient.addOrUpdateCommonlyUsedMedicine(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新常用药品
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateCommonlyUsedMedicineReq
     */
    @PostMapping("/manage/update")
    public String updateMedicine(@LineConvertHump UpdateCommonlyUsedMedicineReq req) {
        AddOrUpdateCommonlyUsedMedicineRequest rpcReq = new AddOrUpdateCommonlyUsedMedicineRequest();
        rpcReq.setBeanList(Collections.singletonList(CommonlyUsedMedicine.convertRPCBean(req.getBean())));

        if (medicalServerClient.addOrUpdateCommonlyUsedMedicine(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除常用药品
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delMedicine(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.CommonlyUsedMedicine rpcbean = new top.itcat.rpc.service.model.CommonlyUsedMedicine();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdateCommonlyUsedMedicineRequest rpcReq = new AddOrUpdateCommonlyUsedMedicineRequest();
        rpcReq.setBeanList(Collections.singletonList(rpcbean));

        if (medicalServerClient.addOrUpdateCommonlyUsedMedicine(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询常用药品
     * 请求方法：Any
     *
     * @return 获取到的常用药品信息
     * @see GetCommonlyUsedMedicineRequest
     * @see CommonlyUsedMedicine
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getMedicine(QueryCommonlyUsedMedicineReq req,
                              HttpServletRequest request) {
        GetCommonlyUsedMedicineRequest rpcReq = new GetCommonlyUsedMedicineRequest();

        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }

        if (req.getLimit() != null) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.getOffset() != null) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.getCatalog() != null) {
            rpcReq.setCatalog(MedicineCategoryEnum.findByValue(req.getCatalog()));
        }

        if (req.getSuitableRange() == null) {
            req.setSuitableRange(SuitableRangeEnum.Personal.getValue());
        }

        if (req.getSuitableRange() != null) {
            SuitableRangeEnum rangeEnum = SuitableRangeEnum.findByValue(req.getSuitableRange());
            rpcReq.setRange(Collections.singletonList(rangeEnum));

            if (rangeEnum == SuitableRangeEnum.Personal) {
                Claims claims = ((Claims) request.getAttribute("claims"));
                rpcReq.setDoctorId(Long.valueOf((String) claims.get("id")));
            } else if (rangeEnum == SuitableRangeEnum.Depart) {
                Claims claims = ((Claims) request.getAttribute("claims"));
                rpcReq.setDepartmentId(Long.valueOf((String) claims.get("departmentId")));
            }
        }

        GetCommonlyUsedMedicineResponse rsp = medicalServerClient.getCommonlyUsedMedicine(rpcReq);
        JSONObject json = GetBaseResponUtil.getSuccessJson();

        if (rsp == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        } else if (rsp.getBeanList() == null || rsp.getBeanListSize() == 0) {
            json.put("list", new JSONArray());
            json.put("count", rsp.getBeanListSize());
            json.put("total", rsp.getTotal());
            return json.toJSONString();
        }

        List<CommonlyUsedMedicine> commonlyUsedMedicineList = rsp.getBeanList()
                .parallelStream()
                .map(CommonlyUsedMedicine::convert)
                .collect(Collectors.toList());
        GetMedicineRequest getMedicineRequest = new GetMedicineRequest();
        getMedicineRequest.setIds(commonlyUsedMedicineList
                .parallelStream()
                .map(CommonlyUsedMedicine::getMedicalId)
                .distinct()
                .collect(Collectors.toList()));
        GetMedicineResponse getMedicineResponse = medicalServerClient.getMedicine(getMedicineRequest);

        if (getMedicineResponse == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        if (getMedicineResponse.getBeanList() != null && getMedicineResponse.getBeanListSize() > 0) {
            Map<Long, Medicine> medicineMap = getMedicineResponse.getBeanList()
                    .parallelStream()
                    .map(Medicine::convert)
                    .collect(Collectors.toMap(Medicine::getId, item -> item));

            for (CommonlyUsedMedicine commonlyUsedMedicine : commonlyUsedMedicineList) {
                Medicine medicine = medicineMap.get(commonlyUsedMedicine.getMedicalId());

                if (medicine != null) {
                    commonlyUsedMedicine.setMedicine(medicine);
                }
            }
        }

        JSONArray jsonArray = commonlyUsedMedicineList.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }
}
