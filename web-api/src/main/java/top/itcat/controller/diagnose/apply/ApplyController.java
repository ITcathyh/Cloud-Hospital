package top.itcat.controller.diagnose.apply;

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
import top.itcat.bean.diagnose.apply.AddApplysReq;
import top.itcat.bean.diagnose.apply.QueryApplyRequest;
import top.itcat.concurrent.CommonThreadPoolFactory;
import top.itcat.controller.action.RegistrationHelper;
import top.itcat.entity.charge.ChargeItem;
import top.itcat.entity.charge.NonmedicalCharge;
import top.itcat.entity.diagnose.Apply;
import top.itcat.entity.diagnose.ApplyItem;
import top.itcat.exception.CommonException;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.diagnose.AddOrUpdateApplyRequest;
import top.itcat.rpc.service.diagnose.GetApplyRequest;
import top.itcat.rpc.service.diagnose.GetApplyResponse;
import top.itcat.rpc.service.hospital.GetNonmedicalChargeRequest;
import top.itcat.rpc.service.hospital.GetNonmedicalChargeResponse;
import top.itcat.rpc.service.model.ApplyCategory;
import top.itcat.rpc.service.order.AddOrUpdateChargeItemRequest;
import top.itcat.rpc.service.order.AddOrUpdateChargeItemResponse;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 申请Controller
 * <p> 主要包括 申请的增删查
 * 申请单只允许开具和作废，不允许更新
 */
@RestController
@RequestMapping("/diagnose/apply")
@Slf4j
@RoleCheck
public class ApplyController {
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private HospitalServiceClient hospitalServiceClient;
    @Autowired
    private RegistrationHelper registrationHelper;
    @Autowired
    private ApplyHelper applyHelper;

    /**
     * 增加申请
     * 请求方法：Post
     *
     * @return 状态
     * @see AddApplysReq
     */
    @PostMapping("/manage/add")
    public String addApply(@LineConvertHump AddApplysReq req,
                           HttpServletRequest request) {
        Apply apply = req.getList().get(0);

        if (apply.getItems() == null || apply.getItems().isEmpty()) {
            return GetBaseResponUtil.getBaseRspStr(400, "申请项目为空");
        }

        List<top.itcat.rpc.service.model.ChargeItem> chargeItems = new ArrayList<>(apply.getItems().size());
        Claims claims = (Claims) request.getAttribute("claims");
        long operatorId = Long.parseLong((String) claims.get("id"));
        long departmentId = Long.parseLong((String) claims.get("departmentId"));
        long billingId = 0;

        apply.setDoctorId(operatorId);
        apply.setTime(System.currentTimeMillis());

        try {
            billingId = registrationHelper.getBillingCategoryId(apply.getMedicalRecordNo());
        } catch (Exception e) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        for (ApplyItem applyItem : apply.getItems()) {
            ChargeItem chargeItem = applyItem.getChargeItem();
            NonmedicalCharge nonmedicalCharge = applyItem.getNonmedicalCharge();

            chargeItem.setName(nonmedicalCharge.getName());
            chargeItem.setDepartmentId(nonmedicalCharge.getDepartmentId());
            chargeItem.setUnitPrice(nonmedicalCharge.getPrice());
            chargeItem.setCreatorId(operatorId);
            chargeItem.setSpecification(nonmedicalCharge.getFormat());
            chargeItem.setCreateDepartmentId(departmentId);
            chargeItem.setCreateDepartmentId(departmentId);
            chargeItem.setMedicalRecordNo(apply.getMedicalRecordNo());
            chargeItem.setProjectId(nonmedicalCharge.getId());
            chargeItem.setChargeSubjectId(nonmedicalCharge.getChargeRecordId());
            chargeItem.setSpecification(nonmedicalCharge.getFormat());
            chargeItem.setBillingCategoryId(billingId);
            chargeItems.add(ChargeItem.convertRPCBean(chargeItem));
            applyItem.setNonmedicalChargeId(nonmedicalCharge.getId());
        }

        AddOrUpdateChargeItemRequest addOrUpdateChargeItemRequest = new AddOrUpdateChargeItemRequest();
        addOrUpdateChargeItemRequest.setBeanList(chargeItems);
        AddOrUpdateChargeItemResponse addOrUpdateChargeItemResponse = orderServiceClient.addOrUpdateChargeItem(addOrUpdateChargeItemRequest);

        if (addOrUpdateChargeItemResponse == null) {
            return GetBaseResponUtil.getBaseRspStr(500, "内部服务错误");
        }

        CommonThreadPoolFactory.getDefaultThreadPool().execute(() -> {
            Map<Long, Long> projectIdToChargeItemIdMap = addOrUpdateChargeItemResponse.getBeanList()
                    .parallelStream()
                    .collect(Collectors.toMap(top.itcat.rpc.service.model.ChargeItem::getProjectId,
                            top.itcat.rpc.service.model.ChargeItem::getId, (k1, k2) -> k1));
            apply.getItems().forEach(item ->
                    item.setChargeItemId(projectIdToChargeItemIdMap.get(item.getNonmedicalChargeId())));

            AddOrUpdateApplyRequest rpcReq = new AddOrUpdateApplyRequest();
            rpcReq.setBean(Apply.convertRPCBean(apply));

            if (diagnoseServiceClient.addOrUpdateApply(rpcReq) == null ){
//                return GetBaseResponUtil.getDefaultErrRspStr();
            }
        });

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除申请
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delApply(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.Apply rpcbean = new top.itcat.rpc.service.model.Apply();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdateApplyRequest rpcReq = new AddOrUpdateApplyRequest();
        rpcReq.setBean(rpcbean);

        try {
            if (diagnoseServiceClient.addOrUpdateApply(rpcReq) == null) {
                return GetBaseResponUtil.getDefaultErrRspStr();
            }
        } catch (CommonException e) {
            return GetBaseResponUtil.getBaseRspStr(403, e.getPromt());
        }


        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询申请
     * 请求方法：Any
     *
     * @return 获取到的申请信息
     * @see QueryApplyRequest
     * @see Apply
     */
    @RequestMapping("/get")
    @RoleCheck(needLogin = false)
    public String getApply(QueryApplyRequest req) {
        GetApplyRequest rpcReq = new GetApplyRequest();

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }

        if (req.getMedicalRecordNo() != null) {
            rpcReq.setMedicalRecordNo(req.getMedicalRecordNo());
        }

        if (req.getCategory() != null) {
            rpcReq.setCategory(ApplyCategory.findByValue(req.getCategory()));
        }

        if (req.getDoctorId() != null) {
            rpcReq.setDoctorId(req.getDoctorId());
        }

        GetApplyResponse rsp = diagnoseServiceClient.getApply(rpcReq);

        List<Apply> applies = rsp.getBeanList()
                .parallelStream()
                .map(Apply::convert)
                .collect(Collectors.toList());

        if (req.getNeedItemDetail() != null && req.getNeedItemDetail()) {
            List<ApplyItem> applyItems = new ArrayList<>();

            for (Apply apply : applies) {
                if (apply.getItems() != null) {
                    applyItems.addAll(apply.getItems());
                }
            }

            applyHelper.packApplyItem(applyItems);
        } else {
            Set<Long> nonmedicalIds = new HashSet<>();
            // 查询具体申请项目
            for (Apply apply : applies) {
                if (apply.getItems() != null) {
                    for (ApplyItem applyItem : apply.getItems()) {
                        nonmedicalIds.add(applyItem.getNonmedicalChargeId());
                    }
                }
            }

            if (nonmedicalIds.size() > 0) {
                GetNonmedicalChargeRequest getNonmedicalChargeRequest = new GetNonmedicalChargeRequest();
                getNonmedicalChargeRequest.setIds(new ArrayList<>(nonmedicalIds));
                GetNonmedicalChargeResponse getNonmedicalChargeResponse = hospitalServiceClient.getNonmedicalCharge(getNonmedicalChargeRequest);
                Map<Long, NonmedicalCharge> nonmedicalChargeMap = getNonmedicalChargeResponse.getBeanList()
                        .parallelStream()
                        .map(NonmedicalCharge::convert)
                        .collect(Collectors.toMap(NonmedicalCharge::getId, i -> i));

                for (Apply apply : applies) {
                    if (apply.getItems() != null) {
                        for (ApplyItem applyItem : apply.getItems()) {
                            applyItem.setNonmedicalCharge(nonmedicalChargeMap.get(applyItem.getNonmedicalChargeId()));
                        }
                    }
                }
            }
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = applies.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }
}