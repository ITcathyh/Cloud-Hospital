namespace java top.itcat.rpc.service.model

enum RoleEnum {
    Patient = 0,
    Doctor = 1,
    Medical_Doctor = 2,
    Toll_Collector = 3,
    Account_Clerk = 4,
    Hospital_Manager = 5,
    Pharmacy_Manager = 6,
}

enum GenderEnum {
    Man = 0,
    Woman = 1,
}

enum DoctorTitleEnum {
    Case = 0, // 主治医师
    House = 1, // 住院医生
    AssistantDirector = 2, // 副主任医生
    Director = 3, // 主任医生
}

// 兼容现场挂号
struct BasePatientInfo {
    1: optional string phone,
    2: optional string name,
    3: optional string idNum,
    4: optional GenderEnum gender,
    5: optional i32 age,
    6: optional string address,
    7: optional i32 birth,
}

struct Patient {
    1: optional BasePatientInfo userInfo,
}

struct User {
    1: optional i64 id,
    2: optional string username,
    3: optional string password,
    4: optional RoleEnum role,
    5: optional string code,
    6: optional string realName,
    7: optional i64 departId,
}

struct OutpatientDoctor {
    1: optional User user,
    2: optional DoctorTitleEnum title,
    3: optional string description,
    4: optional bool inSchedual,
    255: optional i32 valid,
}

struct MedicalDoctor {
    1: optional User user,
    2: optional DoctorTitleEnum title,
    3: optional string description,
    255: optional i32 valid,
}

struct TollCollector {
    1: optional User user,
    255: optional i32 valid,
}

struct AccountClerk {
    1: optional User user,
    255: optional i32 valid,
}

struct HospitalManager {
    1: optional User user,
    255: optional i32 valid,
}

struct PharmacyManager {
    1: optional User user,
    255: optional i32 valid,
}

struct PackedUser {
    1: optional User user,

     // 医生专有字段
    2: optional DoctorTitleEnum title,
    3: optional string description,
 }

struct PatientWechatSignature {
    1: optional i64 id,
    2: optional string signature,
    3: optional string identityCardNo,
    4: optional i32 valid,
}

struct PatientAccount {
    1: optional i64 id,
    2: optional i64 pid,
    3: optional string username,
    4: optional string password,
    5: optional Patient info,
    6: optional i32 valid,
}
