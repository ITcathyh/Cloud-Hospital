package top.itcat.controller.user;

import com.googlecode.protobuf.format.JsonFormat;
import lombok.extern.slf4j.Slf4j;
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
import top.itcat.bean.user.AddAccountClerksReq;
import top.itcat.bean.user.UpdateAccountClerkReq;
import top.itcat.controller.user.pack.UserPack;
import top.itcat.pb_gen.api.user.manage.AccountClerkManage;
import top.itcat.pb_gen.common.UserModel;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.model.RoleEnum;
import top.itcat.rpc.service.model.User;
import top.itcat.rpc.service.user.MGetAccountClerkRequest;
import top.itcat.rpc.service.user.MGetAccountClerkResponse;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 财务管理员Controller
 * <p> 主要包括 财务管理员的增删改查
 */

/**
 * Test Done
 */
@RestController
@RequestMapping("/accountclerk")
@Slf4j
@RoleCheck(roles = {RoleEnum.Hospital_Manager})
public class AccountClerkController {
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private UserPack userPack;

    /**
     * 增加财务管理员
     * 请求方法：Post
     *
     * @return 状态
     * @see AddAccountClerksReq
     */
    @PostMapping("/manage/add")
    public String addAccountClerk(@LineConvertHump AddAccountClerksReq req) {
        userServiceClient.addOrUpdateAccountClerk(req.getAccountClerks()
                .parallelStream()
                .map(userPack::packAccountClerk)
                .collect(Collectors.toList()));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新财务管理员
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateAccountClerkReq
     */
    @PostMapping("/manage/update")
    public String updateAccountClerk(@LineConvertHump UpdateAccountClerkReq req) {
        userServiceClient.addOrUpdateAccountClerk(Collections.
                singletonList(userPack.packAccountClerk(req.getAccountClerk())));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除财务管理员
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delAccountClerk(@RequestBody CommonDelReq req) {
        top.itcat.rpc.service.model.AccountClerk rpcbean = new top.itcat.rpc.service.model.AccountClerk();
        User user = new User();
        user.setId(req.getId());
        rpcbean.setValid(-1);
        rpcbean.setUser(user);

        userServiceClient.addOrUpdateAccountClerk(Collections.singletonList(rpcbean));
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询财务管理员
     * 请求方法：Any
     *
     * @return 获取到的财务管理员信息
     * @see MGetAccountClerkRequest
     */
    @RequestMapping("/get")
    @RoleCheck
    public String getAccountClerk(CommonSearchReq req) {
        MGetAccountClerkRequest rpcReq = new MGetAccountClerkRequest();

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

        MGetAccountClerkResponse rsp = userServiceClient.mGetAccountClerk(rpcReq);
        AccountClerkManage.GetAccountClerkResponse.Builder builder = AccountClerkManage.GetAccountClerkResponse.newBuilder();
        builder.setTotal(rsp.getTotal())
                .setBase(GetBaseResponUtil.getSuccessRsp())
                .setCount(rsp.getUsersSize())
                .addAllList(rsp.getUsers().parallelStream().
                        map(AccountClerkController::convertPBBean).collect(Collectors.toList()));

        return JsonFormat.printToString(builder.build());
    }

    private static top.itcat.rpc.service.model.AccountClerk convertRPCBean(UserModel.AccountClerk bean,
                                                                           Map<String, Object> map) {
        top.itcat.rpc.service.model.AccountClerk rpcbean =
                new top.itcat.rpc.service.model.AccountClerk();
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

        user.setRole(RoleEnum.Account_Clerk);

        rpcbean.setUser(user);
        return rpcbean;
    }

    private static UserModel.AccountClerk convertPBBean(top.itcat.rpc.service.model.AccountClerk rpcbean) {
        UserModel.AccountClerk.Builder builder = UserModel.AccountClerk.newBuilder();
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
