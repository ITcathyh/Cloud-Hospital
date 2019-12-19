package top.itcat.controller.diagnose.medical.record.template;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.LineConvertHump;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.CommonDelReq;
import top.itcat.bean.diagnose.medical.record.template.AddMedicalRecordTemplatesReq;
import top.itcat.bean.diagnose.medical.record.template.GetMedicalRecordTemplateReq;
import top.itcat.bean.diagnose.medical.record.template.UpdateMedicalRecordTemplateReq;
import top.itcat.entity.diagnose.Diagnostic;
import top.itcat.entity.diagnose.DiagnosticForMedicalRecordTemplate;
import top.itcat.entity.diagnose.MedicalRecordTemplate;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.service.diagnose.*;
import top.itcat.rpc.service.model.SuitableRangeEnum;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 病历模板Controller
 * <p> 主要包括 病历模板的增删改查
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/diagnose/medical/template")
@Slf4j
@RoleCheck
public class MedicalRecordTemplateController {
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;

    /**
     * 增加病历模板
     * 请求方法：Post
     *
     * @return 状态
     * @see AddMedicalRecordTemplatesReq
     */
    // todo
    @PostMapping("/manage/add")
    public String addMedicalRecordTemplate(@LineConvertHump AddMedicalRecordTemplatesReq req,
                                           HttpServletRequest request) {
        MedicalRecordTemplate medicalRecordTemplate = req.getBean();
        Claims claims = ((Claims) request.getAttribute("claims"));

        medicalRecordTemplate.setDoctorId(Long.valueOf((String) claims.get("id")));
        medicalRecordTemplate.setDepartmentId(Long.valueOf((String) claims.get("departmentId")));
//        medicalRecordTemplate.setCreatorId(Long.valueOf((String) claims.get("id")));

        AddOrUpdateMedicalRecordTemplateRequest rpcReq = new AddOrUpdateMedicalRecordTemplateRequest();
        rpcReq.setBean(MedicalRecordTemplate.convertRPCBean(medicalRecordTemplate));
        diagnoseServiceClient.addOrUpdateMedicalRecordTemplate(rpcReq);

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新病历模板
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateMedicalRecordTemplateReq
     */
    @PostMapping("/manage/update")
    public String updateMedicalRecordTemplate(@LineConvertHump UpdateMedicalRecordTemplateReq req) {
        AddOrUpdateMedicalRecordTemplateRequest rpcReq = new AddOrUpdateMedicalRecordTemplateRequest();
        rpcReq.setBean(MedicalRecordTemplate.convertRPCBean(req.getMedicalRecordTemplate()));
        diagnoseServiceClient.addOrUpdateMedicalRecordTemplate(rpcReq);

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除病历模板
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delMedicalRecordTemplate(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.MedicalRecordTemplate rpcbean = new top.itcat.rpc.service.model.MedicalRecordTemplate();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdateMedicalRecordTemplateRequest rpcReq = new AddOrUpdateMedicalRecordTemplateRequest();
        rpcReq.setBean(rpcbean);
        diagnoseServiceClient.addOrUpdateMedicalRecordTemplate(rpcReq);
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询病历模板
     * 请求方法：Any
     *
     * @return 获取到的病历模板信息
     * @see GetMedicalRecordTemplateRequest
     * @see GetMedicalRecordTemplateRequest
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getMedicalRecordTemplate(GetMedicalRecordTemplateReq req,
                                           HttpServletRequest request) {
        GetMedicalRecordTemplateRequest rpcReq = new GetMedicalRecordTemplateRequest();

        if (req.getIds() != null && !req.getIds().isEmpty()) {
            rpcReq.setIds(req.getIds());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
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

        rpcReq.setSearchKey(req.getSearchKey());
        GetMedicalRecordTemplateResponse rsp = diagnoseServiceClient.getMedicalRecordTemplate(rpcReq);

        if (rsp == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        } else if (rsp.getBeanList() == null || rsp.getBeanListSize() == 0) {
            JSONObject json = GetBaseResponUtil.getSuccessJson();
            json.put("count", 0);
            json.put("total", 0);
            return json.toJSONString();
        }
        List<MedicalRecordTemplate> medicalRecordTemplates = rsp.getBeanList()
                .parallelStream()
                .map(MedicalRecordTemplate::convert)
                .collect(Collectors.toList());
        GetDiagnosticRequest getDiagnosticRequest = new GetDiagnosticRequest();
        GetDiagnosticResponse getDiagnosticResponse;

        for (MedicalRecordTemplate medicalRecordTemplate : medicalRecordTemplates) {
            if (medicalRecordTemplate.getDoctorDiagnostics() == null) {
                continue;
            }

            for (DiagnosticForMedicalRecordTemplate diagnostic : medicalRecordTemplate.getDoctorDiagnostics()) {
                getDiagnosticRequest.setIds(Collections.
                        singletonList(diagnostic.getDiagnosticId()));
                getDiagnosticResponse = diagnoseServiceClient.getDiagnostic(getDiagnosticRequest);

                if (getDiagnosticResponse == null) {
                    return GetBaseResponUtil.getDefaultErrRspStr();
                } else if (getDiagnosticResponse.getBeanList() != null && getDiagnosticResponse.getBeanListSize() > 0) {
                    diagnostic.setDiagnostic(Diagnostic.convert(getDiagnosticResponse.beanList.get(0)));
                }
            }
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = medicalRecordTemplates.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return json.toJSONString();
    }
}
