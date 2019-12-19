package top.itcat.controller.diagnose.diagnostic;

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
import top.itcat.bean.diagnose.diagnostic.directory.AddSecondDiagDirsReq;
import top.itcat.bean.diagnose.diagnostic.directory.GetSecondDiagDirReq;
import top.itcat.bean.diagnose.diagnostic.directory.UpdateSecondDiagDirReq;
import top.itcat.entity.diagnose.Diagnostic;
import top.itcat.entity.diagnose.SecondDiagDir;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.service.diagnose.*;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 二级诊断目录Controller
 * <p> 主要包括 二级诊断目录的增删改查
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/diagnostic/directory")
@Slf4j
public class SecondDiagDirController {
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;

    /**
     * 增加二级诊断目录
     * 请求方法：Post
     *
     * @return 状态
     * @see AddSecondDiagDirsReq
     */
    @PostMapping("/manage/add")
    public String addSecondDiagDir(@LineConvertHump AddSecondDiagDirsReq req) {
        System.out.println(req.getList());
        AddOrUpdateSecondDiagDirRequest rpcReq = new AddOrUpdateSecondDiagDirRequest();
        rpcReq.setBeanList(req.getList().
                parallelStream().map(SecondDiagDir::convertRPCBean).
                collect(Collectors.toList()));
        diagnoseServiceClient.addOrUpdateSecondDiagDir(rpcReq);

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新二级诊断目录
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateSecondDiagDirReq
     */
    @PostMapping("/manage/update")
    public String updateSecondDiagDir(@LineConvertHump UpdateSecondDiagDirReq req) {
        AddOrUpdateSecondDiagDirRequest rpcReq = new AddOrUpdateSecondDiagDirRequest();
        rpcReq.setBeanList(Collections.
                singletonList(SecondDiagDir.convertRPCBean(req.getDiagnosticDirectory())));
        diagnoseServiceClient.addOrUpdateSecondDiagDir(rpcReq);

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除二级诊断目录
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delSecondDiagDir(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.SecondDiagDir rpcbean = new top.itcat.rpc.service.model.SecondDiagDir();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdateSecondDiagDirRequest rpcReq = new AddOrUpdateSecondDiagDirRequest();
        rpcReq.setBeanList(Collections.singletonList(rpcbean));
        diagnoseServiceClient.addOrUpdateSecondDiagDir(rpcReq);
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询二级诊断目录
     * 请求方法：Any
     *
     * @return 获取到的二级诊断目录信息
     * @see GetSecondDiagDirRequest
     * @see SecondDiagDir
     */
    @RequestMapping("/second/get")
    @RoleCheck()
    public String getSecondDiagDir(GetSecondDiagDirReq req) {
        GetSecondDiagDirRequest rpcReq = new GetSecondDiagDirRequest();

        if (req.getIds() != null && !req.getIds().isEmpty()) {
            rpcReq.setIds(req.getIds());
        }

        if (req.getSecondDircId() != null) {
            rpcReq.setIds(Collections.singletonList(req.getSecondDircId()));
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.getFirstDirecId() != null) {
            rpcReq.setFirstDiagDirId(req.getFirstDirecId());
        }

        rpcReq.setSearchKey(req.getSearchKey());
        GetSecondDiagDirResponse rsp = diagnoseServiceClient.getSecondDiagDir(rpcReq);
        JSONObject json = GetBaseResponUtil.getSuccessJson();
        List<SecondDiagDir> secondDiagDirs = rsp.getBeanList()
                .parallelStream()
                .map(SecondDiagDir::convert)
                .collect(Collectors.toList());

        if (req.getWithDetail()) {
            GetDiagnosticRequest getDiagnosticRequest = new GetDiagnosticRequest();

            for (SecondDiagDir secondDiagDir : secondDiagDirs) {
                getDiagnosticRequest.setSecondDiagDirId(secondDiagDir.getId());

                GetDiagnosticResponse getDiagnosticResponse = diagnoseServiceClient
                        .getDiagnostic(getDiagnosticRequest);

                if (getDiagnosticResponse == null) {
                    return GetBaseResponUtil.getBaseRspStr(500, "内部服务错误");
                }

                secondDiagDir.setDiagnostics(getDiagnosticResponse
                        .getBeanList()
                        .parallelStream()
                        .map(Diagnostic::convert)
                        .collect(Collectors.toList()));
                json.put("diagnostic_count", getDiagnosticResponse.getBeanListSize());
                json.put("diagnostic_total", getDiagnosticResponse.getTotal());
            }
        }
        JSONArray jsonArray = secondDiagDirs.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));
        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return json.toJSONString();
    }
}