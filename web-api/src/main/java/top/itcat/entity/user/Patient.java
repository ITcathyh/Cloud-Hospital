package top.itcat.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
//import top.itcat.rpc.service.model.User;
//
//import java.io.Serializable;

/**
 * <p>
 * 患者
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Data
public class Patient /*extends BaseUser<Patient>*/ {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 患者姓名
     */
    @JSONField(name = "name")
    private String patientName;
    /**
     * 患者性别
     */
    private Integer gender;
    /**
     * 患者年龄
     */
    private Integer age;
    /**
     * 患者出生日期
     */
    private Integer birthday;
    /**
     * 患者身份证号
     */
    @JSONField(name = "id_num")
    private String identityCardNo;
    /**
     * 患者家庭住址
     */
    @JSONField(name = "address")
    private String familyAddress;
    /**
     * 患者联系方式
     */
    private String phone;
    /**
     * 患者有效性
     */
    private Integer valid;

//    @Override
//    protected Serializable pkVal() {
//        return this.id;
//    }

    public static Patient convertPatient(top.itcat.rpc.service.model.Patient rpcUser) {
        Patient patient = new Patient();

        if (rpcUser.isSetUserInfo()) {
            patient.setAge(rpcUser.userInfo.age);
            patient.setBirthday(rpcUser.userInfo.birth);
            patient.setFamilyAddress(rpcUser.userInfo.address);
            patient.setGender(rpcUser.userInfo.gender.getValue());
            patient.setIdentityCardNo(rpcUser.userInfo.idNum);
            patient.setPhone(rpcUser.userInfo.phone);
            patient.setPatientName(rpcUser.userInfo.name);
        }

        return patient;
    }
}
