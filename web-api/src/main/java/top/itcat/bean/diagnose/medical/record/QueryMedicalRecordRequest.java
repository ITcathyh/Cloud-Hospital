package top.itcat.bean.diagnose.medical.record;

import lombok.Data;
import top.itcat.bean.CommonSearchReq;

@Data
public class QueryMedicalRecordRequest extends CommonSearchReq {
    private Long medicalRecordNo;
    private Long doctorId;
    private String idNum;
}
