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
import top.itcat.bean.user.AddPharmacyManagersReq;
import top.itcat.bean.user.UpdatePharmacyManagerReq;
import top.itcat.controller.user.pack.UserPack;
import top.itcat.pb_gen.api.user.manage.PharmacyManagerManage;
import top.itcat.pb_gen.common.UserModel;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.model.RoleEnum;
import top.itcat.rpc.service.model.User;
import top.itcat.rpc.service.user.MGetPharmacyManagerRequest;
import top.itcat.rpc.service.user.MGetPharmacyManagerResponse;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 药房操作员Controller
 * <p> 主要包括 药房操作员的增删改查
 */

/**
 * Test Done
 */
@RestController
@RequestMapping("/pharmacymanager")
@RoleCheck(roles = {RoleEnum.Hospital_Manager})
public class PharmacyManagerController {
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private UserPack userPack;

    /**
     * 增加药房操作员
     * 请求方法：Post
     *
     * @return 状态
     * @see AddPharmacyManagersReq
     */
    @PostMapping("/manage/add")
    public String addPharmacyManager(@LineConvertHump AddPharmacyManagersReq req) {
        userServiceClient.addOrUpdatePharmacyManager(req.getPharmacyManagers().
                parallelStream().map(userPack::packPharmacyManager).
                collect(Collectors.toList()));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新药房操作员
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdatePharmacyManagerReq
     */
    @PostMapping("/manage/update")
    public String updatePharmacyManager(@LineConvertHump UpdatePharmacyManagerReq req) {
        userServiceClient.addOrUpdatePharmacyManager(Collections.
                singletonList(userPack.packPharmacyManager(req.getPharmacyManager())));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除药房操作员
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see PharmacyManagerManage.DelPharmacyManageragerRequest.Builder
     */
    @PostMapping("/manage/del")
    public String delPharmacyManager(@RequestBody CommonDelReq req) {
        top.itcat.rpc.service.model.PharmacyManager rpcbean = new top.itcat.rpc.service.model.PharmacyManager();
        User user = new User();
        user.setId(req.getId());
        rpcbean.setUser(user);
        rpcbean.setValid(-1);

        userServiceClient.addOrUpdatePharmacyManager(Collections.singletonList(rpcbean));
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询药房操作员
     * 请求方法：Any
     *
     * @return 获取到的药房操作员信息
     * @see MGetPharmacyManagerRequest
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getPharmacyManager(CommonSearchReq req) {
        MGetPharmacyManagerRequest rpcReq = new MGetPharmacyManagerRequest();

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

        MGetPharmacyManagerResponse rsp = userServiceClient.mGetPharmacyManager(rpcReq);
        PharmacyManagerManage.GetPharmacyManagerResponse.Builder builder = PharmacyManagerManage.GetPharmacyManagerResponse.newBuilder();
        builder.setTotal(rsp.getTotal())
                .setBase(GetBaseResponUtil.getSuccessRsp())
                .setCount(rsp.getUsersSize())
                .addAllList(rsp.getUsers().parallelStream().
                        map(PharmacyManagerController::convertPBBean).collect(Collectors.toList()));
        return JsonFormat.printToString(builder.build());
    }

    private static top.itcat.rpc.service.model.PharmacyManager convertRPCBean(UserModel.PharmacyManager bean,
                                                                              Map<String, Object> map) {
        top.itcat.rpc.service.model.PharmacyManager rpcbean =
                new top.itcat.rpc.service.model.PharmacyManager();
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

    private static UserModel.PharmacyManager convertPBBean(top.itcat.rpc.service.model.PharmacyManager rpcbean) {
        UserModel.PharmacyManager.Builder builder = UserModel.PharmacyManager.newBuilder();
        UserModel.BaseInfo.Builder baseBuilder = UserModel.BaseInfo.newBuilder();
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
