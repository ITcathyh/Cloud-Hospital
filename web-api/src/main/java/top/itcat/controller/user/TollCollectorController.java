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
import top.itcat.bean.user.AddTollCollectorsReq;
import top.itcat.bean.user.UpdateTollCollectorReq;
import top.itcat.controller.user.pack.UserPack;
import top.itcat.pb_gen.api.user.manage.TollCollectorManage;
import top.itcat.pb_gen.common.UserModel;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.model.RoleEnum;
import top.itcat.rpc.service.model.User;
import top.itcat.rpc.service.user.MGetTollCollectorRequest;
import top.itcat.rpc.service.user.MGetTollCollectorResponse;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 挂号收费员Controller
 * <p> 主要包括 挂号收费员的增删改查
 */

/**
 * Test Done
 */
@RestController
@RequestMapping("/tollcollector")
@RoleCheck(roles = {RoleEnum.Hospital_Manager})
public class TollCollectorController {
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private UserPack userPack;

    /**
     * 增加挂号收费员
     * 请求方法：Post
     *
     * @return 状态
     * @see AddTollCollectorsReq
     */
    @PostMapping("/manage/add")
    public String addTollCollector(@LineConvertHump AddTollCollectorsReq req) {
        userServiceClient.addOrUpdateTollCollector(req.getTollCollectors().
                parallelStream().map(userPack::packTollCollector).
                collect(Collectors.toList()));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新挂号收费员
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateTollCollectorReq
     */
    @PostMapping("/manage/update")
    public String updateTollCollector(@LineConvertHump UpdateTollCollectorReq req) {
        System.out.println(req.getTollCollector().toString());
        userServiceClient.addOrUpdateTollCollector(Collections.
                singletonList(userPack.packTollCollector(req.getTollCollector())));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除挂号收费员
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see TollCollectorManage.DelTollCollectoreragerRequest.Builder
     */
    @PostMapping("/manage/del")
    public String delTollCollector(@RequestBody CommonDelReq req) {
        top.itcat.rpc.service.model.TollCollector rpcbean = new top.itcat.rpc.service.model.TollCollector();
        User user = new User();
        user.setId(req.getId());
        rpcbean.setValid(-1);
        rpcbean.setUser(user);

        userServiceClient.addOrUpdateTollCollector(Collections.singletonList(rpcbean));
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询挂号收费员
     * 请求方法：Any
     *
     * @return 获取到的挂号收费员信息
     * @see MGetTollCollectorRequest
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getTollCollector(CommonSearchReq req) {
        MGetTollCollectorRequest rpcReq = new MGetTollCollectorRequest();

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

        MGetTollCollectorResponse rsp = userServiceClient.mGetTollCollector(rpcReq);
        TollCollectorManage.GetTollCollectorResponse.Builder builder = TollCollectorManage.GetTollCollectorResponse.newBuilder();
        builder.setTotal(rsp.getTotal())
                .setBase(GetBaseResponUtil.getSuccessRsp())
                .setCount(rsp.getUsersSize())
                .addAllList(rsp.getUsers().parallelStream().
                        map(TollCollectorController::convertPBBean).collect(Collectors.toList()));
        return JsonFormat.printToString(builder.build());
    }

    private static top.itcat.rpc.service.model.TollCollector convertRPCBean(UserModel.TollCollector bean,
                                                                            Map<String, Object> map) {
        top.itcat.rpc.service.model.TollCollector rpcbean =
                new top.itcat.rpc.service.model.TollCollector();
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

    private static UserModel.TollCollector convertPBBean(top.itcat.rpc.service.model.TollCollector rpcbean) {
        UserModel.TollCollector.Builder builder = UserModel.TollCollector.newBuilder();
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
