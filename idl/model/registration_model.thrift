namespace java top.itcat.rpc.service.model

enum RegistrationSourceEnum {
    Site = 0,
    Online = 1,
    Special = 2,
}

enum RegistrationTypeEnum {
    Normal = 0,
    Special = 1,
    Emergency = 2,
}

enum RegistrationStatusEnum {
    UnSeen = 0,
    Done = 1,
    Cancel = 2;
}

struct Registration {
    1: optional i64 id,
    2: optional i64 medicalRecordNo,
    3: optional string identityCardNo,
    4: optional i64 schedulePlanId,
    5: optional RegistrationSourceEnum registrationSource,
    6: optional RegistrationStatusEnum status,
    7: optional i64 registrationTime,
    8: optional i64 seeDoctorTime,
    9: optional i64 billingCategoryId,
    10: optional i32 sequenceNumber,
    12: optional double expense,
    255: optional i32 valid,
}

struct RegistrationLevel {
    1: optional i64 id,
    2: optional string code,
    3: optional string name,
    4: optional bool is_default,
    5: optional i64 display_seq_num,
    6: optional double price,
    255: optional i32 valid,
}