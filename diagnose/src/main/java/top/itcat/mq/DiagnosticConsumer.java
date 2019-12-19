package top.itcat.mq;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import top.itcat.entity.diagnostic.Diagnostic;
import top.itcat.es.DiagnosticRepository;

import java.util.List;

@Service
@Slf4j
public class DiagnosticConsumer {
    @Autowired
    private DiagnosticRepository diagnosticRepository;

    @KafkaListener(topics = {"add_up_diagnostic"})
    public void addChargeItem(String msg, Acknowledgment ack) {
        List<Diagnostic> list;

        try {
            list = JSONArray.parseArray(msg, Diagnostic.class);
            list.forEach(i -> i.setValid(null));
        } catch (Exception e) {
            log.error("add_up_diagnostic wrong msg:{}", msg);
            ack.acknowledge();
            return;
        }

        try {
            diagnosticRepository.save(list);
        } catch (Exception e) {
            log.error("diagnosticRepository save err:", e);
            return;
        }

        ack.acknowledge();
    }
}
