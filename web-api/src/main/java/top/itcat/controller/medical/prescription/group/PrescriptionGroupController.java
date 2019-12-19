package top.itcat.controller.medical.prescription.group;

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
import top.itcat.bean.diagnose.prescription.group.AddPrescriptionGroupsReq;
import top.itcat.bean.diagnose.prescription.group.QueryPrescriptionGroupRequest;
import top.itcat.bean.diagnose.prescription.group.UpdatePrescriptionGroupReq;
import top.itcat.entity.medical.Medicine;
import top.itcat.entity.medical.PrescriptionGroup;
import top.itcat.entity.medical.PrescriptionItemTemplate;
import top.itcat.rpc.client.MedicalServiceClient;
import top.itcat.rpc.service.medical.*;
import top.itcat.rpc.service.model.PrescriptionGroupCatalogEnum;
import top.itcat.rpc.service.model.SuitableRangeEnum;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 处方组套Controller
 * <p> 主要包括 处方组套的增删改查
 */
@RestController
@RequestMapping("/diagnose/prescription/group")
@Slf4j
@RoleCheck
public class PrescriptionGroupController {
    @Autowired
    private MedicalServiceClient medicalServiceClient;

    /**
     * 增加处方组套
     * 请求方法：Post
     *
     * @return 状态
     * @see AddPrescriptionGroupsReq
     */
    @PostMapping("/manage/add")
    public String addPrescriptionGroup(@LineConvertHump AddPrescriptionGroupsReq req,
                                       HttpServletRequest request) {
        AddOrUpdatePrescriptionGroupRequest rpcReq = new AddOrUpdatePrescriptionGroupRequest();
        PrescriptionGroup prescriptionGroup = req.getList().get(0);

//        if (prescriptionGroup.getItems() == null || prescriptionGroup.getItems().isEmpty()) {
//            return GetBaseResponUtil.getBaseRspStr(400, "处方为空");
//        }

        Claims claims = ((Claims) request.getAttribute("claims"));
        prescriptionGroup.setDepartmentId(Long.valueOf((String) claims.get("departmentId")));
        prescriptionGroup.setCreatorId(Long.valueOf((String) claims.get("id")));
        rpcReq.setBean(PrescriptionGroup.convertRPCBean(prescriptionGroup));

        if (medicalServiceClient.addOrUpdatePrescriptionGroup(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新处方组套
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdatePrescriptionGroupReq
     */
    @PostMapping("/manage/update")
    public String updatePrescriptionGroup(@LineConvertHump UpdatePrescriptionGroupReq req) {
        AddOrUpdatePrescriptionGroupRequest rpcReq = new AddOrUpdatePrescriptionGroupRequest();
        rpcReq.setBean(PrescriptionGroup.convertRPCBean(req.getPrescriptionGroup()));
        if (medicalServiceClient.addOrUpdatePrescriptionGroup(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除处方组套
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delPrescriptionGroup(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.PrescriptionGroup rpcbean = new top.itcat.rpc.service.model.PrescriptionGroup();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdatePrescriptionGroupRequest rpcReq = new AddOrUpdatePrescriptionGroupRequest();
        rpcReq.setBean(rpcbean);
        medicalServiceClient.addOrUpdatePrescriptionGroup(rpcReq);
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询处方组套
     * 请求方法：Any
     *
     * @return 获取到的处方组套信息
     * @see QueryPrescriptionGroupRequest
     * @see PrescriptionGroup
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getPrescriptionGroup(QueryPrescriptionGroupRequest req,
                                       HttpServletRequest request) {
        GetPrescriptionGroupRequest rpcReq = new GetPrescriptionGroupRequest();

        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }

        if (req.getSearchKey() != null) {
            rpcReq.setSearchKey(req.getSearchKey());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.getCatalog() != null) {
            rpcReq.setCatalog(PrescriptionGroupCatalogEnum.findByValue(req.getCatalog()));
        }

        if (req.getSuitableRange() != null) {
            SuitableRangeEnum rangeEnum = SuitableRangeEnum.findByValue(req.getSuitableRange());
            rpcReq.setRange(Collections.singletonList(rangeEnum));

            if (rangeEnum == SuitableRangeEnum.Personal) {
                Claims claims = ((Claims) request.getAttribute("claims"));
                rpcReq.setCreaterId(Long.valueOf((String) claims.get("id")));
            } else if (rangeEnum == SuitableRangeEnum.Depart) {
                Claims claims = ((Claims) request.getAttribute("claims"));
                rpcReq.setDepartmentId(Long.valueOf((String) claims.get("departmentId")));
            }
        }

        GetPrescriptionGroupResponse rsp = medicalServiceClient.getPrescriptionGroup(rpcReq);

        if (rsp.getBeanList() == null || rsp.getBeanListSize() == 0) {
            JSONObject json = GetBaseResponUtil.getSuccessJson();
            json.put("list", new JSONArray());
            json.put("count", rsp.getBeanListSize());
            json.put("total", rsp.getTotal());
        }
        List<PrescriptionGroup> prescriptions = rsp.getBeanList()
                .parallelStream()
                .map(PrescriptionGroup::convert)
                .collect(Collectors.toList());

        Set<Long> medicineIds = new HashSet<>();

        // 查询具体申请项目
        for (PrescriptionGroup prescription : prescriptions) {
            if (prescription.getItems() != null) {
                for (PrescriptionItemTemplate item : prescription.getItems()) {
                    medicineIds.add(item.getMedicalId());
                }
            }
        }

        if (medicineIds.size() > 0) {
            GetMedicineRequest getMedicineRequest = new GetMedicineRequest();
            getMedicineRequest.setIds(new ArrayList<>(medicineIds));
            GetMedicineResponse getMedicineResponse = medicalServiceClient.getMedicine(getMedicineRequest);
            Map<Long, Medicine> medicineMap = getMedicineResponse.getBeanList()
                    .parallelStream()
                    .map(Medicine::convert)
                    .collect(Collectors.toMap(Medicine::getId,
                            item -> item));

            for (PrescriptionGroup prescription : prescriptions) {
                if (prescription.getItems() != null) {
                    for (PrescriptionItemTemplate item : prescription.getItems()) {
                        item.setMedicine(medicineMap.get(item.getMedicalId()));
                    }
                }
            }
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = prescriptions.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }
}
