namespace java top.itcat.rpc.service.model

enum ChargeItemStatusEnum {
    Unpaid = 0,
    Paid = 1,
    Reversed = 2, // 已红冲
    Reverse = 3, // 红冲账单
    Obsolete = 4,
}

enum CatalogEnum {
    Nonmedical = 0,
    Medical = 1, // 成药
    Registration = 2,
    ChineseMedicine = 3, // 草药
}

struct ChargeSubject {
    1: optional i64 id,
    2: optional string code,
    3: optional string name,
    4: optional CatalogEnum catalog,
    255: optional i32 valid,
}

struct DayKnot {
    1: optional i64 id;
    2: optional i64 startTime;
    3: optional i64 endTime;
    4: optional double chargeAmount;
    5: optional i32 checkThrough;
    6: optional i64 operatorId;
    255: optional i32 valid,
}

struct DayKnotItem {
    1: optional i64 id;
    2: optional i64 chargeItemId;
    3: optional i64 dayKnotId;
    4: optional i64 chargeSubjectId;
    255: optional i32 valid,
}

struct ChargeItem {
    1: optional i64 id,
    2: optional string name,
    3: optional string specification,
    4: optional double unitPrice,
    5: optional double amount,
    6: optional string measureWord,
    7: optional double payable,
    8: optional double actuallyPaid,
    9: optional i64 medicalRecordNo,
    10: optional ChargeItemStatusEnum status,
    11: optional i64 billingCategoryId,
    12: optional i64 operatorId,
    13: optional i64 operationTime,
    14: optional bool dailyKnot,
    15: optional i64 chargeSubjectId
    16: optional i64 projectId,
    17: optional i64 departmentId,
    18: optional i64 creatorId,
    19: optional i64 createDepartmentId,
    255: optional i32 valid,
}