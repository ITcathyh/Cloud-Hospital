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
import top.itcat.bean.diagnose.prescription.herb.AddPrescriptionHerbReq;
import top.itcat.bean.diagnose.prescription.herb.QueryPrescriptionHerbRequest;
import top.itcat.controller.action.ControllerHelper;
import top.itcat.controller.action.RegistrationHelper;
import top.itcat.entity.charge.ChargeItem;
import top.itcat.entity.medical.Medicine;
import top.itcat.entity.medical.PrescriptionHerb;
import top.itcat.entity.medical.PrescriptionHerbItem;
import top.itcat.exception.InvalidParamException;
import top.itcat.exception.NotEnoughException;
import top.itcat.rpc.client.MedicalServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.medical.*;
import top.itcat.rpc.service.order.AddOrUpdateChargeItemRequest;
import top.itcat.rpc.service.order.AddOrUpdateChargeItemResponse;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 草药处方Controller
 * 处方单只允许开具和作废，不允许更新
 * <p> 主要包括 草药处方的增删查
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/diagnose/prescription/herb")
@Slf4j
@RoleCheck
public class PrescriptionHerbController {
    @Autowired
    private MedicalServiceClient medicalServerClient;
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private RegistrationHelper registrationHelper;

    /**
     * 开立草药处方
     * 请求方法：Post
     *
     * @return 状态
     * @see AddPrescriptionHerbReq
     */
    @PostMapping("/manage/add")
    public String addPrescriptionHerb(@RequestBody AddPrescriptionHerbReq req,
                                      HttpServletRequest request) {
        PrescriptionHerb prescriptionHerb = req.getList().get(0);

        if (prescriptionHerb.getItems() == null || prescriptionHerb.getItems().isEmpty()) {
            return GetBaseResponUtil.getBaseRspStr(400, "处方为空");
        }

        long billingId = 0;

        try {
            billingId = registrationHelper.getBillingCategoryId(prescriptionHerb.getMedicalRecordNo());
        } catch (Exception e) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        Claims claims = (Claims) request.getAttribute("claims");
        long operatorId = Long.parseLong((String) claims.get("id"));
        long departmentId = Long.parseLong((String) claims.get("departmentId"));
        List<top.itcat.rpc.service.model.ChargeItem> chargeItems = new ArrayList<>(prescriptionHerb.getItems().size());
        GetMedicineRequest getMedicineRequest = new GetMedicineRequest();

        for (PrescriptionHerbItem prescriptionHerbItem : prescriptionHerb.getItems()) {
            ChargeItem chargeItem = prescriptionHerbItem.getChargeItem();

            getMedicineRequest.setIds(Collections.singletonList(chargeItem.getProjectId()));
            GetMedicineResponse getMedicineResponse = medicalServerClient.getMedicine(getMedicineRequest);

            if (getMedicineResponse == null) {
                return GetBaseResponUtil.getBaseRspStr(500, "内部服务错误");
            }

            Medicine medicine = prescriptionHerbItem.getMedicine();
            chargeItem.setCreatorId(operatorId);
            chargeItem.setName(medicine.getName());
            chargeItem.setDepartmentId(departmentId);
            chargeItem.setUnitPrice(medicine.getPrice());
            chargeItem.setChargeSubjectId(controllerHelper.getChinsesChargeSubjectId());
            chargeItem.setSpecification(medicine.getSpecification());
            chargeItem.setCreateDepartmentId(departmentId);
            chargeItem.setProjectId(medicine.getId());
            chargeItem.setMedicalRecordNo(prescriptionHerb.getMedicalRecordNo());
            chargeItem.setBillingCategoryId(billingId);
            chargeItems.add(ChargeItem.convertRPCBean(chargeItem));

            prescriptionHerbItem.setItemId(medicine.getId());
            prescriptionHerbItem.setNum(chargeItem.getAmount() * prescriptionHerb.getNumber());
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
                        top.itcat.rpc.service.model.ChargeItem::getId));
        prescriptionHerb.getItems()
                .parallelStream()
                .filter(item -> projectIdToChargeItemIdMap.get(item.getItemId()) != null)
                .forEach(item ->
                        item.setChargeItemId(projectIdToChargeItemIdMap.get(item.getItemId())));

        AddOrUpdatePrescriptionHerbRequest rpcReq = new AddOrUpdatePrescriptionHerbRequest();
        rpcReq.setBean(PrescriptionHerb.convertRPCBean(prescriptionHerb));
        AddOrUpdatePrescriptionHerbResponse response = medicalServerClient.addOrUpdatePrescriptionHerb(rpcReq);

        try {
            if (medicalServerClient.addOrUpdatePrescriptionHerb(rpcReq) == null) {
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
     * 作废草药处方
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delPrescriptionHerb(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.PrescriptionHerb rpcbean = new top.itcat.rpc.service.model.PrescriptionHerb();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdatePrescriptionHerbRequest rpcReq = new AddOrUpdatePrescriptionHerbRequest();
        rpcReq.setBean(rpcbean);
        try {
            if (medicalServerClient.addOrUpdatePrescriptionHerb(rpcReq) == null) {
                return GetBaseResponUtil.getDefaultErrRspStr();
            }
        } catch (InvalidParamException e) {
            return GetBaseResponUtil.getBaseRspStr(400, "处方已收费，无法删除");
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 获得草药处方
     * 请求方法：Any
     *
     * @return 获取到的草药处方信息
     * @see GetPrescriptionHerbRequest
     */
    @RequestMapping("/get")
    @RoleCheck(needLogin = false)
    public String getPrescriptionHerb(QueryPrescriptionHerbRequest req) {
        GetPrescriptionHerbRequest rpcReq = new GetPrescriptionHerbRequest();

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

        GetPrescriptionHerbResponse rsp = medicalServerClient.getPrescriptionHerb(rpcReq);
        List<PrescriptionHerb> prescriptionHerbs = rsp.getBeanList()
                .parallelStream()
                .map(PrescriptionHerb::convert)
                .collect(Collectors.toList());

        Set<Long> medicineIds = new HashSet<>();
        Set<Long> chargeItemIds = new HashSet<>();

        // 查询具体项目
        for (PrescriptionHerb PrescriptionHerb : prescriptionHerbs) {
            if (PrescriptionHerb.getItems() != null) {
                for (PrescriptionHerbItem item : PrescriptionHerb.getItems()) {
                    medicineIds.add(item.getItemId());
                    chargeItemIds.add(item.getChargeItemId());
                }
            }
        }

        controllerHelper.packPrescriptionHerb(prescriptionHerbs, medicineIds, chargeItemIds);

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = prescriptionHerbs.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }


}
