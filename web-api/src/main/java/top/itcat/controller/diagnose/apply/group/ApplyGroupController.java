package top.itcat.controller.diagnose.apply.group;

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
import top.itcat.bean.diagnose.apply.group.AddApplyGroupsReq;
import top.itcat.bean.diagnose.apply.group.QueryApplyGroupRequest;
import top.itcat.bean.diagnose.apply.group.UpdateApplyGroupReq;
import top.itcat.entity.charge.NonmedicalCharge;
import top.itcat.entity.diagnose.ApplyGroup;
import top.itcat.entity.diagnose.ApplyItemTemplate;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.service.diagnose.AddOrUpdateApplyGroupRequest;
import top.itcat.rpc.service.diagnose.AddOrUpdateApplyGroupResponse;
import top.itcat.rpc.service.diagnose.GetApplyGroupRequest;
import top.itcat.rpc.service.diagnose.GetApplyGroupResponse;
import top.itcat.rpc.service.hospital.GetNonmedicalChargeRequest;
import top.itcat.rpc.service.hospital.GetNonmedicalChargeResponse;
import top.itcat.rpc.service.model.ApplyCategory;
import top.itcat.rpc.service.model.SuitableRangeEnum;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 申请组套Controller
 * <p> 主要包括 申请组套的增删改查
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/diagnose/apply/group")
@Slf4j
@RoleCheck
public class ApplyGroupController {
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;
    @Autowired
    private HospitalServiceClient hospitalServiceClient;

    /**
     * 增加申请组套
     * 请求方法：Post
     *
     * @return 状态
     * @see AddApplyGroupsReq
     */
    @PostMapping("/manage/add")
    public String addApplyGroup(@LineConvertHump AddApplyGroupsReq req,
                                HttpServletRequest request) {
        ApplyGroup applyGroup = req.getList().get(0);
        Claims claims = ((Claims) request.getAttribute("claims"));

        applyGroup.setDepartmentId(Long.valueOf((String) claims.get("departmentId")));
        applyGroup.setCreatorId(Long.valueOf((String) claims.get("id")));

        AddOrUpdateApplyGroupRequest rpcReq = new AddOrUpdateApplyGroupRequest();
        rpcReq.setBean(ApplyGroup.convertRPCBean(applyGroup));

        if (diagnoseServiceClient.addOrUpdateApplyGroup(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新申请组套
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateApplyGroupReq
     */
    @PostMapping("/manage/update")
    public String updateApplyGroup(@LineConvertHump UpdateApplyGroupReq req) {
        AddOrUpdateApplyGroupRequest rpcReq = new AddOrUpdateApplyGroupRequest();
        rpcReq.setBean(ApplyGroup.convertRPCBean(req.getApplyGroup()));
        diagnoseServiceClient.addOrUpdateApplyGroup(rpcReq);

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除申请组套
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delApplyGroup(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.ApplyGroup rpcbean = new top.itcat.rpc.service.model.ApplyGroup();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdateApplyGroupRequest rpcReq = new AddOrUpdateApplyGroupRequest();
        rpcReq.setBean(rpcbean);
        AddOrUpdateApplyGroupResponse rsp = diagnoseServiceClient.addOrUpdateApplyGroup(rpcReq);

        if (rsp == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        } else if (rsp.getBaseResp().getStatusCode() != 0) {
            return GetBaseResponUtil.getBaseRspStr(403, rsp.getBaseResp().getStatusMessage());
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询申请组套
     * 请求方法：Any
     *
     * @return 获取到的申请组套信息
     * @see QueryApplyGroupRequest
     * @see ApplyGroup
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getApplyGroup(QueryApplyGroupRequest req,
                                HttpServletRequest request) {
        GetApplyGroupRequest rpcReq = new GetApplyGroupRequest();
        rpcReq.setIds(req.getIds());

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.getSearchKey() != null) {
            rpcReq.setSearchKey(req.getSearchKey());
        }

        if (req.getSuitableRange() != null) {
            SuitableRangeEnum rangeEnum = SuitableRangeEnum.findByValue(req.getSuitableRange());
            rpcReq.setRange(Collections.singletonList(rangeEnum));

            if (rangeEnum == SuitableRangeEnum.Personal) {
                Claims claims = ((Claims) request.getAttribute("claims"));
                rpcReq.setCreaterId(Long.valueOf((String) claims.get("id")));
            } else if (rangeEnum == SuitableRangeEnum.Depart) {
                Claims claims = ((Claims) request.getAttribute("claims"));
                rpcReq.setDepartmentId(Long.valueOf((String) claims.get("departmentId")));
            }
        }

        if (req.getCategory() != null) {
            rpcReq.setCategory(ApplyCategory.findByValue(req.getCategory()));
        }

        GetApplyGroupResponse rsp = diagnoseServiceClient.getApplyGroup(rpcReq);
        List<ApplyGroup> applyGroups = rsp.getBeanList()
                .parallelStream()
                .map(ApplyGroup::convert)
                .collect(Collectors.toList());
        Set<Long> nonmedicalIds = new HashSet<>();

        // 查询具体申请项目
        for (ApplyGroup applyGroup : applyGroups) {
            if (applyGroup.getItems() != null) {
                for (ApplyItemTemplate applyItemTemplate : applyGroup.getItems()) {
                    nonmedicalIds.add(applyItemTemplate.getNonmedicalId());
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
                    .collect(Collectors.toMap(NonmedicalCharge::getId,
                            nonmedicalCharge -> nonmedicalCharge));

            for (ApplyGroup applyGroup : applyGroups) {
                if (applyGroup.getItems() == null) {
                    continue;
                }

                for (ApplyItemTemplate applyItemTemplate : applyGroup.getItems()) {
                    NonmedicalCharge nonmedicalCharge = nonmedicalChargeMap.get(applyItemTemplate.getNonmedicalId());
                    if (nonmedicalCharge != null) {
                        applyItemTemplate.setNonmedicalCharge(nonmedicalChargeMap.get(applyItemTemplate.getNonmedicalId()));
                    }
                }
            }
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = applyGroups.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }
}
