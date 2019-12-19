package top.itcat.entity.medical;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.PrescriptionItemStatusEnum;
import top.itcat.rpc.service.model.PrescriptionType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class PrescriptionHerb implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 中草药处方id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 中草药处方病历号
     */
    @JSONField(name = "medical_record_no")
    private Long medicalRecordNo;

    /**
     * 申请医生id
     */
    @JSONField(name = "doctor_id")
    private Long doctorId;

    /**
     * 处方类型 0.普诊，1.专家诊断
     */
    @JSONField(name = "type")
    private Integer type;

    /**
     * 中草药处方付数
     */
    @JSONField(name = "number")
    private Integer number;

    /**
     * 中草药处方频次
     */
    @JSONField(name = "frequency")
    private Integer frequency;

    /**
     * 中草药处方用法0煎煮1外敷
     */
    @JSONField(name = "usage")
    private Integer usage;

    /**
     * 中草药处方治法
     */
    @JSONField(name = "treatment")
    private String treatment;

    /**
     * 中草药处方治法详细
     */
    @JSONField(name = "treatment_detail")
    private String treatmentDetail;

    /**
     * 中草药处方嘱托
     */
    @JSONField(name = "advice")
    private String advice;

    /**
     * 中草药处方金额
     */
    @JSONField(name = "price")
    private Double price;

    /**
     * 中草药处方状态
     */
    @JSONField(name = "status")
    private Integer status;
    private List<PrescriptionHerbItem> items;

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonSetter("medical_record_no")
    public void setMedicalRecordNo(Long medicalRecordNo) {
        this.medicalRecordNo = medicalRecordNo;
    }

    @JsonSetter("doctor_id")
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @JsonSetter("type")
    public void setType(Integer type) {
        this.type = type;
    }

    @JsonSetter("number")
    public void setNumber(Integer number) {
        this.number = number;
    }

    @JsonSetter("frequency")
    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @JsonSetter("usage")
    public void setUsage(Integer usage) {
        this.usage = usage;
    }

    @JsonSetter("treatment")
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    @JsonSetter("treatment_detail")
    public void setTreatmentDetail(String treatmentDetail) {
        this.treatmentDetail = treatmentDetail;
    }

    @JsonSetter("advice")
    public void setAdvice(String advice) {
        this.advice = advice;
    }

    @JsonSetter("price")
    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonSetter("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    public static PrescriptionHerb convert(top.itcat.rpc.service.model.PrescriptionHerb rpcbean) {
        PrescriptionHerb bean = new PrescriptionHerb();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetMedicalRecordNo()) {
            bean.setMedicalRecordNo(rpcbean.getMedicalRecordNo());
        }
        if (rpcbean.isSetDoctorId()) {
            bean.setDoctorId(rpcbean.getDoctorId());
        }
        if (rpcbean.isSetType()) {
            bean.setType(rpcbean.getType().getValue());
        }
        if (rpcbean.isSetNumber()) {
            bean.setNumber(rpcbean.getNumber());
        }
        if (rpcbean.isSetFrequency()) {
            bean.setFrequency(rpcbean.getFrequency());
        }
        if (rpcbean.isSetUsage()) {
            bean.setUsage(rpcbean.getUsage());
        }
        if (rpcbean.isSetTreatment()) {
            bean.setTreatment(rpcbean.getTreatment());
        }
        if (rpcbean.isSetTreatmentDetail()) {
            bean.setTreatmentDetail(rpcbean.getTreatmentDetail());
        }
        if (rpcbean.isSetAdvice()) {
            bean.setAdvice(rpcbean.getAdvice());
        }
        if (rpcbean.isSetPrice()) {
            bean.setPrice(rpcbean.getPrice());
        }
        if (rpcbean.isSetStatus()) {
            bean.setStatus(rpcbean.getStatus().getValue());
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.PrescriptionHerb convertRPCBean(PrescriptionHerb bean) {
        top.itcat.rpc.service.model.PrescriptionHerb rpcbean = new top.itcat.rpc.service.model.PrescriptionHerb();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getMedicalRecordNo() != null) {
            rpcbean.setMedicalRecordNo(bean.getMedicalRecordNo());
        }
        if (bean.getDoctorId() != null) {
            rpcbean.setDoctorId(bean.getDoctorId());
        }
        if (bean.getType() != null) {
            rpcbean.setType(PrescriptionType.findByValue(bean.getType()));
        }
        if (bean.getNumber() != null) {
            rpcbean.setNumber(bean.getNumber());
        }
        if (bean.getFrequency() != null) {
            rpcbean.setFrequency(bean.getFrequency());
        }
        if (bean.getUsage() != null) {
            rpcbean.setUsage(bean.getUsage());
        }
        rpcbean.setTreatment(bean.getTreatment());
        rpcbean.setTreatmentDetail(bean.getTreatmentDetail());
        rpcbean.setAdvice(bean.getAdvice());
        if (bean.getPrice() != null) {
            rpcbean.setPrice(bean.getPrice());
        }
        if (bean.getStatus() != null) {
            rpcbean.setStatus(PrescriptionItemStatusEnum.findByValue(bean.getStatus()));
        }
        if (bean.getItems() != null) {
            rpcbean.setItems(bean.getItems()
                    .parallelStream()
                    .map(PrescriptionHerbItem::convertRPCBean)
                    .collect(Collectors.toList()));
        }
        return rpcbean;
    }

}
