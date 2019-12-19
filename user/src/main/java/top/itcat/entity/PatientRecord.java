package top.itcat.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import top.itcat.entity.BaseUser;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 患者病历号
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("patient_record")
public class PatientRecord extends Model<PatientRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 病历号
     */
    @TableId("medical_record_no")
    private Long medicalRecordNo;
    /**
     * 患者姓名
     */
    @TableField("identity_card_no")
    private String identityCardNo;
    /**
     * 患者性别
     */
    @TableField("patient_id")
    private Long patientId;
    /**
     * 患者有效性
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.medicalRecordNo;
    }

}
