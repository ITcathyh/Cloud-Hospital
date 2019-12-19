package top.itcat.entity.medical;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.entity.charge.ChargeItem;
import top.itcat.rpc.service.model.MedicineUsageEnum;
import top.itcat.rpc.service.model.PrescriptionItemStatusEnum;

import java.io.Serializable;


@Data
@Accessors(chain = true)
public class PrescriptionItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 处方单项id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 处方id
     */
    @JSONField(name = "prescription_id")
    private Long prescriptionId;

    /**
     * 药品id
     */
    @JSONField(name = "item_id")
    private Long itemId;

    /**
     * 收费项目id
     */
    @JSONField(name = "charge_item_id")
    private Long chargeItemId;

    /**
     * 药品操作员id
     */
    @JSONField(name = "medical_doctor_id")
    private Long medicalDoctorId;

    /**
     * 状态
     */
    @JSONField(name = "status")
    private Integer status;

    /**
     * 用法
     */
    @JSONField(name = "use_method")
    private Integer useMethod;

    /**
     * 频次
     */
    @JSONField(name = "use_frequent")
    private String useFrequent;
    private Medicine medicine;
    @JSONField(name = "charge_item")
    private ChargeItem chargeItem;
    private Integer num;

    @JsonSetter("charge_item")
    public void setChargeItem(ChargeItem chargeItem) {
        this.chargeItem = chargeItem;
    }

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonSetter("prescription_id")
    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    @JsonSetter("item_id")
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @JsonSetter("charge_item_id")
    public void setChargeItemId(Long chargeItemId) {
        this.chargeItemId = chargeItemId;
    }

    @JsonSetter("medical_doctor_id")
    public void setMedicalDoctorId(Long medicalDoctorId) {
        this.medicalDoctorId = medicalDoctorId;
    }

    @JsonSetter("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonSetter("use_method")
    public void setUseMethod(Integer useMethod) {
        this.useMethod = useMethod;
    }

    @JsonSetter("use_frequent")
    public void setUseFrequent(String useFrequent) {
        this.useFrequent = useFrequent;
    }

    public static PrescriptionItem convert(top.itcat.rpc.service.model.PrescriptionItem rpcbean) {
        PrescriptionItem bean = new PrescriptionItem();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetPrescriptionId()) {
            bean.setPrescriptionId(rpcbean.getPrescriptionId());
        }
        if (rpcbean.isSetItemId()) {
            bean.setItemId(rpcbean.getItemId());
        }
        if (rpcbean.isSetChargeItemId()) {
            bean.setChargeItemId(rpcbean.getChargeItemId());
        }
        if (rpcbean.isSetMedicalDoctorId()) {
            bean.setMedicalDoctorId(rpcbean.getMedicalDoctorId());
        }
        if (rpcbean.isSetUseMethod()) {
            bean.setUseMethod(rpcbean.getUseMethod().getValue());
        }
        if (rpcbean.isSetUseFrequent()) {
            bean.setUseFrequent(rpcbean.getUseFrequent());
        }
        if (rpcbean.isSetStatus()) {
            bean.setStatus(rpcbean.getStatus().getValue());
        }
        if (rpcbean.isSetNum()) {
            bean.setNum(rpcbean.getNum());
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.PrescriptionItem convertRPCBean(PrescriptionItem bean) {
        top.itcat.rpc.service.model.PrescriptionItem rpcbean = new top.itcat.rpc.service.model.PrescriptionItem();

        if (bean.getMedicine() != null) {
            rpcbean.setItemId(bean.getMedicine().getId());
            rpcbean.setUseMethod(MedicineUsageEnum.findByValue(bean.getMedicine().getUsage()));
            rpcbean.setUseFrequent(bean.getMedicine().getFrequency());
        }

        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getPrescriptionId() != null) {
            rpcbean.setPrescriptionId(bean.getPrescriptionId());
        }
        if (bean.getItemId() != null) {
            rpcbean.setItemId(bean.getItemId());
        }
        if (bean.getChargeItemId() != null) {
            rpcbean.setChargeItemId(bean.getChargeItemId());
        }

//        if (bean.getChargeItem() != null && bean.getChargeItem().getId() != null) {
//            rpcbean.setChargeItemId(bean.getChargeItem().getId());
//        }

        if (bean.getMedicalDoctorId() != null) {
            rpcbean.setMedicalDoctorId(bean.getMedicalDoctorId());
        }
        rpcbean.setUseMethod(MedicineUsageEnum.findByValue(bean.getUseMethod()));
        rpcbean.setUseFrequent(bean.getUseFrequent());
        if (bean.getStatus() != null) {
            rpcbean.setStatus(PrescriptionItemStatusEnum.findByValue(bean.getStatus()));
        }
        if (bean.getNum() != null) {
            rpcbean.setNum(bean.getNum());
        }
        return rpcbean;
    }

}
