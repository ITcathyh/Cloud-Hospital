package top.itcat.controller.user;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.LineConvertHump;
import top.itcat.bean.user.patient.AddWechatSignatureReq;
import top.itcat.bean.user.patient.GetSignatureReq;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.model.PatientWechatSignature;
import top.itcat.rpc.service.user.AddOrUpdatePatientWechatSignatureRequest;
import top.itcat.rpc.service.user.GetPatientWechatSignatureRequest;
import top.itcat.rpc.service.user.GetPatientWechatSignatureResponse;
import top.itcat.util.GetBaseResponUtil;

/**
 * 病人相关Controller
 */
@RestController
@RequestMapping("/user/patient")
@Slf4j
public class PatientController {
    @Autowired
    private UserServiceClient userServiceClient;

    /**
     * 获取签名对应信息
     * 请求方法：Post
     *
     * @see AddWechatSignatureReq
     */
    @PostMapping("/signature/add")
    public String addWechatSignature(@LineConvertHump AddWechatSignatureReq req) {
        AddOrUpdatePatientWechatSignatureRequest rpcReq = new AddOrUpdatePatientWechatSignatureRequest();
        PatientWechatSignature signature = new PatientWechatSignature();

        signature.setIdentityCardNo(req.getIdentityCardNo());
        signature.setSignature(req.getSignature());
        rpcReq.setSignature(signature);

        if (userServiceClient.addOrUpdatePatientWechatSignature(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 获取签名对应信息
     * 请求方法：Any
     *
     * @see GetSignatureReq
     */
    @RequestMapping("/signature/get")
    public String getWechatSignature(GetSignatureReq req) {
        GetPatientWechatSignatureRequest rpcReq = new GetPatientWechatSignatureRequest();
        rpcReq.setSearchKey(req.getSignature());
        GetPatientWechatSignatureResponse rsp = userServiceClient.getPatientWechatSignature(rpcReq);
        JSONObject json = GetBaseResponUtil.getSuccessJson();

        if (rsp == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        } else if (rsp.getSignature() == null) {
            return json.toJSONString();
        }

        if (rsp.isSetInfo()) {
            json.put("patient", rsp.getInfo());
        }

        json.put("identity_card_no", rsp.getSignature().getIdentityCardNo());
        return json.toJSONString();
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}
