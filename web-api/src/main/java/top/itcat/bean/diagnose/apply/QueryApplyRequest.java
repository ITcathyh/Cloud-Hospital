package top.itcat.bean.diagnose.apply;

import lombok.Data;
import top.itcat.bean.CommonSearchReq;

@Data
public class QueryApplyRequest extends CommonSearchReq {
    private Long medicalRecordNo;
    private Integer category;
    private Long doctorId;
    private Boolean needItemDetail;
}
