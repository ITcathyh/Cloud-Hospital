package top.itcat.bean.charge.statistics;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import top.itcat.entity.charge.ChargeSubject;

@Data
public class StatisticsDetail {
    @JSONField(name = "charge_subject")
    private ChargeSubject chargeSubject;
    private double amount;
}
