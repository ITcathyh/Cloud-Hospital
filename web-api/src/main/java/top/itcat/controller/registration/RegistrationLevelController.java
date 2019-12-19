package top.itcat.controller.registration;

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
import top.itcat.bean.registration.level.AddRegistrationLevelsReq;
import top.itcat.bean.registration.level.UpdateRegistrationLevelReq;
import top.itcat.entity.registration.RegistrationLevel;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.service.hospital.GetRegisterationLevelRequest;
import top.itcat.rpc.service.hospital.GetRegisterationLevelResponse;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * 挂号级别Controller
 * <p> 主要包括 挂号级别的增删改查
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/registration/level")
@Slf4j
public class RegistrationLevelController {
    @Autowired
    private HospitalServiceClient hospitalServiceClient;

    /**
     * 增加挂号级别
     * 请求方法：Post
     *
     * @return 状态
     * @see AddRegistrationLevelsReq
     */
    @PostMapping("/manage/add")
    public String addRegistrationLevel(@LineConvertHump AddRegistrationLevelsReq req) {
        hospitalServiceClient.addOrUpdateRegisterationLevel(req.getList().
                parallelStream().map(RegistrationLevel::convertRPCBean).
                collect(Collectors.toList()));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新挂号级别
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateRegistrationLevelReq
     */
    @PostMapping("/manage/update")
    public String updateRegistrationLevel(@LineConvertHump UpdateRegistrationLevelReq req) {
        hospitalServiceClient.addOrUpdateRegisterationLevel(Collections.
                singletonList(RegistrationLevel.convertRPCBean(req.getRegistrationLevel())));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除挂号级别
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delRegistrationLevel(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.RegisterationLevel rpcbean = new top.itcat.rpc.service.model.RegisterationLevel();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        if (!hospitalServiceClient.addOrUpdateRegisterationLevel(Collections.singletonList(rpcbean))) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询挂号级别
     * 请求方法：Any
     *
     * @return 获取到的挂号级别信息
     * @see GetRegisterationLevelRequest
     * @see RegistrationLevel
     */
    @RequestMapping("/get")
    @RoleCheck
    public String getRegistrationLevel(CommonSearchReq req) {
        GetRegisterationLevelRequest rpcReq = new GetRegisterationLevelRequest();

        if (req.getIds() != null && !req.getIds().isEmpty()) {
            rpcReq.setIds(req.getIds());
        }

        GetRegisterationLevelResponse rsp = hospitalServiceClient.getRegisterationLevel(rpcReq);

        if (rsp == null) {
            return GetBaseResponUtil.getBaseRspStr(500, "内部服务错误");
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = rsp.getBeanList()
                .parallelStream()
                .map(RegistrationLevel::convert)
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("total", rsp.getTotal());
        json.put("count", rsp.getBeanListSize());
        return json.toJSONString();
    }
}