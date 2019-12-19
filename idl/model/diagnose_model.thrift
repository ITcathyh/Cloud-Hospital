namespace java top.itcat.rpc.service.model

struct FirstDiagDir {
    1: optional i64 id,
    2: optional string name,
    255: optional i32 valid,
}

struct SecondDiagDir {
    1: optional i64 id,
    2: optional string name,
    3: optional i64 firstDiagDirId,
    255: optional i32 valid,
}

struct Diagnostic {
    1: optional i64 id,
    2: optional string name,
    3: optional string code,
    4: optional i64 secondDiagDirId,
    255: optional i32 valid,
}

enum WorkDayEnum {
    Mon = 0,
    Tue = 1,
    Wed = 2,
    Thu = 3,
    Fri = 4,
    Sat = 5,
    Sun = 6,
}

enum WorkTimeEnum {
    AM = 0,
    PM = 1,
    Night = 2,
}

// 大于0可被正常查询
enum PlanValidEnum {
    Expired = 0,
    Normal = 1,
    Uncovered = 2, // 手动修改
}

struct ScheduleRule {
    1: optional i64 id,
    2: optional i64 doctorId,
    3: optional i64 departmentId,
    4: optional WorkDayEnum day,
    5: optional WorkTimeEnum noonBreak,
    6: optional i32 limitNumber,
    7: optional i32 onlineQuota,
    8: optional i32 operationDate
    9: optional i64 registrationLevelId,
    10: optional bool expired,
    11: optional i64 startTime,
    12: optional i64 endTime,
    255: optional i32 valid,
}

struct SchedulePlan {
    1: optional i64 id,
    2: optional i64 scheduleId,
    3: optional i64 startTime,
    4: optional i64 endTime,
    5: optional i32 remain,
    255: optional i32 valid,
}

enum GroupCategory {
    CheckApply = 0,
    Prescription = 1,
    ChineseMedical = 2,
    Handle = 3,
}

enum UseScope {
    Personal = 0,
    Depart = 1,
    Hospital = 2,
}

enum SuitableRangeEnum {
    Personal = 0,
    Depart = 1,
    Hospital = 2,
}

enum ServiceObject {
    Normal = 0, // 门诊
    Emergency = 1,
}

//struct Group {
//    1: optional i64 id,
//    2: optional string code,
//    3: optional string name,
//    4: optional GroupCategory category,
//    5: optional WorkTimeEnum documentCategory,
//    6: optional i32 serviceObject,
//    7: optional UseScope useScope,
//    8: optional i64 createrId,
//    9: optional i64 departmentId,
//    10: optional i64 time,
//    11: optional i64 clinicalImpression,
//    12: optional i64 clinicalDiagnosis,
//    13: optional i64 goalAndRequirement,
//    14: optional i64 remark,
//    255: optional i32 valid,
//}
//
//struct GroupItem {
//    1: optional i64 id,
//    2: optional i64 groupId,
//    3: optional i64 itemId, // 依据group分类区分
//    4: optional i32 num,
//    255: optional i32 valid,
//}
