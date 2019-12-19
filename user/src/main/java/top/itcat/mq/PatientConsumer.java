package top.itcat.mq;

import org.springframework.stereotype.Service;

@Service
public class PatientConsumer {
//    @Autowired
//    private PatientService diagnosticRepository;
//
//    @KafkaListener(topics = {"add_up_diagnostic"})
//    public void addChargeItem(String msg, Acknowledgment ack) {
//        List<Diagnostic> list;
//
//        try {
//            list = JSONArray.parseArray(msg, Diagnostic.class);
//            list.forEach(i -> i.setValid(null));
//        } catch (Exception e) {
//            log.error("add_up_diagnostic wrong msg:{}", msg);
//            ack.acknowledge();
//            return;
//        }
//
//        try {
//            diagnosticRepository.save(list);
//        } catch (Exception e) {
//            log.error("diagnosticRepository save err:", e);
//            return;
//        }
//
//        ack.acknowledge();
//    }
}
