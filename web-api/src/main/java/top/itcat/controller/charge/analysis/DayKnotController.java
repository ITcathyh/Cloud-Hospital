package top.itcat.controller.charge.analysis;

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
import top.itcat.bean.charge.dayknot.*;
import top.itcat.controller.action.ControllerHelper;
import top.itcat.entity.charge.ChargeItem;
import top.itcat.entity.charge.DayKnot;
import top.itcat.entity.charge.DayKnotItem;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.model.ChargeItemStatusEnum;
import top.itcat.rpc.service.model.RoleEnum;
import top.itcat.rpc.service.order.*;
import top.itcat.rpc.service.user.MGetTollCollectorRequest;
import top.itcat.rpc.service.user.MGetTollCollectorResponse;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 日结Controller
 * <p> 主要包括 获取日结信息、获取当前收费员可日结的收费项目、日结、获取日结单项
 */

@RestController
@RequestMapping("/charge/dayknot")
@Slf4j
@RoleCheck
public class DayKnotController {
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private HospitalServiceClient hospitalServiceClient;
    @Autowired
    private ControllerHelper controllerHelper;

    /**
     * 获取日结
     * 请求方法：Any
     *
     * @return 获取到的dayKnot信息 String
     * @see GetDayKnotReq
     * @see DayKnot
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getDayKnot(GetDayKnotReq req,
                             HttpServletRequest servletRequest) {
        GetDayKnotRequest rpcReq = new GetDayKnotRequest();
        Claims claims = (Claims) servletRequest.getAttribute("claims");

        if (((int) claims.get("role")) == RoleEnum.Toll_Collector.getValue()) {
            rpcReq.setTollCollectorId(Long.parseLong((String) claims.get("id")));
        }

        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.getStartTime() != null) {
            rpcReq.setStartTime(req.getStartTime());
        }

        if (req.getEndTime() != null) {
            rpcReq.setEndTime(req.getEndTime());
        } else {
            rpcReq.setEndTime(System.currentTimeMillis());
        }

        GetDayKnotResponse rsp = orderServiceClient.getDayKnot(rpcReq);
        JSONObject json = GetBaseResponUtil.getSuccessJson();
        List<DayKnot> dayKnots = rsp.getBeanList().parallelStream()
                .map(DayKnot::convert)
                .collect(Collectors.toList());
        //获得dayKnot_id
        List<Long> ids = dayKnots
                .parallelStream()
                .map(DayKnot::getOperatorId)
                .collect(Collectors.toList());

        MGetTollCollectorRequest request = new MGetTollCollectorRequest();
        request.setUids(ids);
        //获得dayKnot 相应的dayKnot
        MGetTollCollectorResponse s = userServiceClient.mGetTollCollector(request);
        //将dayKnot 转化为<dayKnot_id,dayKnot_name>
        Map<Long, String> dayKnotMap = s.getUsers().parallelStream().collect(Collectors.toMap(a -> a.getUser().getId(), a -> a.getUser().getRealName(), (k1, k2) -> k1));

        JSONArray jsonArray = dayKnots.parallelStream()
                .peek((dayKnot) -> dayKnot.setOperatorName(dayKnotMap.get(dayKnot.getOperatorId())))
                .collect(Collectors.toCollection(JSONArray::new));
        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 获取当前收费员可日结的收费项目
     * 请求方法：Any
     *
     * @return 获取到的chargeItem信息 String
     * @see GetDayKnotChargeReq
     * @see DayKnot
     */
    @RequestMapping("/charge/get")
    @RoleCheck()
    public String getDayKnotCharge(GetDayKnotChargeReq req,
                                   HttpServletRequest request) {
        if (req.getEndTime() == null) {
            return GetBaseResponUtil.getBaseRspStr(400,"时间为空");
        }

        long operatorId = Long.parseLong((String) ((Claims) request.getAttribute("claims")).get("id"));
        GetChargeItemRequest rpcReq = new GetChargeItemRequest();
        rpcReq.setEndTime(req.getEndTime());
        rpcReq.setOperatorId(operatorId);
        rpcReq.setCanDayKnot(true);

        if (req.getOffset() != null) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.getLimit() != null) {
            rpcReq.setLimit(req.getLimit());
        }

        GetChargeItemResponse rsp = orderServiceClient.getChargeItem(rpcReq);

        if (rsp == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        List<ChargeItem> chargeItems = controllerHelper.getChargeItem(rsp.getBeanList().parallelStream()
                .filter(i -> i.getStatus() != ChargeItemStatusEnum.Unpaid)
                .collect(Collectors.toList()),true);

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = chargeItems.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 日结
     * 请求方法：Any
     *
     * @return 日结信息 String
     * @see SettleChargeReq
     */
    @PostMapping("/settle")
    @RoleCheck
    public String settle(@LineConvertHump SettleChargeReq req,
                         HttpServletRequest request) {
        SettleChargeRequest settleChargeRequest = new SettleChargeRequest();
        long operatorId = Long.valueOf((String) ((Claims) request.getAttribute("claims")).get("id"));

        settleChargeRequest.setEndTime(req.getEndTime());
        settleChargeRequest.setOperatorId(operatorId);

        orderServiceClient.settleCharge(settleChargeRequest);
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 获取日结单项
     * 请求方法：Any
     *
     * @return 获取到的dayKnot单项信息 String
     * @see GetDayKnotItemReq
     * @see DayKnotItem
     */
    @RequestMapping("/item/get")
    @RoleCheck
    public String getDayKnotItem(GetDayKnotItemReq req,
                                 HttpServletRequest request) {
        if (req.getDayKnotId() == null) {
            return GetBaseResponUtil.getBaseRspStr(400, "请求参数有误");
        }

        GetDayKnotItemRequest rpcReq = new GetDayKnotItemRequest();
//        long operatorId = Long.valueOf((String) ((Claims) request.getAttribute("claims")).get("id"));

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.getIds() != null && !req.getIds().isEmpty()) {
            rpcReq.setIds(req.getIds());
        }

        rpcReq.setDayKnotId(req.getDayKnotId());
        GetDayKnotItemResponse rsp = orderServiceClient.getDayKnotItem(rpcReq);

        if (rsp == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        List<DayKnotItem> dayKnotItems = rsp.getBeanList()
                .parallelStream()
                .map(DayKnotItem::convert)
                .collect(Collectors.toList());

        List<Long> chargeIds = dayKnotItems.parallelStream()
                .map(DayKnotItem::getChargeItemId)
                .distinct()
                .collect(Collectors.toList());
        GetChargeItemRequest getChargeItemRequest = new GetChargeItemRequest();
        getChargeItemRequest.setIds(chargeIds);
        GetChargeItemResponse getChargeItemResponse = orderServiceClient.getChargeItem(getChargeItemRequest);

        if (getChargeItemResponse == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        Map<Long, ChargeItem> chargeItemMap = controllerHelper.getChargeItem(getChargeItemResponse.getBeanList(), true)
                .parallelStream()
                .collect(Collectors.toMap(ChargeItem::getId, i -> i));

        for (DayKnotItem dayKnotItem : dayKnotItems) {
            dayKnotItem.setChargeItem(chargeItemMap.get(dayKnotItem.getChargeItemId()));
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = dayKnotItems.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }

    @PostMapping("/check")
    public String check(@LineConvertHump CheckDayKnotReq req) {
        top.itcat.rpc.service.model.DayKnot dayKnot = new top.itcat.rpc.service.model.DayKnot();
        dayKnot.setId(req.getDayKnotId());
        dayKnot.setCheckThrough(1);

        AddOrUpdateDayKnotRequest rpcReq = new AddOrUpdateDayKnotRequest();
        rpcReq.setBeanList(Collections.singletonList(dayKnot));

        if (orderServiceClient.addOrUpdateDayKnot(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }
}
