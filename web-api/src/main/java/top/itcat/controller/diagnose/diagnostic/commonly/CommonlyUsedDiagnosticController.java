package top.itcat.controller.diagnose.diagnostic.commonly;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.LineConvertHump;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.CommonDelReq;
import top.itcat.bean.diagnose.diagnostic.commonly.AddCommonlyUsedDiagnosticsReq;
import top.itcat.bean.diagnose.diagnostic.commonly.GetCommonlyUsedDiagnosticReq;
import top.itcat.bean.diagnose.diagnostic.commonly.UpdateCommonlyUsedDiagnosticReq;
import top.itcat.entity.diagnose.CommonlyUsedDiagnostic;
import top.itcat.entity.diagnose.Diagnostic;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.service.diagnose.*;
import top.itcat.rpc.service.model.SuitableRangeEnum;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 常用诊断Controller
 * <p> 主要包括 常用诊断的增删改查
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/diagnose/common/diagnostic/")
@Slf4j
@RoleCheck
public class CommonlyUsedDiagnosticController {
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;

    /**
     * 增加常用诊断
     * 请求方法：Post
     *
     * @return 状态
     * @see AddCommonlyUsedDiagnosticsReq
     */
    @PostMapping("/manage/add")
    public String addCommonlyUsedDiagnostic(@LineConvertHump AddCommonlyUsedDiagnosticsReq req,
                                            HttpServletRequest request) {
        Claims claims = ((Claims) request.getAttribute("claims"));

        for (CommonlyUsedDiagnostic commonlyUsedDiagnostic : req.getList()) {
            commonlyUsedDiagnostic.setDoctorId(Long.valueOf((String) claims.get("id")));
            commonlyUsedDiagnostic.setDepartmentId(Long.valueOf((String) claims.get("departmentId")));
        }

        AddOrUpdateCommonlyUsedDiagnosticRequest rpcReq = new AddOrUpdateCommonlyUsedDiagnosticRequest();
        rpcReq.setBean(req.getList().
                parallelStream().map(CommonlyUsedDiagnostic::convertRPCBean).
                collect(Collectors.toList()));
        diagnoseServiceClient.addOrUpdateCommonlyUsedDiagnostic(rpcReq);

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新常用诊断
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateCommonlyUsedDiagnosticReq
     */
    @PostMapping("/manage/update")
    public String updateCommonlyUsedDiagnostic(@LineConvertHump UpdateCommonlyUsedDiagnosticReq req) {
        AddOrUpdateCommonlyUsedDiagnosticRequest rpcReq = new AddOrUpdateCommonlyUsedDiagnosticRequest();
        rpcReq.setBean(Collections.singletonList(CommonlyUsedDiagnostic.convertRPCBean(req.getCommonlyUsedDiagnostic())));
        diagnoseServiceClient.addOrUpdateCommonlyUsedDiagnostic(rpcReq);

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除常用诊断
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delCommonlyUsedDiagnostic(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.CommonlyUsedDiagnostic rpcbean = new top.itcat.rpc.service.model.CommonlyUsedDiagnostic();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdateCommonlyUsedDiagnosticRequest rpcReq = new AddOrUpdateCommonlyUsedDiagnosticRequest();
        rpcReq.setBean(Collections.singletonList(rpcbean));
        diagnoseServiceClient.addOrUpdateCommonlyUsedDiagnostic(rpcReq);
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询常用诊断
     * 请求方法：Any
     *
     * @return 获取到的常用诊断信息
     * @see GetCommonlyUsedDiagnosticRequest
     * @see CommonlyUsedDiagnostic
     */
    @RequestMapping("/get")
    @RoleCheck
    public String getCommonlyUsedDiagnostic(GetCommonlyUsedDiagnosticReq req,
                                            HttpServletRequest request) {
        GetCommonlyUsedDiagnosticRequest rpcReq = new GetCommonlyUsedDiagnosticRequest();
        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.getSuitableRange() != null) {
            SuitableRangeEnum rangeEnum = SuitableRangeEnum.findByValue(req.getSuitableRange());
            rpcReq.setRange(Collections.singletonList(rangeEnum));

            if (rangeEnum == SuitableRangeEnum.Personal) {
                Claims claims = ((Claims) request.getAttribute("claims"));
                rpcReq.setDoctorId(Long.valueOf((String) claims.get("id")));
            } else if (rangeEnum == SuitableRangeEnum.Depart) {
                Claims claims = ((Claims) request.getAttribute("claims"));
                rpcReq.setDepartmentId(Long.valueOf((String) claims.get("departmentId")));
            }
        }

        GetCommonlyUsedDiagnosticResponse rsp = diagnoseServiceClient.getCommonlyUsedDiagnostic(rpcReq);
        JSONObject json = GetBaseResponUtil.getSuccessJson();
        List<CommonlyUsedDiagnostic> commonlyUsedDiagnostics = rsp.getBeanList().parallelStream()
                .map(CommonlyUsedDiagnostic::convert)
                .collect(Collectors.toList());
        //获得diagnostic_id
        List<Long> ids = commonlyUsedDiagnostics.parallelStream().map(CommonlyUsedDiagnostic::getDiagnosticId).collect(Collectors.toList());

        GetDiagnosticRequest getDiagnosticRequest = new GetDiagnosticRequest();
        getDiagnosticRequest.setIds(ids);
        //获得diagnostic 相应的diagnostic
        GetDiagnosticResponse s = diagnoseServiceClient.getDiagnostic(getDiagnosticRequest);
        //将diagnostic 转化为<diagnostic_id>
        Map<Long, Diagnostic> diagnosticMap = s.getBeanList().stream().collect(Collectors.toMap(top.itcat.rpc.service.model.Diagnostic::getId, a -> Diagnostic.convert(a), (k1, k2) -> k1));

        JSONArray jsonArray = commonlyUsedDiagnostics.parallelStream()
                .peek((commonlyUsedDiagnostic) -> commonlyUsedDiagnostic.setDiagnostic(diagnosticMap.get(commonlyUsedDiagnostic.getDiagnosticId())))
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }
}
