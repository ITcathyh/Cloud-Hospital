package top.itcat;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.itcat.cache.RedisCache;
import top.itcat.rpc.service.ServiceServer;
import top.itcat.rpc.service.diagnose.*;
import top.itcat.rpc.service.model.*;
import top.itcat.service.CommonlyUsedDiagnosticService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class NewTest {
    @Autowired
    ServiceServer serviceServer;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private CommonlyUsedDiagnosticService commonlyUsedDiagnosticService;

    @Before
    public void before() throws ClassNotFoundException {
        RedisCache.setRedisTemplate(redisTemplate);
    }

    @Test
    public void testGetCommonlyUsedDiagnostic() throws TException {
        GetCommonlyUsedDiagnosticRequest rpcReq = new GetCommonlyUsedDiagnosticRequest();
//        rpcReq.setIds(Collections.singletonList(1L));
        GetCommonlyUsedDiagnosticResponse rsp = serviceServer.getCommonlyUsedDiagnostic(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertEquals(rsp.getTotal(), 4);
        assertEquals(rsp.getBeanList().get(0).getId(), 11);
    }

    @Test
    public void testInCommonlyUsedDiagnosticRequest() throws Exception {
        top.itcat.rpc.service.model.CommonlyUsedDiagnostic commonlyUsedDiagnostic = new top.itcat.rpc.service.model.CommonlyUsedDiagnostic();

        commonlyUsedDiagnostic.setCatalog(DoctorDiagnosticCatalogEnum.Western);
        commonlyUsedDiagnostic.setDiagnosticId(1);
        commonlyUsedDiagnostic.setDoctorId(1);
        commonlyUsedDiagnostic.setSuitableRange(SuitableRangeEnum.Hospital);
        commonlyUsedDiagnostic.setDoctorId(1);

        AddOrUpdateCommonlyUsedDiagnosticRequest req = new AddOrUpdateCommonlyUsedDiagnosticRequest();
        req.setBean(Collections.singletonList(commonlyUsedDiagnostic));
        serviceServer.addOrUpdateCommonlyUsedDiagnostic(req);
    }

    @Test
    public void testGetApplyGroup() throws TException {
        GetApplyGroupRequest rpcReq = new GetApplyGroupRequest();
//        rpcReq.setIds(Collections.singletonList(1L));
        GetApplyGroupResponse rsp = serviceServer.getApplyGroup(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertEquals(rsp.getTotal(), 1);
        assertEquals(rsp.getBeanList().get(0).getId(), 1);
    }

    @Test
    public void testInApplyGroupRequest() throws Exception {
        top.itcat.rpc.service.model.ApplyGroup applyGroup = new top.itcat.rpc.service.model.ApplyGroup();
        top.itcat.rpc.service.model.ApplyItemTemplate applyItemTemplate = new top.itcat.rpc.service.model.ApplyItemTemplate();
        
        applyGroup.setCode("a");
        applyGroup.setName("a");
        applyGroup.setCategory(ApplyCategory.Inspection);
        applyGroup.setDocumentCategory(DocumentCategory.Normal);
        applyGroup.setServiceObject(ServiceObject.Normal);
        applyGroup.setSuitableRange(SuitableRangeEnum.Hospital);
        applyGroup.setCreatorId(1L);
        applyGroup.setDepartmentId(1L);
        applyGroup.setCreateTime(1L);
        applyGroup.setClinicalImpression("a");
        applyGroup.setClinicalDiagnosis("a");
        applyGroup.setGoalAndRequirement("a");
        applyGroup.setRemark("a");

        applyItemTemplate.setGroupId(1L);
        applyItemTemplate.setNonmedicalId(1L);
        applyItemTemplate.setNote("a");

        List<ApplyItemTemplate> list = new ArrayList<>();
        list.add(applyItemTemplate);
        applyGroup.setItems(list);

        AddOrUpdateApplyGroupRequest req = new AddOrUpdateApplyGroupRequest();
        req.setBean(applyGroup);
        serviceServer.addOrUpdateApplyGroup(req);
    }

    @Test
    public void testGetMedicalRecordTemplate() throws TException {
        GetMedicalRecordTemplateRequest rpcReq = new GetMedicalRecordTemplateRequest();
//        rpcReq.setIds(Collections.singletonList(1L));
        GetMedicalRecordTemplateResponse rsp = serviceServer.getMedicalRecordTemplate(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertEquals(rsp.getTotal(), 4);
        assertEquals(rsp.getBeanList().get(0).getId(), 1);
    }

    @Test
    public void testInMedicalRecordTemplateRequest() throws Exception {
        top.itcat.rpc.service.model.MedicalRecordTemplate medicalRecordTemplate = new top.itcat.rpc.service.model.MedicalRecordTemplate();

        medicalRecordTemplate.setCode("a");
        medicalRecordTemplate.setName("a");
        medicalRecordTemplate.setDoctorId(1L);
        medicalRecordTemplate.setSuitableRange(SuitableRangeEnum.Hospital);
        medicalRecordTemplate.setComplain("a");
        medicalRecordTemplate.setCurrentMedicalHistory("a");
        medicalRecordTemplate.setPhysicalCheckUp("a");
        medicalRecordTemplate.setPreliminaryDiagnosisWestern("a");
        medicalRecordTemplate.setPreliminaryDiagnosisChinese("a");

        List<DiagnosticForMedicalRecordTemplate> list = new ArrayList<>();

        DiagnosticForMedicalRecordTemplate diagnosticForMedicalRecordTemplate = new DiagnosticForMedicalRecordTemplate();
        diagnosticForMedicalRecordTemplate.setId(1L);
        diagnosticForMedicalRecordTemplate.setDoctorId(1L);
        diagnosticForMedicalRecordTemplate.setDiagnosticId(1L);
        diagnosticForMedicalRecordTemplate.setMedicalRecordTemplateId(1L);
        diagnosticForMedicalRecordTemplate.setCatalog(DoctorDiagnosticCatalogEnum.Western);

        list.add(diagnosticForMedicalRecordTemplate);
        medicalRecordTemplate.setDoctorDiagnostics(list);
        AddOrUpdateMedicalRecordTemplateRequest req = new AddOrUpdateMedicalRecordTemplateRequest();
        req.setBean(medicalRecordTemplate);
        serviceServer.addOrUpdateMedicalRecordTemplate(req);
    }
}
