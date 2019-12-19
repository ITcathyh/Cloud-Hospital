package top.itcat.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
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
@TableName("patient")
public class Patient extends Model<Patient> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 患者姓名
     */
    @TableField("patient_name")
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
    @TableField("identity_card_no")
    private String identityCardNo;
    /**
     * 患者家庭住址
     */
    @TableField("family_address")
    private String familyAddress;
    /**
     * 患者联系方式
     */
    private String phone;
    /**
     * 患者有效性
     */
    private Integer valid;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

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
