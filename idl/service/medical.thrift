namespace java top.itcat.rpc.service.medical

include "base.thrift"
include "../model/diagnose_model.thrift"
include "../model/prescription_model.thrift"
include "../model/medicine_model.thrift"
include "../model/charge_model.thrift"

struct AddOrUpdatePrescriptionGroupRequest {
    1: optional prescription_model.PrescriptionGroup bean,
}

struct AddOrUpdatePrescriptionGroupResponse {
    255: required base.BaseResp BaseResp,
}

struct GetPrescriptionGroupRequest {
    1: optional list<i64> ids,
    2: optional list<diagnose_model.SuitableRangeEnum> range,
    3: optional i64 createrId,
    4: optional i64 departmentId,
    5: optional string searchKey,
    6: optional prescription_model.PrescriptionGroupCatalogEnum catalog,
    100: optional i32 offset,
    101: optional i32 limit,
}

struct GetPrescriptionGroupResponse {
    1: optional list<prescription_model.PrescriptionGroup> beanList,
    254: required i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdatePrescriptionRequest {
    1: optional prescription_model.Prescription bean,
}

struct AddOrUpdatePrescriptionResponse {
    255: required base.BaseResp BaseResp,
}

struct GetPrescriptionRequest {
    1: optional list<i64> ids,
    2: optional i64 doctorId,
    3: optional i64 medicalRecordNo,
    100: optional i32 offset,
    101: optional i32 limit,
}

struct GetPrescriptionResponse {
    1: optional list<prescription_model.Prescription> beanList,
    254: required i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateMedicineRequest {
    1: optional list<medicine_model.Medicine> bean,
}

struct AddOrUpdateMedicineResponse {
    255: required base.BaseResp BaseResp,
}

struct GetMedicineRequest {
    1: optional list<i64> ids,
    2: optional string searchKey,
    3: optional medicine_model.MedicineCategoryEnum category,
    100: optional i32 offset,
    101: optional i32 limit,
}

struct GetMedicineResponse {
    1: optional list<medicine_model.Medicine> beanList,
    254: required i32 total,
    255: required base.BaseResp BaseResp,
}

struct GetPatientMedicineRequest {
    1: optional i64 medicalRecordNo,
    2: optional i64 time,
//    3: optional
    100: optional i32 offset,
    101: optional i32 limit,
}

struct GetPatientMedicineResponse {
    1: optional list<medicine_model.Medicine> beanList,
    254: required i32 total,
    255: required base.BaseResp BaseResp,
}

struct DispenseMedicineRequest {
    1: optional list<i64> ids,
    2: optional i64 operatorId,
    3: optional i64 medicalRecordNo,
}

struct DispenseMedicineResponse {
    1: optional map<i64, bool> resultMap,
    255: required base.BaseResp BaseResp,
}

struct DrugRepercussionRequest {
    1: optional i64 id,
    2: optional i64 operatorId,
    3: optional prescription_model.PrescriptionGroupCatalogEnum catalog,
    4: optional i32 num,
//    3: optional i64 medicalRecordNo,
}

struct DrugRepercussionResponse {
//    1: optional map<i64, bool> resultMap,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdatePrescriptionHerbRequest {
    1: optional prescription_model.PrescriptionHerb bean,
}

struct AddOrUpdatePrescriptionHerbResponse {
    255: required base.BaseResp BaseResp,
}

struct GetPrescriptionHerbRequest {
    1: optional list<i64> ids,
    2: optional i64 doctorId,
    3: optional i64 medicalRecordNo,
    100: optional i32 offset,
    101: optional i32 limit,
}

struct GetPrescriptionHerbResponse {
    1: optional list<prescription_model.PrescriptionHerb> beanList,
    254: required i32 total,
    255: required base.BaseResp BaseResp,
}

struct GetPrescriptionItemRequest {
//    1: optional list<i64> ids,
    2: optional charge_model.CatalogEnum catalogEnum,
    3: optional i64 chargeItemId,
//    3: optional i64 medicalRecordNo,
}

struct GetPrescriptionItemResponse {
    1: optional list<prescription_model.PrescriptionItem> prescriptionItems,
    2: optional list<prescription_model.PrescriptionHerbItem> prescriptionHerbItems,
    254: required i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateCommonlyUsedMedicineRequest {
    1: optional list<medicine_model.CommonlyUsedMedicine> beanList,
}

struct AddOrUpdateCommonlyUsedMedicineResponse {
    255: required base.BaseResp BaseResp,
}

struct GetCommonlyUsedMedicineRequest {
    1: optional list<i64> ids,
    2: optional list<diagnose_model.SuitableRangeEnum> range,
    3: optional i64 doctorId,
    4: optional i64 departmentId,
    5: optional medicine_model.MedicineCategoryEnum catalog,
    100: optional i32 offset,
    101: optional i32 limit,
}

struct GetCommonlyUsedMedicineResponse {
    1: optional list<medicine_model.CommonlyUsedMedicine> beanList,
    254: required i32 total,
    255: required base.BaseResp BaseResp,
}

service MedicalService {
    AddOrUpdatePrescriptionGroupResponse addOrUpdatePrescriptionGroup(1:AddOrUpdatePrescriptionGroupRequest req)
    GetPrescriptionGroupResponse getPrescriptionGroup(1:GetPrescriptionGroupRequest req)

    AddOrUpdatePrescriptionResponse addOrUpdatePrescription(1:AddOrUpdatePrescriptionRequest req)
    GetPrescriptionResponse getPrescription(1:GetPrescriptionRequest req)

    AddOrUpdateMedicineResponse addOrUpdateMedicine(1:AddOrUpdateMedicineRequest req)
    GetMedicineResponse getMedicine(1:GetMedicineRequest req)

    DispenseMedicineResponse dispenseMedicine(1:DispenseMedicineRequest req)
    DrugRepercussionResponse drugRepercussion(1:DrugRepercussionRequest req)

    AddOrUpdatePrescriptionHerbResponse addOrUpdatePrescriptionHerb(1:AddOrUpdatePrescriptionHerbRequest req)
    GetPrescriptionHerbResponse getPrescriptionHerb(1:GetPrescriptionHerbRequest req)

    GetPrescriptionItemResponse getPrescriptionItem(1:GetPrescriptionItemRequest req)

    AddOrUpdateCommonlyUsedMedicineResponse addOrUpdateCommonlyUsedMedicine(1:AddOrUpdateCommonlyUsedMedicineRequest req)
    GetCommonlyUsedMedicineResponse getCommonlyUsedMedicine(1:GetCommonlyUsedMedicineRequest req)
}