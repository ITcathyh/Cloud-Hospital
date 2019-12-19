namespace java top.itcat.rpc.service.model

include "diagnose_model.thrift"
include "medicine_model.thrift"

enum PrescriptionType {
    Normal = 0,
    Expert = 1,
}

enum PrescriptionCategoty {
    ChinesePatent = 0,
    Western = 1,
}

enum PrescriptionItemStatusEnum {
    Unattained = 0, // 未取药的
    Attained = 1,
    Return = 2,
}

enum PrescriptionGroupCatalogEnum {
    Chinese = 0,
    Western = 1,
}

struct Prescription{
    1: optional i64 id,
    2: optional i64 medicalRecordNo,
    3: optional PrescriptionType type,
    4: optional PrescriptionCategoty categoty,
    5: optional double price,
    6: optional string remark,
    7: optional i64 doctorId,
    8: optional list<PrescriptionItem> items,
    255: optional i32 valid,
}

struct PrescriptionItem{
    1: optional i64 id,
    2: optional i64 prescriptionId,
    3: optional i64 itemId, // 药品ID
    4: optional i64 chargeItemId,
    5: optional i64 medicalDoctorId, // 药房人员
    6: optional medicine_model.MedicineUsageEnum useMethod,
    7: optional string useFrequent,
    8: optional PrescriptionItemStatusEnum status,
    9: optional i32 num,
    255: optional i32 valid,
}

struct PrescriptionHerb{
    1: optional i64 id,
    2: optional i64 medicalRecordNo,
    3: optional PrescriptionType type,
    4: optional i32 num,
    5: optional i32 frequency,
    6: optional i32 usage,
    7: optional string treatment,
    8: optional string treatmentDetail,
    9: optional string advice,
    10: optional double price,
    11: optional list<PrescriptionHerbItem> items,
    12: optional i64 doctorId,
    13: optional i32 number,
    14: optional PrescriptionItemStatusEnum status,
    255: optional i32 valid,
}

struct PrescriptionHerbItem{
    1: optional i64 id,
    2: optional i64 prescriptionId,
    3: optional i64 itemId, // 药品ID
    4: optional i64 chargeItemId,
    5: optional i64 medicalDoctorId, // 药房人员
    6: optional double price,
    7: optional PrescriptionItemStatusEnum status,
    8: optional i32 num,
    255: optional i32 valid,
}

enum DocumentCategory {
    Normal = 0,
    Expert = 1,
}

struct PrescriptionGroup{
    1: optional i64 id,
    2: optional string code,
    3: optional string name,
    4: optional diagnose_model.ServiceObject serviceObject,
    5: optional diagnose_model.SuitableRangeEnum suitableRange,
    6: optional list<PrescriptionItemTemplate> items,
    7: optional i64 departmentId,
    8: optional i64 createTime,
    9: optional string remark,
    10: optional DocumentCategory documentCategory,
    11: optional i64 creatorId,
    12: optional PrescriptionGroupCatalogEnum catalog,
    255: optional i32 valid,
}

struct PrescriptionItemTemplate{
    1: optional i64 id,
    2: optional i64 groupId,
    3: optional i64 medicalId, // 药品ID
    4: optional string note,
    255: optional i32 valid,
}