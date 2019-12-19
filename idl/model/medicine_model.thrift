namespace java top.itcat.rpc.service.model

include "../model/diagnose_model.thrift"

enum MedicineCategoryEnum {
    WesternMedicine = 0,
    ChineseMedicine = 1,
    PatentDrug = 2,
}

enum MedicineUsageEnum {
    MedicineUsageEnum_Take = 0; // 口服
    MedicineUsageEnum_Drip = 1; // 静脉点滴
    MedicineUsageEnum_Injection = 2; // 皮下注射
}

struct Medicine {
    1: optional i64 id,
    2: optional string code,
    3: optional string name,
    4: optional string specification,
    5: optional string form,
    6: optional string packageUnit,
    7: optional i32 packageNum,
    8: optional double price
    9: optional MedicineCategoryEnum category,
    10: optional MedicineUsageEnum usage,
    11: optional double dosage,
    12: optional string unit,
    13: optional string advice,
    14: optional string factory,
    15: optional string pinyin,
    16: optional string frequency,
    17: optional i32 dayUsage,
    255: optional i32 valid,
}

struct CommonlyUsedMedicine{
    1: optional i64 id,
    2: optional i64 doctorId,
    3: optional i64 medicalId,
    4: optional MedicineCategoryEnum catalog,
    5: optional diagnose_model.SuitableRangeEnum suitableRange,
    6: optional i64 departmentId,
    255: optional i32 valid,
}