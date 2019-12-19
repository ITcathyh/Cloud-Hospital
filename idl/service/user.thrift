namespace java top.itcat.rpc.service.user

include "base.thrift"
include "../model/user_model.thrift"

struct MGetDoctorRequest {
    1: optional i64 departId,
    2: optional user_model.DoctorTitleEnum title,
    3: optional list<i64> uids,
    4: optional string name,
    5: optional string idNum,
    100: optional i32 offset,
    101: optional i32 limit,
    102: optional string searchKey,
}

struct MGetDoctorResponse {
    1: optional list<user_model.OutpatientDoctor> users,
    2: i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateDoctorRequest {
    1: required list<user_model.OutpatientDoctor> users,
}

struct AddOrUpdateDoctorResponse {
    1: optional list<user_model.OutpatientDoctor> users,
    255: required base.BaseResp BaseResp,
}

struct MGetTollCollectorRequest {
    1: optional list<i64> uids,
    100: optional i32 offset,
    101: optional i32 limit,
    102: optional string searchKey,
}

struct MGetTollCollectorResponse {
    1: optional list<user_model.TollCollector> users,
    2: i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateTollCollectorRequest {
    1: required list<user_model.TollCollector> users,
}

struct AddOrUpdateTollCollectorResponse {
    1: optional list<user_model.TollCollector> users,
    255: required base.BaseResp BaseResp,
}

struct MGetAccountClerkRequest {
    1: optional list<i64> uids,
    100: optional i32 offset,
    101: optional i32 limit,
    102: optional string searchKey,
}

struct MGetAccountClerkResponse {
    1: optional list<user_model.AccountClerk> users,
    2: i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateAccountClerkRequest {
    1: required list<user_model.AccountClerk> users,
}

struct AddOrUpdateAccountClerkResponse {
    1: optional list<user_model.AccountClerk> users,
    255: required base.BaseResp BaseResp,
}

struct MGetHospitalManagerRequest {
    1: optional list<i64> uids,
    100: optional i32 offset,
    101: optional i32 limit,
    102: optional string searchKey,
}

struct MGetHospitalManagerResponse {
    1: optional list<user_model.HospitalManager> users,
    2: i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateHospitalManagerRequest {
    1: required list<user_model.HospitalManager> users,
}

struct AddOrUpdateHospitalManagerResponse {
    1: optional list<user_model.HospitalManager> users,
    255: required base.BaseResp BaseResp,
}

struct MGetPharmacyManagerRequest {
    1: optional list<i64> uids,
    100: optional i32 offset,
    101: optional i32 limit,
    102: optional string searchKey,
}

struct MGetPharmacyManagerResponse {
    1: optional list<user_model.PharmacyManager> users,
    2: optional i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdatePharmacyManagerRequest {
    1: required list<user_model.PharmacyManager> users,
}

struct AddOrUpdatePharmacyManagerResponse {
    1: optional list<user_model.PharmacyManager> users,
    255: required base.BaseResp BaseResp,
}

struct MGetPatientRequest {
    1: optional list<string> idNums,
    100: optional i32 offset,
    101: optional i32 limit,
    102: optional string searchKey,
}

struct MGetPatientResponse {
    1: optional list<user_model.Patient> users,
    2: optional i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdatePatientRequest {
    1: required list<user_model.Patient> users,
}

struct AddOrUpdatePatientResponse {
    255: required base.BaseResp BaseResp,
}

struct GetUserByUsernameAndPasswordRequest {
    1: required string username,
    2: required string password,
    // 废弃
    3: optional user_model.RoleEnum role,
}

struct GetUserByUsernameAndPasswordResponse {
    1: optional user_model.PackedUser user,
    255: required base.BaseResp BaseResp,
}

struct MGetMedicalDoctorRequest {
    1: optional i64 departId,
    2: optional user_model.DoctorTitleEnum title,
    3: optional list<i64> uids,
    4: optional string name,
    5: optional string idNum,
    100: optional i32 offset,
    101: optional i32 limit,
    102: optional string searchKey,
}

struct MGetMedicalDoctorResponse {
    1: optional list<user_model.MedicalDoctor> users,
    2: optional i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateMedicalDoctorRequest {
    1: required list<user_model.MedicalDoctor> users,
}

struct AddOrUpdateMedicalDoctorResponse {
    1: optional list<user_model.MedicalDoctor> users,
    255: required base.BaseResp BaseResp,
}

struct GetUserIdsRequest {
    1: optional list<user_model.RoleEnum> roles,
    2: optional i64 departId,
}

struct GetUserIdsResponse {
    1: optional map<user_model.RoleEnum,list<i64> >ids,
    255: required base.BaseResp BaseResp,
}

struct EditPwdRequest {
    1: required i64 id,
    2: required string code,
    3: required string oldPwd,
    4: required string pwd,
}

struct EditPwdResponse {
    255: required base.BaseResp BaseResp,
}

struct GetPatientWechatSignatureRequest {
    1: optional string searchKey,
}

struct GetPatientWechatSignatureResponse {
    1: optional user_model.PatientWechatSignature signature,
    2: optional user_model.Patient info,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdatePatientWechatSignatureRequest {
    1: optional user_model.PatientWechatSignature signature ,
}

struct AddOrUpdatePatientWechatSignatureResponse {
    255: required base.BaseResp BaseResp,
}

struct GetPatientAccountRequest {
    1: optional string username,
    2: optional string password,
}

struct GetPatientAccountResponse {
    1: optional user_model.Patient info,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdatePatientAccountRequest {
    1: required user_model.PatientAccount account ,
}

struct AddOrUpdatePatientAccountResponse {
    255: required base.BaseResp BaseResp,
}

service UserService {
    MGetPatientResponse mGetPatient(1:MGetPatientRequest req)
    MGetTollCollectorResponse mGetTollCollector(1:MGetTollCollectorRequest req)
    MGetDoctorResponse mGetDoctor(1:MGetDoctorRequest req)
    MGetMedicalDoctorResponse mGetMedicalDoctor(1:MGetMedicalDoctorRequest req)
    MGetAccountClerkResponse mGetAccountClerk(1:MGetAccountClerkRequest req)
    MGetHospitalManagerResponse mGetHospitalManager(1:MGetHospitalManagerRequest req)
    MGetPharmacyManagerResponse mGetPharmacyManager(1:MGetPharmacyManagerRequest req)

    AddOrUpdatePatientResponse addOrUpdatePatient(1:AddOrUpdatePatientRequest req)
    AddOrUpdateTollCollectorResponse addOrUpdateTollCollector(1:AddOrUpdateTollCollectorRequest req)
    AddOrUpdateDoctorResponse addOrUpdateDoctor(1:AddOrUpdateDoctorRequest req)
    AddOrUpdateMedicalDoctorResponse addOrUpdateMedicalDoctor(1:AddOrUpdateMedicalDoctorRequest req)
    AddOrUpdateAccountClerkResponse addOrUpdateAccountClerk(1:AddOrUpdateAccountClerkRequest req)
    AddOrUpdateHospitalManagerResponse addOrUpdateHospitalManager(1:AddOrUpdateHospitalManagerRequest req)
    AddOrUpdatePharmacyManagerResponse addOrUpdatePharmacyManager(1:AddOrUpdatePharmacyManagerRequest req)

    GetUserByUsernameAndPasswordResponse getUserByUsernameAndPassword(1:GetUserByUsernameAndPasswordRequest req)

    GetUserIdsResponse getUserIds(1:GetUserIdsRequest req)
    EditPwdResponse editPwd(1:EditPwdRequest req)

    AddOrUpdatePatientWechatSignatureResponse addOrUpdatePatientWechatSignature(1:AddOrUpdatePatientWechatSignatureRequest req)
    GetPatientWechatSignatureResponse getPatientWechatSignature(1:GetPatientWechatSignatureRequest req)
    AddOrUpdatePatientAccountResponse addOrUpdatePatientAccount(1:AddOrUpdatePatientAccountRequest req)
    GetPatientAccountResponse getPatientAccount(1:GetPatientAccountRequest req)

}