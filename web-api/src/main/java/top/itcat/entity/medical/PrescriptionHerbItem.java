package top.itcat.entity.medical;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.entity.charge.ChargeItem;
import top.itcat.rpc.service.model.PrescriptionItemStatusEnum;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class PrescriptionHerbItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 处方单项id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 药品id
     */
    @JSONField(name = "prescription_id")
    private Long prescriptionId;

    /**
     * 处方单项对应id
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
     * 申请状态0未开1已开2已退
     */
    @JSONField(name = "status")
    private Integer status;

    /**
     * 处方单项小计
     */
    @JSONField(name = "price")
    private Double price;
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

    @JsonSetter("price")
    public void setPrice(Double price) {
        this.price = price;
    }

    public static PrescriptionHerbItem convert(top.itcat.rpc.service.model.PrescriptionHerbItem rpcbean) {
        PrescriptionHerbItem bean = new PrescriptionHerbItem();
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
        if (rpcbean.isSetStatus()) {
            bean.setStatus(rpcbean.getStatus().getValue());
        }
        if (rpcbean.isSetPrice()) {
            bean.setPrice(rpcbean.getPrice());
        }
        if (rpcbean.isSetNum()) {
            bean.setNum(rpcbean.getNum());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.PrescriptionHerbItem convertRPCBean(PrescriptionHerbItem bean) {
        top.itcat.rpc.service.model.PrescriptionHerbItem rpcbean = new top.itcat.rpc.service.model.PrescriptionHerbItem();
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
        if (bean.getMedicalDoctorId() != null) {
            rpcbean.setMedicalDoctorId(bean.getMedicalDoctorId());
        }
        if (bean.getStatus() != null) {
            rpcbean.setStatus(PrescriptionItemStatusEnum.findByValue(bean.getStatus()));
        }
        if (bean.getPrice() != null) {
            rpcbean.setPrice(bean.getPrice());
        }
        if (bean.getNum() != null) {
            rpcbean.setNum(bean.getNum());
        }
        return rpcbean;
    }

}
