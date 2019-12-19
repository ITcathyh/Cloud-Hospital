package top.itcat.controller.diagnose.schedule;

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
import top.itcat.bean.diagnose.schedule.scheduleRule.AddScheduleRulesReq;
import top.itcat.bean.diagnose.schedule.scheduleRule.QueryScheduleRuleRequest;
import top.itcat.bean.diagnose.schedule.scheduleRule.UpdateScheduleRuleReq;
import top.itcat.entity.diagnose.ScheduleRule;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.diagnose.AddOrUpdateScheduleRuleRequest;
import top.itcat.rpc.service.diagnose.GetScheduleRuleRequest;
import top.itcat.rpc.service.diagnose.GetScheduleRuleResponse;
import top.itcat.rpc.service.hospital.GetDepartmentRequest;
import top.itcat.rpc.service.hospital.GetDepartmentResponse;
import top.itcat.rpc.service.user.MGetDoctorRequest;
import top.itcat.rpc.service.user.MGetDoctorResponse;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 排班规则Controller
 * <p> 主要包括 排班规则的增删改查
 */
@RestController
@RequestMapping("/diagnose/schedule/rule")
@Slf4j
public class ScheduleRuleController {
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private HospitalServiceClient hospitalServiceClient;

    /**
     * 增加排班规则
     * 请求方法：Post
     *
     * @return 状态
     * @see AddScheduleRulesReq
     */
    @PostMapping("/manage/add")
    public String addScheduleRule(@LineConvertHump AddScheduleRulesReq req) {
        AddOrUpdateScheduleRuleRequest rpcReq = new AddOrUpdateScheduleRuleRequest();
        rpcReq.setBeanList(req.getList()
                .parallelStream()
                .map(ScheduleRule::convertRPCBean)
                .collect(Collectors.toList()));
        diagnoseServiceClient.addOrUpdateScheduleRule(rpcReq);

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新排班规则
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateScheduleRuleReq
     */
    @PostMapping("/manage/update")
    public String updateScheduleRule(@LineConvertHump UpdateScheduleRuleReq req) {
        AddOrUpdateScheduleRuleRequest rpcReq = new AddOrUpdateScheduleRuleRequest();
        rpcReq.setBeanList(Collections.
                singletonList(ScheduleRule.convertRPCBean(req.getScheduleRule())));
        diagnoseServiceClient.addOrUpdateScheduleRule(rpcReq);

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除排班规则
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delScheduleRule(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.ScheduleRule rpcbean = new top.itcat.rpc.service.model.ScheduleRule();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdateScheduleRuleRequest rpcReq = new AddOrUpdateScheduleRuleRequest();
        rpcReq.setBeanList(Collections.singletonList(rpcbean));
        diagnoseServiceClient.addOrUpdateScheduleRule(rpcReq);
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询排班规则
     * 请求方法：Any
     *
     * @return 获取到的排班规则信息
     * @see QueryScheduleRuleRequest
     * @see ScheduleRule
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getScheduleRule(QueryScheduleRuleRequest req) {
        GetScheduleRuleRequest rpcReq = new GetScheduleRuleRequest();

        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }
        if (req.getDoctorId() != null) {
            rpcReq.setDoctorId(req.getDoctorId());
        }
//        if (req.getStartTime() != null) {
//            rpcReq.setStartTime(req.getStartTime());
//        }
//        if (req.getEndTime() != null) {
//            rpcReq.setEndTime(req.getEndTime());
//        }
        if (req.getDepartId() != null) {
            rpcReq.setDepartId(req.getDepartId());
        }

        rpcReq.setOnlyNormal(true);
        rpcReq.setNeedExpired(req.isNeedExpiredRule());
        GetScheduleRuleResponse rsp = diagnoseServiceClient.getScheduleRule(rpcReq);

        if (rsp == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        List<ScheduleRule> rules = rsp.getBeanList()
                .parallelStream()
                .filter(i -> i.getValid() != 2)
                .map(ScheduleRule::convert)
                .collect(Collectors.toList());

        MGetDoctorRequest mGetDoctorRequest = new MGetDoctorRequest();
        GetDepartmentRequest getDepartmentRequest = new GetDepartmentRequest();

        for (ScheduleRule rule : rules) {
            mGetDoctorRequest.setUids(Collections
                    .singletonList(rule.getDoctorId()));
            getDepartmentRequest.setIds(Collections
                    .singletonList(rule.getDepartmentId()));

            MGetDoctorResponse getDoctorResponse = userServiceClient.mGetDoctor(mGetDoctorRequest);
            GetDepartmentResponse getDepartmentResponse = hospitalServiceClient.getDepartment(getDepartmentRequest);

            if (getDepartmentResponse.getBeanListSize() != 0) {
                rule.setDepartName(getDepartmentResponse.getBeanList().get(0).getName());
            }

            if (getDoctorResponse.getUsersSize() != 0) {
                rule.setDoctorName(getDoctorResponse.getUsers().get(0).getUser().getRealName());
            }
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = rules.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return json.toJSONString();
    }
}