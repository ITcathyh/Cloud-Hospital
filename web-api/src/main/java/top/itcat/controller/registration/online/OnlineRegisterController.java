//package top.itcat.controller.registration.online;
//
//import com.googlecode.protobuf.format.JsonFormat;
//import io.jsonwebtoken.Claims;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.jms.core.JmsMessagingTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import redis.clients.jedis.Jedis;
//import top.itcat.aop.auth.annotation.RoleCheck;
//import top.itcat.aop.limiter.annotation.RateLimit;
//import top.itcat.cache.BillingCategoryManage.DefaultCacheManager;
//import top.itcat.mq.SendMsgUtil;
//import top.itcat.mq.model.AppointmentMsg;
//import top.itcat.pb_gen.api.registration.RegistrationController;
//import top.itcat.rpc.client.OrderServiceClient;
//import top.itcat.util.GetBaseResponUtil;
//import top.itcat.util.IDGeneratorUtil;
//import top.itcat.util.RedisUtil;
//
//import javax.annotation.Resource;
//import javax.jms.Destination;
//import javax.servlet.http.HttpServletRequest;
//import java.sql.Timestamp;
//
//@RestController
//@RequestMapping("/register/online")
//@RoleCheck
//@Slf4j
//public class OnlineRegisterController {
//    @Autowired
//    private OrderServiceClient orderServiceClient;
//    @Autowired
//    private RedisTemplate redisTemplate;
//    @Autowired
//    private DefaultCacheManager cache;
//    @Resource
//    private JmsMessagingTemplate jmsMessagingTemplate;
//    @Resource(name = "appointmentDestination")
//    private Destination destination;
//    private static final String SUBMIT_APPOINTMENT_KEY = "schedule_plan_remain:%d";
//    private static final String DECR_LUA = " local val1 = redis.call ('get', KEYS [1]) \n "
//            + " if val1 and val1 ~= '0' then \n "
//            + " return redis.call ('decr', KEYS[1]) \n "
//            + " end \n "
//            + " return -1 \n ";
//    private final static String USER_CAN_BUY_KEY = "schedule_plan_patient:%d:%d";
//
//    /**
//     * 是否能够点击预约按钮
//     * 包含时间检查、名额检查
//     */
//    @GetMapping("/canbuy")
//    public String canSubmit() {
//        return GetBaseResponUtil.getSuccessRspStr();
//    }
//
//    /**
//     * 提交预约请求
//     * 确认是否可以进入支付页
//     * <p>
//     * 返回成功后前端仍显示排队中
//     * 但接口变为轮询是否可支付
//     */
//    @PostMapping("/submit")
//    @RequestMapping(value = "/")
//    @RateLimit(limitNum = 1000)
////    @HystrixCommand(fallbackMethod = "submitFallBack", commandProperties = {
////            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
////    },
////            threadPoolProperties = {
////                    @HystrixProperty(name = "coreSize", value = "1"),
////                    @HystrixProperty(name = "maximumSize", value = "100"),
////                    @HystrixProperty(name = "maxQueueSize", value = "1000")
////            })
//    public String submit(RegistrationController.SubmitSiteRegistrationRequest.Builder req,
//                         HttpServletRequest request) {
//        if (req.getSchdulePlanId() <= 0) {
//            return GetBaseResponUtil.getBaseRspStr(400, "请求有误");
//        }
//
//        String key = String.format(SUBMIT_APPOINTMENT_KEY, req.getSchdulePlanId());
//        long userId = (long) ((Claims) request.getAttribute("claims")).get("id");
//
//        if (cache.get("over:" + key) != null) {
//            return GetBaseResponUtil.getBaseRspStr(403, "预约名额不足");
//        }
//
//        if (!RedisUtil.setnx(redisTemplate, String.format(USER_CAN_BUY_KEY,
//                req.getSchdulePlanId(), userId), 1, 15 * 60)) {
//            return GetBaseResponUtil.getBaseRspStr(403, "请勿重复预约");
//        }
//
//        int result = preSubmit(key, req.getSchdulePlanId());
//
//        if (result == -1) {
//            return GetBaseResponUtil.getBaseRspStr(403, "预约名额不足");
//        }
//
//        key = null;
//        String medicalRecordNo = IDGeneratorUtil.genOnlyNumID(32,Long.toString(userId));
//        AppointmentMsg appointmentMsg = AppointmentMsg.builder()
//                .appointmentId(req.getSchdulePlanId())
//                .time(new Timestamp(System.currentTimeMillis()))
//                .userId(userId)
//                .orderId(medicalRecordNo)
//                .build();
//
//        if (!SendMsgUtil.sendMessage(jmsMessagingTemplate, destination, appointmentMsg)) {
//            log.error("Send MQ err, msg:{}", appointmentMsg.toString());
//            return GetBaseResponUtil.getBaseRspStr(500, "内部服务错误");
//        }
//
//        RegistrationController.SubmitSiteRegistrationResponse.Builder builder = RegistrationController.SubmitSiteRegistrationResponse.newBuilder();
//        builder.setBase(GetBaseResponUtil.getSuccessRsp())
//                .setMedicalRecordNo(123L)
//                .setRegistrationId(13L);
//        return JsonFormat.printToString(builder.build());
//    }
//
//    @GetMapping("/payable")
//    @RateLimit
//    public String payable() {
//        String orderId = "";
//
//        // todo DB查询消息队列是否处理成功
//
//        return GetBaseResponUtil.getSuccessRspStr();
//    }
//
//    @PostMapping("/pay")
//    public String pay() {
//        String orderId = "";
//
//        return GetBaseResponUtil.getSuccessRspStr();
//    }
//
//    @PostMapping("/cancle")
//    public String cancle() {
//        String orderId = "";
//        return null;
////        return orderServiceClient.cancelRegistration(t) ?
////                GetBaseResponUtil.getSuccessRspStr() : GetBaseResponUtil.getBaseRspStr(500, "内部服务错误");
//    }
//
//
//    @RateLimit(fieldKeys = {"#id"})
//    protected int preSubmit(String appokey, long id) {
//        long remain = (long) ((Jedis) redisTemplate.getConnectionFactory().
//                getConnection().getNativeConnection()).eval(DECR_LUA, 1, appokey);
//
//        if (remain <= 0) {
//            cache.set("over:" + appokey, true, 0, 10);
//
//            if (remain < 0) {
//                return -1;
//            }
//        }
//
//        return 0;
//    }
//}
