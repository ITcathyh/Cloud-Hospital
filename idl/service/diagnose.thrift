namespace java top.itcat.rpc.service.diagnose

include "base.thrift"
include "../model/diagnose_model.thrift"
include "../model/registration_model.thrift"
include "../model/medical_record_model.thrift"
include "../model/apply_model.thrift"
include "../model/prescription_model.thrift"

struct GetFirstDiagDirRequest {
    1: optional list<i64> ids,
    100: optional i32 offset,
    101: optional i32 limit,
    102: optional string searchKey,
}

struct GetFirstDiagDirResponse {
    1: optional list<diagnose_model.FirstDiagDir> beanList,
    2: i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateFirstDiagDirRequest {
    1: required list<diagnose_model.FirstDiagDir> beanList,
}

struct AddOrUpdateFirstDiagDirResponse {
    255: required base.BaseResp BaseResp,
}

struct GetSecondDiagDirRequest {
    1: optional list<i64> ids,
    2: optional i64 firstDiagDirId,
    100: optional i32 offset,
    101: optional i32 limit,
    102: optional string searchKey,
}

struct GetSecondDiagDirResponse {
    1: optional list<diagnose_model.SecondDiagDir> beanList,
    2: i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateSecondDiagDirRequest {
    1: required list<diagnose_model.SecondDiagDir> beanList,
}

struct AddOrUpdateSecondDiagDirResponse {
    255: required base.BaseResp BaseResp,
}

struct GetDiagnosticRequest {
    1: optional list<i64> ids,
    2: optional i64 secondDiagDirId,
    100: optional i32 offset,
    101: optional i32 limit,
    102: optional string searchKey,
}

struct GetDiagnosticResponse {
    1: optional list<diagnose_model.Diagnostic> beanList,
    2: i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateDiagnosticRequest {
    1: required list<diagnose_model.Diagnostic> beanList,
}

struct AddOrUpdateDiagnosticResponse {
    255: required base.BaseResp BaseResp,
}

struct GetSchedulePlanRequest {
    1: optional list<i64> ids,
    2: optional i64 doctorId,
    3: optional i64 departId,
    4: optional i64 curTime,
    6: optional bool needExpired,
    7: optional i32 offset,
    8: optional i32 limit,
    9: optional i64 startTime,
    10: optional i64 endTime,
    11: optional string searchKey,
}

struct GetSchedulePlanResponse {
    1: optional list<diagnose_model.SchedulePlan> beanList,
    2: optional i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateSchedulePlanRequest {
    1: optional diagnose_model.SchedulePlan plan,
    2: optional diagnose_model.ScheduleRule rule,
}

struct AddOrUpdateSchedulePlanResponse {
    255: required base.BaseResp BaseResp,
}

struct GetScheduleRuleRequest {
    1: optional list<i64> ids,
    2: optional i64 doctorId,
    3: optional i64 departId,
    4: optional bool needExpired,
    5: optional i32 offset,
    6: optional i32 limit,
    7: optional string searchKey,
    8: optional bool onlyNormal,
}

struct GetScheduleRuleResponse {
    1: optional list<diagnose_model.ScheduleRule> beanList,
    2: optional i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateScheduleRuleRequest {
    1: optional list<diagnose_model.ScheduleRule> beanList,
}

struct AddOrUpdateScheduleRuleResponse {
    255: required base.BaseResp BaseResp,
}

struct GetRegistrationRequest {
    1: optional list<i64> ids,
    2: optional string searchKey,
    3: optional i64 doctorId,
    4: optional i64 departId,
    5: optional i64 curTime,
//    6: optional string idNum,
//    7: optional i64 medicalRecordNo,
    6: optional list<string> idNums,
    8: optional registration_model.RegistrationStatusEnum status,
    110: optional i32 offset,
    111: optional i32 limit,
}

struct GetRegistrationResponse {
    1: optional list<registration_model.Registration> beanList,
    254: optional i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateRegistrationRequest {
    1: optional registration_model.Registration bean,
    2: optional bool needBook,
    3: optional i64 operatorId,
    4: optional i64 creatorId,
}

struct AddOrUpdateRegistrationResponse {
    1: optional registration_model.Registration bean,
    255: required base.BaseResp BaseResp,
}

struct CancelRegistrationRequest {
    1: required i64 id,
}

struct CancelRegistrationResponse {
    255: required base.BaseResp BaseResp,
}

struct GetRegistrationCountRequest {
    1: optional i64 doctorId,
    2: optional i64 departId,
    3: required i64 startTime,
    4: required i64 endTime,
}

struct GetRegistrationCountResponse {
    1: required i32 count,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateMedicalRecordRequest {
    1: optional medical_record_model.MedicalRecord bean,
}

struct AddOrUpdateMedicalRecordResponse {
    255: required base.BaseResp BaseResp,
}

struct GetMedicalRecordRequest {
    1: optional list<i64> ids,
    2: optional i64 medicalRecordNo,
    3: optional string idNum,
    4: optional i64 doctorId,
    100: optional i32 offset,
    101: optional i32 limit,
}

struct GetMedicalRecordResponse {
    1: optional list<medical_record_model.MedicalRecord> beanList,
    254: required i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateMedicalRecordTemplateRequest {
    1: optional medical_record_model.MedicalRecordTemplate bean,
}

struct AddOrUpdateMedicalRecordTemplateResponse {
    255: required base.BaseResp BaseResp,
}

struct GetMedicalRecordTemplateRequest {
    1: optional list<i64> ids,
    2: optional list<diagnose_model.SuitableRangeEnum> range,
    3: optional i64 doctorId,
    4: optional string searchKey,
    5: optional i64 departmentId,
    100: optional i32 offset,
    101: optional i32 limit,
}

struct GetMedicalRecordTemplateResponse {
    1: optional list<medical_record_model.MedicalRecordTemplate> beanList,
    254: required i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateCommonlyUsedDiagnosticRequest {
    1: optional list<medical_record_model.CommonlyUsedDiagnostic> bean,
}

struct AddOrUpdateCommonlyUsedDiagnosticResponse {
    255: required base.BaseResp BaseResp,
}

struct GetCommonlyUsedDiagnosticRequest {
    1: optional list<i64> ids,
    2: optional list<diagnose_model.SuitableRangeEnum> range,
    3: optional i64 doctorId,
    4: optional i64 departmentId,
    100: optional i32 offset,
    101: optional i32 limit,
}

struct GetCommonlyUsedDiagnosticResponse {
    1: optional list<medical_record_model.CommonlyUsedDiagnostic> beanList,
    254: required i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateApplyRequest {
    1: optional apply_model.Apply bean,
}

struct AddOrUpdateApplyResponse {
    255: required base.BaseResp BaseResp,
}

struct GetApplyRequest {
    1: optional list<i64> ids,
    2: optional i64 medicalRecordNo,
    3: optional apply_model.ApplyCategory category,
    4: optional i64 doctorId,
    100: optional i32 offset,
    101: optional i32 limit,
}

struct GetApplyResponse {
    1: optional list<apply_model.Apply> beanList,
    254: required i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateApplyGroupRequest {
    1: optional apply_model.ApplyGroup bean,
}

struct AddOrUpdateApplyGroupResponse {
    255: required base.BaseResp BaseResp,
}

struct GetApplyGroupRequest {
    1: optional list<i64> ids,
    2: optional list<diagnose_model.SuitableRangeEnum> range,
    3: optional i64 createrId,
    4: optional i64 departmentId,
    5: optional string searchKey,
    6: optional apply_model.ApplyCategory category,
    100: optional i32 offset,
    101: optional i32 limit,
}

struct GetApplyGroupResponse {
    1: optional list<apply_model.ApplyGroup> beanList,
    254: required i32 total,
    255: required base.BaseResp BaseResp,
}

struct GetApplyItemRequest {
    1: optional list<i64> ids,
    2: optional i64 applyId,
    3: optional i64 departmentId,
    4: optional i64 medicalDoctorId,
    5: optional i64 medicalRecordNo,
    6: optional i64 startTime,
    7: optional i64 endTime,
    100: optional i32 offset,
    101: optional i32 limit,
}

struct GetApplyItemResponse {
    1: optional list<apply_model.ApplyItem> beanList,
    254: required i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateDoctorDiagnosticRequest {
    1: optional list<medical_record_model.DoctorDiagnostic> bean,
}

struct AddOrUpdateDoctorDiagnosticResponse {
    255: required base.BaseResp BaseResp,
}

struct EnterApplyItemResultRequest {
    1: required apply_model.ApplyItem item,
}

struct EnterApplyItemResultResponse {
    255: required base.BaseResp BaseResp,
}

struct UpdateApplyItemRequest {
    1: required apply_model.ApplyItem item,
}

struct UpdateApplyItemResponse {
    255: required base.BaseResp BaseResp,
}

service DiagnoseService {
    GetFirstDiagDirResponse getFirstDiagDir(1:GetFirstDiagDirRequest req)
    AddOrUpdateFirstDiagDirResponse addOrUpdateFirstDiagDir(1:AddOrUpdateFirstDiagDirRequest req)
      
    GetSecondDiagDirResponse getSecondDiagDir(1:GetSecondDiagDirRequest req)
    AddOrUpdateSecondDiagDirResponse addOrUpdateSecondDiagDir(1:AddOrUpdateSecondDiagDirRequest req)
    
    GetDiagnosticResponse getDiagnostic(1:GetDiagnosticRequest req)
    AddOrUpdateDiagnosticResponse addOrUpdateDiagnostic(1:AddOrUpdateDiagnosticRequest req)

    GetSchedulePlanResponse getSchedulePlan(1:GetSchedulePlanRequest req)
    AddOrUpdateSchedulePlanResponse addOrUpdateSchedulePlan(1:AddOrUpdateSchedulePlanRequest req)

    GetScheduleRuleResponse getScheduleRule(1:GetScheduleRuleRequest req)
    AddOrUpdateScheduleRuleResponse addOrUpdateScheduleRule(1:AddOrUpdateScheduleRuleRequest req)

    GetRegistrationResponse getRegistration(1:GetRegistrationRequest req)
    AddOrUpdateRegistrationResponse addOrUpdateRegistration(1:AddOrUpdateRegistrationRequest req)
    CancelRegistrationResponse cancelRegistration(1:CancelRegistrationRequest req)
    GetRegistrationCountResponse getRegistrationCount(1:GetRegistrationCountRequest req)

    AddOrUpdateMedicalRecordTemplateResponse addOrUpdateMedicalRecordTemplate(1:AddOrUpdateMedicalRecordTemplateRequest req)
    GetMedicalRecordTemplateResponse getMedicalRecordTemplate(1:GetMedicalRecordTemplateRequest req)

    AddOrUpdateMedicalRecordResponse addOrUpdateMedicalRecord(1:AddOrUpdateMedicalRecordRequest req)
    GetMedicalRecordResponse getMedicalRecord(1:GetMedicalRecordRequest req)
    UpdateApplyItemResponse updateApplyItem(1:UpdateApplyItemRequest req)

    AddOrUpdateCommonlyUsedDiagnosticResponse addOrUpdateCommonlyUsedDiagnostic(1:AddOrUpdateCommonlyUsedDiagnosticRequest req)
    GetCommonlyUsedDiagnosticResponse getCommonlyUsedDiagnostic(1:GetCommonlyUsedDiagnosticRequest req)

    AddOrUpdateApplyResponse addOrUpdateApply(1:AddOrUpdateApplyRequest req)
    GetApplyResponse getApply(1:GetApplyRequest req)

    GetApplyItemResponse getApplyItem(1:GetApplyItemRequest req)
    EnterApplyItemResultResponse enterApplyItemResult(1:EnterApplyItemResultRequest req)

    AddOrUpdateApplyGroupResponse addOrUpdateApplyGroup(1:AddOrUpdateApplyGroupRequest req)
    GetApplyGroupResponse getApplyGroup(1:GetApplyGroupRequest req)
}