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
import top.itcat.rpc.service.hospital.*;
import top.itcat.rpc.service.model.DepartClassificationEnum;

import java.util.Arrays;
import java.util.Collections;

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


    @Test
    public void testGetDoctorStatisticalData() throws TException {
        GetDoctorStatisticalDataRequest rpcReq = new GetDoctorStatisticalDataRequest();
        rpcReq.setStartTime(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 15);
        rpcReq.setEndTime(System.currentTimeMillis());
//        rpcReq.setDoctorIds(Collections.singletonList(15L));
        GetDoctorStatisticalDataResponse rsp = serviceServer.getDoctorStatistical(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
    }

    @Test
    public void testGetDepartStatisticalData() throws TException {
        GetDepartStatisticalDataRequest rpcReq = new GetDepartStatisticalDataRequest();
        rpcReq.setStartTime(System.currentTimeMillis() - 60 * 1000 * 60 * 365);
        rpcReq.setEndTime(System.currentTimeMillis());
//        rpcReq.setDepartIds(Collections.singletonList(3L));
        GetDepartStatisticalDataResponse rsp = serviceServer.getDepartStatistical(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
    }

    @Test
    public void testGetDepart() throws TException {
        GetDepartmentRequest rpcReq = new GetDepartmentRequest();
//        rpcReq.setIds(Collections.singletonList(1L));
        GetDepartmentResponse rsp = serviceServer.getDepartment(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
    }

    @Test
    public void testInDepartRequest() throws Exception {
        top.itcat.rpc.service.model.Department department = new top.itcat.rpc.service.model.Department();

        department.setCategory("test");
        department.setClassification(DepartClassificationEnum.Medical);
        department.setCode("TEST");
        department.setName("测试");


        AddOrUpdateDepartmentRequest req = new AddOrUpdateDepartmentRequest(Collections.singletonList(department));
        serviceServer.addOrUpdateDepartment(req);
    }

    @Test
    public void testGetBillingCategory() throws TException {
        GetBillingCategoryRequest rpcReq = new GetBillingCategoryRequest();
//        rpcReq.setIds(Collections.singletonList(1L));
        GetBillingCategoryResponse rsp = serviceServer.getBillingCategory(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertEquals(rsp.getTotal(), 3);
        assertEquals(rsp.getBeanList().get(0).getId(), 1);
    }

    @Test
    public void testInBillingCategoryRequest() throws Exception {
        top.itcat.rpc.service.model.BillingCategory billingCategory = new top.itcat.rpc.service.model.BillingCategory();

        billingCategory.setDiscount(0.75);
        billingCategory.setName("测试");

        AddOrUpdateBillingCategoryRequest req = new AddOrUpdateBillingCategoryRequest(Collections.singletonList(billingCategory));
        serviceServer.addOrUpdateBillingCategory(req);
    }

    @Test
    public void testGetNonmedicalCharge() throws TException {
        GetNonmedicalChargeRequest rpcReq = new GetNonmedicalChargeRequest();
//        rpcReq.setIds(Collections.singletonList(1L));

        GetNonmedicalChargeResponse rsp = serviceServer.getNonmedicalCharge(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertEquals(rsp.getBeanListSize(), 52);
        assertEquals(rsp.getBeanList().get(0).getId(), 1);
    }

    @Test
    public void testInNonmedicalChargeRequest() throws Exception {
        top.itcat.rpc.service.model.NonmedicalCharge nonmedicalCharge = new top.itcat.rpc.service.model.NonmedicalCharge();

        nonmedicalCharge.setDescription("test");
        nonmedicalCharge.setPrice(10);
//        nonmedicalCharge.setPhonetic("CS");
        nonmedicalCharge.setFormat("测试");
//        nonmedicalCharge.setCategory(1);
        nonmedicalCharge.setChargeSubjectId(1);
        nonmedicalCharge.setDepartmentId(1);
        nonmedicalCharge.setCode("999999999");
        nonmedicalCharge.setName("测试");

        AddOrUpdateNonmedicalChargeRequest req = new AddOrUpdateNonmedicalChargeRequest(Collections.singletonList(nonmedicalCharge));
        serviceServer.addOrUpdateNonmedicalCharge(req);
    }

    @Test
    public void testGetRegistrationLevel() throws TException {
        GetRegisterationLevelRequest rpcReq = new GetRegisterationLevelRequest();
//        rpcReq.setIds(Collections.singletonList(1L));

        GetRegisterationLevelResponse rsp = serviceServer.getRegisterationLevel(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
        assertEquals(rsp.getTotal(), 5);
        assertEquals(rsp.getBeanList().get(0).getId(), 1);
    }

    @Test
    public void testInRegistrationLevelRequest() throws Exception {
        top.itcat.rpc.service.model.RegisterationLevel registerationLevel = new top.itcat.rpc.service.model.RegisterationLevel();

        registerationLevel.setIsDefault(true);
        registerationLevel.setPrice(10);
        registerationLevel.setDisplaySeqNum(1);
        registerationLevel.setCode("TEST");
        registerationLevel.setName("测试");

        AddOrUpdateRegisterationLevelRequest req = new AddOrUpdateRegisterationLevelRequest(Collections.singletonList(registerationLevel));
        serviceServer.addOrUpdateRegisterationLevel(req);
    }
}
