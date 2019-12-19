package top.itcat.bean.diagnose.apply.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.itcat.bean.CommonSearchReq;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetApplyItemReq extends CommonSearchReq {
    private Long medicalRecordNo;
    private Long time;
//    private String idNum;
}
