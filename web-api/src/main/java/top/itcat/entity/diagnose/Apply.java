package top.itcat.entity.diagnose;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.ApplyCategory;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class Apply implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 申请id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 病历号
     */
    @JSONField(name = "medical_record_no")
    private Long medicalRecordNo;

    /**
     * 申请时间
     */
    @JSONField(name = "time")
    private Long time;

    /**
     * 申请分类0 检查 1检验 2处置
     */
    @JSONField(name = "category")
    private Integer category;

    /**
     * 申请医生id
     */
    @JSONField(name = "doctor_id")
    private Long doctorId;

    @JSONField(name = "items")
    private List<ApplyItem> items;

    @JSONField(name = "valid")
    private Integer valid;

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonSetter("medical_record_no")
    public void setMedicalRecordNo(Long medicalRecordNo) {
        this.medicalRecordNo = medicalRecordNo;
    }

    @JsonSetter("time")
    public void setTime(Long time) {
        this.time = time;
    }

    @JsonSetter("category")
    public void setCategory(Integer category) {
        this.category = category;
    }

    @JsonSetter("doctor_id")
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @JsonSetter("valid")
    private void setValid(Integer valid) {
        this.valid = valid;
    }

    public static Apply convert(top.itcat.rpc.service.model.Apply rpcbean) {
        Apply bean = new Apply();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetMedicalRecordNo()) {
            bean.setMedicalRecordNo(rpcbean.getMedicalRecordNo());
        }
        if (rpcbean.isSetTime()) {
            bean.setTime(rpcbean.getTime());
        }
        if (rpcbean.isSetCategory()) {
            bean.setCategory(rpcbean.getCategory().getValue());
        }
        if (rpcbean.isSetDoctorId()) {
            bean.setDoctorId(rpcbean.getDoctorId());
        }
        if (rpcbean.isSetItems()) {
            bean.setItems(rpcbean.getItems().parallelStream()
                    .map(ApplyItem::convert)
                    .collect(Collectors.toList()));
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.Apply convertRPCBean(Apply bean) {
        top.itcat.rpc.service.model.Apply rpcbean = new top.itcat.rpc.service.model.Apply();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getMedicalRecordNo() != null) {
            rpcbean.setMedicalRecordNo(bean.getMedicalRecordNo());
        }
        if (bean.getTime() != null) {
            rpcbean.setTime(bean.getTime());
        }
        if (bean.getCategory() != null) {
            rpcbean.setCategory(ApplyCategory.findByValue(bean.getCategory()));
        }
        if (bean.getDoctorId() != null) {
            rpcbean.setDoctorId(bean.getDoctorId());
        }
        rpcbean.setItems(bean.getItems().
                parallelStream().map(ApplyItem::convertRPCBean).
                collect(Collectors.toList()));
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }
        return rpcbean;
    }

}
