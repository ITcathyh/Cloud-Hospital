namespace java top.itcat.rpc.service.model

include "../model/diagnose_model.thrift"

enum MedicalRecordStatusEnum {
    Unseen = 0,
    Done = 1, // 确诊
    Over = 2, // 诊毕
    Seen = 3, // 已就诊
}

struct MedicalRecord{
    1: optional i64 id,
    2: optional i64 medicalRecordNo,
    3: optional i64 doctorId,
    4: optional i64 time,
    5: optional string complain,
    6: optional string currentMedicalHistory,
    7: optional string currentMedicalTreatment,
    8: optional string allergyHistory,
    9: optional string pastMedicalHistory,
    10: optional string physicalCheckUp,
    11: optional string preliminaryDiagnosisWestern,
    12: optional string preliminaryDiagnosisChinese,
    13: optional MedicalRecordStatusEnum status,
    14: optional list<DoctorDiagnostic> doctorDiagnostics,
    255: optional i32 valid,
}

struct MedicalRecordTemplate{
    1: optional i64 id,
    2: optional string code,
    3: optional string name,
    4: optional diagnose_model.SuitableRangeEnum suitableRange,
    5: optional string complain,
    6: optional string currentMedicalHistory,
    7: optional string allergyHistory,
    8: optional string physicalCheckUp,
    9: optional string preliminaryDiagnosisWestern,
    10: optional string preliminaryDiagnosisChinese,
    11: optional i64 doctorId,
    12: optional list<DiagnosticForMedicalRecordTemplate> doctorDiagnostics,
    13: optional i64 departmentId,
    255: optional i32 valid,
}

enum DoctorDiagnosticCatalogEnum {
    Chinese = 0,
    Western = 1,
}

struct DoctorDiagnostic{ // 实际的诊断
    1: optional i64 id,
    2: optional i64 doctorId,
    3: optional i64 diagnosticId, // 疾病ID
    4: optional diagnose_model.SuitableRangeEnum suitableRange,
    5: optional DoctorDiagnosticCatalogEnum catalog,
    6: optional bool main, // 主诊
    7: optional bool suspect, // 疑似
    8: optional i64 medicalRecordNo,
    255: optional i32 valid,
}

struct CommonlyUsedDiagnostic{
    1: optional i64 id,
    2: optional i64 doctorId,
    3: optional i64 diagnosticId, // 疾病ID
    4: optional DoctorDiagnosticCatalogEnum catalog,
    5: optional diagnose_model.SuitableRangeEnum suitableRange,
    6: optional i64 departmentId,
    255: optional i32 valid,
}

struct DiagnosticForMedicalRecordTemplate{
    1: optional i64 id,
    2: optional i64 doctorId,
    3: optional i64 diagnosticId, // 疾病ID
    4: optional DoctorDiagnosticCatalogEnum catalog,
    5: optional i64 medicalRecordTemplateId,
    255: optional i32 valid,
}

