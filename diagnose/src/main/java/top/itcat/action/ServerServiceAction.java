package top.itcat.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.itcat.cache.annotation.LoadingCache;
import top.itcat.constant.RedisKey;
import top.itcat.entity.DiagnosticForMedicalRecordTemplate;
import top.itcat.entity.DoctorDiagnostic;
import top.itcat.entity.MedicalRecordTemplate;
import top.itcat.entity.apply.ApplyGroup;
import top.itcat.entity.apply.ApplyItemTemplate;
import top.itcat.entity.diagnostic.Diagnostic;
import top.itcat.entity.registrantion.Registration;
import top.itcat.entity.registrantion.SchedulePlan;
import top.itcat.entity.registrantion.ScheduleRule;
import top.itcat.es.DiagnosticRepository;
import top.itcat.exception.CommonException;
import top.itcat.exception.InternalException;
import top.itcat.exception.InvalidParamException;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.WrapperGenerator;
import top.itcat.rpc.service.diagnose.*;
import top.itcat.rpc.service.hospital.GetNonmedicalChargeRequest;
import top.itcat.rpc.service.hospital.GetNonmedicalChargeResponse;
import top.itcat.rpc.service.hospital.GetRegisterationLevelRequest;
import top.itcat.rpc.service.hospital.GetRegisterationLevelResponse;
import top.itcat.rpc.service.model.*;
import top.itcat.rpc.service.order.GetChargeSubjectRequest;
import top.itcat.rpc.service.order.GetChargeSubjectResponse;
import top.itcat.rpc.service.user.MGetPatientRequest;
import top.itcat.rpc.service.user.MGetPatientResponse;
import top.itcat.service.*;
import top.itcat.util.PatternUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
@SuppressWarnings("unchecked")
public class ServerServiceAction {
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private HospitalServiceClient hospitalServiceClient;
    @Autowired
    private SchedulePlanService schedulePlanService;
    @Autowired
    private ScheduleRuleService scheduleRuleService;
    @Autowired
    private DoctorDiagnosticService doctorDiagnosticService;
    @Autowired
    private ApplyItemTemplateService applyItemTemplateService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private DiagnosticForMedicalRecordTemplateService diagnosticForMedicalRecordTemplateService;
    @Autowired
    private ApplyItemService applyItemService;
    @Autowired
    private ResultItemService resultItemService;
    @Autowired
    private TransactionalAction transactionalAction;
    @Autowired
    private DiagnosticService diagnosticService;
    @Autowired
    private DiagnosticRepository diagnosticRepository;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Map<String, Object> getRegistrationDetail(Registration registration) throws CommonException {
//        GetBillingCategoryRequest getBillingCategoryRequest = new GetBillingCategoryRequest();
//        getBillingCategoryRequest.setIds(Collections.singletonList(registration.getBillingCategoryId()));
//        GetBillingCategoryResponse getBillingCategoryResponse = hospitalServiceClient.
//                getBillingCategory(getBillingCategoryRequest);
//
//        if (getBillingCategoryResponse == null ||
//                getBillingCategoryResponse.getTotal() == 0) {
//            throw new InvalidParamException();
//        }
//
//        BillingCategory billingCategory = getBillingCategoryResponse.getBeanList().get(0);
        Map<String, Object> map = new HashMap<>(4);
        SchedulePlan schedulePlan = schedulePlanService.
                selectById(registration.getSchedulePlanId());

        if (schedulePlan == null) {
            throw new InvalidParamException();
        }

        ScheduleRule scheduleRule = scheduleRuleService.selectById(schedulePlan.getScheduleId());

        if (scheduleRule == null) {
            throw new InvalidParamException();
        }

        RegisterationLevel registerationLevel = getLevel().get(scheduleRule.getRegistrationLevelId());

        if (registerationLevel == null) {
            throw new InvalidParamException();
        }

        registration.setExpense(registerationLevel.getPrice());
//        registration.setExpense(registerationLevel.getPrice() * billingCategory.discount);
        map.put("scheduleRule", scheduleRule);
        map.put("schedulePlan", schedulePlan);
        map.put("registerationLevel", registerationLevel);
//        map.put("billingCategory", billingCategory);
        return map;
    }

    @LoadingCache(prefix = "regi_levels",
            expireTime = 30,
            localExpireTime = 10)
    public Map<Long, RegisterationLevel> getLevel() {
        GetRegisterationLevelRequest registerationLevelRequest = new GetRegisterationLevelRequest();
        GetRegisterationLevelResponse getRegisterationLevelResponse = hospitalServiceClient.getRegisterationLevel(new GetRegisterationLevelRequest());

        if (getRegisterationLevelResponse == null ||
                getRegisterationLevelResponse.getBeanListSize() == 0) {
            throw new InvalidParamException();
        }

        return getRegisterationLevelResponse.getBeanList()
                .parallelStream()
                .collect(Collectors.toMap(RegisterationLevel::getId, i -> i));
    }

    public List<top.itcat.rpc.service.model.MedicalRecord> getMedicalRecords(Page<top.itcat.entity.MedicalRecord> medicalRecordPage) {
        List<top.itcat.entity.MedicalRecord> medicalRecords = medicalRecordPage.getRecords();
        List<DoctorDiagnostic> doctorDiagnostics = doctorDiagnosticService
                .selectList(new EntityWrapper<DoctorDiagnostic>()
                        .eq("valid", 1)
                        .in("medical_record_no",
                                medicalRecords.parallelStream()
                                        .map(top.itcat.entity.MedicalRecord::getMedicalRecordNo)
                                        .collect(Collectors.toList())));
        Map<Long, List<top.itcat.rpc.service.model.DoctorDiagnostic>> map = new HashMap<>();

        for (DoctorDiagnostic doctorDiagnostic : doctorDiagnostics) {
            List<top.itcat.rpc.service.model.DoctorDiagnostic> list = map.computeIfAbsent(doctorDiagnostic.
                    getMedicalRecordNo(), k -> new ArrayList<>());
            list.add(DoctorDiagnostic.convertRPCBean(doctorDiagnostic));
        }

        List<top.itcat.rpc.service.model.MedicalRecord> records = medicalRecords
                .parallelStream()
                .map(top.itcat.entity.MedicalRecord::convertRPCBean)
                .peek(medicalRecord -> medicalRecord.setDoctorDiagnostics(
                        map.get(medicalRecord.getMedicalRecordNo()) == null ?
                                new ArrayList<>() :
                                map.get(medicalRecord.getMedicalRecordNo())
                ))
                .collect(Collectors.toList());

        return records;
    }

    public List<top.itcat.rpc.service.model.ApplyGroup> getApplyGroup(List<ApplyGroup> list) {
        List<top.itcat.rpc.service.model.ApplyGroup> applyGroups = new ArrayList<>(list.size());

        for (ApplyGroup item : list) {
            List<ApplyItemTemplate> applyItemTemplates = applyItemTemplateService.selectList(
                    new EntityWrapper<ApplyItemTemplate>()
                            .eq("valid", 1)
                            .eq("group_id", item.getId()));
            top.itcat.rpc.service.model.ApplyGroup applyGroup = ApplyGroup.convertRPCBean(item);
            applyGroup.setItems(applyItemTemplates.parallelStream()
                    .map(ApplyItemTemplate::convertRPCBean)
                    .collect(Collectors.toList()));
            applyGroups.add(applyGroup);
        }

        return applyGroups;
    }

    public List<top.itcat.rpc.service.model.MedicalRecordTemplate> getMedicalRecordTemplate(List<MedicalRecordTemplate> list) {
        List<top.itcat.rpc.service.model.MedicalRecordTemplate> medicalRecordTemplates = new ArrayList<>(list.size());

        for (MedicalRecordTemplate medicalRecordTemplate : list) {
            top.itcat.rpc.service.model.MedicalRecordTemplate template = MedicalRecordTemplate.convertRPCBean(medicalRecordTemplate);

            List<DiagnosticForMedicalRecordTemplate> items = diagnosticForMedicalRecordTemplateService.selectList(
                    new EntityWrapper<DiagnosticForMedicalRecordTemplate>()
                            .eq("valid", 1)
                            .eq("medical_record_template_id", medicalRecordTemplate.getId()));
            template.setDoctorDiagnostics(items.parallelStream()
                    .map(DiagnosticForMedicalRecordTemplate::convertRPCBean)
                    .collect(Collectors.toList()));
            medicalRecordTemplates.add(template);
        }

        return medicalRecordTemplates;
    }

    public void addOrUpdateSchedulePlan(SchedulePlan plan, ScheduleRule rule) {
        boolean first = false;

        if (plan.getId() != null) {
            SchedulePlan oldPlan = schedulePlanService.selectOne(new EntityWrapper<SchedulePlan>()
                    .eq("valid", 1)
                    .eq("id", plan.getId()));
            long curTime = System.currentTimeMillis();
            long startTime = new DateTime().withMillisOfDay(0).getMillis();
            plan.setRemain(null);

            if (oldPlan == null) {
                throw new InvalidParamException();
            }

            // 拒绝当日排班更新，仅可以删除
            if (oldPlan.getStartTime() <= startTime || (plan.getValid() == null || plan.getValid() != -1)) {
                throw new InvalidParamException().withPromt("非未来排班不可更新");
            }

            // 挂号数量设置有误
            if (plan.getRemain() != null && plan.getRemain() < oldPlan.getRemain()) {
                throw new InvalidParamException();
            }

            // 排班时间有误
            if (plan.getEndTime() != null && plan.getEndTime() < curTime) {
                throw new InvalidParamException();
            }
        } else {
            if (scheduleRuleService.selectCount(new EntityWrapper<ScheduleRule>()
                    .eq("doctor_id", rule.getDoctorId())
                    .eq("start_time", rule.getStartTime())
                    .eq("noon_break", rule.getNoonBreak())
                    .last("limit 1")) > 0) {
                throw new InvalidParamException().withPromt("存在重复排班");
            }

            // 增加临时排班规则
            rule.setValid(2);
            scheduleRuleService.insert(rule);
            plan.setScheduleId(rule.getId());
            first = true;
            plan.setEndTime(new DateTime(plan.getStartTime()).withHourOfDay(23).getMillis());
        }

        schedulePlanService.insertOrUpdate(plan);

        if (first) {
            DateTime time = new DateTime(plan.getStartTime()).withHourOfDay(23);

            redisTemplate.opsForValue().set(String.format(RedisKey.SUBMIT_SCHEDULE_SITE_KEY, plan.getId()), plan.getRemain(),
                    (time.getMillis() - System.currentTimeMillis()) / 1000, TimeUnit.SECONDS);
        }
    }

    public Page<top.itcat.entity.apply.ApplyItem> getApplyItem(GetApplyItemRequest req) {
        Wrapper wrapper = new EntityWrapper().eq("valid", 1);
        int offset = 0;
        int limit = 100;

        if (req.isSetIds()) {
            wrapper = wrapper.in("id", req.getIds());
        }

        if (req.isSetApplyId()) {
            wrapper = wrapper.eq("apply_id", req.getApplyId());
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetDepartmentId()) {
            List<NonmedicalCharge> nonmedicalCharges = getNonmeidical(req.getDepartmentId());
            wrapper = wrapper.in("nonmedical_charge_id", nonmedicalCharges
                    .parallelStream()
                    .map(NonmedicalCharge::getId)
                    .collect(Collectors.toList()));
        }

        List<Long> applyIds = new ArrayList<>();

        if (req.isSetApplyId()) {
            applyIds.add(req.getApplyId());
        }

        if (req.isSetMedicalDoctorId()) {
            wrapper = wrapper.eq("medical_doctor_id", req.getMedicalDoctorId());
        }

        if (req.isSetMedicalRecordNo()) {
            List<top.itcat.entity.apply.Apply> applies = applyService.selectList(new EntityWrapper<top.itcat.entity.apply.Apply>()
                    .eq("medical_record_no", req.getMedicalRecordNo())
                    .eq("valid", 1)
                    .setSqlSelect("id"));

            if (applies.isEmpty()) {
                Page<top.itcat.entity.apply.ApplyItem> page = new Page<>();
                page.setRecords(new ArrayList<>(0));
                page.setTotal(0);
                return page;
            }

            applies.forEach(i -> applyIds.add(i.getId()));
        }

        if (req.isSetStartTime()) {
            wrapper = wrapper.ge("operate_time", req.getStartTime());
        }

        if (req.isSetEndTime()) {
            wrapper = wrapper.le("operate_time", req.getEndTime());
        }

        if (!applyIds.isEmpty()) {
            wrapper = wrapper.in("apply_id", applyIds);
        }

        Page<top.itcat.entity.apply.ApplyItem> page = applyItemService.selectPage(new Page<>(offset, limit), wrapper);
        page.setTotal(applyItemService.selectCount(wrapper));
        packApplyItem(page.getRecords());

        return page;
    }

    @LoadingCache(prefix = "nonmeidical_char_subjects",
            expireTime = 60, localExpireTime = 15,
            cacheOperation = LoadingCache.CacheOperation.QUERY)
    public List<Long> getNonmedicalChargeSubjectIds() {
        GetChargeSubjectRequest req = new GetChargeSubjectRequest();
        req.setCatelog(CatalogEnum.Nonmedical);
        GetChargeSubjectResponse rsp = orderServiceClient.getChargeSubject(req);

        if (rsp == null || rsp.getBeanListSize() == 0) {
            throw new InternalException();
        }

        return rsp.getBeanList()
                .parallelStream()
                .map(ChargeSubject::getId)
                .collect(Collectors.toList());
    }

    @LoadingCache(prefix = "depart_nonmeidical",
            expireTime = 60, localExpireTime = 15,
            cacheOperation = LoadingCache.CacheOperation.QUERY)
    public List<NonmedicalCharge> getNonmeidical(long departId) {
        GetNonmedicalChargeRequest getNonmedicalChargeRequest = new GetNonmedicalChargeRequest();
        getNonmedicalChargeRequest.setDepartmentId(departId);
        GetNonmedicalChargeResponse getNonmedicalChargeResponse = hospitalServiceClient.getNonmedicalCharge(getNonmedicalChargeRequest);

        if (getNonmedicalChargeResponse == null) {
            throw new InternalException();
        }

        return getNonmedicalChargeResponse.getBeanList();
    }

    public void packApplyItem(List<top.itcat.entity.apply.ApplyItem> applyItems) {
        List<Long> applyItemIds = applyItems.parallelStream()
                .map(top.itcat.entity.apply.ApplyItem::getId)
                .collect(Collectors.toList());
        List<top.itcat.entity.apply.ResultItem> resultItems = resultItemService
                .selectList(new EntityWrapper<top.itcat.entity.apply.ResultItem>()
                        .in("item_id", applyItemIds)
                        .eq("valid", 1));
        Map<Long, List<top.itcat.entity.apply.ResultItem>> resultMap = new HashMap<>(applyItems.size());

        for (top.itcat.entity.apply.ResultItem item : resultItems) {
            List<top.itcat.entity.apply.ResultItem> items = resultMap.computeIfAbsent(item.getItemId(), k -> new ArrayList<>(2));
            items.add(item);
        }

        applyItems.forEach(i -> i.setResultItems(resultMap.get(i.getId())));
    }

    public void enterApplyItemResult(EnterApplyItemResultRequest req) {
        top.itcat.entity.apply.ApplyItem applyItem = top.itcat.entity.apply.ApplyItem.convert(req.getItem());
        List<top.itcat.entity.apply.ResultItem> resultItems = applyItem.getResultItems();

        if (resultItems == null || resultItems.isEmpty()) {
            throw new InvalidParamException();
        }

        applyItem.setStatus(ApplyItemStatus.Done.getValue());
        transactionalAction.enterApplyItemResult(applyItem, resultItems);
    }

    public boolean updateApplyItem(UpdateApplyItemRequest req) {
        top.itcat.entity.apply.ApplyItem applyItem = top.itcat.entity.apply.ApplyItem.convert(req.getItem());

        return applyItemService.update(applyItem, new EntityWrapper<top.itcat.entity.apply.ApplyItem>()
                .eq("id", applyItem.getId())
                .eq("status", ApplyItemStatus.Unregistered.getValue())
                .eq("valid", 1));
    }

    public Page<Diagnostic> getDiagnostic(GetDiagnosticRequest req) {
        int offset = 0;
        int limit = 100;
        Page<Diagnostic> page = null;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetSearchKey()) {
            String key = "*" + req.getSearchKey().toLowerCase() + "*";
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                    .add(QueryBuilders.matchQuery("name", req.getSearchKey()),
                            ScoreFunctionBuilders.weightFactorFunction(10))
                    .add(QueryBuilders.wildcardQuery("code", key),
                            ScoreFunctionBuilders.weightFactorFunction(10))
                    .scoreMode("sum")
                    .setMinScore(10);


            Pageable pageable = new PageRequest(offset, limit);
            queryBuilder.withPageable(pageable)
                    .withQuery(functionScoreQueryBuilder).build();
            org.springframework.data.domain.Page<top.itcat.entity.diagnostic.Diagnostic> espage = diagnosticRepository.search(queryBuilder.build());

            if (!espage.getContent().isEmpty()) {
                page = new Page<>();
                page.setRecords(espage.getContent());
                page.setTotal(espage.getTotalElements());
                return page;
            }
        }

        Wrapper wrapper = new EntityWrapper().eq("valid", 1);

        if (req.isSetIds()) {
            wrapper = wrapper.in("id", req.getIds());
        }

        if (req.isSetSecondDiagDirId()) {
            wrapper = wrapper.eq("directory_id", req.getSecondDiagDirId());
        }

        if (req.isSetSearchKey()) {
            wrapper = wrapper.andNew()
                    .like("name", req.getSearchKey())
                    .or()
                    .like("code", req.getSearchKey());
        }

        page = diagnosticService.selectPage(new Page<>(offset, limit), wrapper);
        return page;
    }

    public Page<Registration> getRegistration(GetRegistrationRequest req) {
        int offset = 0;
        int limit = 100;
        Page<Registration> page;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetDepartId() || req.isSetDoctorId() || req.isSetCurTime()) {
            Long doctorId = !req.isSetDoctorId() ? null : req.getDoctorId();
            Long departId = !req.isSetDepartId() ? null : req.getDepartId();
            Long curTime = !req.isSetCurTime() ? null : req.getCurTime();
            return registrationService.selectUsingDoctorOrDepart(new Page<>(offset, limit),
                    doctorId, departId, curTime);
        }

        Wrapper wrapper = WrapperGenerator.getRegistrationWrapper(req);

        if (!StringUtils.isEmpty(req.getSearchKey())) {
            List<String> idNums = new ArrayList<>();

            if (!PatternUtil.isNumeric(req.getSearchKey())) {
                MGetPatientRequest getPatientRequest = new MGetPatientRequest();
                getPatientRequest.setSearchKey(req.getSearchKey());
                MGetPatientResponse rsp = userServiceClient.mGetPatient(getPatientRequest);

                if (rsp == null) {
                    return null;
                } else if (rsp.getUsersSize() > 0) {
                    for (Patient p : rsp.getUsers()) {
                        idNums.add(p.getUserInfo().getIdNum());
                    }
                }
            }

            if (PatternUtil.isNumeric(req.getSearchKey())) {
                idNums.add(req.getSearchKey());
            }

            if (!idNums.isEmpty()) {
                wrapper = wrapper.andNew().in("identity_card_no", idNums);
            }

            if (PatternUtil.isNumeric(req.getSearchKey())) {
                wrapper = wrapper.or().eq("medical_record_no", Long.valueOf(req.getSearchKey()));
            }
        }

        page = registrationService.selectPage(
                new Page<>(offset, limit), wrapper);
        return page;
    }
}
