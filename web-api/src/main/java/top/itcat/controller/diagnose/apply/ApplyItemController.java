package top.itcat.controller.diagnose.apply;

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
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.CommonDelReq;
import top.itcat.bean.diagnose.apply.item.EnterApplyItemReq;
import top.itcat.bean.diagnose.apply.item.GetApplyItemReq;
import top.itcat.controller.action.ControllerHelper;
import top.itcat.entity.charge.ChargeItem;
import top.itcat.entity.charge.NonmedicalCharge;
import top.itcat.entity.diagnose.ApplyItem;
import top.itcat.exception.InternalException;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.diagnose.EnterApplyItemResultRequest;
import top.itcat.rpc.service.diagnose.GetApplyItemRequest;
import top.itcat.rpc.service.diagnose.GetApplyItemResponse;
import top.itcat.rpc.service.diagnose.UpdateApplyItemRequest;
import top.itcat.rpc.service.hospital.GetNonmedicalChargeRequest;
import top.itcat.rpc.service.hospital.GetNonmedicalChargeResponse;
import top.itcat.rpc.service.model.ApplyItemStatus;
import top.itcat.rpc.service.order.GetChargeItemRequest;
import top.itcat.rpc.service.order.GetChargeItemResponse;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 申请单项Controller
 * <p> 主要包括 申请单项的获取、录入结果、改变状态
 */

/**
 * todo test
 * 获取申请单项
 */
@RestController
@RequestMapping("/diagnose/apply/item")
@Slf4j
@RoleCheck
public class ApplyItemController {
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;
    @Autowired
    private HospitalServiceClient hospitalServiceClient;
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private ApplyHelper applyHelper;

    /**
     * 获取申请单项
     * 仅可以查看医技医生自己科室的申请单项
     *
     * @return 获取到的申请单项信息
     * @see GetApplyItemReq
     */
    @RequestMapping("/get")
    public String getApplyItem(GetApplyItemReq req,
                               HttpServletRequest request) {
        GetApplyItemRequest rpcReq = new GetApplyItemRequest();
        Claims claims = (Claims) request.getAttribute("claims");

        long operatorId = Long.parseLong((String) claims.get("id"));
        long departmentId = Long.parseLong((String) claims.get("departmentId"));

        rpcReq.setDepartmentId(departmentId);

        if (req.getMedicalRecordNo() != null) {
            rpcReq.setMedicalRecordNo(req.getMedicalRecordNo());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }

        GetApplyItemResponse getApplyItemResponse = diagnoseServiceClient.getApplyItem(rpcReq);

        if (getApplyItemResponse == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        } else if (getApplyItemResponse.getBeanListSize() == 0) {
            JSONObject jsonObject = GetBaseResponUtil.getSuccessJson();
            jsonObject.put("list", new JSONArray());
            jsonObject.put("total", 0);
            jsonObject.put("count", 0);
            return jsonObject.toJSONString();
        }

        final List<ApplyItem> applyItems = getApplyItemResponse.getBeanList()
                .parallelStream()
                .map(ApplyItem::convert)
                .collect(Collectors.toList());

        try {
            applyHelper.packApplyItem(applyItems);
        } catch (InternalException e) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = applyItems.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", getApplyItemResponse.getBeanListSize());
        json.put("total", getApplyItemResponse.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 录入申请单项结果
     * 请求方法：Post
     *
     * @return 状态
     * @see EnterApplyItemReq
     */
    @PostMapping("/enter")
    public String enter(@RequestBody EnterApplyItemReq req) {
        ApplyItem applyItem = req.getBean();
        applyItem.setOperateTime(System.currentTimeMillis());
        EnterApplyItemResultRequest rpcReq = new EnterApplyItemResultRequest();
        rpcReq.setItem(ApplyItem.convertRPCBean(applyItem));

        if (diagnoseServiceClient.enterApplyItemResult(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 改变申请单项状态
     * 请求方法：Post
     *
     * @return 状态
     * @see UpdateApplyItemRequest
     */
    @PostMapping("/check")
    public String check(@RequestBody CommonDelReq req,
                        HttpServletRequest request) {
        top.itcat.rpc.service.model.ApplyItem applyItem = new top.itcat.rpc.service.model.ApplyItem();
        Claims claims = (Claims) request.getAttribute("claims");
        long operatorId = Long.parseLong((String) claims.get("id"));

        applyItem.setId(req.getId());
        applyItem.setStatus(ApplyItemStatus.Registered);
        applyItem.setMedicalDoctorId(operatorId);
        applyItem.setOperateTime(System.currentTimeMillis());
        UpdateApplyItemRequest rpcReq = new UpdateApplyItemRequest();
        rpcReq.setItem(applyItem);

        if (diagnoseServiceClient.updateApplyItem(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }
}
