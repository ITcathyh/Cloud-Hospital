package top.itcat.action;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import top.itcat.cache.annotation.LoadingCache;
import top.itcat.concurrent.CommonThreadPoolFactory;
import top.itcat.entity.DiagnosticForMedicalRecordTemplate;
import top.itcat.entity.DoctorDiagnostic;
import top.itcat.entity.MedicalRecord;
import top.itcat.entity.MedicalRecordTemplate;
import top.itcat.entity.apply.Apply;
import top.itcat.entity.apply.ApplyGroup;
import top.itcat.entity.apply.ApplyItem;
import top.itcat.entity.apply.ApplyItemTemplate;
import top.itcat.entity.registrantion.Registration;
import top.itcat.entity.registrantion.SchedulePlan;
import top.itcat.entity.registrantion.ScheduleRule;
import top.itcat.exception.CommonException;
import top.itcat.exception.InvalidParamException;
import top.itcat.exception.code.CodeConst;
import top.itcat.mq.MQProducer;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.model.*;
import top.itcat.rpc.service.order.*;
import top.itcat.service.*;
import top.itcat.util.IDGeneratorUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static top.itcat.constant.RedisKey.*;

/**
 * 事务控制
 *
 * @author huangyuhang
 */
@Service
@Slf4j
public class TransactionalAction {
    @Autowired
    private SchedulePlanService schedulePlanService;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private DoctorDiagnosticService doctorDiagnosticService;
    @Autowired
    private ApplyItemService applyItemService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private ResultItemService resultItemService;
    @Autowired
    private ApplyItemTemplateService applyItemTemplateService;
    @Autowired
    private ApplyGroupService applyGroupService;
    @Autowired
    private MedicalRecordTemplateService medicalRecordTemplateService;
    @Autowired
    private DiagnosticForMedicalRecordTemplateService diagnosticForMedicalRecordTemplateService;
    @Autowired
    private MQProducer mqProducer;
    @Autowired
    private DiagnosticService diagnosticService;

    @Transactional
    public boolean generateSchedulePlan(ScheduleRule rule) {
        DateTime time = new DateTime().plusDays(rule.getDay() + 1).withMillisOfDay(0);
        long startTime = time.getMillis();

        generateSchedulePlan(rule, startTime);
        return true;
    }

    @Transactional
    public void generateSchedulePlan(ScheduleRule rule, long startTime) {
        int count = schedulePlanService.selectCount(new EntityWrapper<SchedulePlan>()
                .eq("start_time", startTime)
                .eq("schedule_id", rule.getId())
                .last("limit 1"));

        if (count != 0) {
            return;
        }

        SchedulePlan scheduleWeek = new SchedulePlan();

        scheduleWeek.setScheduleId(rule.getId());
        scheduleWeek.setRemain(rule.getLimitNumber());
        scheduleWeek.setValid(1);
        scheduleWeek.setStartTime(startTime);
        scheduleWeek.setEndTime(new DateTime(startTime).withHourOfDay(23).getMillis());
//        scheduleWeek.setEndTime(new DateTime(startTime).withHourOfDay(23).getMillis());

        schedulePlanService.insert(scheduleWeek);
    }

    @Transactional
    public boolean cancelRegistration(Registration registration) throws Exception {
        if (!registrationService.update(registration, new EntityWrapper<Registration>()
                .eq("id", registration.getId())
                .eq("status", RegistrationStatusEnum.UnSeen.getValue())
                .eq("valid", 1))) {
            return false;
        }

        schedulePlanService.addRemain(registration.getSchedulePlanId());

        GetChargeItemRequest getChargeItemRequest = new GetChargeItemRequest();
        getChargeItemRequest.setChargeSubjectId(getRegistrationChargeSubjectId());
        getChargeItemRequest.setProjectIds(Collections.singletonList(registration.getId()));
        GetChargeItemResponse getChargeItemResponse = orderServiceClient.getChargeItem(getChargeItemRequest);

        if (getChargeItemResponse == null) {
            return false;
        }

        ChargeItem chargeItem = getChargeItemResponse.getBeanList().get(0);
        CancelChargeRequest cancelChargeRequest = new CancelChargeRequest();
        cancelChargeRequest.setChargeItemIds(Collections.singletonList(chargeItem.getId()));
        cancelChargeRequest.setOperatorId(chargeItem.getOperatorId());

        if (orderServiceClient.cancelCharge(cancelChargeRequest) == null) {
            return false;
        }

        String key = String.format(SUBMIT_SCHEDULE_SITE_KEY, registration.getSchedulePlanId());

        if (redisTemplate.opsForValue().increment(key, 1) == null) {
            throw new Exception();
        }

        redisTemplate.delete(String.format(IS_DUPL_SUBMIT_SCHEDULE_SITE_KEY,
                registration.getSchedulePlanId(), registration.getIdentityCardNo()));
        return true;
    }

    @Transactional
    public boolean addRegistration(Registration registration, Map<String, Object> detailInfo, boolean needBook, long opeartorId) throws Exception {
        int count = registrationService.selectCount(new EntityWrapper<Registration>()
                .eq("identity_card_no", registration.getIdentityCardNo())
                .eq("schedule_plan_id", registration.getSchedulePlanId())
                .eq("valid", 1));

        if (count > 0) {
            throw new InvalidParamException("重复挂号", "重复挂号", CodeConst.InvalidParamExceptionCode.getValue());
        }

        if (!schedulePlanService.decrRemain(registration.getSchedulePlanId())) {
            return false;
        }

        DateTime dateTime = new DateTime();
        DateTime nextDay = dateTime.plusDays(1).withMillisOfDay(0);
        long expireTime = (nextDay.getMillis() - dateTime.getMillis()) / 1000;
        long curNum = (long) ((Jedis) redisTemplate
                .getConnectionFactory()
                .getConnection()
                .getNativeConnection())
                .eval(GET_INCRING_SEQ, 2, String.format("regi_seq:%d", registration.getSchedulePlanId()), String.valueOf(expireTime));
        registration.setSequenceNumber(curNum);

        if (!registrationService.insert(registration)) {
            throw new Exception();
        }

        CommonThreadPoolFactory.getDefaultThreadPool().submit(() -> {
            top.itcat.mq.bean.ChargeItem chargeItemMsg = new top.itcat.mq.bean.ChargeItem();
            chargeItemMsg.setActuallyPaid(registration.getExpense());
            chargeItemMsg.setBillingCategoryId(registration.getBillingCategoryId());
            chargeItemMsg.setChargeSubjectId(getRegistrationChargeSubjectId());
            chargeItemMsg.setDepartmentId(((ScheduleRule) detailInfo.get("scheduleRule")).getDepartmentId());
            chargeItemMsg.setMedicalRecordNo(registration.getMedicalRecordNo());
            chargeItemMsg.setName("挂号");
            chargeItemMsg.setPayable(((RegisterationLevel) detailInfo.get("registerationLevel")).getPrice());
            chargeItemMsg.setStatus(ChargeItemStatusEnum.Paid.getValue());
            chargeItemMsg.setProjectId(registration.getId());
            chargeItemMsg.setCreatorId(((ScheduleRule) detailInfo.get("scheduleRule")).getDoctorId());
            chargeItemMsg.setCreateDepartmentId(((ScheduleRule) detailInfo.get("scheduleRule")).getDepartmentId());
            chargeItemMsg.setOperationTime(System.currentTimeMillis());
            chargeItemMsg.setOperatorId(opeartorId);
            chargeItemMsg.setUnitPrice(registration.getExpense());
            chargeItemMsg.setAmount(1);

            if (needBook) {
                chargeItemMsg.setActuallyPaid(chargeItemMsg.getActuallyPaid() + 1);
            }

            sendChargeItemMsg(Collections.singletonList(chargeItemMsg));
        });

        return true;
    }

    @LoadingCache(prefix = "regis_charge_subject_id",
            cacheOperation = LoadingCache.CacheOperation.QUERY,
            localExpireTime = 60,
            expireTime = 60)
    public long getRegistrationChargeSubjectId() {
        GetChargeSubjectRequest req = new GetChargeSubjectRequest();
        req.setCatelog(CatalogEnum.Registration);
        GetChargeSubjectResponse rsp = orderServiceClient.getChargeSubject(req);
        return rsp.getBeanList().get(0).getId();
    }

    @Transactional
    public boolean addOrUpdateMedicalRecord(MedicalRecord record,
                                            List<DoctorDiagnostic> doctorDiagnostics) throws Exception {
        // 新增，更新挂号信息
        if (record.getId() == null) {
            if (medicalRecordService.selectCount(new EntityWrapper<MedicalRecord>()
                    .eq("medical_record_no", record.getMedicalRecordNo())
                    .eq("valid", 1)
                    .last("limit 1")) > 0) {
                throw new InvalidParamException("病历已存在", "病历已存在", CodeConst.InvalidParamExceptionCode.getValue());
            }

            Registration registration = new Registration();
            registration.setStatus(RegistrationStatusEnum.Done.getValue());
            registration.setSeeDoctorTime(System.currentTimeMillis());

            if (!registrationService.update(registration, new EntityWrapper<Registration>()
                    .eq("medical_record_no", record.getMedicalRecordNo())
                    .eq("status", RegistrationStatusEnum.UnSeen.getValue())
                    .eq("valid", 1))) {
                log.warn("挂号状态有误");
                throw new InvalidParamException("挂号状态有误", "挂号状态有误", CodeConst.InvalidParamExceptionCode.getValue());
            }
        } else {
            DoctorDiagnostic doctorDiagnostic = new DoctorDiagnostic();
            doctorDiagnostic.setValid(-1);
            doctorDiagnosticService.update(doctorDiagnostic, new EntityWrapper<DoctorDiagnostic>()
                    .eq("medical_record_no", record.getMedicalRecordNo())
                    .eq("valid", 1));
        }

        if (!medicalRecordService.insertOrUpdate(record)) {
            throw new Exception();
        }

        if (doctorDiagnostics != null && !doctorDiagnostics.isEmpty()) {
            for (DoctorDiagnostic item : doctorDiagnostics) {
                item.setMedicalRecordNo(record.getMedicalRecordNo());
                item.setId(null);
            }

            if (!doctorDiagnosticService.insertBatch(doctorDiagnostics)) {
                throw new Exception();
            }
        }

        return true;
    }

    @Transactional
    public void delApply(Apply apply) {
        applyService.updateById(apply);

        ApplyItem applyItem = new ApplyItem();
        applyItem.setValid(-1);

        applyItemService.update(applyItem, new EntityWrapper<ApplyItem>()
                .eq("apply_id", apply.getId()));

        List<ApplyItem> applyItems = applyItemService.selectList(new EntityWrapper<ApplyItem>()
                .eq("apply_id", apply.getId())
                .setSqlSelect("charge_item_id as chargeItemId"));
        GetChargeItemRequest getChargeItemRequest = new GetChargeItemRequest();
        getChargeItemRequest.setIds(applyItems.parallelStream()
                .map(ApplyItem::getChargeItemId)
                .collect(Collectors.toList()));
        List<ChargeItem> chargeItems = orderServiceClient.getChargeItem(getChargeItemRequest).getBeanList();

        if (!chargeItems.isEmpty()) {
            List<top.itcat.mq.bean.ChargeItem> list = new ArrayList<>(chargeItems.size());

            for (ChargeItem chargeItem : chargeItems) {
                if (chargeItem.getStatus() != ChargeItemStatusEnum.Unpaid) {
                    throw new InvalidParamException("申请单已付费", "申请单已付费", CodeConst.InvalidParamExceptionCode.getValue());
                }

                chargeItem.setStatus(ChargeItemStatusEnum.Obsolete);
                chargeItem.setValid(-1);

                list.add(top.itcat.mq.bean.ChargeItem.convert(chargeItem));
            }

            sendChargeItemMsg(list);

//            AddOrUpdateChargeItemRequest addOrUpdateChargeItemRequest = new AddOrUpdateChargeItemRequest();
//            addOrUpdateChargeItemRequest.setBeanList(chargeItems);
//
//            if (orderServiceClient.addOrUpdateChargeItem(addOrUpdateChargeItemRequest) == null) {
//                throw new CommonException("回滚", 1);
//            }

//            mqProducer.send("add_up_charge", top.itcat.mq.bean.ChargeItem.convert(c));
        }
    }

    @Transactional
    public void addApply(Apply apply,
                         List<ApplyItem> applyItems) throws Exception {
        if (!applyService.insert(apply)) {
            throw new CommonException("applyService.insertOrUpdate err", 1);
        }

        if (applyItems != null && !applyItems.isEmpty()) {
            for (ApplyItem applyItem : applyItems) {
                applyItem.setApplyId(apply.getId());
            }

            if (!applyItemService.insertBatch(applyItems)) {
                throw new CommonException("applyItemService.insertOrUpdate err", 1);
            }
        }
    }

    @Transactional
    public void addOrUpdateApplyGroup(ApplyGroup applyGroup,
                                      List<ApplyItemTemplate> itemTemplates) {
        if (applyGroup.getId() != null) {
            ApplyItemTemplate tmp = new ApplyItemTemplate();
            tmp.setValid(-1);

            applyItemTemplateService.update(tmp, new EntityWrapper<ApplyItemTemplate>()
                    .eq("group_id", applyGroup.getId())
                    .eq("valid", 1));
        }

        if (!applyGroupService.insertOrUpdate(applyGroup)) {
            throw new CommonException("applyGroupService.insertOrUpdate err", 1);
        }

        if (itemTemplates != null && !itemTemplates.isEmpty()) {
            for (ApplyItemTemplate itemTemplate : itemTemplates) {
                itemTemplate.setGroupId(applyGroup.getId());
                itemTemplate.setId(null);
            }

            if (!applyItemTemplateService.insertBatch(itemTemplates)) {
                throw new CommonException("applyItemTemplateService.insertBatch err", 1);
            }
        }
    }

    @Transactional
    public void delApplyGroup(ApplyGroup applyGroup) {
        ApplyItemTemplate tmp = new ApplyItemTemplate();
        tmp.setValid(-1);

        applyItemTemplateService.update(tmp, new EntityWrapper<ApplyItemTemplate>()
                .eq("group_id", applyGroup.getId())
                .eq("valid", 1));

        if (!applyGroupService.insertOrUpdate(applyGroup)) {
            throw new CommonException("applyGroupService.insertOrUpdate err", 1);
        }
    }

    @Transactional
    public void delMedicalRecordTemplate(MedicalRecordTemplate medicalRecordTemplate) {
        DiagnosticForMedicalRecordTemplate tmp = new DiagnosticForMedicalRecordTemplate();
        tmp.setValid(-1);

        diagnosticForMedicalRecordTemplateService.update(tmp, new EntityWrapper<DiagnosticForMedicalRecordTemplate>()
                .eq("medical_record_template_id", medicalRecordTemplate.getId())
                .eq("valid", 1));

        if (!medicalRecordTemplateService.updateById(medicalRecordTemplate)) {
            throw new CommonException("medicalRecordTemplateService.update err", 1);
        }
    }

    @Transactional
    public void addOrUpdateMedicalRecordTemplate(MedicalRecordTemplate medicalRecordTemplate,
                                                 List<DiagnosticForMedicalRecordTemplate> itemTemplates) {
        if (medicalRecordTemplate.getId() != null) {
            DiagnosticForMedicalRecordTemplate tmp = new DiagnosticForMedicalRecordTemplate();
            tmp.setValid(-1);

            diagnosticForMedicalRecordTemplateService.update(tmp, new EntityWrapper<DiagnosticForMedicalRecordTemplate>()
                    .eq("medical_record_template_id", medicalRecordTemplate.getId())
                    .eq("valid", 1));
        }

        if (!medicalRecordTemplateService.insertOrUpdate(medicalRecordTemplate)) {
            throw new CommonException("medicalRecordTemplate.insertOrUpdate err", 1);
        }

        if (itemTemplates != null && !itemTemplates.isEmpty()) {
            for (DiagnosticForMedicalRecordTemplate item : itemTemplates) {
                item.setMedicalRecordTemplateId(medicalRecordTemplate.getId());
                item.setId(null);
            }

            if (!diagnosticForMedicalRecordTemplateService.insertBatch(itemTemplates)) {
                throw new CommonException("diagnosticForMedicalRecordTemplateService.insertBatch err", 1);
            }
        }
    }

    @Transactional
    public void enterApplyItemResult(top.itcat.entity.apply.ApplyItem applyItem,
                                     List<top.itcat.entity.apply.ResultItem> resultItems) {
        if (!applyItemService.update(applyItem, new EntityWrapper<top.itcat.entity.apply.ApplyItem>()
                .eq("id", applyItem.getId())
                .eq("status", ApplyItemStatus.Registered.getValue())
                .eq("valid", 1))) {
            throw new InvalidParamException();
        }

        if (!resultItemService.insertBatch(resultItems)) {
            throw new InvalidParamException();
        }
    }

    private void sendChargeItemMsg(List<top.itcat.mq.bean.ChargeItem> chargeItems) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(chargeItems);
        mqProducer.send("add_up_charge", jsonArray.toJSONString());
    }

    @Transactional
    public void addOrUpdateDiagnostic(List<Diagnostic> diagnostics) {
        if (!diagnostics.get(0).isSetId()) {
            diagnostics.parallelStream().forEach(item -> item.setCode(
                    IDGeneratorUtil.genCode("DSC", redisTemplate, "diagnostic_code_incr")
            ));
        }

        List<top.itcat.entity.diagnostic.Diagnostic> list = diagnostics.parallelStream().map(top.itcat.entity.diagnostic.Diagnostic::convert).collect(Collectors.toList());
        diagnosticService.insertOrUpdateBatch(list);

        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        mqProducer.send("add_up_diagnostic", jsonArray.toJSONString());
    }
}
