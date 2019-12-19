package top.itcat.consumer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import top.itcat.entity.ChargeItem;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.order.AddOrUpdateChargeItemRequest;
import top.itcat.rpc.service.order.AddOrUpdateChargeItemResponse;

import java.util.Collections;

@Service
@Slf4j
public class OrderConsumer {
    @Autowired
    private OrderServiceClient orderServiceClient;

//    @Autowired
//    private OrderServiceClient orderServiceClient;
//    private ExecutorService executors = CommonThreadPoolFactory.newThreadPool();
//
//    // 取消超时订单
//    @JmsListener(destination = "order.check.delay")
//    public void receiveQueue(ObjectMessage msg) throws Exception {
//        log.debug(Thread.currentThread().getName() + ":Consumer收到的报文为:" + msg.getObject());
//        AppointmentMsg appointmentMsg = (AppointmentMsg) msg.getObject();
//        QueryOrderRequest req = new QueryOrderRequest();
//        req.setOrderId(appointmentMsg.getOrderId());
//        req.setStatus(OrderStatusEnum.UnPaid);
//        List<top.itcat.rpc.service.model.order.Order> orderList = orderServiceClient.queryOrder(req);
//
//        if (orderList == null || orderList.size() == 0) {
//            return;
//        }
//
//        executors.submit(() -> {
//            orderServiceClient.cancelOrder(appointmentMsg.getOrderId());
//        });
//        msg.acknowledge();
//    }

    @KafkaListener(topics = {"add_order"})
    public void addChargeItem(String msg, Acknowledgment ack) {
        ChargeItem chargeItem;

        try {
            chargeItem = JSONObject.parseObject(msg, ChargeItem.class);
        } catch (Exception e) {
            log.error("add_order wrong msg:{}", msg);
            ack.acknowledge();
            return;
        }

        top.itcat.rpc.service.model.ChargeItem bean = ChargeItem.convertRPCBean(chargeItem);
        AddOrUpdateChargeItemRequest rpcReq = new AddOrUpdateChargeItemRequest();
        rpcReq.setBeanList(Collections.singletonList(bean));
        AddOrUpdateChargeItemResponse rsp = orderServiceClient.addOrUpdateChargeItem(rpcReq);

        if (rsp != null) {
            ack.acknowledge();
        }
    }
}
