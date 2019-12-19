package top.itcat.mq;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import top.itcat.entity.apply.Apply;
import top.itcat.rpc.service.ServiceServer;
import top.itcat.rpc.service.order.AddOrUpdateChargeItemRequest;
import top.itcat.rpc.service.order.AddOrUpdateChargeItemResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DiagnoseConsumer {
    @Autowired
    private ServiceServer serviceServer;
    // todo 增加诊断增加es记录

//    @KafkaListener(topics = {"add_apply"})
//    public void addChargeItem(String msg, Acknowledgment ack) {
//        List<Apply> chargeItems;
//
//        try {
//            chargeItems = JSONArray.parseArray(msg, ChargeItem.class);
//        } catch (Exception e) {
//            log.error("add_up_charge wrong msg:{}", msg);
//            ack.acknowledge();
//            return;
//        }
//
//        List<top.itcat.rpc.service.model.ChargeItem> rpcList = chargeItems.parallelStream()
//                .map(ChargeItem::convertRPCBean)
//                .collect(Collectors.toList());
//        AddOrUpdateChargeItemRequest rpcReq = new AddOrUpdateChargeItemRequest();
//        rpcReq.setBeanList(rpcList);
//        AddOrUpdateChargeItemResponse rsp = null;
//
//        try {
//            rsp = serviceServer.addOrUpdateChargeItem(rpcReq);
//        } catch (TException e) {
//            log.error("addOrUpdateChargeItem err:", e);
//        }
//
//        if (rsp != null) {
//            ack.acknowledge();
//        }
//    }
}
