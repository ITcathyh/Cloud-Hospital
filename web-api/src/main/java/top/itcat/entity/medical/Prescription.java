package top.itcat.entity.medical;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.PrescriptionCategoty;
import top.itcat.rpc.service.model.PrescriptionType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Accessors(chain = true)
public class Prescription implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 处方id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 病历号
     */
    @JSONField(name = "medical_record_no")
    private Long medicalRecordNo;

    /**
     * 处方类型 0.普诊，1.专家诊断
     */
    @JSONField(name = "type")
    private Integer type;

    /**
     * 处方类别 0中成药、1.西药
     */
    @JSONField(name = "categoty")
    private Integer categoty;

    /**
     * 处方金额
     */
    @JSONField(name = "price")
    private Double price;

    /**
     * 处方备注
     */
    @JSONField(name = "remark")
    private String remark;

    /**
     * 申请医生id
     */
    @JSONField(name = "doctor_id")
    private Long doctorId;
    @JSONField(name = "items")
    private List<PrescriptionItem> items;

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonSetter("medical_record_no")
    public void setMedicalRecordNo(Long medicalRecordNo) {
        this.medicalRecordNo = medicalRecordNo;
    }

    @JsonSetter("type")
    public void setType(Integer type) {
        this.type = type;
    }

    @JsonSetter("categoty")
    public void setCategoty(Integer categoty) {
        this.categoty = categoty;
    }

    @JsonSetter("price")
    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonSetter("remark")
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonSetter("doctor_id")
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public static Prescription convert(top.itcat.rpc.service.model.Prescription rpcbean) {
        Prescription bean = new Prescription();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetMedicalRecordNo()) {
            bean.setMedicalRecordNo(rpcbean.getMedicalRecordNo());
        }
        if (rpcbean.isSetType()) {
            bean.setType(rpcbean.getType().getValue());
        }
        if (rpcbean.isSetCategoty()) {
            bean.setCategoty(rpcbean.getCategoty().getValue());
        }
        if (rpcbean.isSetPrice()) {
            bean.setPrice(rpcbean.getPrice());
        }
        if (rpcbean.isSetRemark()) {
            bean.setRemark(rpcbean.getRemark());
        }
        if (rpcbean.isSetDoctorId()) {
            bean.setDoctorId(rpcbean.getDoctorId());
        }
        if (rpcbean.isSetItems()) {
            bean.setItems(rpcbean.getItems().parallelStream()
                    .map(PrescriptionItem::convert)
                    .collect(Collectors.toList()));
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.Prescription convertRPCBean(Prescription bean) {
        top.itcat.rpc.service.model.Prescription rpcbean = new top.itcat.rpc.service.model.Prescription();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getMedicalRecordNo() != null) {
            rpcbean.setMedicalRecordNo(bean.getMedicalRecordNo());
        }
        if (bean.getType() != null) {
            rpcbean.setType(PrescriptionType.findByValue(bean.getType()));
        }
        if (bean.getCategoty() != null) {
            rpcbean.setCategoty(PrescriptionCategoty.findByValue(bean.getCategoty()));
        }
        if (bean.getPrice() != null) {
            rpcbean.setPrice(bean.getPrice());
        }
        rpcbean.setRemark(bean.getRemark());
        if (bean.getDoctorId() != null) {
            rpcbean.setDoctorId(bean.getDoctorId());
        }
        rpcbean.setItems(bean.getItems().
                parallelStream().map(PrescriptionItem::convertRPCBean).
                collect(Collectors.toList()));
        return rpcbean;
    }

}
