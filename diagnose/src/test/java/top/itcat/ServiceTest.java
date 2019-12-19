package top.itcat;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import top.itcat.cache.RedisCache;
import top.itcat.entity.registrantion.SchedulePlan;
import top.itcat.rpc.service.ServiceServer;
import top.itcat.rpc.service.diagnose.*;
import top.itcat.rpc.service.model.*;
import top.itcat.service.SchedulePlanService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class ServiceTest {
    @Autowired
    ServiceServer serviceServer;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Before
    public void before() {
        RedisCache.setRedisTemplate(redisTemplate);
    }

    @Autowired
    SchedulePlanService schedulePlanService;

    @Test
    public void testEnterApplyItemResult() throws TException {
        EnterApplyItemResultRequest req = new EnterApplyItemResultRequest();
        ApplyItem applyItem = new ApplyItem();
        applyItem.setResult("新的结果");
        applyItem.setId(22L);
        ResultItem resultItem = new ResultItem();
        resultItem.setItemId(22L);
        resultItem.setImagePath("一个路径");
        applyItem.setResultItems(Collections.singletonList(resultItem));

        req.setItem(applyItem);
        EnterApplyItemResultResponse rsp = serviceServer.enterApplyItemResult(req);
        assertEquals(0, rsp.getBaseResp().getStatusCode());
    }

    @Test
    public void testGetApplyItemResult() throws TException {
        GetApplyItemRequest req = new GetApplyItemRequest();
        GetApplyItemResponse rsp = serviceServer.getApplyItem(req);
        System.out.println(rsp);
        assertEquals(0, rsp.getBaseResp().getStatusCode());
    }

    @Test
    public void testCancelRegistration() throws TException {
        CancelRegistrationRequest req = new CancelRegistrationRequest();
        req.setId(9L);
        CancelRegistrationResponse rsp = serviceServer.cancelRegistration(req);
        assertEquals(0, rsp.getBaseResp().getStatusCode());
    }

    @Test
    public void testGetRegistrationCount() throws TException {
        GetRegistrationCountRequest req = new GetRegistrationCountRequest();
        req.setEndTime(System.currentTimeMillis());
        req.setStartTime(System.currentTimeMillis() - 60 * 60 * 24 * 1000 * 15);
        req.setDoctorId(1L);
//        req.setDepartId(1);

        System.out.println(serviceServer.getRegistrationCount(req));
    }

    @Test
    public void testRegistration() throws TException {
        AddOrUpdateRegistrationRequest req = new AddOrUpdateRegistrationRequest();
        Registration registration = new Registration();
        registration.setIdentityCardNo("213");
        registration.setBillingCategoryId(1L);
        registration.setMedicalRecordNo(123);
        registration.setRegistrationSource(RegistrationSourceEnum.Site);
        registration.setSchedulePlanId(2);
        registration.setRegistrationTime(System.currentTimeMillis());
        registration.setSeeDoctorTime(0);
        registration.setStatus(RegistrationStatusEnum.UnSeen);
        req.setBean(registration);

        System.out.println(serviceServer.addOrUpdateRegistration(req));
    }

    @Test
    public void testAddScheduleRule() throws TException, InterruptedException {
        AddOrUpdateScheduleRuleRequest req = new AddOrUpdateScheduleRuleRequest();
        ScheduleRule scheduleRule = new ScheduleRule();
        scheduleRule.setLimitNumber(40);
        scheduleRule.setStartTime(System.currentTimeMillis() - 60 * 60 * 1000 * 24);
        scheduleRule.setEndTime(System.currentTimeMillis() + 60 * 60 * 1000 * 24 * 15);
        scheduleRule.setRegistrationLevelId(1L);
        scheduleRule.setOperationDate(20190530);
        scheduleRule.setDoctorId(15L);
        scheduleRule.setDay(WorkDayEnum.Fri);
        scheduleRule.setDepartmentId(1L);
        scheduleRule.setNoonBreak(WorkTimeEnum.AM);
        req.setBeanList(Collections.singletonList(scheduleRule));
        serviceServer.addOrUpdateScheduleRule(req);

        Thread.sleep(5000);
    }

    @Test
    public void testUpdateScheduleRule() throws TException, InterruptedException {
        AddOrUpdateScheduleRuleRequest req = new AddOrUpdateScheduleRuleRequest();
        ScheduleRule scheduleRule = new ScheduleRule();
        scheduleRule.setId(2L);
        scheduleRule.setLimitNumber(500);
        scheduleRule.setDepartmentId(1L);

        req.setBeanList(Collections.singletonList(scheduleRule));
        serviceServer.addOrUpdateScheduleRule(req);

        Thread.sleep(5000);
    }

    @Test
    public void testGetFirstDiagDir() throws TException {
        GetFirstDiagDirRequest rpcReq = new GetFirstDiagDirRequest();
//        rpcReq.setIds(Collections.singletonList(1L));
        GetFirstDiagDirResponse rsp = serviceServer.getFirstDiagDir(rpcReq);
        System.out.println(rsp);
//        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
//        assertEquals(rsp.getTotal(), 23);
//        assertEquals(rsp.getBeanList().get(0).getId(), 1);
    }

    @Test
    public void testInFirstDiagDirRequest() throws Exception {
        top.itcat.rpc.service.model.FirstDiagDir firstDiagDir = new top.itcat.rpc.service.model.FirstDiagDir();


        AddOrUpdateFirstDiagDirRequest req = new AddOrUpdateFirstDiagDirRequest(Collections.singletonList(firstDiagDir));
        serviceServer.addOrUpdateFirstDiagDir(req);
    }

    @Test
    public void testGetSecondDiagDir() throws TException {
        GetSecondDiagDirRequest rpcReq = new GetSecondDiagDirRequest();
//        rpcReq.setIds(Collections.singletonList(1L));
        GetSecondDiagDirResponse rsp = serviceServer.getSecondDiagDir(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
//        assertEquals(rsp.getTotal(), 26);
//        assertEquals(rsp.getBeanList().get(0).getId(), 1);
    }

    @Test
    public void testInSecondDiagDirRequest() throws Exception {
        top.itcat.rpc.service.model.SecondDiagDir secondDiagDir = new top.itcat.rpc.service.model.SecondDiagDir();

        secondDiagDir.setName("测试");
        secondDiagDir.setFirstDiagDirId(1);

        AddOrUpdateSecondDiagDirRequest req = new AddOrUpdateSecondDiagDirRequest(Collections.singletonList(secondDiagDir));
        serviceServer.addOrUpdateSecondDiagDir(req);
    }

    @Test
    public void testGetApply() throws TException {
        GetApplyRequest rpcReq = new GetApplyRequest();
//        rpcReq.setIds(Collections.singletonList(1L));
        GetApplyResponse rsp = serviceServer.getApply(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
//        assertEquals(rsp.getTotal(), 1);
//        assertEquals(rsp.getBeanList().get(0).getId(), 1);
    }

    @Test
    @Rollback(false)
    public void testInApplyRequest() throws Exception {
        top.itcat.rpc.service.model.Apply apply = new top.itcat.rpc.service.model.Apply();
        top.itcat.rpc.service.model.ApplyItem applyItem = new top.itcat.rpc.service.model.ApplyItem();

        apply.setCategory(ApplyCategory.Disposal);
        apply.setDoctorId(1);
        apply.setMedicalRecordNo(1);
        apply.setTime(System.currentTimeMillis());

        applyItem.setId(1L);
        applyItem.setApplyId(1L);
        applyItem.setChargeItemId(1L);
        applyItem.setMedicalDoctorId(1L);
        applyItem.setNote("a");
        applyItem.setResult("a");
        applyItem.setStatus(ApplyItemStatus.Registered);

        List<ApplyItem> list = new ArrayList<>();
        list.add(applyItem);
        apply.setItems(list);

        AddOrUpdateApplyRequest req = new AddOrUpdateApplyRequest();
        req.setBean(apply);
        serviceServer.addOrUpdateApply(req);
    }

    @Test
    @Rollback(false)
    public void testInApplyRequest1() throws Exception {
        top.itcat.rpc.service.model.Apply apply = new top.itcat.rpc.service.model.Apply();
        apply.setId(6);
        apply.setValid(-1);

        AddOrUpdateApplyRequest req = new AddOrUpdateApplyRequest();
        req.setBean(apply);
        serviceServer.addOrUpdateApply(req);
    }

    @Test
    public void testGetMedicalRecord() throws TException {
        GetMedicalRecordRequest rpcReq = new GetMedicalRecordRequest();
        GetMedicalRecordResponse rsp = serviceServer.getMedicalRecord(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
//        assertEquals(rsp.getTotal(), 1);
//        assertEquals(rsp.getBeanList().get(0).getId(), 1);
    }

    @Test
    public void testInMedicalRecordRequest() throws Exception {
        top.itcat.rpc.service.model.MedicalRecord medicalRecord = new top.itcat.rpc.service.model.MedicalRecord();

        medicalRecord.setAllergyHistory("测试");
        medicalRecord.setDoctorId(1);
        medicalRecord.setComplain("测试");
        medicalRecord.setMedicalRecordNo(1);
        medicalRecord.setCurrentMedicalHistory("测试");
        medicalRecord.setCurrentMedicalTreatment("测试");
        medicalRecord.setPastMedicalHistory("测试");
        medicalRecord.setPhysicalCheckUp("测试");
        medicalRecord.setPreliminaryDiagnosisChinese("测试");
        medicalRecord.setPreliminaryDiagnosisWestern("测试");
        medicalRecord.setTime(1);

        List<DoctorDiagnostic> list = new ArrayList<>();

        DoctorDiagnostic doctorDiagnostic = new DoctorDiagnostic();
        doctorDiagnostic.setId(1L);
        doctorDiagnostic.setDoctorId(1L);
        doctorDiagnostic.setDiagnosticId(1L);
        doctorDiagnostic.setMedicalRecordNo(6L);
        doctorDiagnostic.setCatalog(DoctorDiagnosticCatalogEnum.Chinese);
        doctorDiagnostic.setMain(true);
        doctorDiagnostic.setSuspect(false);

        list.add(doctorDiagnostic);
        medicalRecord.setDoctorDiagnostics(list);
        AddOrUpdateMedicalRecordRequest req = new AddOrUpdateMedicalRecordRequest();
        req.setBean(medicalRecord);
        serviceServer.addOrUpdateMedicalRecord(req);
    }

    @Test
    public void testGetDiagnostic() throws TException {
        GetDiagnosticRequest rpcReq = new GetDiagnosticRequest();
        rpcReq.setSearchKey("wt");

        GetDiagnosticResponse rsp = serviceServer.getDiagnostic(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
    }

    @Test
    public void testInDiagnosticRequest() throws Exception {
        top.itcat.rpc.service.model.Diagnostic diagnostic = new top.itcat.rpc.service.model.Diagnostic();

        diagnostic.setName("测试");
        diagnostic.setCode("CS");
        diagnostic.setSecondDiagDirId(1);

        AddOrUpdateDiagnosticRequest req = new AddOrUpdateDiagnosticRequest(Collections.singletonList(diagnostic));
        serviceServer.addOrUpdateDiagnostic(req);
    }


    @Test
    public void testGetSchedulePlan() throws TException {
        GetSchedulePlanRequest rpcReq = new GetSchedulePlanRequest();
//        rpcReq.setIds(Collections.singletonList(1L));
        rpcReq.setStartTime(System.currentTimeMillis() - 13*1000*60*60*24);
        GetSchedulePlanResponse rsp = serviceServer.getSchedulePlan(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
//        assertEquals(rsp.getTotal(), 1);
//        assertEquals(rsp.getBeanList().get(0).getId(), 1);
    }

    @Test
    public void testInSchedulePlanRequest() throws Exception {
        top.itcat.rpc.service.model.SchedulePlan schedulePlan = new top.itcat.rpc.service.model.SchedulePlan();
        schedulePlan.setStartTime(new DateTime().withMillisOfDay(0).getMillis());
        schedulePlan.setEndTime(new DateTime().withMillisOfDay(0).getMillis());
//        schedulePlan.setScheduleId(6);
        schedulePlan.setRemain(40);
        ScheduleRule rule = new ScheduleRule();
        rule.setDepartmentId(3);
        rule.setDoctorId(1);
        rule.setNoonBreak(WorkTimeEnum.AM);
        rule.setStartTime(new DateTime().withMillisOfDay(0).getMillis());
        rule.setEndTime(new DateTime().plusDays(2).withMillisOfDay(0).getMillis());
        rule.setRegistrationLevelId(2);
        rule.setLimitNumber(40);
        rule.setOperationDate(20190625);

        AddOrUpdateSchedulePlanRequest req = new AddOrUpdateSchedulePlanRequest();
        req.setPlan(schedulePlan);
        req.setRule(rule);
        serviceServer.addOrUpdateSchedulePlan(req);
    }

    @Test
    public void testGetScheduleRule() throws TException {
        GetScheduleRuleRequest rpcReq = new GetScheduleRuleRequest();
//        rpcReq.setIds(Collections.singletonList(1L));
        GetScheduleRuleResponse rsp = serviceServer.getScheduleRule(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertEquals(rsp.getTotal(), 1);
        assertEquals(rsp.getBeanList().get(0).getId(), 1);
    }

//    @Test
//    public void testInScheduleRuleRequest() throws Exception {
//        top.itcat.rpc.service.model.ScheduleRule scheduleRule = new top.itcat.rpc.service.model.ScheduleRule();
//
//        scheduleRule.set
//
//        AddOrUpdateScheduleRuleRequest req = new AddOrUpdateScheduleRuleRequest(Collections.singletonList(scheduleRule));
//        serviceServer.addOrUpdateScheduleRule(req);
//    }

//    @Test
//    public void testCancelRegistrationRequest() throws Exception {
//
//    }

    @Test
    public void testGetRegistration() throws TException {
        GetRegistrationRequest rpcReq = new GetRegistrationRequest();
        rpcReq.setSearchKey("441382199806245612");
//        rpcReq.setStatus(RegistrationStatusEnum.Done);
        GetRegistrationResponse rsp = serviceServer.getRegistration(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
//        assertEquals(rsp.getTotal(), 1);
//        assertEquals(rsp.getBeanList().get(0).getId(), 1);
    }

    @Test
    public void testGetApplyItem() throws TException {
        GetApplyItemRequest rpcReq = new GetApplyItemRequest();
        rpcReq.setMedicalRecordNo(1906210013);
        GetApplyItemResponse rsp = serviceServer.getApplyItem(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
    }

//    @Test
//    public void testInRegistrationRequest() throws Exception {
//        top.itcat.rpc.service.model.Registration registration = new top.itcat.rpc.service.model.Registration();
//
//        registration.setIdentity_card_no("210102201905299999");
//        registration.setBillingCategoryId(1);
//        registration.setMedicalRecordNo(2019052912345L);
//        registration.setRegistrationSource(RegistrationSourceEnum.Site);
//        registration.setRegistrationTime(201905291158L);
//        registration.setSchedulePlanId(1);
//        registration.setSeeDoctorTime(201905291200L);
//        registration.setBillingCategoryId(1);
//        registration.setStatus(RegistrationStatusEnum.Done);
//        registration.setSequenceNumber(1);
//
//        AddOrUpdateRegistrationRequest req = new AddOrUpdateRegistrationRequest(Collections.singletonList(registration));
//        serviceServer.addOrUpdateRegistration(req);
//    }
}