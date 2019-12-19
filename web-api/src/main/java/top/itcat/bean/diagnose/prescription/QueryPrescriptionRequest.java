package top.itcat.bean.diagnose.prescription;

import lombok.Getter;
import lombok.Setter;
import top.itcat.bean.CommonSearchReq;

@Setter
@Getter
public class QueryPrescriptionRequest extends CommonSearchReq {
    private Long medicalRecordNo;
    private Long doctorId;
}
