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
import top.itcat.bean.diagnose.diagnostic.AddDiagnosticsReq;
import top.itcat.bean.diagnose.diagnostic.QueryDiagnosticRequest;
import top.itcat.bean.diagnose.diagnostic.UpdateDiagnosticReq;
import top.itcat.entity.diagnose.Diagnostic;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.service.diagnose.AddOrUpdateDiagnosticRequest;
import top.itcat.rpc.service.diagnose.GetDiagnosticRequest;
import top.itcat.rpc.service.diagnose.GetDiagnosticResponse;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * 诊断Controller
 * <p> 主要包括 诊断的增删改查
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/diagnostic")
@Slf4j
@RoleCheck
public class DiagnosticController {
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;

    /**
     * 增加诊断
     * 请求方法：Post
     *
     * @return 状态
     * @see AddDiagnosticsReq
     */
    @PostMapping("/manage/add")
    public String addDiagnostic(@LineConvertHump AddDiagnosticsReq req) {
        AddOrUpdateDiagnosticRequest rpcReq = new AddOrUpdateDiagnosticRequest();
        rpcReq.setBeanList(req.getList().
                parallelStream().map(Diagnostic::convertRPCBean).
                collect(Collectors.toList()));
        diagnoseServiceClient.addOrUpdateDiagnostic(rpcReq);

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新诊断
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateDiagnosticReq
     */
    @PostMapping("/manage/update")
    public String updateDiagnostic(@LineConvertHump UpdateDiagnosticReq req) {
        AddOrUpdateDiagnosticRequest rpcReq = new AddOrUpdateDiagnosticRequest();
        rpcReq.setBeanList(Collections.
                singletonList(Diagnostic.convertRPCBean(req.getDiagnostic())));
        diagnoseServiceClient.addOrUpdateDiagnostic(rpcReq);

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除诊断
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delDiagnostic(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.Diagnostic rpcbean = new top.itcat.rpc.service.model.Diagnostic();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdateDiagnosticRequest rpcReq = new AddOrUpdateDiagnosticRequest();
        rpcReq.setBeanList(Collections.singletonList(rpcbean));
        diagnoseServiceClient.addOrUpdateDiagnostic(rpcReq);
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询诊断
     * 请求方法：Any
     *
     * @return 获取到的诊断信息
     * @see GetDiagnosticRequest
     * @see Diagnostic
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getDiagnostic(QueryDiagnosticRequest req) {
        GetDiagnosticRequest rpcReq = new GetDiagnosticRequest();

        if (req.getIds() != null && !req.getIds().isEmpty()) {
            rpcReq.setIds(req.getIds());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.getSearchKey() != null) {
            rpcReq.setSearchKey(req.getSearchKey());
        }

        if (req.getSecondDirecId() != null) {
            rpcReq.setSecondDiagDirId(req.getSecondDirecId());
        }

        GetDiagnosticResponse rsp = diagnoseServiceClient.getDiagnostic(rpcReq);

        if (rsp == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        } else if (rsp.getBeanListSize() == 0) {
            JSONObject json = GetBaseResponUtil.getSuccessJson();
            json.put("count", 0);
            json.put("total", 0);
            return json.toJSONString();
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = rsp.getBeanList().parallelStream()
                .map(Diagnostic::convert)
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return json.toJSONString();
    }
}