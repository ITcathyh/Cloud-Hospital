package top.itcat.controller.diagnose.medical.record;

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
import top.itcat.bean.diagnose.medical.record.AddMedicalRecordReq;
import top.itcat.bean.diagnose.medical.record.QueryMedicalRecordRequest;
import top.itcat.bean.diagnose.medical.record.UpdateMedicalRecordReq;
import top.itcat.entity.Patient;
import top.itcat.entity.diagnose.Diagnostic;
import top.itcat.entity.diagnose.DoctorDiagnostic;
import top.itcat.entity.diagnose.MedicalRecord;
import top.itcat.exception.InvalidParamException;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.diagnose.*;
import top.itcat.rpc.service.model.MedicalRecordStatusEnum;
import top.itcat.rpc.service.user.MGetPatientRequest;
import top.itcat.rpc.service.user.MGetPatientResponse;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 病历Controller
 * <p> 主要包括 病历的增删改查
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/diagnose/medical/record")
@RoleCheck
@Slf4j
public class MedicalRecordController {
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;
    @Autowired
    private UserServiceClient userServiceClient;

    /**
     * 增加病历
     * 请求方法：Post
     *
     * @return 状态
     * @see AddMedicalRecordReq
     */
    @PostMapping("/manage/add")
    public String addMedicalRecord(@LineConvertHump AddMedicalRecordReq req,
                                   HttpServletRequest request) {
        MedicalRecord record = req.getBean();

//        if (record.getList() == null || record.getList().isEmpty()) {
//            return GetBaseResponUtil.getBaseRspStr(400, "诊断为空");
//        }

        Claims claims = (Claims) request.getAttribute("claims");
        long operatorId = Long.parseLong((String) claims.get("id"));

        for (DoctorDiagnostic diagnostic : record.getList()) {
            diagnostic.setDoctorId(operatorId);
            diagnostic.setId(null);
        }

        record.setDoctorId(operatorId);
        record.setStatus(MedicalRecordStatusEnum.Seen.getValue());
        record.setId(null);

        AddOrUpdateMedicalRecordRequest rpcreq =
                new AddOrUpdateMedicalRecordRequest();
        top.itcat.rpc.service.model.MedicalRecord rpcRecord =
                MedicalRecord.convertRPCBean(record);
        rpcreq.setBean(rpcRecord);

        try {
            AddOrUpdateMedicalRecordResponse rsp = diagnoseServiceClient.addOrUpdateMedicalRecord(rpcreq);

            if (rsp == null) {
                return GetBaseResponUtil.getDefaultErrRspStr();
            } else if (rsp.getBaseResp().getStatusCode() != 0) {
                return GetBaseResponUtil.getBaseRspStr(403, rsp.getBaseResp().getStatusMessage());
            }
        } catch (InvalidParamException e) {
            return GetBaseResponUtil.getBaseRspStr(403, "无效请求");
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新病历
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateMedicalRecordReq
     */
    @PostMapping("/manage/update")
    public String updateMedicalRecord(@LineConvertHump UpdateMedicalRecordReq req,
                                      HttpServletRequest request) {
        AddOrUpdateMedicalRecordRequest rpcReq = new AddOrUpdateMedicalRecordRequest();

        if (req.getMedicalRecord().getId() == null || req.getMedicalRecord().getStatus() == null) {
            return GetBaseResponUtil.getBaseRspStr(400, "请求参数有误");
        }

        Claims claims = (Claims) request.getAttribute("claims");
        long operatorId = Long.parseLong((String) claims.get("id"));

        for (DoctorDiagnostic diagnostic : req.getMedicalRecord().getList()) {
            diagnostic.setDoctorId(operatorId);
            diagnostic.setId(null);
        }

        top.itcat.rpc.service.model.MedicalRecord medicalRecord = MedicalRecord.convertRPCBean(req.getMedicalRecord());
        medicalRecord.setId(req.getMedicalRecord().getId());
        medicalRecord.setStatus(MedicalRecordStatusEnum.findByValue(req.getMedicalRecord().getStatus()));
        rpcReq.setBean(medicalRecord);

        if (diagnoseServiceClient.addOrUpdateMedicalRecord(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除病历
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delMedicalRecord(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.MedicalRecord rpcbean = new top.itcat.rpc.service.model.MedicalRecord();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdateMedicalRecordRequest rpcReq = new AddOrUpdateMedicalRecordRequest();
        rpcReq.setBean(rpcbean);

        if (diagnoseServiceClient.addOrUpdateMedicalRecord(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询病历
     * 请求方法：Any
     *
     * @return 获取到的病历信息
     * @see QueryMedicalRecordRequest
     * @see MedicalRecord
     */
    @RequestMapping("/get")
    @RoleCheck(needLogin = false)
    public String getMedicalRecord(QueryMedicalRecordRequest req) {
        GetMedicalRecordRequest rpcReq = new GetMedicalRecordRequest();

        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.getMedicalRecordNo() != null) {
            rpcReq.setMedicalRecordNo(req.getMedicalRecordNo());
        }

        if (req.getDoctorId() != null) {
            rpcReq.setDoctorId(req.getDoctorId());
        }

        rpcReq.setIdNum(req.getIdNum());
        GetMedicalRecordResponse rsp = diagnoseServiceClient.getMedicalRecord(rpcReq);
        List<MedicalRecord> medicalRecords = rsp.getBeanList()
                .parallelStream()
                .map(MedicalRecord::convert)
                .collect(Collectors.toList());
        GetDiagnosticRequest getDiagnosticRequest = new GetDiagnosticRequest();
        GetDiagnosticResponse getDiagnosticResponse;
        MGetPatientRequest getPatientRequest = new MGetPatientRequest();

        for (MedicalRecord medicalRecord : medicalRecords) {
            List<DoctorDiagnostic> doctorDiagnostics = medicalRecord.getList();

            if (doctorDiagnostics == null) {
                continue;
            }

            for (DoctorDiagnostic doctorDiagnostic : doctorDiagnostics) {
                getDiagnosticRequest.setIds(Collections.
                        singletonList(doctorDiagnostic.getDiagnosticId()));
                getDiagnosticResponse = diagnoseServiceClient.getDiagnostic(getDiagnosticRequest);
                doctorDiagnostic.setDiagnostic(Diagnostic.convert(getDiagnosticResponse.beanList.get(0)));
            }

            getPatientRequest.setSearchKey(String.valueOf(medicalRecord.getMedicalRecordNo()));
            MGetPatientResponse getPatientResponse = userServiceClient.mGetPatient(getPatientRequest);

            if (getPatientResponse == null) {
                return GetBaseResponUtil.getDefaultErrRspStr();
            } else if (getPatientResponse.getUsers() != null && getPatientResponse.getUsersSize() > 0) {
                medicalRecord.setPatient(Patient.convertPatient(getPatientResponse.getUsers().get(0)));
            }
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = medicalRecords.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }

}
