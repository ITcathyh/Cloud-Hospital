package top.itcat.bean.charge;

import lombok.Data;

@Data
public class GetChargeReq {
    private Long medicalRecordNo;
    private Integer offset;
    private Integer limit;
}
