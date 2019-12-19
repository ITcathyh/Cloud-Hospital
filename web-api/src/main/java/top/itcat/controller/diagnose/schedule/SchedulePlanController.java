package top.itcat.controller.diagnose.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.LineConvertHump;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.CommonDelReq;
import top.itcat.bean.diagnose.schedule.schedulePlan.AddSchedulePlansReq;
import top.itcat.bean.diagnose.schedule.schedulePlan.QuerySchedulePlanRequest;
import top.itcat.bean.diagnose.schedule.schedulePlan.UpdateSchedulePlanReq;
import top.itcat.controller.action.ControllerHelper;
import top.itcat.entity.diagnose.SchedulePlan;
import top.itcat.entity.diagnose.ScheduleRule;
import top.itcat.exception.InvalidParamException;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.diagnose.AddOrUpdateSchedulePlanRequest;
import top.itcat.rpc.service.diagnose.GetSchedulePlanRequest;
import top.itcat.rpc.service.diagnose.GetSchedulePlanResponse;
import top.itcat.util.GetBaseResponUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 排班计划Controller
 * <p> 主要包括 排班计划的增删改查
 */
@RestController
@RequestMapping("/diagnose/schedule/plan")
@Slf4j
public class SchedulePlanController {
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private HospitalServiceClient hospitalServiceClient;
    @Autowired
    private ControllerHelper controllerHelper;

    /**
     * 增加排班计划
     * 请求方法：Post
     *
     * @return 状态
     * @see AddSchedulePlansReq
     */
    @PostMapping("/manage/add")
    public String addSchedulePlan(@LineConvertHump AddSchedulePlansReq req) {
        AddOrUpdateSchedulePlanRequest rpcReq = new AddOrUpdateSchedulePlanRequest();
        SchedulePlan plan = req.getList().get(0);
        ScheduleRule rule = plan.getScheduleRule();

        plan.setRemain(rule.getLimitNumber());
        rule.setStartTime(plan.getStartTime());
        rule.setEndTime(new DateTime(rule.getStartTime()).withHourOfDay(23).getMillis());
        rule.setOperationDate(Integer.valueOf(new DateTime().toString("yyyyMMdd")));
        rpcReq.setPlan(SchedulePlan.convertRPCBean(plan));
        rpcReq.setRule(ScheduleRule.convertRPCBean(rule));

        try {
            if (diagnoseServiceClient.addOrUpdateSchedulePlan(rpcReq) == null) {
                return GetBaseResponUtil.getDefaultErrRspStr();
            }
        } catch (InvalidParamException e) {
            return GetBaseResponUtil.getBaseRspStr(400, "无效请求");
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新排班计划
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateSchedulePlanReq
     */
    @PostMapping("/manage/update")
    public String updateSchedulePlan(@LineConvertHump UpdateSchedulePlanReq req) {
        AddOrUpdateSchedulePlanRequest rpcReq = new AddOrUpdateSchedulePlanRequest();
        req.getSchedulePlan().setRemain(null);
        rpcReq.setPlan(SchedulePlan.convertRPCBean(req.getSchedulePlan()));

        try {
            if (diagnoseServiceClient.addOrUpdateSchedulePlan(rpcReq) == null) {
                return GetBaseResponUtil.getDefaultErrRspStr();
            }
        } catch (InvalidParamException e) {
            return GetBaseResponUtil.getBaseRspStr(400, "无效请求");
        }
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除排班计划
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delSchedulePlan(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.SchedulePlan rpcbean = new top.itcat.rpc.service.model.SchedulePlan();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdateSchedulePlanRequest rpcReq = new AddOrUpdateSchedulePlanRequest();
        rpcReq.setPlan(rpcbean);
        try {
            if (diagnoseServiceClient.addOrUpdateSchedulePlan(rpcReq) == null) {
                return GetBaseResponUtil.getDefaultErrRspStr();
            }
        } catch (InvalidParamException e) {
            return GetBaseResponUtil.getBaseRspStr(400, "无效请求");
        }
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询排班计划
     * 请求方法：Any
     *
     * @return 获取到的排班计划信息
     * @see QuerySchedulePlanRequest
     * @see SchedulePlan
     */
    @RequestMapping("/get")
    @RoleCheck(needLogin = false)
    public String getSchedulePlan(QuerySchedulePlanRequest req) {
        GetSchedulePlanRequest rpcReq = new GetSchedulePlanRequest();

        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }
        if (req.getDoctorId() != null) {
            rpcReq.setDoctorId(req.getDoctorId());
        }
        if (req.getStartTime() != null) {
            rpcReq.setStartTime(req.getStartTime());
        }
        if (req.getEndTime() != null) {
            rpcReq.setEndTime(req.getEndTime());
        }
        if (req.getDepartId() != null) {
            rpcReq.setDepartId(req.getDepartId());
        }

        if (req.getStartTime() == null && req.getEndTime() == null) {
            rpcReq.setCurTime(new DateTime().withMillisOfDay(0).getMillis());
        }

        GetSchedulePlanResponse rsp = diagnoseServiceClient.getSchedulePlan(rpcReq);

        if (rsp.getBeanList() == null || rsp.getBeanListSize() == 0) {
            JSONObject json = GetBaseResponUtil.getSuccessJson();
            json.put("list", new JSONArray());
            json.put("count", rsp.getBeanListSize());
            json.put("total", rsp.getTotal());
            return json.toJSONString();
        }

        List<SchedulePlan> schedulePlans = controllerHelper.packSchedulePlan(rsp.getBeanList());

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = schedulePlans
                .parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }
}