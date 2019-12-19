package top.itcat.controller.file;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.file.DelFileRequest;
import top.itcat.util.GetBaseResponUtil;
import top.itcat.util.IDGeneratorUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
@RequestMapping("/file")
@RoleCheck
@Slf4j
public class FileController {
    // 部门ID，病历号，类别
    private String basePath = "/Users/huangyuhang/img/";
    private final String filePath = "%d/%d/%d/";

    @RequestMapping("/upload/img")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("medicalRecordNo") long medicalRecordNo,
                             @RequestParam("category") int category,
                             HttpServletRequest request) {
        if (file == null || StringUtils.isEmpty(file.getOriginalFilename())) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        Claims claims = ((Claims) request.getAttribute("claims"));
        long departId = Long.valueOf((String) claims.get("departmentId"));
        long doctorId = Long.valueOf((String) claims.get("id"));
        String fileName = file.getOriginalFilename();
        String randomAttachName = IDGeneratorUtil.genPrimaryID(doctorId)
                + fileName.substring(fileName.lastIndexOf("."));
        String fpath = String.format(filePath, departId, medicalRecordNo, category);
        String path = basePath + fpath;

        if (!transferFile(path, randomAttachName, file)) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        json.put("path", fpath + randomAttachName);
        return json.toJSONString();
    }

    @RequestMapping("/del")
    public String delete(@RequestBody DelFileRequest req) {
        File file = new File(basePath + req.getPath());

        if (!file.exists()) {
            return GetBaseResponUtil.getBaseRspStr(404, "文件不存在");
        }

        File newFile = new File(file.getParentFile().getAbsolutePath() + "/." + file.getName());

        if (!file.renameTo(newFile)) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    private boolean transferFile(String path, String fileName, MultipartFile uploadfile) {
        try {
            File file = new File(path);

            if (!file.exists()) {
                synchronized (this) {
                    if (!file.exists()) {
                        if (!file.mkdirs()) {
                            return false;
                        }
                    }
                }
            }

            uploadfile.transferTo(new File(path + fileName));
        } catch (Exception e) {
            log.error("transferFile err:", e);
            return false;
        }

        return true;
    }
}