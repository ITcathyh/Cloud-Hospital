namespace java top.itcat.rpc.service.model

include "diagnose_model.thrift"
include "charge_model.thrift"

enum ApplyItemStatus {
    Unregistered = 0,
    Registered = 1,
    Done = 2,
    Cancel = 3,
}

enum ApplyCategory {
    Inspection = 0, // 检验
    Examination = 1, // 检查
    Disposal = 2, // 处置
}

struct Apply {
    1: optional i64 id,
    2: optional i64 medicalRecordNo,
    3: optional ApplyCategory category,
    4: optional i64 time,
    5: optional i32 status, // todo unused
    6: optional i64 doctorId,
//    7: optional ChargeStatusEnum chargeStatus,
//    8: optional ImplementStatusEnum implementStatus,
//    9: optional double price,
    10: optional list<ApplyItem> items,

    255: optional i32 valid,
}

struct ResultItem {
     1: optional i64 id;
     2: optional i64 itemId;
     3: optional string imagePath;
}

struct ApplyItem {
    1: optional i64 id,
    2: optional i64 applyId,
    3: optional i64 chargeItemId,
    4: optional i64 medicalDoctorId,
    5: optional string note,
    6: optional string result,
    7: optional ApplyItemStatus status,
    8: optional charge_model.ChargeItem chargeItem,
    9: optional i64 nonmedicalChargeId,
    10: optional list<ResultItem> resultItems,
    11: optional i64 operateTime,
    255: optional i32 valid,
}

enum DocumentCategory {
    Normal = 0,
    Ultrasound = 1,
}

struct ApplyGroup {
    1: optional i64 id,
    2: optional string code,
    3: optional string name,
    4: optional ApplyCategory category,
    5: optional DocumentCategory documentCategory,
    6: optional diagnose_model.ServiceObject serviceObject,
    7: optional diagnose_model.SuitableRangeEnum suitableRange,
    8: optional i64 creatorId,
    9: optional i64 departmentId,
    10: optional i64 createTime,
    11: optional string clinicalImpression,
    12: optional string clinicalDiagnosis,
    13: optional string goalAndRequirement,
    14: optional string remark,
    15: optional list<ApplyItemTemplate> items,
    255: optional i32 valid,
}

struct ApplyItemTemplate {
    1: optional i64 id,
    2: optional i64 groupId,
    3: optional i64 nonmedicalId,
    4: optional string note,
    255: optional i32 valid,
}