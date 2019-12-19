package top.itcat.bean.registration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.itcat.bean.CommonSearchReq;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetRegistrationReq extends CommonSearchReq {
    private Long medicalRecordNo;
    private String identityCardNo;
    private Long departId;
    private Long doctorId;
    private Integer type;
    private Integer status;
}
