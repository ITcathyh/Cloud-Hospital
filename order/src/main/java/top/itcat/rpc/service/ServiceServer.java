package top.itcat.rpc.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.itcat.action.TransactionalAction;
import top.itcat.entity.ChargeItem;
import top.itcat.entity.ChargeSubject;
import top.itcat.entity.DayKnot;
import top.itcat.entity.DayKnotItem;
import top.itcat.exception.CommonException;
import top.itcat.exception.EmptyResultException;
import top.itcat.exception.InternalException;
import top.itcat.exception.InvalidParamException;
import top.itcat.rpc.service.model.ChargeItemStatusEnum;
import top.itcat.rpc.service.order.*;
import top.itcat.rpc.thrift.ThriftUtil;
import top.itcat.service.ChargeItemService;
import top.itcat.service.ChargeSubjectService;
import top.itcat.service.DayKnotItemService;
import top.itcat.service.DayKnotService;
import top.itcat.util.PinYinUtil;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
@SuppressWarnings("unchecked")
public class ServiceServer implements OrderService.Iface {
    @Autowired
    private ChargeItemService chargeItemService;
    @Autowired
    private ChargeSubjectService chargeSubjectService;
    @Autowired
    private DayKnotService dayKnotService;
    @Autowired
    private DayKnotItemService dayKnotItemService;
    @Autowired
    private TransactionalAction transactionalAction;

    @Override
    public GetChargeItemResponse getChargeItem(GetChargeItemRequest req) throws TException {
        GetChargeItemResponse rsp = new GetChargeItemResponse();
        List<ChargeItem> list;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1).orderBy("id desc");
        int offset = 0;
        int limit = 100;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetIds()) {
            wrapper = wrapper.in("id", req.getIds());
        }

        if (req.isSetOperatorId()) {
            wrapper = wrapper.eq("operator_id", req.getOperatorId());
        }

        if (req.isSetMedicalRecordNo()) {
            wrapper = wrapper.eq("medical_record_no", req.getMedicalRecordNo());
        }

        if (req.isSetChargeSubjectId()) {
            wrapper = wrapper.eq("charge_subject_id", req.getChargeSubjectId());
        }

        if (req.isSetProjectIds()) {
            wrapper = wrapper.in("project_id", req.getProjectIds());
        }

        if (req.isSetEndTime()) {
            wrapper = wrapper.le("operation_time", req.getEndTime());
        }

        if (req.isSetCanDayKnot()) {
            if (!req.isSetOperatorId()) {
                rsp.setBaseResp(ThriftUtil.getBaseResp(new InvalidParamException()));
                return rsp;
            }

            wrapper = wrapper.eq("daily_knot", 0);
        }

        Page<ChargeItem> page = null;
        try {
            page = chargeItemService.selectPage(
                    new Page<>(offset, limit), wrapper);
        } catch (Exception e) {
            log.error("getChargeItem err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        list = page.getRecords();
        List<top.itcat.rpc.service.model.ChargeItem> rpcList =
                list.parallelStream().map(ChargeItem::convertRPCBean).collect(Collectors.toList());

        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateChargeItemResponse addOrUpdateChargeItem(AddOrUpdateChargeItemRequest req) throws TException {
        AddOrUpdateChargeItemResponse rsp = new AddOrUpdateChargeItemResponse();
        List<ChargeItem> list =
                req.getBeanList().parallelStream().map(ChargeItem::convert).collect(Collectors.toList());
        try {
            chargeItemService.insertOrUpdateBatch(list);
        } catch (Exception e) {
            log.error("addOrUpdateChargeItem err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBeanList(list.parallelStream()
                .map(ChargeItem::convertRPCBean)
                .collect(Collectors.toList()));
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetDayKnotItemResponse getDayKnotItem(GetDayKnotItemRequest req) throws TException {
        GetDayKnotItemResponse rsp = new GetDayKnotItemResponse();
        List<DayKnotItem> list;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1);
        int offset = 0;
        int limit = 100;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetIds()) {
            wrapper = wrapper.in("id", req.getIds());
        }

//        if (req.isSetTollCollectorId()) {
//            wrapper = wrapper.eq("toll_collector_id", req.getTollCollectorId());
//        }

//        if (req.isSetSearchKey()) {
//            wrapper = wrapper.like("charge_item_id", req.getSearchKey())
//                    .or()
//                    .like("day_knot_id", req.getSearchKey())
//                    .or()
//                    .like("charge_subject_id", req.getSearchKey());
//        }
        if (req.isSetDayKnotId()) {
            wrapper = wrapper.eq("day_knot_id", req.getDayKnotId());
        }

        Page<DayKnotItem> page = null;
        try {
            page = dayKnotItemService.selectPage(
                    new Page<>(offset, limit), wrapper);
        } catch (Exception e) {
            log.error("getDayKnotItem err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        list = page.getRecords();
        page.setTotal(dayKnotItemService.selectCount(wrapper));
        List<top.itcat.rpc.service.model.DayKnotItem> rpcList =
                list.parallelStream().map(DayKnotItem::convertRPCBean).collect(Collectors.toList());

        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetDayKnotResponse getDayKnot(GetDayKnotRequest req) throws TException {
        GetDayKnotResponse rsp = new GetDayKnotResponse();
        List<DayKnot> list;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1);
        int offset = 0;
        int limit = 100;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetIds()) {
            wrapper = wrapper.in("id", req.getIds());
        }

        if (req.isSetStartTime()) {
            wrapper = wrapper.ge("end_time", req.getStartTime());
        }

        if (req.isSetEndTime()) {
            wrapper = wrapper.le("end_time", req.getEndTime());
        }

        if (req.isSetTollCollectorId()) {
            wrapper = wrapper.eq("operator_id", req.getTollCollectorId());
        }

//        if (req.isSetSearchKey()) {
//            wrapper = wrapper.like("start_time", req.getSearchKey())
//                    .or()
//                    .like("end_time", req.getSearchKey())
//                    .or()
//                    .like("charge_amount", req.getSearchKey())
//                    .or()
//                    .like("check_through", req.getSearchKey())
//                    .or()
//                    .like("operator_id", req.getSearchKey());
//        }
        wrapper = wrapper.orderBy("id", false);

        Page<DayKnot> page = null;
        try {
            page = dayKnotService.selectPage(
                    new Page<>(offset, limit), wrapper);
        } catch (Exception e) {
            log.error("getDayKnot err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        list = page.getRecords();
        page.setTotal(dayKnotService.selectCount(wrapper));
        List<top.itcat.rpc.service.model.DayKnot> rpcList =
                list.parallelStream().map(DayKnot::convertRPCBean).collect(Collectors.toList());

        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    //     todo 仅用于财务更新是否审核通过？
    @Override
    public AddOrUpdateDayKnotResponse addOrUpdateDayKnot(AddOrUpdateDayKnotRequest req) throws TException {
        AddOrUpdateDayKnotResponse rsp = new AddOrUpdateDayKnotResponse();
        List<DayKnot> list = req.getBeanList()
                .parallelStream()
                .map(DayKnot::convert)
                .collect(Collectors.toList());
        try {
            dayKnotService.insertOrUpdateBatch(list);
        } catch (Exception e) {
            log.error("addOrUpdateDayKnot err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetChargeSubjectResponse getChargeSubject(GetChargeSubjectRequest req) throws TException {
        GetChargeSubjectResponse rsp = new GetChargeSubjectResponse();
        Wrapper wrapper = new EntityWrapper().eq("valid", 1);
        int offset = 0;
        int limit = 100;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetIds()) {
            wrapper = wrapper.in("id", req.getIds());
        }

        if (req.isSetCatelog()) {
            wrapper = wrapper.eq("catalog", req.getCatelog().getValue());
        }

        if (req.isSetSearchKey()) {
            wrapper = wrapper.andNew()
                    .like("code", req.getSearchKey())
                    .or()
                    .like("name", req.getSearchKey());
        }

        Page<ChargeSubject> page = null;
        try {
            page = chargeSubjectService.selectPage(
                    new Page<>(offset, limit), wrapper);
            page.setTotal(chargeSubjectService.selectCount(wrapper));
        } catch (Exception e) {
            log.error("getChargeSubject err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        List<top.itcat.rpc.service.model.ChargeSubject> rpcList =
                page.getRecords().parallelStream().map(ChargeSubject::convertRPCBean).collect(Collectors.toList());

        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateChargeSubjectResponse addOrUpdateChargeSubject(AddOrUpdateChargeSubjectRequest req) throws TException {
        AddOrUpdateChargeSubjectResponse rsp = new AddOrUpdateChargeSubjectResponse();

        try {
            List<ChargeSubject> list = req.getBeanList()
                    .parallelStream().map(ChargeSubject::convert).collect(Collectors.toList());


            for (ChargeSubject chargeSubject : list) {
                if (chargeSubject.getName() != null) {
                    chargeSubject.setCode(PinYinUtil.getFirstPinYin(chargeSubject.getName()));
                }
            }

            chargeSubjectService.insertOrUpdateBatch(list);
        } catch (Exception e) {
            log.error("addOrUpdateChargeSubject err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public SettleChargeResponse settleCharge(SettleChargeRequest req) throws TException {
        SettleChargeResponse rsp = new SettleChargeResponse();
        long endTime = req.getEndTime();
        long operatorId = req.getOperatorId();

        List<ChargeItem> chargeItems = null;
        try {
            chargeItems = chargeItemService.selectList(new EntityWrapper<ChargeItem>()
                    .eq("operator_id", operatorId)
                    .le("operation_time", endTime)
                    .eq("daily_knot", 0));
        } catch (Exception e) {
            log.error("settleCharge err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        if (chargeItems == null || chargeItems.isEmpty()) {
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InvalidParamException()));
            return rsp;
        }

        List<DayKnotItem> dayKnotItems = new ArrayList<>(chargeItems.size());
        DayKnot dayKnot = new DayKnot();
        double amount = 0;

        for (ChargeItem item : chargeItems) {
            DayKnotItem dayKnotItem = new DayKnotItem();
            dayKnotItem.setChargeItemId(item.getId());
            dayKnotItem.setChargeSubjectId(item.getChargeSubjectId());
            amount += item.getAmount();
            dayKnotItems.add(dayKnotItem);
            item.setDailyKnot(1);
        }

        dayKnot.setChargeAmount(amount);
        dayKnot.setEndTime(endTime);
        dayKnot.setStartTime(chargeItems
                .parallelStream()
                .min((d1, d2) -> (int) (d1.getOperationTime() - d2.getOperationTime()))
                .get()
                .getOperationTime());
        dayKnot.setOperatorId(operatorId);

        try {
            if (!transactionalAction.settleCharge(dayKnotItems, dayKnot, chargeItems)) {
                rsp.setBaseResp(ThriftUtil.getBaseResp(new InvalidParamException()));
                return rsp;
            }
        } catch (Exception e) {
            if (!(e instanceof CommonException)) {
                log.error("settleCharge err:", e);
            }

            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public CancelChargeResponse cancelCharge(CancelChargeRequest req) throws TException {
        CancelChargeResponse rsp = new CancelChargeResponse();
        String redisKey = "update_charge:%d";
        List<ChargeItem> chargeItems = null;

        try {
            chargeItems = chargeItemService
                    .selectList(new EntityWrapper<ChargeItem>()
                            .in("id", req.getChargeItemIds())
                            .eq("daily_knot", 0)
                            .eq("status", ChargeItemStatusEnum.Paid.getValue())
                            .eq("valid", 1));
        } catch (Exception e) {
            log.error("cancelCharge err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        List<ChargeItem> copiedItems = new ArrayList<>(chargeItems.size());

        try {
            for (ListIterator<ChargeItem> it = chargeItems.listIterator(); it.hasNext(); ) {
                ChargeItem item = it.next();

                // todo lock?
//                if (!RedisUtil.setnx(redisTemplate,
//                        String.format(redisKey, item.getId()), true, 10)) {
//                    it.remove();
//                    continue;
//                }

                ChargeItem cpItem;

                try {
                    cpItem = (ChargeItem) item.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                    it.remove();
                    continue;
                }

                cpItem.setStatus(ChargeItemStatusEnum.Reverse.getValue());
                cpItem.setActuallyPaid(0 - cpItem.getActuallyPaid());
                cpItem.setPayable(0 - cpItem.getPayable());
                item.setStatus(ChargeItemStatusEnum.Reversed.getValue());
                copiedItems.add(cpItem);
            }
        } catch (Exception e) {
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        if (copiedItems.isEmpty()) {
            rsp.setBaseResp(ThriftUtil.getBaseResp(new EmptyResultException()));
            return rsp;
        }

        try {
            transactionalAction.cancelCharge(chargeItems, copiedItems);
        } catch (Exception e) {
            log.error("cancelCharge err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public PayChargeResponse payCharge(PayChargeRequest req) throws TException {
        PayChargeResponse rsp = new PayChargeResponse();
        long medicalRecordNo = req.getMedicalRecordNo();
        ChargeItem chargeItem = new ChargeItem();

        chargeItem.setOperatorId(req.getOperatorId());
        chargeItem.setOperationTime(System.currentTimeMillis());
        chargeItem.setStatus(ChargeItemStatusEnum.Paid.getValue());

        try {
            if (!chargeItemService.update(chargeItem, new EntityWrapper<ChargeItem>()
                    .eq("medical_record_no", medicalRecordNo)
                    .eq("status", ChargeItemStatusEnum.Unpaid.getValue())
                    .eq("valid", 1))) {
                rsp.setBaseResp(ThriftUtil.getBaseResp(new InvalidParamException()));
                return rsp;
            }
        } catch (Exception e) {
            log.error("payCharge err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    /**
     * 获取单项收费
     * 依据费用科目分组
     *
     * @throws TException RPC框架异常
     * @see GetIndividualAmountRequest
     * @see GetIndividualAmountResponse
     */
    @Override
    public GetIndividualAmountResponse getIndividualAmount(GetIndividualAmountRequest req) throws TException {
        GetIndividualAmountResponse rsp = new GetIndividualAmountResponse();
        Wrapper<ChargeItem> wrapper = new EntityWrapper<ChargeItem>()
                .ge("operation_time", req.getStartTime())
                .le("operation_time", req.getEndTime())
                .eq("status", ChargeItemStatusEnum.Paid.getValue())
                .groupBy("charge_subject_id")
                .setSqlSelect("sum(payable) as total, charge_subject_id as chargeSubjectId");

        if (req.isSetChargeSubjectIds()) {
            wrapper = wrapper.in("charge_subject_id", req.getChargeSubjectIds());
        }

        if (req.isSetDepartId()) {
            wrapper = wrapper.eq("create_department_id", req.getDepartId());
        }

        if (req.isSetDoctorId()) {
            wrapper = wrapper.eq("creator_id", req.getDoctorId());
        }

        if (req.isSetChargeItemIds()) {
            wrapper = wrapper.in("id", req.getChargeItemIds());
        }

        List<ChargeItem> chargeItems = chargeItemService.selectList(wrapper);

        if (chargeItems == null || chargeItems.isEmpty()) {
            rsp.setBaseResp(ThriftUtil.getBaseResp());
            rsp.setAmountMap(new HashMap<>(0));
            return rsp;
        }

        Map<Long, Double> amountMap = chargeItems.parallelStream()
                .filter(Objects::nonNull)
                .filter(chargeItem -> chargeItem.getChargeSubjectId() != null)
                .filter(chargeItem -> chargeItem.getTotal() != null)
                .collect(Collectors.toMap(
                        ChargeItem::getChargeSubjectId
                        , ChargeItem::getTotal));

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        rsp.setAmountMap(amountMap);
        return rsp;
    }
}
