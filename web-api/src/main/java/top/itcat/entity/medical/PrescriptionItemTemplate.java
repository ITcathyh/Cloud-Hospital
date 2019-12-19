package top.itcat.entity.medical;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class PrescriptionItemTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * item id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 申组套id
     */
    @JSONField(name = "group_id")
    private Long groupId;

    /**
     * 药品id
     */
    @JSONField(name = "medical_id")
    private Long medicalId;

    /**
     * 备注
     */
    @JSONField(name = "note")
    private String note;

    private Medicine medicine;

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonSetter("group_id")
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @JsonSetter("medical_id")
    public void setMedicalId(Long medicalId) {
        this.medicalId = medicalId;
    }

    @JsonSetter("note")
    public void setNote(String note) {
        this.note = note;
    }

    public static PrescriptionItemTemplate convert(top.itcat.rpc.service.model.PrescriptionItemTemplate rpcbean) {
        PrescriptionItemTemplate bean = new PrescriptionItemTemplate();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetGroupId()) {
            bean.setGroupId(rpcbean.getGroupId());
        }
        if (rpcbean.isSetMedicalId()) {
            bean.setMedicalId(rpcbean.getMedicalId());
        }
        if (rpcbean.isSetNote()) {
            bean.setNote(rpcbean.getNote());
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.PrescriptionItemTemplate convertRPCBean(PrescriptionItemTemplate bean) {
        top.itcat.rpc.service.model.PrescriptionItemTemplate rpcbean = new top.itcat.rpc.service.model.PrescriptionItemTemplate();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getGroupId() != null) {
            rpcbean.setGroupId(bean.getGroupId());
        }
        if (bean.getMedicalId() != null) {
            rpcbean.setMedicalId(bean.getMedicalId());
        }
        rpcbean.setNote(bean.getNote());
        return rpcbean;
    }

}
