package top.itcat.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 患者微信签名
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("patient_wechat_signature")
public class PatientWechatSignature extends Model<PatientWechatSignature> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 微信签名
     */
    private String signature;
    /**
     * 身份证号
     */
    @TableField("identity_card_no")
    private String identityCardNo;
    /**
     * 有效性
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static PatientWechatSignature convert(top.itcat.rpc.service.model.PatientWechatSignature rpcbean) {
        if (rpcbean == null) {
            return null;
        }
        PatientWechatSignature bean = new PatientWechatSignature();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetSignature()) {
            bean.setSignature(rpcbean.getSignature());
        }
        if (rpcbean.isSetIdentityCardNo()) {
            bean.setIdentityCardNo(rpcbean.getIdentityCardNo());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.PatientWechatSignature convertRPCBean(PatientWechatSignature bean) {
        if (bean == null) {
            return null;
        }
        top.itcat.rpc.service.model.PatientWechatSignature rpcbean = new top.itcat.rpc.service.model.PatientWechatSignature();
        rpcbean.setId(bean.getId());
        rpcbean.setSignature(bean.getSignature());
        rpcbean.setIdentityCardNo(bean.getIdentityCardNo());

        return rpcbean;
    }
}