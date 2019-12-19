namespace java top.itcat.rpc.service.order

include "base.thrift"
include "../model/charge_model.thrift"

struct AddOrUpdateChargeSubjectRequest {
    1: optional list<charge_model.ChargeSubject> beanList,
}

struct AddOrUpdateChargeSubjectResponse {
    255: required base.BaseResp BaseResp,
}

struct GetChargeSubjectRequest {
    1: optional list<i64> ids,
    2: optional string searchKey,
    3: optional charge_model.CatalogEnum catelog,
    110: optional i32 offset,
    111: optional i32 limit,
}

struct GetChargeSubjectResponse {
    1: optional list<charge_model.ChargeSubject> beanList,
    254: optional i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateDayKnotRequest {
    1: optional list<charge_model.DayKnot> beanList,
    2: optional list<charge_model.DayKnotItem> itemList,
}

struct AddOrUpdateDayKnotResponse {
    255: required base.BaseResp BaseResp,
}

struct GetDayKnotRequest {
    1: optional list<i64> ids,
    2: optional i64 tollCollectorId,
    3: optional i64 startTime,
    4: optional i64 endTime,
    110: optional i32 offset,
    111: optional i32 limit,
}

struct GetDayKnotResponse {
    1: optional list<charge_model.DayKnot> beanList,
    254: optional i32 total,
    255: required base.BaseResp BaseResp,
}

struct GetDayKnotItemRequest {
    1: optional list<i64> ids,
    2: optional i64 dayKnotId,
    5: optional i32 offset,
    6: optional i32 limit,
}

struct GetDayKnotItemResponse {
    1: optional list<charge_model.DayKnotItem> beanList,
    2: optional i32 total,
    255: required base.BaseResp BaseResp,
}

//struct AddOrUpdateDayKnotItemRequest {
//    1: optional list<charge_model.DayKnotItem> beanList,
//}
//
//struct AddOrUpdateDayKnotItemResponse {
//    255: required base.BaseResp BaseResp,
//}

struct GetChargeItemRequest {
    1: optional list<i64> ids,
    2: optional i64 operatorId,
    3: optional i64 endTime,
    4: optional i64 medicalRecordNo,
    5: optional i64 departmentId,
    6: optional list<i64> projectIds,
    7: optional i64 chargeSubjectId,
    8: optional bool canDayKnot,
    254: optional i32 offset,
    255: optional i32 limit,
}

struct GetChargeItemResponse {
    1: optional list<charge_model.ChargeItem> beanList,
    2: optional i32 total,
    255: required base.BaseResp BaseResp,
}

struct AddOrUpdateChargeItemRequest {
    1: optional list<charge_model.ChargeItem> beanList,
}

struct AddOrUpdateChargeItemResponse {
    1: optional list<charge_model.ChargeItem> beanList,
    255: required base.BaseResp BaseResp,
}

struct SettleChargeRequest {
    1: required i64 operatorId,
    2: required i64 endTime,
}

struct SettleChargeResponse {
    255: required base.BaseResp BaseResp,
}

struct CancelChargeRequest {
    1: required i64 operatorId,
    2: required list<i64> chargeItemIds,
}

struct CancelChargeResponse {
    255: required base.BaseResp BaseResp,
}

struct PayChargeRequest {
    1: required i64 operatorId,
    2: required i64 medicalRecordNo,
}

struct PayChargeResponse {
    255: required base.BaseResp BaseResp,
}

struct GetIndividualAmountRequest {
    1: optional i64 doctorId,
    2: optional i64 departId,
    3: optional list<i64> chargeSubjectIds,
    4: optional i64 startTime,
    5: optional i64 endTime,
    6: optional list<i64> chargeItemIds,
}

struct GetIndividualAmountResponse {
    1: optional map<i64, double> amountMap,
    255: required base.BaseResp BaseResp,
}

service OrderService {
    GetChargeItemResponse getChargeItem(1:GetChargeItemRequest req)
    AddOrUpdateChargeItemResponse addOrUpdateChargeItem(1:AddOrUpdateChargeItemRequest req)

    GetDayKnotItemResponse getDayKnotItem(1:GetDayKnotItemRequest req)
//    AddOrUpdateDayKnotItemResponse addOrUpdateDayKnotItem(1:AddOrUpdateDayKnotItemRequest req)

    GetDayKnotResponse getDayKnot(1:GetDayKnotRequest req)
    AddOrUpdateDayKnotResponse addOrUpdateDayKnot(1:AddOrUpdateDayKnotRequest req)

    GetChargeSubjectResponse getChargeSubject(1:GetChargeSubjectRequest req)
    AddOrUpdateChargeSubjectResponse addOrUpdateChargeSubject(1:AddOrUpdateChargeSubjectRequest req)

    SettleChargeResponse settleCharge(1:SettleChargeRequest req)
    CancelChargeResponse cancelCharge(1:CancelChargeRequest req)
    PayChargeResponse payCharge(1:PayChargeRequest req)

    GetIndividualAmountResponse getIndividualAmount(1:GetIndividualAmountRequest req)
}