package top.itcat.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unchecked")
public class MQProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(String topic, String msg) {
        kafkaTemplate.send(topic, msg);
    }
}
