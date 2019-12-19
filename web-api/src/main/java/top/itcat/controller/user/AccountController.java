package top.itcat.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.protobuf.format.JsonFormat;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.LineConvertHump;
import top.itcat.aop.auth.AuthManager;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.user.ApiUser;
import top.itcat.bean.user.EditPwdReq;
import top.itcat.bean.user.LoginReq;
import top.itcat.exception.ExcessiveAttemptsException;
import top.itcat.exception.InvalidParamException;
import top.itcat.exception.UnknownUserException;
import top.itcat.pb_gen.api.user.Login;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.model.RoleEnum;
import top.itcat.rpc.service.user.EditPwdRequest;
import top.itcat.rpc.service.user.EditPwdResponse;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Test Done
 */
@RestController
@RequestMapping("/user")
public class AccountController {
    @Autowired
    private AuthManager authManager;
    @Autowired
    private UserServiceClient userServiceClient;

    @PostMapping("/login")
    public String login(@LineConvertHump LoginReq req,
                        HttpSession session) {
        try {
            String token = authManager.doLogin(session,
                    req.getUsername(), req.getPassword());

            if (token != null) {
                return JsonFormat.printToString(Login.LoginResponse.newBuilder()
                        .setBase(GetBaseResponUtil.getSuccessRsp())
                        .setToken(token)
                        .build());
            }
        } catch (InvalidParamException e) {
            return GetBaseResponUtil.getBaseRspStr(400, "参数错误");
        } catch (UnknownUserException e) {
            return GetBaseResponUtil.getBaseRspStr(400, "账号或密码有误");
        } catch (ExcessiveAttemptsException e) {
            return GetBaseResponUtil.getBaseRspStr(403, "登陆次数过多，请15秒后重试");
        }

        return GetBaseResponUtil.getBaseRspStr(400, "无效请求");
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session,
                         HttpServletRequest request) {
        authManager.doLogout(session);

        return GetBaseResponUtil.getSuccessRspStr();
    }

    @RequestMapping("/info/get")
    @RoleCheck
    public String getUserInfo(HttpServletRequest request) {
        Claims claims = ((Claims) request.getAttribute("claims"));
        JSONObject jsonObject = GetBaseResponUtil.getSuccessJson();
        JSONArray array = new JSONArray();
        array.add(claims.get("role"));
        RoleEnum role = RoleEnum.findByValue((Integer) claims.get("role"));
        String avaterPath = "/avatar/";

        switch (role) {
            case Patient:
                jsonObject.put("avatar", avaterPath + "patient.jpg");
                break;
            case Doctor:
                jsonObject.put("avatar", avaterPath + "doctor.jpg");
                break;
            case Toll_Collector:
                jsonObject.put("avatar", avaterPath + "toll_coll.jpg");
                break;
            case Hospital_Manager:
                jsonObject.put("avatar", avaterPath + "hosp_mana.jpg");
                break;
            case Pharmacy_Manager:
                jsonObject.put("avatar", avaterPath + "phar_mana.jpg");
                break;
            case Account_Clerk:
                jsonObject.put("avatar", avaterPath + "acc_clerk.jpg");
                break;
            case Medical_Doctor:
                jsonObject.put("avatar", avaterPath + "me_doctor.jpg");
                break;
            default:
        }

        jsonObject.put("id", Long.valueOf((String) claims.get("id")));
        jsonObject.put("code", claims.get("code"));
        jsonObject.put("department_id", Long.valueOf((String) claims.get("departmentId")));
        jsonObject.put("description", claims.get("description"));
        jsonObject.put("realname", ((ApiUser)request.getSession().getAttribute("user")).getRealname());
        jsonObject.put("title", claims.get("title"));
        jsonObject.put("roles", array);

        return jsonObject.toJSONString();
    }

    @PostMapping("/manage/pwd/edit")
    @RoleCheck
    public String editPwd(@LineConvertHump EditPwdReq req,
                          HttpServletRequest request) {
        if (req.getOldPwd().equals(req.getPwd())) {
            return GetBaseResponUtil.getBaseRspStr(400, "请求参数有误");
        }

        Claims claims = ((Claims) request.getAttribute("claims"));
        EditPwdRequest rpcReq = new EditPwdRequest();
        rpcReq.setCode((String) claims.get("code"));
        rpcReq.setId(Long.valueOf((String) claims.get("id")));
        rpcReq.setPwd(req.getPwd());
        rpcReq.setOldPwd(req.getOldPwd());
        EditPwdResponse rsp = userServiceClient.editPwd(rpcReq);

        if (rsp == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        } else if (rsp.getBaseResp().getStatusCode() != 0) {
            return GetBaseResponUtil.getBaseRspStr(403, rsp.getBaseResp().getStatusMessage());
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }
}
