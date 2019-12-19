namespace java top.itcat.rpc.service.model

enum DepartClassificationEnum {
    Clinical = 0,
    Medical = 1,
    Finance = 2,
    Administrative = 3,
}

enum NonmedicalChargeCategoryEnum {
    Inspection = 0; // 检验
    Examination = 1; // 检查
    Disposal = 2; // 处置
}

struct Department {
    1: optional i64 id,
    2: optional string code,
    3: optional string name,
    4: optional DepartClassificationEnum classification,
    5: optional string category,
    255: optional i32 valid,
}

struct BillingCategory {
    1: optional i64 id,
    2: optional string name,
    3: optional double discount,
    255: optional i32 valid,
}

struct RegisterationLevel {
    1: optional i64 id,
    2: optional string code,
    3: optional string name,
    4: optional bool isDefault,
    5: optional i32 displaySeqNum,
    6: optional double price,
    255: optional i32 valid,
}

struct NonmedicalCharge {
    1: optional i64 id,
    2: optional string code,
    3: optional string name,
    4: optional double price,
    5: optional string description,
    6: optional string format,
    7: optional NonmedicalChargeCategoryEnum category,
    8: optional i64 chargeSubjectId,
    9: optional i64 departmentId,
    10: optional string pinyin,
    255: optional i32 valid,
}

struct BaseStatisticalData {
    1: optional double otherFee,
    2: optional i32 patientNum,
    3: optional i32 prescriptionNum,
    4: optional double westernMedicineFee,
    5: optional double chineseMedicineFee,
    6: optional double patentMedicineFee, // 中成药
    7: optional double registrationFee,
    8: optional double examinationFee, // 诊查费
    9: optional double verificationFee, // 校验费
    10: optional double inspectionFee, // 检查费
    11: optional double treatFee,
    12: optional double treatFeeIncludeMaterial,
    13: optional double operationFee,
    255: optional i32 valid,
}

struct DepartStatisticalData {
    1: optional i64 departId,
    2: optional map<i64, double> chargeDetail,
    3: optional i32 registrationCount,
}

struct DoctorStatisticalData {
    1: optional i64 doctorId,
    2: optional i32 registrationCount,
    3: optional i32 prescriptionNum,
    4: optional map<i64, double> chargeDetail,
}