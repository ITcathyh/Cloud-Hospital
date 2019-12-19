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
import top.itcat.bean.user.AddMedicalDoctorsReq;
import top.itcat.bean.user.DoctorSearchReq;
import top.itcat.bean.user.UpdateMedicalDoctorReq;
import top.itcat.controller.user.pack.UserPack;
import top.itcat.entity.user.MedicalDoctor;
import top.itcat.pb_gen.api.user.manage.DoctorManage;
import top.itcat.pb_gen.common.UserModel;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.model.DoctorTitleEnum;
import top.itcat.rpc.service.model.RoleEnum;
import top.itcat.rpc.service.model.User;
import top.itcat.rpc.service.user.MGetMedicalDoctorRequest;
import top.itcat.rpc.service.user.MGetMedicalDoctorResponse;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 医技医生Controller
 * <p> 主要包括 医技医生的增删改查
 */
@RestController
@RequestMapping("/doctor/medical")
@RoleCheck(roles = {RoleEnum.Hospital_Manager})
public class MedicalDoctorController {
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private UserPack userPack;

    /**
     * 增加医技医生
     * 请求方法：Post
     *
     * @return 状态
     * @see AddMedicalDoctorsReq
     */
    @PostMapping("/manage/add")
    public String addMedicalDoctor(@LineConvertHump AddMedicalDoctorsReq req) {
        userServiceClient.addOrUpdateMedicalDoctor(req.getMedicalDoctors().
                parallelStream().map(userPack::packMedicalDoctor).
                collect(Collectors.toList()));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新医技医生
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateMedicalDoctorReq
     */
    @PostMapping("/manage/update")
    public String updateMedicalDoctor(@LineConvertHump UpdateMedicalDoctorReq req) {
        userServiceClient.addOrUpdateMedicalDoctor(Collections.
                singletonList(userPack.packMedicalDoctor(req.getDoctor())));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除医技医生
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see DoctorManage.DelMedicalDoctorRequest.Builder
     */
    @PostMapping("/manage/del")
    public String delMedicalDoctor(@RequestBody CommonDelReq req) {
        top.itcat.rpc.service.model.MedicalDoctor rpcbean = new top.itcat.rpc.service.model.MedicalDoctor();
        User user = new User();
        user.setId(req.getId());
        rpcbean.setUser(user);
        rpcbean.setValid(-1);

        userServiceClient.addOrUpdateMedicalDoctor(Collections.singletonList(rpcbean));
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询医技医生
     * 请求方法：Any
     *
     * @return 获取到的医技医生信息
     * @see MGetMedicalDoctorRequest
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getMedicalDoctor(DoctorSearchReq req) {
        MGetMedicalDoctorRequest rpcReq = new MGetMedicalDoctorRequest();

        if (req.getIds() != null && !req.getIds().isEmpty()) {
            rpcReq.setUids(req.getIds());
        }

        rpcReq.setSearchKey(req.getSearchKey());
        rpcReq.setTitle(req.getTitle() == null ? null : DoctorTitleEnum.findByValue(req.getTitle()));

        if (req.getDepartmentId() != null) {
            rpcReq.setDepartId(req.getDepartmentId());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        MGetMedicalDoctorResponse rsp = userServiceClient.mGetMedicalDoctor(rpcReq);
        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = rsp.getUsers()
                .parallelStream()
                .map(MedicalDoctor::convertMedicalDoctor)
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getUsersSize());
        json.put("total", rsp.getTotal());
        return json.toJSONString();
    }

    private static top.itcat.rpc.service.model.MedicalDoctor convertRPCBean(UserModel.MedicalDoctor bean,
                                                                            Map<String, Object> map) {
        top.itcat.rpc.service.model.MedicalDoctor rpcbean =
                new top.itcat.rpc.service.model.MedicalDoctor();
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
        rpcbean.setTitle(DoctorTitleEnum.findByValue(bean.getTitle()));

        return rpcbean;
    }

}
