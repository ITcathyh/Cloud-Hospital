namespace java top.itcat.rpc.service.hospital

include "base.thrift"
include "../model/hospital_model.thrift"

struct GetDepartmentRequest {
    1: optional list<i64> ids,
    2: optional string code,
    3: optional string name,
    4: optional hospital_model.DepartClassificationEnum classification,
    5: optional string catalog,
    100: optional i32 offset,
    101: optional i32 limit,
    102: optional string searchKey,
}

struct GetDepartmentResponse {
    1: optional list<hospital_model.Department> beanList,
    2: i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateDepartmentRequest {
    1: required list<hospital_model.Department> beanList,
}

struct AddOrUpdateDepartmentResponse {
    255: required base.BaseResp BaseResp,
}

struct GetBillingCategoryRequest {
    1: optional list<i64> ids,
}

struct GetBillingCategoryResponse {
    1: optional list<hospital_model.BillingCategory> beanList,
    2: i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateBillingCategoryRequest {
    1: required list<hospital_model.BillingCategory> beanList,
}

struct AddOrUpdateBillingCategoryResponse {
    255: required base.BaseResp BaseResp,
}

struct GetRegisterationLevelRequest {
    1: optional list<i64> ids,
    2: optional string code,
}

struct GetRegisterationLevelResponse {
    1: optional list<hospital_model.RegisterationLevel> beanList,
    2: i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateRegisterationLevelRequest {
    1: required list<hospital_model.RegisterationLevel> beanList,
}

struct AddOrUpdateRegisterationLevelResponse {
    255: required base.BaseResp BaseResp,
}

struct GetNonmedicalChargeRequest {
    1: optional list<i64> ids,
    2: optional list<i64> chargeSubjectIds,
    3: optional hospital_model.NonmedicalChargeCategoryEnum category,
    4: optional i64 departmentId,
    100: optional i32 offset,
    101: optional i32 limit,
    102: optional string searchKey,
}

struct GetNonmedicalChargeResponse {
    1: optional list<hospital_model.NonmedicalCharge> beanList,
    2: i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateNonmedicalChargeRequest {
    1: required list<hospital_model.NonmedicalCharge> beanList,
}

struct AddOrUpdateNonmedicalChargeResponse {
    255: required base.BaseResp BaseResp,
}

struct GetDepartStatisticalDataRequest {
    1: optional list<i64> departIds,
    2: required i64 startTime,
    3: required i64 endTime,
}

struct GetDepartStatisticalDataResponse {
    1: optional list<hospital_model.DepartStatisticalData> beanList,
    255: required base.BaseResp BaseResp,
}

struct GetDoctorStatisticalDataRequest {
    1: optional list<i64> doctorIds,
    2: required i64 startTime,
    3: required i64 endTime,
}

struct GetDoctorStatisticalDataResponse {
    1: optional list<hospital_model.DoctorStatisticalData> beanList,
    255: required base.BaseResp BaseResp,
}

service HospitalService {
    GetDepartmentResponse getDepartment(1:GetDepartmentRequest req)
    AddOrUpdateDepartmentResponse addOrUpdateDepartment(1:AddOrUpdateDepartmentRequest req)

    GetBillingCategoryResponse getBillingCategory(1:GetBillingCategoryRequest req)
    AddOrUpdateBillingCategoryResponse addOrUpdateBillingCategory(1:AddOrUpdateBillingCategoryRequest req)

    GetRegisterationLevelResponse getRegisterationLevel(1:GetRegisterationLevelRequest req)
    AddOrUpdateRegisterationLevelResponse addOrUpdateRegisterationLevel(1:AddOrUpdateRegisterationLevelRequest req)

    GetNonmedicalChargeResponse getNonmedicalCharge(1:GetNonmedicalChargeRequest req)
    AddOrUpdateNonmedicalChargeResponse addOrUpdateNonmedicalCharge(1:AddOrUpdateNonmedicalChargeRequest req)

    GetDepartStatisticalDataResponse getDepartStatistical(1:GetDepartStatisticalDataRequest req)
    GetDoctorStatisticalDataResponse getDoctorStatistical(1:GetDoctorStatisticalDataRequest req)
}