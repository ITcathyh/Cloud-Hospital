package top.itcat.bean.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.itcat.bean.CommonSearchReq;

@EqualsAndHashCode(callSuper = true)
@Data
public class DoctorSearchReq extends CommonSearchReq {
    private Long departmentId;
    private Integer title;
    private Boolean inSchedule;
}
