package top.itcat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.itcat.entity.ChargeItem;
import top.itcat.rpc.service.model.ChargeItemStatusEnum;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class LocalTest {
    @Autowired
    KafkaTemplate kafkaTemplate;

    @Test
    public void test() throws Exception{
        ChargeItem chargeItem = new ChargeItem();
        chargeItem.setName("测试");
        chargeItem.setBillingCategoryId(1L);
        chargeItem.setAmount(1);
        chargeItem.setActuallyPaid(100D);
        chargeItem.setDepartmentId(1L);
        chargeItem.setMeasureWord("测试");
        chargeItem.setMedicalRecordNo(1L);
        chargeItem.setOperatorId(1L);
        chargeItem.setSpecification("测试");
        chargeItem.setUnitPrice(100D);
        chargeItem.setPayable(50D);
        chargeItem.setStatus(ChargeItemStatusEnum.Unpaid.getValue());
        chargeItem.setOperationTime(System.currentTimeMillis());
        chargeItem.setDailyKnot(0);
        chargeItem.setChargeSubjectId(1L);
        chargeItem.setProjectId(1L);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(chargeItem);

        kafkaTemplate.send("add_up_charge", jsonArray.toJSONString());
        Thread.sleep(4000);
    }
}
