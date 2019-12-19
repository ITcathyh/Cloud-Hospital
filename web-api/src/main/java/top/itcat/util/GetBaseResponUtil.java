package top.itcat.util;

import com.alibaba.fastjson.JSONObject;
import top.itcat.exception.CommonException;
import top.itcat.pb_gen.common.Base;

/**
 * 获取API请求状态工具类
 */
public class GetBaseResponUtil {
    private static Base.BaseResponse successRsp =
            Base.BaseResponse.newBuilder().setCode(200).build();

    /**
     * 获取成功状态
     *
     * @return BaseResponse
     */
    public static Base.BaseResponse getSuccessRsp() {
        return successRsp;
    }

    /**
     * 获取成功状态
     *
     * @param obj 附带参数
     * @return String
     */
    public static String getSuccessRspStr(Object obj) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(obj);
        JSONObject base = new JSONObject();
        base.put("code", 200);
        base.put("msg", "success");
        jsonObject.put("base", base);
        return jsonObject.toJSONString();
    }

    /**
     * 获取成功状态
     *
     * @return JSONObject
     */
    public static JSONObject getSuccessJson() {
        JSONObject jsonObject = new JSONObject();
        JSONObject base = new JSONObject();
        base.put("code", 200);
        base.put("msg", "success");
        jsonObject.put("base", base);
        return jsonObject;
    }

    /**
     * 获取成功状态
     *
     * @return String
     */
    public static String getSuccessRspStr() {
        return "{\"base\":{\"code\":200, \"msg\":\"success\"}}";
    }

    /**
     * 获取默认失败状态
     *
     * @return String
     */
    public static String getDefaultErrRspStr() {
        return "{\"base\":{\"code\":500, \"msg\":\"内部服务错误\"}}";
    }

    /**
     * 获取异常状态
     *
     * @param ce 异常
     * @return BaseResponse
     */
    public static Base.BaseResponse getBaseRsp(CommonException ce) {
        return getBaseRsp(ce.getCode(), ce.getPromt());
    }

    /**
     * 获取其它状态
     *
     * @param code 状态码
     * @param msg  提示信息
     * @return BaseResponse
     */
    public static Base.BaseResponse getBaseRsp(int code, String msg) {
        return Base.BaseResponse.newBuilder().setCode(code).setMsg(msg).build();
    }

    /**
     * 获取其它状态
     *
     * @param code 状态码
     * @param msg  提示信息
     * @return String
     */
    public static String getBaseRspStr(int code, String msg) {
        JSONObject jsonObject = new JSONObject();
        JSONObject base = new JSONObject();
        base.put("code", code);
        base.put("msg", msg);
        jsonObject.put("base", base);
        return jsonObject.toJSONString();
    }
}
