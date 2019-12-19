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
import top.itcat.bean.CommonSearchReq;
import top.itcat.bean.charge.AddChargeSubjectsReq;
import top.itcat.bean.charge.UpdateChargeSubjectReq;
import top.itcat.entity.charge.ChargeSubject;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.order.AddOrUpdateChargeSubjectRequest;
import top.itcat.rpc.service.order.GetChargeSubjectRequest;
import top.itcat.rpc.service.order.GetChargeSubjectResponse;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 费用科目Controller
 * <p> 主要包括 费用科目的增删改查
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/charge/subject")
@Slf4j
@RoleCheck
public class ChargeSubjectController {
    @Autowired
    private OrderServiceClient orderServiceClient;

    /**
     * 增加费用科目
     * 请求方法：Post
     *
     * @return 状态
     * @see AddChargeSubjectsReq
     */
    @PostMapping("/manage/add")
    public String addChargeSubject(@LineConvertHump AddChargeSubjectsReq req) {
        AddOrUpdateChargeSubjectRequest rpcReq = new AddOrUpdateChargeSubjectRequest();
        rpcReq.setBeanList(req.getList()
                .parallelStream()
                .filter(Objects::nonNull)
                .peek(i -> i.setId(null))
                .map(ChargeSubject::convertRPCBean)
                .collect(Collectors.toList()));

        if (rpcReq.getBeanListSize() == 0) {
            return GetBaseResponUtil.getBaseRspStr(400, "请求有误");
        }

        if (orderServiceClient.addOrUpdateChargeSubject(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新费用科目
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateChargeSubjectReq
     */
    @PostMapping("/manage/update")
    public String updateChargeSubject(@LineConvertHump UpdateChargeSubjectReq req) {
        AddOrUpdateChargeSubjectRequest rpcReq = new AddOrUpdateChargeSubjectRequest();
        rpcReq.setBeanList(Collections.
                singletonList(ChargeSubject.convertRPCBean(req.getChargeSubject())));

        if (orderServiceClient.addOrUpdateChargeSubject(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除费用科目
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delChargeSubject(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.ChargeSubject rpcbean = new top.itcat.rpc.service.model.ChargeSubject();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdateChargeSubjectRequest rpcReq = new AddOrUpdateChargeSubjectRequest();
        rpcReq.setBeanList(Collections.singletonList(rpcbean));

        if (orderServiceClient.addOrUpdateChargeSubject(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询费用科目
     * 请求方法：Any
     *
     * @return 获取到的费用科目信息
     * @see GetChargeSubjectRequest
     * @see ChargeSubject
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getChargeSubject(CommonSearchReq req) {
        GetChargeSubjectRequest rpcReq = new GetChargeSubjectRequest();

        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        rpcReq.setSearchKey(req.getSearchKey());
        GetChargeSubjectResponse rsp = orderServiceClient.getChargeSubject(rpcReq);
        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = rsp.getBeanList().parallelStream()
                .map(ChargeSubject::convert)
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return json.toJSONString();
    }
}
