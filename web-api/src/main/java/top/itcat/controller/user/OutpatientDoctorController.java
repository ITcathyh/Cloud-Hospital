package top.itcat.controller.user;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.LineConvertHump;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.CommonDelReq;
import top.itcat.bean.user.AddOutpatientDoctorsReq;
import top.itcat.bean.user.DoctorSearchReq;
import top.itcat.bean.user.UpdateOutpatientDoctorReq;
import top.itcat.controller.user.pack.UserPack;
import top.itcat.entity.user.OutpatientDoctor;
import top.itcat.pb_gen.api.user.manage.DoctorManage;
import top.itcat.pb_gen.common.UserModel;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.model.DoctorTitleEnum;
import top.itcat.rpc.service.model.RoleEnum;
import top.itcat.rpc.service.model.User;
import top.itcat.rpc.service.user.MGetDoctorRequest;
import top.itcat.rpc.service.user.MGetDoctorResponse;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 门诊医生Controller
 * <p> 主要包括 门诊医生的增删改查
 */
@RestController
@RequestMapping("/doctor/outpatient")
@RoleCheck(roles = {RoleEnum.Hospital_Manager})
public class OutpatientDoctorController {
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private UserPack userPack;

    /**
     * 增加门诊医生
     * 请求方法：Post
     *
     * @return 状态
     * @see AddOutpatientDoctorsReq
     */
    @PostMapping("/manage/add")
    public String addDoctor(@LineConvertHump AddOutpatientDoctorsReq req) {
        System.out.println(req.getOutpatientDoctors().toString());
        userServiceClient.addOrUpdateDoctor(req.getOutpatientDoctors().
                parallelStream().map(userPack::packOutpatientDoctor).
                collect(Collectors.toList()));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新门诊医生
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateOutpatientDoctorReq
     */
    @PostMapping("/manage/update")
    public String updateDoctor(@LineConvertHump UpdateOutpatientDoctorReq req) {
        userServiceClient.addOrUpdateDoctor(Collections.
                singletonList(userPack.packOutpatientDoctor(req.getDoctor())));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除门诊医生
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see DoctorManage.DelOutpatientDoctorRequest.Builder
     */
    @PostMapping("/manage/del")
    public String delDoctor(@RequestBody CommonDelReq req) {
        top.itcat.rpc.service.model.OutpatientDoctor rpcbean = new top.itcat.rpc.service.model.OutpatientDoctor();
        User user = new User();
        user.setId(req.getId());
        rpcbean.setUser(user);
        rpcbean.setValid(-1);

        userServiceClient.addOrUpdateDoctor(Collections.singletonList(rpcbean));
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询门诊医生
     * 请求方法：Any
     *
     * @return 获取到的门诊医生信息
     * @see MGetDoctorRequest
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getDoctor(DoctorSearchReq req) {
        MGetDoctorRequest rpcReq = new MGetDoctorRequest();

        if (req.getIds() != null && !req.getIds().isEmpty()) {
            rpcReq.setUids(req.getIds());
        }

        rpcReq.setSearchKey(req.getSearchKey());

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        rpcReq.setTitle(req.getTitle() == null ? null : DoctorTitleEnum.findByValue(req.getTitle()));

        if (req.getDepartmentId() != null) {
            rpcReq.setDepartId(req.getDepartmentId());
        }

//        if (req.getInSchedule() != null) {
//            rpcReq.setInSchedule(req.getInSchedule());
//        }

        MGetDoctorResponse rsp = userServiceClient.mGetDoctor(rpcReq);
        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = rsp.getUsers()
                .parallelStream()
                .map(OutpatientDoctor::convertOutpatientDoctor)
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getUsersSize());
        json.put("total", rsp.getTotal());
        return json.toJSONString();
    }

    private static top.itcat.rpc.service.model.OutpatientDoctor convertRPCBean(UserModel.OutpatientDoctor bean,
                                                                               Map<String, Object> map) {
        top.itcat.rpc.service.model.OutpatientDoctor rpcbean =
                new top.itcat.rpc.service.model.OutpatientDoctor();
        User user = new User();

        if (bean.getId() > 0) {
            user.setId(bean.getId());
        }

        if (!StringUtils.isEmpty(bean.getCode())) {
            user.setCode(bean.getCode());
        }

        if (bean.getDepartmentId() > 0) {
            user.setDepartId(bean.getDepartmentId());
        }

        if (!StringUtils.isEmpty(bean.getPassword())) {
            user.setPassword(bean.getPassword());
        }

        if (!StringUtils.isEmpty(bean.getRealname())) {
            user.setRealName(bean.getRealname());
        }

        rpcbean.setUser(user);
        rpcbean.setInSchedual(bean.getInSchedule());
        rpcbean.setTitle(DoctorTitleEnum.findByValue(bean.getTitle()));

        return rpcbean;
    }
}