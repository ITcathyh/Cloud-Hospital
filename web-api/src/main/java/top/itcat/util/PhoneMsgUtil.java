package top.itcat.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

public class PhoneMsgUtil {
    private String uidstr = "ITcathyh";
    private String keystr = "d41d8cd98f00b204e980";
    private short codeLen = 6;
    private String content = "【祥云医院】您的验证码为：%s";

    public JSONObject sendVerifyMsg(String phoneNum) throws IOException {
        String code = IDGeneratorUtil.getRandomString(codeLen);
        return sendMsg(phoneNum, String.format(content, code));
    }

    public JSONObject sendMsg(String phoneNum, String content) throws IOException {
        org.apache.commons.httpclient.HttpClient client = new HttpClient();
        JSONObject json = new JSONObject();
        PostMethod post = new PostMethod("http://gbk.api.smschinese.cn");
        String code = IDGeneratorUtil.getRandomString(codeLen);
        NameValuePair[] data = {new NameValuePair("Uid", uidstr),
                new NameValuePair("Key", keystr),
                new NameValuePair("smsMob", phoneNum),
                new NameValuePair("smsText", content)};

        post.addRequestHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=gbk");
        post.setRequestBody(data);
        client.executeMethod(post);
        int result = Integer.parseInt(new String(post.getResponseBodyAsString().getBytes("gbk")));
        post.releaseConnection();

        if (result > 0) {
            json.put("done", true);
            json.put("result", code);
        } else {
            /*
             * 错误代码查看：
             * http://www.webchinese.com.cn/api.shtml
             */
            json.put("done", false);
            json.put("result", result);
        }

        return json;
    }

    public void setUidstr(String uidstr) {
        this.uidstr = uidstr;
    }

    public void setKeystr(String keystr) {
        this.keystr = keystr;
    }

    public void setCodeLen(short codeLen) {
        this.codeLen = codeLen;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
