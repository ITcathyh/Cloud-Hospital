package top.itcat.controller.medical.prescription;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.LineConvertHump;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.CommonDelReq;
import top.itcat.bean.diagnose.prescription.AddPrescriptionsReq;
import top.itcat.bean.diagnose.prescription.QueryPrescriptionRequest;
import top.itcat.controller.action.ControllerHelper;
import top.itcat.controller.action.RegistrationHelper;
import top.itcat.entity.charge.ChargeItem;
import top.itcat.entity.medical.Medicine;
import top.itcat.entity.medical.Prescription;
import top.itcat.entity.medical.PrescriptionItem;
import top.itcat.exception.InvalidParamException;
import top.itcat.exception.NotEnoughException;
import top.itcat.rpc.client.MedicalServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.medical.AddOrUpdatePrescriptionRequest;
import top.itcat.rpc.service.medical.GetMedicineRequest;
import top.itcat.rpc.service.medical.GetPrescriptionRequest;
import top.itcat.rpc.service.medical.GetPrescriptionResponse;
import top.itcat.rpc.service.order.AddOrUpdateChargeItemRequest;
import top.itcat.rpc.service.order.AddOrUpdateChargeItemResponse;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 处方Controller
 * 处方单只允许开具和作废，不允许更新
 * <p> 主要包括 处方的增删查
 */
@RestController
@RequestMapping("/diagnose/prescription")
@Slf4j
@RoleCheck
public class PrescriptionController {
    @Autowired
    private MedicalServiceClient medicalServiceClient;
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private RegistrationHelper registrationHelper;

    /**
     * 开立处方
     * 请求方法：Post
     *
     * @return 状态
     * @see AddPrescriptionsReq
     */
    @PostMapping("/manage/add")
    public String addPrescription(@RequestBody AddPrescriptionsReq req,
                                  HttpServletRequest request) {
        Prescription prescription = req.getList().get(0);

        if (prescription.getItems() == null || prescription.getItems().isEmpty()) {
            return GetBaseResponUtil.getBaseRspStr(400, "处方为空");
        }

        for (PrescriptionItem item : prescription.getItems()) {
            if (item.getMedicine().getUsage() == null) {
                return GetBaseResponUtil.getBaseRspStr(400, "使用方法为空");
            }

            if (item.getMedicine().getDosage() <= 0) {
                return GetBaseResponUtil.getBaseRspStr(400, "用量填写有误");
            }
        }

        long billingId = 0;

        try {
            billingId = registrationHelper.getBillingCategoryId(prescription.getMedicalRecordNo());
        } catch (Exception e) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        Claims claims = (Claims) request.getAttribute("claims");
        long operatorId = Long.parseLong((String) claims.get("id"));
        long departmentId = Long.parseLong((String) claims.get("departmentId"));
        prescription.setDoctorId(operatorId);
        List<top.itcat.rpc.service.model.ChargeItem> chargeItems = new ArrayList<>(prescription.getItems().size());
        GetMedicineRequest getMedicineRequest = new GetMedicineRequest();

        for (PrescriptionItem prescriptionItem : prescription.getItems()) {
            ChargeItem chargeItem = prescriptionItem.getChargeItem();
            Medicine medicine = prescriptionItem.getMedicine();

            chargeItem.setCreatorId(operatorId);
            chargeItem.setName(medicine.getName());
            chargeItem.setDepartmentId(departmentId);
            chargeItem.setUnitPrice(medicine.getPrice());
            chargeItem.setSpecification(medicine.getSpecification());
//            chargeItem.setChargeSubjectId(controllerHelper.getWestrenChargeSubjectId());
            chargeItem.setCreateDepartmentId(departmentId);
            chargeItem.setProjectId(medicine.getId());
            chargeItem.setMedicalRecordNo(prescription.getMedicalRecordNo());
            chargeItem.setBillingCategoryId(billingId);
            chargeItems.add(ChargeItem.convertRPCBean(chargeItem));

            prescriptionItem.setUseFrequent(medicine.getFrequency());
            prescriptionItem.setItemId(medicine.getId());
            // todo 使用方法枚举？
            prescriptionItem.setUseMethod(medicine.getUsage());
            prescriptionItem.setNum(chargeItem.getAmount());
        }

        AddOrUpdateChargeItemRequest addOrUpdateChargeItemRequest = new AddOrUpdateChargeItemRequest();
        addOrUpdateChargeItemRequest.setBeanList(chargeItems);
        AddOrUpdateChargeItemResponse addOrUpdateChargeItemResponse = orderServiceClient.addOrUpdateChargeItem(addOrUpdateChargeItemRequest);

        if (addOrUpdateChargeItemResponse == null) {
            return GetBaseResponUtil.getBaseRspStr(500, "内部服务错误");
        }

        Map<Long, Long> projectIdToChargeItemIdMap = addOrUpdateChargeItemResponse.getBeanList()
                .parallelStream()
                .collect(Collectors.toMap(top.itcat.rpc.service.model.ChargeItem::getProjectId,
                        top.itcat.rpc.service.model.ChargeItem::getId, (k1, k2) -> k1));
        prescription.getItems()
                .parallelStream()
                .filter(item -> projectIdToChargeItemIdMap.get(item.getItemId()) != null)
                .forEach(item ->
                        item.setChargeItemId(projectIdToChargeItemIdMap.get(item.getItemId())));

        AddOrUpdatePrescriptionRequest rpcReq = new AddOrUpdatePrescriptionRequest();
        rpcReq.setBean(Prescription.convertRPCBean(prescription));

        try {
            if (medicalServiceClient.addOrUpdatePrescription(rpcReq) == null) {
                return GetBaseResponUtil.getBaseRspStr(500, "内部服务错误");
            }
        } catch (InvalidParamException e) {
            return GetBaseResponUtil.getBaseRspStr(400, "请求参数错误");
        } catch (NotEnoughException e) {
            return GetBaseResponUtil.getBaseRspStr(403, "药品库存不足");
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 作废处方
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delPrescription(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.Prescription rpcbean = new top.itcat.rpc.service.model.Prescription();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdatePrescriptionRequest rpcReq = new AddOrUpdatePrescriptionRequest();
        rpcReq.setBean(rpcbean);

        try {
            if (medicalServiceClient.addOrUpdatePrescription(rpcReq) == null) {
                return GetBaseResponUtil.getDefaultErrRspStr();
            }
        } catch (InvalidParamException e) {
            return GetBaseResponUtil.getBaseRspStr(400, "处方已收费，无法删除");
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 获得处方
     * 请求方法：Any
     *
     * @return 获取到的处方信息
     * @see QueryPrescriptionRequest
     */
    @RequestMapping("/get")
    @RoleCheck(needLogin = false)
    public String getPrescription(QueryPrescriptionRequest req) {
        GetPrescriptionRequest rpcReq = new GetPrescriptionRequest();

        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }

        if (req.getMedicalRecordNo() != null) {
            rpcReq.setMedicalRecordNo(req.getMedicalRecordNo());
        }

        if (req.getDoctorId() != null) {
            rpcReq.setDoctorId(req.getDoctorId());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        GetPrescriptionResponse rsp = medicalServiceClient.getPrescription(rpcReq);
        List<Prescription> prescriptions = rsp.getBeanList()
                .parallelStream()
                .map(Prescription::convert)
                .collect(Collectors.toList());

        Set<Long> medicineIds = new HashSet<>();
        Set<Long> chargeItemIds = new HashSet<>();

        // 查询具体申请项目
        for (Prescription prescription : prescriptions) {
            if (prescription.getItems() != null) {
                for (PrescriptionItem item : prescription.getItems()) {
                    medicineIds.add(item.getItemId());
                    chargeItemIds.add(item.getChargeItemId());
                }
            }
        }

        controllerHelper.packPrescription(prescriptions, medicineIds, chargeItemIds);

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = prescriptions.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }
}
