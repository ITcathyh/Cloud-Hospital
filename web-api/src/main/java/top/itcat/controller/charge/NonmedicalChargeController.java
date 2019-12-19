package top.itcat.controller.charge;

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
import top.itcat.bean.charge.nonmedical.AddNonmedicalChargesReq;
import top.itcat.bean.charge.nonmedical.GetNonmedicalChargeReq;
import top.itcat.bean.charge.nonmedical.UpdateNonmedicalChargeReq;
import top.itcat.entity.charge.NonmedicalCharge;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.service.hospital.GetNonmedicalChargeRequest;
import top.itcat.rpc.service.hospital.GetNonmedicalChargeResponse;
import top.itcat.rpc.service.model.NonmedicalChargeCategoryEnum;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * 非药品收费项目Controller
 * <p> 主要包括 非药品收费项目的增删改查
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/medical/charges/nonmedical")
@Slf4j
public class NonmedicalChargeController {
    @Autowired
    private HospitalServiceClient hospitalServiceClient;

    /**
     * 增加非药品收费项目
     * 请求方法：Post
     *
     * @return 状态
     * @see AddNonmedicalChargesReq
     */
    @PostMapping("/manage/add")
    public String addNonmedicalCharge(@LineConvertHump AddNonmedicalChargesReq req) {
        hospitalServiceClient.addOrUpdateNonmedicalCharge(req.getList().
                parallelStream().map(NonmedicalCharge::convertRPCBean).
                collect(Collectors.toList()));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新非药品收费项目
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateNonmedicalChargeReq
     */
    @PostMapping("/manage/update")
    public String updateNonmedicalCharge(@LineConvertHump UpdateNonmedicalChargeReq req) {
        hospitalServiceClient.addOrUpdateNonmedicalCharge(Collections.
                singletonList(NonmedicalCharge.convertRPCBean(req.getNonmedicalCharge())));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除非药品收费项目
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delNonmedicalCharge(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.NonmedicalCharge rpcbean = new top.itcat.rpc.service.model.NonmedicalCharge();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        hospitalServiceClient.addOrUpdateNonmedicalCharge(Collections.singletonList(rpcbean));
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询非药品收费项目
     * 请求方法：Any
     *
     * @return 获取到的非药品收费项目信息
     * @see GetNonmedicalChargeReq
     * @see NonmedicalCharge
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getNonmedicalCharge(GetNonmedicalChargeReq req) {
        GetNonmedicalChargeRequest rpcReq = new GetNonmedicalChargeRequest();
        if (req.getIds() != null && !req.getIds().isEmpty()) {
            rpcReq.setIds(req.getIds());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.getCategory() != null) {
            rpcReq.setCategory(NonmedicalChargeCategoryEnum.findByValue(req.getCategory()));
        }

        rpcReq.setSearchKey(req.getSearchKey());

        GetNonmedicalChargeResponse rsp = hospitalServiceClient.getNonmedicalCharge(rpcReq);

        if (rsp.getBeanListSize() == 0) {
            JSONObject json = GetBaseResponUtil.getSuccessJson();
            json.put("list", new JSONArray());
            json.put("count", rsp.getBeanListSize());
            json.put("total", rsp.getTotal());
            return json.toJSONString();
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = rsp.getBeanList().parallelStream()
                .map(NonmedicalCharge::convert)
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return json.toJSONString();
    }
}