package top.itcat.mq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import top.itcat.mq.bean.ChargeItem;

@Service
public class MQProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(String topic, String msg) {
        kafkaTemplate.send(topic, msg);
    }
}
