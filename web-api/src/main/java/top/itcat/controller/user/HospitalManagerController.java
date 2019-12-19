package top.itcat.controller.user;

import com.googlecode.protobuf.format.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.LineConvertHump;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.CommonDelReq;
import top.itcat.bean.CommonSearchReq;
import top.itcat.bean.user.AddHospitalManagersReq;
import top.itcat.bean.user.UpdateHospitalManagerReq;
import top.itcat.controller.user.pack.UserPack;
import top.itcat.pb_gen.api.user.manage.HospitalManagerManage;
import top.itcat.pb_gen.common.UserModel;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.model.RoleEnum;
import top.itcat.rpc.service.model.User;
import top.itcat.rpc.service.user.MGetHospitalManagerRequest;
import top.itcat.rpc.service.user.MGetHospitalManagerResponse;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 医院管理员Controller
 * <p> 主要包括 医院管理员的增删改查
 */

/**
 * Test Done
 */
@RestController
@RequestMapping("/hospitalmanager")
@RoleCheck(roles = {RoleEnum.Hospital_Manager})
public class HospitalManagerController {
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private UserPack userPack;

    /**
     * 增加医院管理员
     * 请求方法：Post
     *
     * @return 状态
     * @see AddHospitalManagersReq
     */
    @PostMapping("/manage/add")
    @RoleCheck(roles = {RoleEnum.Hospital_Manager})
    public String addHospitalManager(@LineConvertHump AddHospitalManagersReq req) {
        userServiceClient.addOrUpdateHospitalManager(req.getHospitalManagers().
                parallelStream().map(userPack::packHospitalManagerr).
                collect(Collectors.toList()));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新医院管理员
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateHospitalManagerReq
     */
    @PostMapping("/manage/update")
    @RoleCheck(roles = {RoleEnum.Hospital_Manager})
    public String updateHospitalManager(@LineConvertHump UpdateHospitalManagerReq req) {
        System.out.println(req.getHospitalManager().toString());
        userServiceClient.addOrUpdateHospitalManager(Collections.
                singletonList(userPack.packHospitalManagerr(req.getHospitalManager())));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除医院管理员
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see HospitalManagerManage.DelHospitalManagerRequest.Builder
     */
    @PostMapping("/manage/del")
    @RoleCheck(roles = {RoleEnum.Hospital_Manager})
    public String delHospitalManager(@RequestBody CommonDelReq req) {
        top.itcat.rpc.service.model.HospitalManager rpcbean = new top.itcat.rpc.service.model.HospitalManager();
        User user = new User();
        user.setId(req.getId());
        rpcbean.setUser(user);
        rpcbean.setValid(-1);

        userServiceClient.addOrUpdateHospitalManager(Collections.singletonList(rpcbean));
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询医院管理员
     * 请求方法：Any
     *
     * @return 获取到的医院管理员信息
     * @see MGetHospitalManagerRequest
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getHospitalManager(CommonSearchReq req) {
        MGetHospitalManagerRequest rpcReq = new MGetHospitalManagerRequest();

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

        MGetHospitalManagerResponse rsp = userServiceClient.mGetHospitalManager(rpcReq);
        HospitalManagerManage.GetHospitalManagerResponse.Builder builder = HospitalManagerManage.GetHospitalManagerResponse.newBuilder();
        builder.setTotal(rsp.getTotal())
                .setBase(GetBaseResponUtil.getSuccessRsp())
                .setCount(rsp.getUsersSize())
                .addAllList(rsp.getUsers()
                        .parallelStream()
                        .map(HospitalManagerController::convertPBBean)
                        .collect(Collectors.toList()));
        return JsonFormat.printToString(builder.build());
    }

    private static top.itcat.rpc.service.model.HospitalManager convertRPCBean(UserModel.HospitalManager bean,
                                                                              Map<String, Object> map) {
        top.itcat.rpc.service.model.HospitalManager rpcbean =
                new top.itcat.rpc.service.model.HospitalManager();
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

        if (user != null) {
            rpcbean.setUser(user);
        }

        return rpcbean;
    }

    private static UserModel.HospitalManager convertPBBean(top.itcat.rpc.service.model.HospitalManager rpcbean) {
        UserModel.HospitalManager.Builder builder = UserModel.HospitalManager.newBuilder();
        User user = rpcbean.getUser();

        if (user != null) {
            builder.setDepartmentId(user.getDepartId())
                    .setId(user.getId())
                    .setCode(user.getCode())
                    .setRealname(user.getRealName());
        }

        return builder.build();
    }
}
