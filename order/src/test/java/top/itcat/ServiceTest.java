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
import top.itcat.rpc.service.model.CatalogEnum;
import top.itcat.rpc.service.model.ChargeItemStatusEnum;
import top.itcat.rpc.service.order.*;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class ServiceTest {
    @Autowired
    private ServiceServer serviceServer;
    @Autowired
    private RedisTemplate redisTemplate;

    @Before
    public void before() {
        RedisCache.setRedisTemplate(redisTemplate);
    }

    @Test
    public void clearCache() {
        new RedisCache("top.itcat.mapper.ScheduleRuleMapper").clear();
    }

    @Test
    public void testGetIndividualAmount() throws Exception {
        GetIndividualAmountRequest request = new GetIndividualAmountRequest();
//        request.setChargeSubjectIds(Collections.singletonList(1L));
        request.setDepartId(2);
        request.setStartTime(System.currentTimeMillis() - 60 * 1000 * 60 * 365);
        request.setEndTime(System.currentTimeMillis());
//        request.setDoctorId(1L);

        System.out.println(serviceServer.getIndividualAmount(request));
    }

    @Test
    public void testGetChargeItem() throws TException {
        GetChargeItemRequest rpcReq = new GetChargeItemRequest();
        rpcReq.setIds(Collections.singletonList(3L));
        GetChargeItemResponse rsp = serviceServer.getChargeItem(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
    }

    @Test
    public void testInChargeItemRequest() throws Exception {
        top.itcat.rpc.service.model.ChargeItem chargeItem = new top.itcat.rpc.service.model.ChargeItem();

        chargeItem.setName("测试");
        chargeItem.setBillingCategoryId(1);
        chargeItem.setAmount(1);
        chargeItem.setActuallyPaid(100);
        chargeItem.setDepartmentId(1);
        chargeItem.setMeasureWord("测试");
        chargeItem.setMedicalRecordNo(1);
        chargeItem.setOperatorId(1);
        chargeItem.setSpecification("测试");
        chargeItem.setUnitPrice(100);
        chargeItem.setPayable(50);
        chargeItem.setStatus(ChargeItemStatusEnum.Unpaid);
        chargeItem.setOperationTime(System.currentTimeMillis());
        chargeItem.setDailyKnot(false);
        chargeItem.setChargeSubjectId(1);
        chargeItem.setProjectId(1);

        AddOrUpdateChargeItemRequest req = new AddOrUpdateChargeItemRequest();
        req.setBeanList(Collections.singletonList(chargeItem));
        System.out.println(serviceServer.addOrUpdateChargeItem(req));
    }

    @Test
    public void testGetChargeSubject() throws TException {
        GetChargeSubjectRequest rpcReq = new GetChargeSubjectRequest();
//        rpcReq.setIds(Collections.singletonList(1L));
        GetChargeSubjectResponse rsp = serviceServer.getChargeSubject(rpcReq);
        System.out.println(rsp);
//        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
//        assertEquals(rsp.getTotal(), 21);
//        assertEquals(rsp.getBeanList().get(0).getId(), 1);
    }

    @Test
    public void testInChargeSubjectRequest() throws Exception {
        top.itcat.rpc.service.model.ChargeSubject chargeSubject = new top.itcat.rpc.service.model.ChargeSubject();

        chargeSubject.setCode("CS");
        chargeSubject.setName("测试");
        chargeSubject.setCatalog(CatalogEnum.Medical);

        AddOrUpdateChargeSubjectRequest req = new AddOrUpdateChargeSubjectRequest();
        req.setBeanList(Collections.singletonList(chargeSubject));
        serviceServer.addOrUpdateChargeSubject(req);
    }

//    @Test
//    public void testSettleCharge() throws Exception {
//
//    }

//    @Test
//    public void testCancelCharge() throws Exception {
//
//    }

//    @Test
//    public void testPayCharge() throws Exception {
//
//    }

    @Test
    public void testGetDayKnot() throws TException {
        GetDayKnotRequest rpcReq = new GetDayKnotRequest();
//        rpcReq.setIds(Collections.singletonList(1L));
        GetDayKnotResponse rsp = serviceServer.getDayKnot(rpcReq);
        System.err.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
    }

//    @Test
//    public void testInDayKnotRequest() throws Exception {
//        top.itcat.rpc.service.model.DayKnot dayKnot = new top.itcat.rpc.service.model.DayKnot();
//
//        dayKnot.setChargeAmount(1);
//        dayKnot.setEndTime(201905301246L);
//        dayKnot.setCheckThrough(1);
//        dayKnot.setOperatorId(1);
//        dayKnot.setStartTime(201905301245L);
//
//        AddOrUpdateDayKnotRequest req = new AddOrUpdateDayKnotRequest(Collections.singletonList(dayKnot));
//        serviceServer.addOrUpdateDayKnot(req);
//    }

    @Test
    public void testGetDayKnotItem() throws TException {
        GetDayKnotItemRequest rpcReq = new GetDayKnotItemRequest();
//        rpcReq.setIds(Collections.singletonList(1L));
        GetDayKnotItemResponse rsp = serviceServer.getDayKnotItem(rpcReq);
        System.err.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
    }

    @Test
    public void testPayCharge() throws TException {
        PayChargeRequest rpcReq = new PayChargeRequest();
        rpcReq.setOperatorId(1);
        rpcReq.setMedicalRecordNo(1);
//        rpcReq.setIds(Collections.singletonList(1L));
        PayChargeResponse rsp = serviceServer.payCharge(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
    }

    @Test
    public void testSettleCharge() throws TException {
        SettleChargeRequest rpcReq = new SettleChargeRequest();
        rpcReq.setEndTime(System.currentTimeMillis());
        rpcReq.setOperatorId(1);
//        rpcReq.setIds(Collections.singletonList(1L));
        SettleChargeResponse rsp = serviceServer.settleCharge(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
    }

    @Test
    public void testCancelCharge() throws TException {
        CancelChargeRequest rpcReq = new CancelChargeRequest();
        rpcReq.setChargeItemIds(Collections.singletonList(10L));
        rpcReq.setOperatorId(11);
//        rpcReq.setIds(Collections.singletonList(1L));
        CancelChargeResponse rsp = serviceServer.cancelCharge(rpcReq);
        System.out.println(rsp);
        assertEquals(rsp.getBaseResp().getStatusCode(), 0);
    }
}
