//package top.itcat.consumer;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.jms.core.JmsMessagingTemplate;
//import org.springframework.stereotype.Service;
//import top.itcat.mq.SendMsgUtil;
//import top.itcat.mq.model.AppointmentMsg;
//import top.itcat.rpc.client.OrderServiceClient;
//import top.itcat.rpc.service.model.order.Order;
//import top.itcat.rpc.service.model.order.OrderStatusEnum;
//
//import javax.annotation.Resource;
//import javax.jms.Destination;
//import javax.jms.ObjectMessage;
//import java.util.concurrent.TimeUnit;
//
//@Service
//@Slf4j
//public class AppointmentConsumer {
//    @Resource
//    private JmsMessagingTemplate jmsMessagingTemplate;
//    @Autowired
//    private RedisTemplate redisTemplate;
//    @Autowired
//    private OrderServiceClient orderServiceClient;
//    @Resource(name = "orderDelayCheckDestination")
//    private Destination orderDelayCheckDestination;
//    private static final int DELAY_TIME = (int) TimeUnit.MINUTES.toMillis(10);
//
//    @JmsListener(destination = "doctor.appointment")
//    public void receiveQueue(ObjectMessage msg) throws Exception {
//        log.debug(Thread.currentThread().getName() + ":Consumer收到的报文为:" + msg.getObject());
//        AppointmentMsg appointmentMsg = (AppointmentMsg) msg.getObject();
//
//        Order order = new Order();
//        order.setId(123);
//        order.setCreateTime(appointmentMsg.getTime());
//        order.setAppoId(appointmentMsg.getAppointmentId());
//        order.setUserId(appointmentMsg.getUserId());
//        order.setStatus(OrderStatusEnum.UnPaid);
//
//        orderServiceClient.submitOrder(order);
//        SendMsgUtil.sendMessageDelay(jmsMessagingTemplate,
//                orderDelayCheckDestination, appointmentMsg, DELAY_TIME);
//        msg.acknowledge();
//    }
//}
