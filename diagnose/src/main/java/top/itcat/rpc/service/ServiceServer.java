package top.itcat.rpc.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.itcat.action.ServerServiceAction;
import top.itcat.action.TransactionalAction;
import top.itcat.entity.*;
import top.itcat.entity.apply.Apply;
import top.itcat.entity.apply.ApplyGroup;
import top.itcat.entity.apply.ApplyItem;
import top.itcat.entity.apply.ApplyItemTemplate;
import top.itcat.entity.diagnostic.Diagnostic;
import top.itcat.entity.diagnostic.FirstDiagDir;
import top.itcat.entity.diagnostic.SecondDiagDir;
import top.itcat.entity.registrantion.Registration;
import top.itcat.entity.registrantion.SchedulePlan;
import top.itcat.entity.registrantion.ScheduleRule;
import top.itcat.exception.InternalException;
import top.itcat.exception.InvalidParamException;
import top.itcat.exception.NotEnoughException;
import top.itcat.rpc.service.diagnose.*;
import top.itcat.rpc.service.model.RegistrationStatusEnum;
import top.itcat.rpc.thrift.ThriftUtil;
import top.itcat.service.*;
import top.itcat.task.ScheduleTaskCentry;
import top.itcat.util.IDGeneratorUtil;

import java.util.*;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unchecked")
@Slf4j
public class ServiceServer implements DiagnoseService.Iface {
    @Autowired
    private SchedulePlanService schedulePlanService;
    @Autowired
    private ScheduleRuleService scheduleRuleService;
    @Autowired
    private ScheduleTaskCentry scheduleTaskCentry;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private TransactionalAction transactionalAction;
    @Autowired
    private FirstDiagDirService firstDiagDirService;
    @Autowired
    private SecondDiagDirService secondDiagDirService;
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private ApplyItemService applyItemService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ApplyGroupService applyGroupService;
    @Autowired
    private MedicalRecordTemplateService medicalRecordTemplateService;
    @Autowired
    private CommonlyUsedDiagnosticService commonlyUsedDiagnosticService;
    @Autowired
    private ServerServiceAction serverServiceAction;

    @Override
    public GetFirstDiagDirResponse getFirstDiagDir(GetFirstDiagDirRequest getFirstDiagDirRequest) throws TException {
        GetFirstDiagDirResponse rsp = new GetFirstDiagDirResponse();
        List<FirstDiagDir> list;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1).orderBy("id desc");
        int offset = 0;
        int limit = 100;

        if (getFirstDiagDirRequest.isSetOffset()) {
            offset = getFirstDiagDirRequest.getOffset();
        }

        if (getFirstDiagDirRequest.isSetLimit()) {
            limit = getFirstDiagDirRequest.getLimit();
        }

        if (getFirstDiagDirRequest.isSetIds()) {
            wrapper = wrapper.in("id", getFirstDiagDirRequest.getIds());
        }

        if (getFirstDiagDirRequest.isSetSearchKey()) {
            wrapper = wrapper.andNew().like("name", getFirstDiagDirRequest.getSearchKey());
        }

        Page<FirstDiagDir> page = null;
        try {
            page = firstDiagDirService.selectPage(
                    new Page<>(offset, limit), wrapper);
            page.setTotal(firstDiagDirService.selectCount(wrapper));
        } catch (Exception e) {
            log.error("getFirstDiagDir err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        list = page.getRecords();
        List<top.itcat.rpc.service.model.FirstDiagDir> rpcList =
                list.parallelStream().map(FirstDiagDir::convertRPCBean).collect(Collectors.toList());

        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateFirstDiagDirResponse addOrUpdateFirstDiagDir(AddOrUpdateFirstDiagDirRequest addOrUpdateFirstDiagDirRequest) throws TException {
        AddOrUpdateFirstDiagDirResponse rsp = new AddOrUpdateFirstDiagDirResponse();
        List<FirstDiagDir> list =
                addOrUpdateFirstDiagDirRequest.getBeanList().parallelStream().map(FirstDiagDir::convert).collect(Collectors.toList());
        try {
            firstDiagDirService.insertOrUpdateBatch(list);
        } catch (Exception e) {
            log.error("addOrUpdateFirstDiagDir err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetSecondDiagDirResponse getSecondDiagDir(GetSecondDiagDirRequest getSecondDiagDirRequest) throws TException {
        GetSecondDiagDirResponse rsp = new GetSecondDiagDirResponse();
        List<SecondDiagDir> list;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1).orderBy("id desc");
        int offset = 0;
        int limit = 500;

        if (getSecondDiagDirRequest.isSetOffset()) {
            offset = getSecondDiagDirRequest.getOffset();
        }

        if (getSecondDiagDirRequest.isSetLimit()) {
            limit = getSecondDiagDirRequest.getLimit();
        }

        if (getSecondDiagDirRequest.isSetIds()) {
            wrapper = wrapper.in("id", getSecondDiagDirRequest.getIds());
        }

        if (getSecondDiagDirRequest.isSetFirstDiagDirId()) {
            wrapper = wrapper.eq("first_diag_dir_id", getSecondDiagDirRequest.getFirstDiagDirId());
        }

        if (getSecondDiagDirRequest.isSetSearchKey()) {
            wrapper = wrapper.andNew().like("name", getSecondDiagDirRequest.getSearchKey());
        }

        Page<SecondDiagDir> page = null;
        try {
            page = secondDiagDirService.selectPage(
                    new Page<>(offset, limit), wrapper);
            list = page.getRecords();
            page.setTotal(secondDiagDirService.selectCount(wrapper));
        } catch (Exception e) {
            log.error("getSecondDiagDir err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        List<top.itcat.rpc.service.model.SecondDiagDir> rpcList =
                list.parallelStream().map(SecondDiagDir::convertRPCBean).collect(Collectors.toList());
        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateSecondDiagDirResponse addOrUpdateSecondDiagDir(AddOrUpdateSecondDiagDirRequest addOrUpdateSecondDiagDirRequest) throws TException {
        AddOrUpdateSecondDiagDirResponse rsp = new AddOrUpdateSecondDiagDirResponse();
        List<SecondDiagDir> list =
                addOrUpdateSecondDiagDirRequest.getBeanList().parallelStream().map(SecondDiagDir::convert).collect(Collectors.toList());
        try {
            secondDiagDirService.insertOrUpdateBatch(list);
        } catch (Exception e) {
            log.error("addOrUpdateSecondDiagDir err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetDiagnosticResponse getDiagnostic(GetDiagnosticRequest req) throws TException {
        GetDiagnosticResponse rsp = new GetDiagnosticResponse();
        Page<Diagnostic> page;

        try {
            page = serverServiceAction.getDiagnostic(req);
        } catch (Exception e) {
            log.error("getDiagnostic err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        List<top.itcat.rpc.service.model.Diagnostic> rpcList =
                page.getRecords().parallelStream().map(Diagnostic::convertRPCBean).collect(Collectors.toList());
        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateDiagnosticResponse addOrUpdateDiagnostic(AddOrUpdateDiagnosticRequest req) throws TException {
        AddOrUpdateDiagnosticResponse rsp = new AddOrUpdateDiagnosticResponse();

        try {
            transactionalAction.addOrUpdateDiagnostic(req.getBeanList());
        } catch (Exception e) {
            log.error("addOrUpdateDiagnostic err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetSchedulePlanResponse getSchedulePlan(GetSchedulePlanRequest req) throws TException {
        GetSchedulePlanResponse rsp = new GetSchedulePlanResponse();
        List<SchedulePlan> list;
        int offset = 0;
        int limit = 100;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        Page<SchedulePlan> page;

        try {
            if (req.isSetIds()) {
                Wrapper wrapper = new EntityWrapper().ge("valid", 1).orderBy("id desc")
                        .in("id", req.getIds());
                page = schedulePlanService.selectPage(
                        new Page<>(offset, limit), wrapper);
                page.setTotal(schedulePlanService.selectCount(wrapper));
            } else {
                Long doctorId = req.isSetDoctorId() ?
                        req.getDoctorId() : null;
                Long departId = req.isSetDepartId() ?
                        req.getDepartId() : null;
                boolean needExpired = req.isSetNeedExpired() && req.isNeedExpired();
                Long curTime = req.isSetCurTime() ?
                        req.getCurTime() : null;
                Long startTime = req.isSetStartTime() ?
                        req.getStartTime() : null;
                Long endTime = req.isSetEndTime() ?
                        req.getEndTime() : null;
                page = schedulePlanService.selectUsingRule(new Page<>(offset, limit),
                        doctorId, departId, needExpired, curTime, startTime, endTime);
//
//                if (req.isSetSearchKey()) {
//                    List<Long> ids = serverServiceAction.getDoctorIds(req.getSearchKey());
//                    page.setRecords(page.getRecords().parallelStream().filter(i->ids.contains(i.get)))
//                }
                // todo size
            }
        } catch (Exception e) {
            log.error("getSchedulePlan err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        list = page.getRecords();
        List<top.itcat.rpc.service.model.SchedulePlan> rpcList =
                list.parallelStream().map(SchedulePlan::convertRPCBean).collect(Collectors.toList());
        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());


        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateSchedulePlanResponse addOrUpdateSchedulePlan(AddOrUpdateSchedulePlanRequest req) throws TException {
        AddOrUpdateSchedulePlanResponse rsp = new AddOrUpdateSchedulePlanResponse();

        try {
            serverServiceAction.addOrUpdateSchedulePlan(SchedulePlan.convert(req.getPlan()), ScheduleRule.convert(req.getRule()));
        } catch (InvalidParamException e) {
            log.error("addOrUpdateSchedulePlan err:", e.getPromt());
            rsp.setBaseResp(ThriftUtil.getBaseResp(e));
            return rsp;
        } catch (Exception e) {
            log.error("addOrUpdateSchedulePlan err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetScheduleRuleResponse getScheduleRule(GetScheduleRuleRequest req) throws TException {
        GetScheduleRuleResponse rsp = new GetScheduleRuleResponse();
        List<ScheduleRule> list;
        Wrapper wrapper = WrapperGenerator.getScheduleRuleWrapper(req);
        int offset = 0;
        int limit = 100;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        Page<ScheduleRule> page = null;
        try {
            page = scheduleRuleService.selectPage(
                    new Page<>(offset, limit), wrapper);
            list = page.getRecords();
            page.setTotal(scheduleRuleService.selectCount(wrapper));
        } catch (Exception e) {
            log.error("getScheduleRule err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        List<top.itcat.rpc.service.model.ScheduleRule> rpcList =
                list.parallelStream().map(ScheduleRule::convertRPCBean).collect(Collectors.toList());
        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateScheduleRuleResponse addOrUpdateScheduleRule(AddOrUpdateScheduleRuleRequest req) throws TException {
        AddOrUpdateScheduleRuleResponse rsp = new AddOrUpdateScheduleRuleResponse();
        List<ScheduleRule> list = req.getBeanList()
                .parallelStream()
                .map(ScheduleRule::convert)
                .collect(Collectors.toList());
        try {
            scheduleRuleService.insertOrUpdateBatch(list);

            // 检验是否需要生成排班计划
            Set<Long> departIds = new HashSet<>();

            for (ScheduleRule rule : list) {
                if (rule.getDepartmentId() != null) {
                    departIds.add(rule.getDepartmentId());
                }
            }

            if (departIds.size() > 0) {
                scheduleTaskCentry.generatePlan(new ArrayList<>(departIds));
            }
        } catch (Exception e) {
            log.error("addOrUpdateScheduleRule err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetRegistrationResponse getRegistration(GetRegistrationRequest req) throws TException {
        GetRegistrationResponse rsp = new GetRegistrationResponse();
        Page<Registration> page;

        try {
            page = serverServiceAction.getRegistration(req);

            if (page == null) {
                rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
                return rsp;
            }
        } catch (Exception e) {
            e.printStackTrace();
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        List<Registration> list = page.getRecords();
        List<top.itcat.rpc.service.model.Registration> rpcList =
                list.parallelStream().map(Registration::convertRPCBean).collect(Collectors.toList());

        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateRegistrationResponse addOrUpdateRegistration(AddOrUpdateRegistrationRequest req) throws TException {
        AddOrUpdateRegistrationResponse rsp = new AddOrUpdateRegistrationResponse();
        Registration registration = Registration.convert(req.getBean());
        Map<String, Object> detailInfo = null;

        try {
            if (registration.getSchedulePlanId() != null) {
                detailInfo = serverServiceAction.getRegistrationDetail(registration);
            }
            if (req.getBean().isSetId()) {
                registrationService.updateById(registration);
            } else {
                if (!transactionalAction.addRegistration(registration, detailInfo, req.isNeedBook(), req.getOperatorId())) {
                    rsp.setBaseResp(ThriftUtil.getBaseResp(new NotEnoughException()));
                    return rsp;
                }
            }
        } catch (InvalidParamException e) {
            rsp.setBaseResp(ThriftUtil.getBaseResp(e));
            return rsp;
        } catch (Exception e) {
            log.error("addOrUpdateRegistration err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        rsp.setBean(Registration.convertRPCBean(registration));
        return rsp;
    }

    @Override
    public CancelRegistrationResponse cancelRegistration(CancelRegistrationRequest req) throws TException {
        CancelRegistrationResponse rsp = new CancelRegistrationResponse();
        Registration oriRegistration = registrationService.selectOne(new EntityWrapper<Registration>()
                .eq("id", req.getId())
                .eq("valid", 1)
                .eq("status", RegistrationStatusEnum.UnSeen.getValue())
                .setSqlSelect("schedule_plan_id as schedulePlanId"));

        if (oriRegistration == null) {
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InvalidParamException()));
            return rsp;
        }

        Registration registration = new Registration();
        registration.setId(req.getId());
        registration.setStatus(RegistrationStatusEnum.Cancel.getValue());
        registration.setSchedulePlanId(oriRegistration.getSchedulePlanId());
        oriRegistration = null;

        try {
            if (!transactionalAction.cancelRegistration(registration)) {
                rsp.setBaseResp(ThriftUtil.getBaseResp(new InvalidParamException()));
                return rsp;
            }
        } catch (Exception e) {
            log.error("cancelRegistration err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetRegistrationCountResponse getRegistrationCount(GetRegistrationCountRequest req) throws TException {
        GetRegistrationCountResponse rsp = new GetRegistrationCountResponse();
        try {
            int count = registrationService.selectRegistrationCount(req.getDoctorId(),
                    req.getDepartId(), req.getStartTime(), req.getEndTime());
            rsp.setCount(count);
        } catch (Exception e) {
            log.error("getRegistrationCount err:", e);
        }
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateMedicalRecordResponse addOrUpdateMedicalRecord(AddOrUpdateMedicalRecordRequest req) throws TException {
        AddOrUpdateMedicalRecordResponse rsp = new AddOrUpdateMedicalRecordResponse();
        MedicalRecord record = MedicalRecord.convert(req.getBean());
        List<DoctorDiagnostic> doctorDiagnostics = null;

        if (req.getBean().isSetDoctorDiagnostics()) {
            doctorDiagnostics = req.getBean()
                    .getDoctorDiagnostics()
                    .parallelStream()
                    .map(DoctorDiagnostic::convert)
                    .collect(Collectors.toList());
        }

        try {
            if (!transactionalAction.addOrUpdateMedicalRecord(record, doctorDiagnostics)) {
                rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
                return rsp;
            }
        } catch (InvalidParamException e) {
            log.warn(e.getMessage());
            rsp.setBaseResp(ThriftUtil.getBaseResp(e));
            return rsp;
        } catch (Exception e) {
            log.error("addOrUpdateMedicalRecord err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetMedicalRecordResponse getMedicalRecord(GetMedicalRecordRequest req) throws TException {
        GetMedicalRecordResponse rsp = new GetMedicalRecordResponse();
        Wrapper wrapper = new EntityWrapper().eq("valid", 1).orderBy("id desc");
        Page<MedicalRecord> medicalRecordPage;

        try {
            if (req.isSetIds()) {
                wrapper = wrapper.in("id", req.getIds());
                medicalRecordPage = medicalRecordService.selectPage(new Page<>(0, 100), wrapper);
            } else {
                Long medicalRecordNo = req.isSetMedicalRecordNo() ? req.getMedicalRecordNo() : null;
                medicalRecordPage = medicalRecordService.
                        selectUsingIdNum(new Page<>(0, 100),
                                req.getIdNum(), medicalRecordNo);
            }

            rsp.setBeanList(serverServiceAction.getMedicalRecords(medicalRecordPage));
        } catch (Exception e) {
            log.error("getMedicalRecord err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        rsp.setTotal((int) medicalRecordPage.getTotal());
        return rsp;
    }

    @Override
    public UpdateApplyItemResponse updateApplyItem(UpdateApplyItemRequest req) throws TException {
        UpdateApplyItemResponse rsp = new UpdateApplyItemResponse();
        rsp.setBaseResp(ThriftUtil.getBaseResp());

        try {
            if (!serverServiceAction.updateApplyItem(req)) {
                rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            }
        } catch (Exception e) {
            log.error("updateApplyItem err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
        }

        return rsp;
    }

    @Override
    public AddOrUpdateApplyGroupResponse addOrUpdateApplyGroup(AddOrUpdateApplyGroupRequest req) throws TException {
        AddOrUpdateApplyGroupResponse rsp = new AddOrUpdateApplyGroupResponse();
        ApplyGroup applyGroup = ApplyGroup.convert(req.getBean());

        try {
            if (applyGroup.getValid() != null && applyGroup.getValid() == -1) {
                transactionalAction.delApplyGroup(applyGroup);
            } else {
                if (applyGroup.getId() == null) {
                    applyGroup.setCode(IDGeneratorUtil.genCode("APG", redisTemplate, "apply_group_code_incr"));
                }

                List<ApplyItemTemplate> itemTemplates = null;

                if (req.getBean().isSetItems()) {
                    itemTemplates = req.getBean().getItems()
                            .parallelStream()
                            .map(ApplyItemTemplate::convert)
                            .collect(Collectors.toList());
                }

                transactionalAction.addOrUpdateApplyGroup(applyGroup, itemTemplates);
            }
        } catch (Exception e) {
            log.error("addOrUpdateApplyGroup err,", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetApplyGroupResponse getApplyGroup(GetApplyGroupRequest req) throws TException {
        GetApplyGroupResponse rsp = new GetApplyGroupResponse();
        Wrapper wrapper = WrapperGenerator.getApplyGroupWrapper(req);
        int offset = 0;
        int limit = 100;

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        Page<ApplyGroup> page;
        try {
            page = applyGroupService.selectPage(
                    new Page<>(offset, limit), wrapper);
            page.setTotal(applyGroupService.selectCount(wrapper));
        } catch (Exception e) {
            log.error("getApplyGroup err,", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBeanList(serverServiceAction.getApplyGroup(page.getRecords()));
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateMedicalRecordTemplateResponse addOrUpdateMedicalRecordTemplate(AddOrUpdateMedicalRecordTemplateRequest req) throws TException {
        AddOrUpdateMedicalRecordTemplateResponse rsp = new AddOrUpdateMedicalRecordTemplateResponse();

        try {
            if (!req.getBean().isSetId()) {
                req.getBean().setCode(IDGeneratorUtil.genCode("MRD",
                        redisTemplate,
                        "medical_record_temp_code_incr"));
            }
        } catch (Exception e) {
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        MedicalRecordTemplate medicalRecordTemplate = MedicalRecordTemplate.convert(req.getBean());

        try {
            if (req.getBean().isSetValid() && medicalRecordTemplate.getValid() == -1) {
                transactionalAction.delMedicalRecordTemplate(medicalRecordTemplate);
            } else {
                List<DiagnosticForMedicalRecordTemplate> diagnosticForMedicalRecordTemplates = null;

                if (req.getBean().isSetDoctorDiagnostics()) {
                    diagnosticForMedicalRecordTemplates = req.getBean().getDoctorDiagnostics()
                            .parallelStream()
                            .map(DiagnosticForMedicalRecordTemplate::convert)
                            .collect(Collectors.toList());
                }

                transactionalAction.addOrUpdateMedicalRecordTemplate(medicalRecordTemplate, diagnosticForMedicalRecordTemplates);
            }
        } catch (Exception e) {
            log.error("addOrUpdateMedicalRecordTemplate err,", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetMedicalRecordTemplateResponse getMedicalRecordTemplate(GetMedicalRecordTemplateRequest getMedicalRecordTemplateRequest) throws TException {
        GetMedicalRecordTemplateResponse rsp = new GetMedicalRecordTemplateResponse();
        Wrapper wrapper = WrapperGenerator.getMedicalRecordTemplateWrapper(getMedicalRecordTemplateRequest);
        int offset = 0;
        int limit = 100;

        if (getMedicalRecordTemplateRequest.isSetOffset()) {
            offset = getMedicalRecordTemplateRequest.getOffset();
        }

        if (getMedicalRecordTemplateRequest.isSetLimit()) {
            limit = getMedicalRecordTemplateRequest.getLimit();
        }

        Page<MedicalRecordTemplate> page = null;
        try {
            page = medicalRecordTemplateService.selectPage(
                    new Page<>(offset, limit), wrapper);
            page.setTotal(medicalRecordTemplateService.selectCount(wrapper));
        } catch (Exception e) {
            log.error("getMedicalRecordTemplate err,", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBeanList(serverServiceAction.getMedicalRecordTemplate(page.getRecords()));
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateCommonlyUsedDiagnosticResponse addOrUpdateCommonlyUsedDiagnostic(AddOrUpdateCommonlyUsedDiagnosticRequest addOrUpdateCommonlyUsedDiagnosticRequest) throws TException {
        AddOrUpdateCommonlyUsedDiagnosticResponse rsp = new AddOrUpdateCommonlyUsedDiagnosticResponse();

        try {
            List<CommonlyUsedDiagnostic> list =
                    addOrUpdateCommonlyUsedDiagnosticRequest.getBean().parallelStream().map(CommonlyUsedDiagnostic::convert).collect(Collectors.toList());
            commonlyUsedDiagnosticService.insertOrUpdateBatch(list);
        } catch (Exception e) {
            log.error("addOrUpdateCommonlyUsedDiagnostic err,", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetCommonlyUsedDiagnosticResponse getCommonlyUsedDiagnostic(GetCommonlyUsedDiagnosticRequest req) throws TException {
        GetCommonlyUsedDiagnosticResponse rsp = new GetCommonlyUsedDiagnosticResponse();
        List<CommonlyUsedDiagnostic> list;
        Wrapper wrapper = WrapperGenerator.getCommonlyUsedDiagnosticWrapper(req);
        int offset = 0;
        int limit = 100;

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        Page<CommonlyUsedDiagnostic> page = null;
        try {
            page = commonlyUsedDiagnosticService.selectPage(
                    new Page<>(offset, limit), wrapper);
            page.setTotal(commonlyUsedDiagnosticService.selectCount(wrapper));
        } catch (Exception e) {
            log.error("getCommonlyUsedDiagnostic err,", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        list = page.getRecords();
        List<top.itcat.rpc.service.model.CommonlyUsedDiagnostic> rpcList =
                list.parallelStream().map(CommonlyUsedDiagnostic::convertRPCBean).collect(Collectors.toList());
        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    /**
     * 开具申请单
     * 申请单只允许开具和作废，不允许更新
     *
     * @see AddOrUpdateApplyRequest
     * @see AddOrUpdateApplyResponse
     */
    @Override
    public AddOrUpdateApplyResponse addOrUpdateApply(AddOrUpdateApplyRequest req) throws TException {
        AddOrUpdateApplyResponse rsp = new AddOrUpdateApplyResponse();
        Apply apply = Apply.convert(req.getBean());

        try {
            if (apply.getValid() != null && apply.getValid() == -1) {
                transactionalAction.delApply(apply);
            } else {
                List<ApplyItem> applyItems = null;

                if (req.getBean().isSetItems()) {
                    applyItems = req.getBean().getItems()
                            .parallelStream()
                            .filter(Objects::nonNull)
                            .map(ApplyItem::convert)
                            .collect(Collectors.toList());
                }

                transactionalAction.addApply(apply, applyItems);
            }
        } catch (NotEnoughException | InvalidParamException e) {
            log.info(e.getMessage());
            rsp.setBaseResp(ThriftUtil.getBaseResp(e));
            return rsp;
        } catch (Exception e) {
            log.error("addOrUpdateApply err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetApplyResponse getApply(GetApplyRequest req) throws TException {
        GetApplyResponse rsp = new GetApplyResponse();
        Wrapper wrapper = WrapperGenerator.getApplyWrapper(req);
        int offset = 0;
        int limit = 100;

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        Page<Apply> page = null;
        try {
            page = applyService.selectPage(
                    new Page<>(offset, limit), wrapper);
        } catch (Exception e) {
            log.error("getApply err,", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        List<top.itcat.rpc.service.model.Apply> rpcList = page.getRecords().parallelStream().map(Apply::convertRPCBean).collect(Collectors.toList());

        try {
            List<Long> applyIds = page.getRecords().parallelStream().map(Apply::getId).collect(Collectors.toList());
            List<ApplyItem> applyItems = applyItemService.selectList(new EntityWrapper<ApplyItem>()
                    .in("apply_id", applyIds)
                    .eq("valid", 1));
            serverServiceAction.packApplyItem(applyItems);
            Map<Long, List<top.itcat.rpc.service.model.ApplyItem>> applyItemMap = new HashMap<>(applyIds.size());

            for (ApplyItem item : applyItems) {
                List<top.itcat.rpc.service.model.ApplyItem> items = applyItemMap.computeIfAbsent(item.getApplyId(), k -> new ArrayList<>(2));
                items.add(ApplyItem.convertRPCBean(item));
            }

            for (top.itcat.rpc.service.model.Apply apply : rpcList) {
                apply.setItems(applyItemMap.get(apply.getId()));
            }
        } catch (Exception e) {
            log.error("getApply err,", e);
        }


        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetApplyItemResponse getApplyItem(GetApplyItemRequest req) throws TException {
        GetApplyItemResponse rsp = new GetApplyItemResponse();
        Page<ApplyItem> page;

        try {
            page = serverServiceAction.getApplyItem(req);
        } catch (Exception e) {
            log.error("getApplyItem err,", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        List<top.itcat.rpc.service.model.ApplyItem> rpcList = page.getRecords()
                .parallelStream().map(ApplyItem::convertRPCBean).collect(Collectors.toList());
        rsp.setBeanList(rpcList);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public EnterApplyItemResultResponse enterApplyItemResult(EnterApplyItemResultRequest req) throws TException {
        EnterApplyItemResultResponse rsp = new EnterApplyItemResultResponse();
        rsp.setBaseResp(ThriftUtil.getBaseResp());

        try {
            serverServiceAction.enterApplyItemResult(req);
        } catch (InvalidParamException e) {
            log.warn("EnterApplyItemResult InvalidParamException");
            rsp.setBaseResp(ThriftUtil.getBaseResp(e));
        } catch (Exception e) {
            log.error("enterApplyItemResult err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp());
        }

        return rsp;
    }
}
