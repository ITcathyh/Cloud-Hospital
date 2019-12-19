package top.itcat.controller.charge;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.LineConvertHump;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.CommonSearchReq;
import top.itcat.entity.charge.BillingCategory;
import top.itcat.entity.charge.ChargeItem;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.hospital.GetBillingCategoryRequest;
import top.itcat.rpc.service.hospital.GetBillingCategoryResponse;
import top.itcat.rpc.service.order.GetChargeItemRequest;
import top.itcat.rpc.service.order.GetChargeItemResponse;
import top.itcat.util.GetBaseResponUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 收费单项Controller
 * <p> 主要包括 收费单项的获取
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/charge/chargeItem")
@Slf4j
@RoleCheck
public class ChargeItemController {
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private HospitalServiceClient hospitalServiceClient;

//    @PostMapping("/manage/add")
//    public String addChargeItem(@LineConvertHump AddChargeItemsReq req) {
//        System.out.println(req.getList());
//
//        AddOrUpdateChargeItemRequest rpcReq = new AddOrUpdateChargeItemRequest();
//        rpcReq.setBeanList(req.getList().
//                parallelStream().map(ChargeItem::convertRPCBean).
//                collect(Collectors.toList()));
//        orderServiceClient.addOrUpdateChargeItem(rpcReq);
//        return GetBaseResponUtil.getSuccessRspStr();
//    }
//
//    @PostMapping("/manage/update")
//    public String updateChargeItem(@LineConvertHump UpdateChargeItemReq req) {
//        System.out.println(req.getChargeItem());
//        AddOrUpdateChargeItemRequest rpcReq = new AddOrUpdateChargeItemRequest();
//        rpcReq.setBeanList(Collections.
//                singletonList(ChargeItem.convertRPCBean(req.getChargeItem())));
//        orderServiceClient.addOrUpdateChargeItem(rpcReq);
//        return GetBaseResponUtil.getSuccessRspStr();
//    }
//
//    @PostMapping("/manage/del")
//    public String delChargeItem(@LineConvertHump CommonDelReq req) {
//        top.itcat.rpc.service.model.ChargeItem rpcbean = new top.itcat.rpc.service.model.ChargeItem();
//        rpcbean.setValid(-1);
//        rpcbean.setId(req.getId());
//
//        AddOrUpdateChargeItemRequest rpcReq = new AddOrUpdateChargeItemRequest();
//        rpcReq.setBeanList(Collections.singletonList(rpcbean));
//        orderServiceClient.addOrUpdateChargeItem(rpcReq);
//        return GetBaseResponUtil.getSuccessRspStr();
//    }

    /**
     * 查询收费单项
     * 请求方法：Any
     *
     * @param req CommonSearchReq
     * @return 获取到的收费单项信息
     * @see GetChargeItemRequest
     * @see ChargeItem
     */
    @RequestMapping("/get")
    @RoleCheck
    public String getChargeItem(@LineConvertHump CommonSearchReq req) {
        GetChargeItemRequest rpcReq = new GetChargeItemRequest();
        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }
        GetChargeItemResponse rsp = orderServiceClient.getChargeItem(rpcReq);

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        List<ChargeItem> chargeItems = rsp.getBeanList().parallelStream()
                .map(ChargeItem::convert)
                .collect(Collectors.toList());
        //获得billingCategory_id
        List<Long> ids = chargeItems.parallelStream().map(ChargeItem::getBillingCategoryId).collect(Collectors.toList());

        GetBillingCategoryRequest request = new GetBillingCategoryRequest();
        request.ids = ids;
        //获得billingCategory 相应的billingCategory
        GetBillingCategoryResponse s = hospitalServiceClient.getBillingCategory(request);
        //将billingCategory 转化为<billingCategory_id>
        Map<Long, BillingCategory> billingCategoryMap = s.getBeanList().stream().collect(Collectors.toMap(top.itcat.rpc.service.model.BillingCategory::getId, a -> BillingCategory.convert(a), (k1, k2) -> k1));

        JSONArray jsonArray = chargeItems.parallelStream()
                .peek((chargeItem) -> chargeItem.setBillingCategory(billingCategoryMap.get(chargeItem.getBillingCategoryId())))
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }
}
