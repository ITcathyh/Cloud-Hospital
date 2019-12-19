package top.itcat.controller.charge.billing;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.LineConvertHump;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.CommonDelReq;
import top.itcat.bean.CommonSearchReq;
import top.itcat.bean.charge.AddBillingCategorysReq;
import top.itcat.bean.charge.UpdateBillingCategoryReq;
import top.itcat.entity.charge.BillingCategory;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.service.hospital.GetBillingCategoryRequest;
import top.itcat.rpc.service.hospital.GetBillingCategoryResponse;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * 结算类别controller
 * <p> 主要包括 结算类别的增删改查
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/charge/billingcategory")
@Slf4j
public class BillingCategoryController {
    @Autowired
    private HospitalServiceClient hospitalServiceClient;

    /**
     * 增加结算类别
     * 请求方法：Post
     *
     * @return 状态
     * @see AddBillingCategorysReq
     */
    @PostMapping("/manage/add")
    public String addBillingCategory(@LineConvertHump AddBillingCategorysReq req) {
        hospitalServiceClient.addOrUpdateBillingCategory(req.getList().
                parallelStream().map(BillingCategory::convertRPCBean).
                collect(Collectors.toList()));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新结算类别
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateBillingCategoryReq
     */
    @PostMapping("/manage/update")
    public String updateBillingCategory(@LineConvertHump UpdateBillingCategoryReq req) {
        hospitalServiceClient.addOrUpdateBillingCategory(Collections.
                singletonList(BillingCategory.convertRPCBean(req.getBillingCategory())));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除结算类别
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delBillingCategory(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.BillingCategory rpcbean = new top.itcat.rpc.service.model.BillingCategory();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        hospitalServiceClient.addOrUpdateBillingCategory(Collections.singletonList(rpcbean));
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询结算类别
     * 请求方法：Any
     *
     * @return 获取到的结算类别信息
     * @see GetBillingCategoryRequest
     * @see BillingCategory
     */
    @RequestMapping("/get")
    @RoleCheck(needLogin = false)
    public String getBillingCategory(CommonSearchReq req) {
        GetBillingCategoryRequest rpcReq = new GetBillingCategoryRequest();

        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }

        GetBillingCategoryResponse rsp = hospitalServiceClient.getBillingCategory(rpcReq);
        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = rsp.getBeanList().parallelStream()
                .map(BillingCategory::convert)
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return json.toJSONString();
    }
}