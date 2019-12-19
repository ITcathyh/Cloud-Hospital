package top.itcat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.itcat.cache.RedisCache;
import top.itcat.constant.Const;
import top.itcat.rpc.service.model.*;
import top.itcat.rpc.service.user.*;
import top.itcat.util.EncryUtil;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class ServiceTest {
    @Autowired
    private UserService.Iface userService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Before
    public void before() {
        RedisCache.setRedisTemplate(redisTemplate);
    }

    @Test
    public void tests() throws Exception{
        new RedisCache("top.itcat.").clear();
    }

    @Test
    public void testIn() throws Exception {
        top.itcat.rpc.service.model.OutpatientDoctor doctor = new top.itcat.rpc.service.model.OutpatientDoctor();
        top.itcat.rpc.service.model.User user = new top.itcat.rpc.service.model.User();

        user.setPassword("1");
        user.setDepartId(1);
        user.setCode("ODT");
        user.setRealName("odTest");
        doctor.setTitle(DoctorTitleEnum.AssistantDirector);
        doctor.setDescription("a");
        doctor.setUser(user);
        doctor.setInSchedual(true);

        AddOrUpdateDoctorRequest req = new AddOrUpdateDoctorRequest(Collections.singletonList(doctor));
        userService.addOrUpdateDoctor(req);
    }

    @Test
    public void testMGetDoctor1() throws Exception {
        MGetDoctorRequest req = new MGetDoctorRequest();
        MGetDoctorResponse rsp = userService.mGetDoctor(req);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
    }


    @Test
    public void testLogin() throws Exception {
        GetUserByUsernameAndPasswordRequest req = new GetUserByUsernameAndPasswordRequest();
        req.setUsername("OD000001");
        req.setPassword("1");

        GetUserByUsernameAndPasswordResponse rsp = userService.getUserByUsernameAndPassword(req);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertNotNull(rsp.getUser());

        req.setPassword("asdasd");
        rsp = userService.getUserByUsernameAndPassword(req);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertNull(rsp.getUser());
    }

    @Test
    public void testInPatientRequest() throws Exception {
        top.itcat.rpc.service.model.Patient patient = new top.itcat.rpc.service.model.Patient();

        BasePatientInfo basePatientInfo = new BasePatientInfo();
        basePatientInfo.setAddress("1");
        basePatientInfo.setAge(1);
        basePatientInfo.setBirth(20190527);
        basePatientInfo.setGender(GenderEnum.Woman);
        basePatientInfo.setName("1");
        basePatientInfo.setPhone("13333333333");
        basePatientInfo.setIdNum("210102201905277777");

        patient.setUserInfo(basePatientInfo);

        AddOrUpdatePatientRequest req = new AddOrUpdatePatientRequest(Collections.singletonList(patient));
        userService.addOrUpdatePatient(req);
    }

    @Test
    public void testMGetPatient() throws Exception {
        MGetPatientRequest req = new MGetPatientRequest();
        req.setSearchKey("511521199808041111");

        MGetPatientResponse rsp = userService.mGetPatient(req);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
    }

    @Test
    public void testInAccountClerkRequest() throws Exception {
        top.itcat.rpc.service.model.AccountClerk accountClerk = new top.itcat.rpc.service.model.AccountClerk();
        top.itcat.rpc.service.model.User user = new top.itcat.rpc.service.model.User();

        user.setPassword("1");
        user.setDepartId(1);
        user.setCode("asd");
        user.setRealName("test");
        accountClerk.setUser(user);

        AddOrUpdateAccountClerkRequest req = new AddOrUpdateAccountClerkRequest(Collections.singletonList(accountClerk));
        userService.addOrUpdateAccountClerk(req);
    }

    @Test
    public void testMGetAccountClerk() throws Exception {
        MGetAccountClerkRequest req = new MGetAccountClerkRequest();
        req.setLimit(100);
        req.setOffset(0);

        MGetAccountClerkResponse rsp = userService.mGetAccountClerk(req);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
//        assertEquals(rsp.getUsers().size(), 1);
//        assertEquals(rsp.getUsers().get(0).getUser().getId(), 1);
    }


    @Test
    public void testMGetAccountClerk1() throws Exception {
        MGetAccountClerkRequest req = new MGetAccountClerkRequest();
        req.setOffset(1);
        req.setLimit(10);

        MGetAccountClerkResponse rsp = userService.mGetAccountClerk(req);
        System.out.println(rsp);
    }

    @Test
    public void testAccountClerkLogin() throws Exception {
        GetUserByUsernameAndPasswordRequest req = new GetUserByUsernameAndPasswordRequest();
        req.setUsername("asd");
        req.setPassword("1");
        req.setRole(RoleEnum.Account_Clerk);

        GetUserByUsernameAndPasswordResponse rsp = userService.getUserByUsernameAndPassword(req);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertNotNull(rsp.getUser());

        req.setPassword("asdasd");
        rsp = userService.getUserByUsernameAndPassword(req);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertNull(rsp.getUser());
    }

    @Test
    public void testInHospitalManagerRequest() throws Exception {
        top.itcat.rpc.service.model.HospitalManager hospitalManager = new top.itcat.rpc.service.model.HospitalManager();
        top.itcat.rpc.service.model.User user = new top.itcat.rpc.service.model.User();

        user.setPassword("1");
        user.setDepartId(1);
        user.setCode("asd");
        user.setRealName("test");
        hospitalManager.setUser(user);

        AddOrUpdateHospitalManagerRequest req = new AddOrUpdateHospitalManagerRequest(Collections.singletonList(hospitalManager));
        userService.addOrUpdateHospitalManager(req);
    }

    @Test
    public void testMGetHospitalManager() throws Exception {
        MGetHospitalManagerRequest req = new MGetHospitalManagerRequest();
        req.setUids(Arrays.asList((long) 1, (long) -999));

        MGetHospitalManagerResponse rsp = userService.mGetHospitalManager(req);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertEquals(rsp.getUsers().size(), 1);
        assertEquals(rsp.getUsers().get(0).getUser().getId(), 1);
    }

    @Test
    public void testMGetHospitalManager1() throws Exception {
        MGetHospitalManagerRequest req = new MGetHospitalManagerRequest();

        MGetHospitalManagerResponse rsp = userService.mGetHospitalManager(req);
        System.out.println(rsp);
    }

    @Test
    public void testHospitalManagerLogin() throws Exception {
        GetUserByUsernameAndPasswordRequest req = new GetUserByUsernameAndPasswordRequest();
        req.setUsername("asd");
        req.setPassword("1");
        req.setRole(RoleEnum.Hospital_Manager);

        GetUserByUsernameAndPasswordResponse rsp = userService.getUserByUsernameAndPassword(req);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertNotNull(rsp.getUser());

        req.setPassword("asdasd");
        rsp = userService.getUserByUsernameAndPassword(req);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertNull(rsp.getUser());
    }

    @Test
    public void testInMedicalDoctorRequest() throws Exception {
        top.itcat.rpc.service.model.MedicalDoctor medicalDoctor = new top.itcat.rpc.service.model.MedicalDoctor();
        top.itcat.rpc.service.model.User user = new top.itcat.rpc.service.model.User();

        user.setPassword("1");
        user.setDepartId(1);
        user.setCode("MDT");
        user.setRealName("MDTest");
        medicalDoctor.setTitle(DoctorTitleEnum.Director);
        medicalDoctor.setDescription("aa");
        medicalDoctor.setUser(user);

        AddOrUpdateMedicalDoctorRequest req = new AddOrUpdateMedicalDoctorRequest(Collections.singletonList(medicalDoctor));
        userService.addOrUpdateMedicalDoctor(req);
    }

    @Test
    public void testInMedicalDoctorRequest1() throws Exception {
        top.itcat.rpc.service.model.MedicalDoctor medicalDoctor = new top.itcat.rpc.service.model.MedicalDoctor();
        top.itcat.rpc.service.model.User user = new top.itcat.rpc.service.model.User();

        user.setId(34);
        medicalDoctor.setValid(-1);
        medicalDoctor.setUser(user);

        AddOrUpdateMedicalDoctorRequest req = new AddOrUpdateMedicalDoctorRequest(Collections.singletonList(medicalDoctor));
        userService.addOrUpdateMedicalDoctor(req);
    }

    @Test
    public void testMGetMedicalDoctor() throws Exception {
        MGetMedicalDoctorRequest req = new MGetMedicalDoctorRequest();
//        req.setUids(Arrays.asList((long) 1, (long) -999));

        MGetMedicalDoctorResponse rsp = userService.mGetMedicalDoctor(req);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
//        assertEquals(rsp.getUsers().size(), 1);
//        assertEquals(rsp.getUsers().get(0).getUser().getId(), 1);
    }

    @Test
    public void testMedicalDoctorLogin() throws Exception {
        GetUserByUsernameAndPasswordRequest req = new GetUserByUsernameAndPasswordRequest();
        req.setUsername("MD000002");
        req.setPassword("1");

        GetUserByUsernameAndPasswordResponse rsp = userService.getUserByUsernameAndPassword(req);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertNotNull(rsp.getUser());


        req.setPassword("asdasd");
        rsp = userService.getUserByUsernameAndPassword(req);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertNull(rsp.getUser());
    }

    @Test
    public void testInPharmacyManagerRequest() throws Exception {
        top.itcat.rpc.service.model.PharmacyManager pharmacyManager = new top.itcat.rpc.service.model.PharmacyManager();
        top.itcat.rpc.service.model.User user = new top.itcat.rpc.service.model.User();

        user.setPassword("1");
        user.setDepartId(1);
        user.setCode("asd");
        user.setRealName("test");
        pharmacyManager.setUser(user);

        AddOrUpdatePharmacyManagerRequest req = new AddOrUpdatePharmacyManagerRequest(Collections.singletonList(pharmacyManager));
        userService.addOrUpdatePharmacyManager(req);
    }

    @Test
    public void testMGetPharmacyManager() throws Exception {
        MGetPharmacyManagerRequest req = new MGetPharmacyManagerRequest();
        req.setUids(Arrays.asList((long) 1, (long) -999));

        MGetPharmacyManagerResponse rsp = userService.mGetPharmacyManager(req);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertEquals(rsp.getUsers().size(), 1);
        assertEquals(rsp.getUsers().get(0).getUser().getId(), 1);
    }

    @Test
    public void testMGetPharmacyManager1() throws Exception {
        MGetPharmacyManagerRequest req = new MGetPharmacyManagerRequest();

        MGetPharmacyManagerResponse rsp = userService.mGetPharmacyManager(req);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
    }

    @Test
    public void testPharmacyManagerLogin() throws Exception {
        GetUserByUsernameAndPasswordRequest req = new GetUserByUsernameAndPasswordRequest();
        req.setUsername("asd");
        req.setPassword("1");
        req.setRole(RoleEnum.Pharmacy_Manager);

        GetUserByUsernameAndPasswordResponse rsp = userService.getUserByUsernameAndPassword(req);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertNotNull(rsp.getUser());

        req.setPassword("asdasd");
        rsp = userService.getUserByUsernameAndPassword(req);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertNull(rsp.getUser());
    }

    @Test
    public void testInTollCollectorRequest() throws Exception {
        top.itcat.rpc.service.model.TollCollector tollCollector = new top.itcat.rpc.service.model.TollCollector();
        top.itcat.rpc.service.model.User user = new top.itcat.rpc.service.model.User();

        user.setPassword("1");
        user.setDepartId(1);
        user.setCode("asd");
        user.setRealName("test");
        tollCollector.setUser(user);

        AddOrUpdateTollCollectorRequest req = new AddOrUpdateTollCollectorRequest(Collections.singletonList(tollCollector));
        userService.addOrUpdateTollCollector(req);
    }

    @Test
    public void testMGetTollCollector() throws Exception {
        MGetTollCollectorRequest req = new MGetTollCollectorRequest();

        MGetTollCollectorResponse rsp = userService.mGetTollCollector(req);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
//        assertEquals(rsp.getUsers().size(), 1);
//        assertEquals(rsp.getUsers().get(0).getUser().getId(), 1);
    }

    @Test
    public void testMGetTollCollector1() throws Exception {
        MGetTollCollectorRequest req = new MGetTollCollectorRequest();

        MGetTollCollectorResponse rsp = userService.mGetTollCollector(req);
        System.out.println(rsp);
    }

    @Test
    public void testTollCollectorLogin() throws Exception {
        GetUserByUsernameAndPasswordRequest req = new GetUserByUsernameAndPasswordRequest();
        req.setUsername("asd");
        req.setPassword("1");
        req.setRole(RoleEnum.Toll_Collector);

        GetUserByUsernameAndPasswordResponse rsp = userService.getUserByUsernameAndPassword(req);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertNotNull(rsp.getUser());

        req.setPassword("asdasd");
        rsp = userService.getUserByUsernameAndPassword(req);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertNull(rsp.getUser());
    }

    @Test
    public void test() throws Exception {
        EditPwdRequest r = new EditPwdRequest();
        r.setOldPwd("1");
        r.setId(1);
        r.setCode("OD000004");
        r.setPwd("OD000004");
        userService.editPwd(r);
    }
}
