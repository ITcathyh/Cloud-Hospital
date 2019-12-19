package top.itcat.controller.charge;

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
import top.itcat.bean.charge.GetChargeReq;
import top.itcat.bean.charge.PayChargeReq;
import top.itcat.bean.charge.RefundChargeReq;
import top.itcat.controller.action.ControllerHelper;
import top.itcat.entity.charge.ChargeItem;
import top.itcat.exception.CommonException;
import top.itcat.exception.EmptyResultException;
import top.itcat.exception.InternalException;
import top.itcat.exception.InvalidParamException;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.client.MedicalServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.diagnose.GetApplyItemRequest;
import top.itcat.rpc.service.diagnose.GetApplyItemResponse;
import top.itcat.rpc.service.diagnose.GetRegistrationRequest;
import top.itcat.rpc.service.diagnose.GetRegistrationResponse;
import top.itcat.rpc.service.medical.GetPrescriptionItemRequest;
import top.itcat.rpc.service.medical.GetPrescriptionItemResponse;
import top.itcat.rpc.service.model.*;
import top.itcat.rpc.service.order.*;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 收费相关controller
 * <p> 主要包括 缴费、退费、查询费用科目
 */
@RestController
@RequestMapping("/charge")
@RoleCheck
@Slf4j
public class ChargeController {
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;
    @Autowired
    private MedicalServiceClient medicalServiceClient;
    @Autowired
    private ControllerHelper controllerHelper;

    /**
     * 缴费
     *
     * @return 状态
     * @see PayChargeReq
     */
    @PostMapping("/pay")
    @RoleCheck(needLogin = false)
    public String payCharge(@LineConvertHump PayChargeReq req,
                            HttpServletRequest request) {
        PayChargeRequest payChargeRequest = new PayChargeRequest();
        long operatorId = 0;

        if (request.getAttribute("claims") != null) {
            operatorId = Long.valueOf((String) ((Claims) request.getAttribute("claims")).get("id"));
        }

        payChargeRequest.setMedicalRecordNo(req.getMedicalRecordNo());
        payChargeRequest.setOperatorId(operatorId);

        try {
            if (orderServiceClient.payCharge(payChargeRequest) == null) {
                return GetBaseResponUtil.getDefaultErrRspStr();
            }
        } catch (InvalidParamException e) {
            return GetBaseResponUtil.getBaseRspStr(400, "无可支付订单");
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 退费
     * 在此做退费项目是否可退判断
     *
     * @return
     * @see RefundChargeReq
     */
    @PostMapping("/refund")
    @RoleCheck(roles = {RoleEnum.Toll_Collector})
    public String refundCharge(@LineConvertHump RefundChargeReq req,
                               HttpServletRequest request) {
        long chargeItemId = req.getIds().get(0);
        GetChargeItemRequest getChargeItemRequest = new GetChargeItemRequest();
        getChargeItemRequest.setIds(Collections.singletonList(chargeItemId));
        GetChargeItemResponse getChargeItemResponse = orderServiceClient.getChargeItem(getChargeItemRequest);

        if (getChargeItemResponse == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        top.itcat.rpc.service.model.ChargeItem chargeItem = getChargeItemResponse.getBeanList().get(0);

        try {
            String result = canRefund(chargeItem);

            if (result != null) {
                return GetBaseResponUtil.getBaseRspStr(403, result);
            }
        } catch (CommonException e) {
            return GetBaseResponUtil.getBaseRspStr(500, e.getPromt());
        }

        CancelChargeRequest rpcReq = new CancelChargeRequest();
        long operatorId = Long.valueOf((String) ((Claims) request.getAttribute("claims")).get("id"));

        rpcReq.setChargeItemIds(req.getIds());
        rpcReq.setOperatorId(operatorId);

        try {
            if (orderServiceClient.cancelCharge(rpcReq) == null) {
                return GetBaseResponUtil.getDefaultErrRspStr();
            }
        } catch (EmptyResultException e) {
            return GetBaseResponUtil.getBaseRspStr(403, "该收费项目无法退款");
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询收费项目
     * 打包对应科室和患者信息
     *
     * @return 获取到的收费项目信息
     * @see GetChargeReq
     * @see GetChargeItemRequest
     */
    @RequestMapping("/get")
    @RoleCheck(needLogin = false)
    public String getCharges(GetChargeReq req,
                             HttpServletRequest request) {
        GetChargeItemRequest rpcReq = new GetChargeItemRequest();
//        long operatorId = Long.parseLong((String) ((Claims) request.getAttribute("claims")).get("id"));

        if (req.getMedicalRecordNo() != null) {
            rpcReq.setMedicalRecordNo(req.getMedicalRecordNo());
        }

        if (req.getOffset() != null) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.getLimit() != null) {
            rpcReq.setLimit(req.getLimit());
        }

        GetChargeItemResponse rsp = orderServiceClient.getChargeItem(rpcReq);

        if (rsp == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        } else if (rsp.getBeanListSize() == 0) {
            rsp.setBeanList(new ArrayList<>(0));
        }

        List<ChargeItem> chargeItems = controllerHelper.getChargeItem(rsp.getBeanList());

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = chargeItems.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 判断是否可以退费
     *
     * @param chargeItem 收费项目
     * @return bool
     * @throws InternalException
     * @see GetChargeSubjectRequest
     * @see ChargeSubject
     * @see GetPrescriptionItemRequest
     * @see GetApplyItemRequest
     * @see GetRegistrationRequest
     */
    private String canRefund(top.itcat.rpc.service.model.ChargeItem chargeItem) {
        if (chargeItem.getStatus() != ChargeItemStatusEnum.Paid) {
            return "无效账单";
        } else if (chargeItem.isDailyKnot()) {
            return "账单已日结";
        }

        GetChargeSubjectRequest getChargeSubjectRequest = new GetChargeSubjectRequest();
        getChargeSubjectRequest.setIds(Collections.singletonList(chargeItem.getChargeSubjectId()));
        GetChargeSubjectResponse getChargeSubjectResponse = orderServiceClient.getChargeSubject(getChargeSubjectRequest);

        if (getChargeSubjectResponse == null) {
            throw new InternalException();
        }

        ChargeSubject chargeSubject = getChargeSubjectResponse.getBeanList().get(0);
        CatalogEnum catalogEnum = chargeSubject.getCatalog();

        if (catalogEnum == CatalogEnum.Registration) {
//            GetRegistrationRequest getRegistrationRequest = new GetRegistrationRequest();
//            getRegistrationRequest.setIds(Collections.singletonList(chargeItem.getProjectId()));
//            GetRegistrationResponse getRegistrationResponse = diagnoseServiceClient.getRegistration(getRegistrationRequest);
//
//            if (getRegistrationResponse == null) {
//                throw new InternalException();
//            } else if (getRegistrationResponse.getBeanListSize() == 0) {
//                return "未找到对应挂号记录";
//            } else if (getRegistrationResponse.getBeanList().get(0).getStatus() != RegistrationStatusEnum.UnSeen) {
//                return "挂号已就诊";
//            }
            return "挂号账单不可单独退费";
        } else if (catalogEnum == CatalogEnum.Medical) {
            GetPrescriptionItemRequest getPrescriptionItemRequest = new GetPrescriptionItemRequest();
            getPrescriptionItemRequest.setCatalogEnum(CatalogEnum.Medical);
            getPrescriptionItemRequest.setChargeItemId(chargeItem.getId());
            GetPrescriptionItemResponse getPrescriptionItemResponse = medicalServiceClient.getPrescriptionItem(getPrescriptionItemRequest);

            if (getPrescriptionItemResponse == null) {
                throw new InternalException();
            } else if (getPrescriptionItemResponse.getPrescriptionItemsSize() == 0) {
                return "未找到对应处方";
            }

            PrescriptionItem prescriptionItem = getPrescriptionItemResponse.getPrescriptionItems().get(0);

            if (prescriptionItem.getStatus() == PrescriptionItemStatusEnum.Attained) {
                return "药品未退还";
            }
        } else if (catalogEnum == CatalogEnum.Nonmedical) {
            GetApplyItemRequest getApplyItemRequest = new GetApplyItemRequest();
            getApplyItemRequest.setMedicalRecordNo(chargeItem.getMedicalRecordNo());

            GetApplyItemResponse getApplyItemResponse = diagnoseServiceClient.getApplyItem(getApplyItemRequest);

            if (getApplyItemResponse == null) {
                throw new InternalException();
            } else if (getApplyItemResponse.getBeanListSize() == 0) {
                return "未找到对应申请单";
            }

            ApplyItem applyItem = null;

            for (ApplyItem item : getApplyItemResponse.getBeanList()) {
                if (item.getChargeItemId() == chargeItem.getId()) {
                    applyItem = item;
                    break;
                }
            }

            if (applyItem == null) {
                log.warn("获取申请单项失败,id:{}", chargeItem.getProjectId());
                return "未找到对应申请单";
            } else if (applyItem.getStatus() != ApplyItemStatus.Unregistered) {
                return "该申请已登记或完成";
            }
        } else if (catalogEnum == CatalogEnum.ChineseMedicine) {
            GetPrescriptionItemRequest getPrescriptionItemRequest = new GetPrescriptionItemRequest();
            getPrescriptionItemRequest.setCatalogEnum(CatalogEnum.ChineseMedicine);
            getPrescriptionItemRequest.setChargeItemId(chargeItem.getId());
            GetPrescriptionItemResponse getPrescriptionItemResponse = medicalServiceClient.getPrescriptionItem(getPrescriptionItemRequest);

            if (getPrescriptionItemResponse == null) {
                throw new InternalException();
            } else if (getPrescriptionItemResponse.getPrescriptionHerbItemsSize() == 0) {
                return "未找到对应处方";
            }

            PrescriptionHerbItem prescriptionItem = getPrescriptionItemResponse.getPrescriptionHerbItems().get(0);

            if (prescriptionItem.getStatus() == PrescriptionItemStatusEnum.Attained) {
                return "药品未退还";
            }
        } else {
            throw new InvalidParamException();
        }

        return null;
    }
}
