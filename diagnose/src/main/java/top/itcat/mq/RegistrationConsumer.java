package top.itcat.mq;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import top.itcat.rpc.service.ServiceServer;
import top.itcat.rpc.service.diagnose.AddOrUpdateRegistrationRequest;
import top.itcat.rpc.service.model.Registration;
import top.itcat.rpc.thrift.ThriftUtil;

@Service
@Slf4j
public class RegistrationConsumer {
    @Autowired
    private ServiceServer serviceServer;

    @KafkaListener(topics = {"add_registration"})
    public void addChargeItem(String msg, Acknowledgment ack) {
        AddOrUpdateRegistrationRequest req = new AddOrUpdateRegistrationRequest();

        try {
            JSONObject jsonObject = JSONObject.parseObject(msg);
            AddOrUpdateRegistrationRequest tmp = JSONObject.parseObject(jsonObject.toJSONString(), AddOrUpdateRegistrationRequest.class);

            Registration registration = new Registration();
            registration.setMedicalRecordNo(tmp.getBean().getMedicalRecordNo());
            registration.setIdentityCardNo(tmp.getBean().getIdentityCardNo());
            registration.setSchedulePlanId(tmp.getBean().getSchedulePlanId());
            registration.setRegistrationTime(tmp.getBean().getRegistrationTime());
            registration.setBillingCategoryId(tmp.getBean().getBillingCategoryId());

            req.setBean(registration);
            req.setNeedBook(tmp.isNeedBook());
            req.setOperatorId(tmp.getOperatorId());

            ThriftUtil.checkResponse(serviceServer.addOrUpdateRegistration(req).getBaseResp().getStatusCode());
        } catch (Exception e) {
            log.error("add_registration wrong msg:{}, err:{}", msg, e);
            return;
        }

        ack.acknowledge();
    }
}
