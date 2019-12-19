package top.itcat.entity.diagnose;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.entity.charge.ChargeItem;
import top.itcat.entity.charge.NonmedicalCharge;
import top.itcat.rpc.service.model.ApplyItemStatus;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class ApplyItem {

    private static final long serialVersionUID = 1L;

    /**
     * 申请单项id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 申请id
     */
    @JSONField(name = "apply_id")
    private Long applyId;

    /**
     * 收费项目id（通过收费项目可查询相关信息）
     */
    @JSONField(name = "charge_item_id")
    private Long chargeItemId;

    @JSONField(name = "charge_item")
    private ChargeItem chargeItem;

    /**
     * 执行医生id
     */
    @JSONField(name = "medical_doctor_id")
    private Long medicalDoctorId;

    /**
     * 备注
     */
    @JSONField(name = "note")
    private String note;

    /**
     * 结果
     */
    @JSONField(name = "result")
    private String result;

    /**
     * 申请状态0未登记1已登记2已完成3作废
     */
    @JSONField(name = "status")
    private Integer status;

    @JSONField(name = "nonmedical_charge")
    private NonmedicalCharge nonmedicalCharge;

    @JSONField(name = "nonmedical_charge_id")
    private Long nonmedicalChargeId;

    @JSONField(name = "result_items")
    private List<ResultItem> resultItems;

    @JSONField(name = "operate_time")
    private Long operateTime;

    @JsonSetter("result_items")
    public void setResultItems(List<ResultItem> resultItems) {
        this.resultItems = resultItems;
    }

    @JsonSetter("nonmedical_charge_id")
    public void setNonmedicalChargeId(long nonmedicalChargeId) {
        this.nonmedicalChargeId = nonmedicalChargeId;
    }

    @JsonSetter("nonmedical_charge")
    public void setNonmedicalCharge(NonmedicalCharge nonmedicalCharge) {
        this.nonmedicalCharge = nonmedicalCharge;
    }

    @JsonSetter("charge_item")
    public void setChargeItem(ChargeItem chargeItem) {
        this.chargeItem = chargeItem;
    }

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonSetter("apply_id")
    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    @JsonSetter("charge_item_id")
    public void setChargeItemId(Long chargeItemId) {
        this.chargeItemId = chargeItemId;
    }

    @JsonSetter("medical_doctor_id")
    public void setMedicalDoctorId(Long medicalDoctorId) {
        this.medicalDoctorId = medicalDoctorId;
    }

    @JsonSetter("note")
    public void setNote(String note) {
        this.note = note;
    }

    @JsonSetter("result")
    public void setResult(String result) {
        this.result = result;
    }

    @JsonSetter("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    public static ApplyItem convert(top.itcat.rpc.service.model.ApplyItem rpcbean) {
        ApplyItem bean = new ApplyItem();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetApplyId()) {
            bean.setApplyId(rpcbean.getApplyId());
        }
        if (rpcbean.isSetChargeItemId()) {
            bean.setChargeItemId(rpcbean.getChargeItemId());
        }
        if (rpcbean.isSetMedicalDoctorId()) {
            bean.setMedicalDoctorId(rpcbean.getMedicalDoctorId());
        }
        if (rpcbean.isSetNote()) {
            bean.setNote(rpcbean.getNote());
        }
        if (rpcbean.isSetResult()) {
            bean.setResult(rpcbean.getResult());
        }
        if (rpcbean.isSetStatus()) {
            bean.setStatus(rpcbean.getStatus().getValue());
        }
        if (rpcbean.isSetChargeItem()) {
            bean.setChargeItem(ChargeItem.convert(rpcbean.getChargeItem()));
        }
        if (rpcbean.isSetNonmedicalChargeId()) {
            bean.setNonmedicalChargeId(rpcbean.getNonmedicalChargeId());
        }
        if (rpcbean.isSetResultItems()) {
            bean.setResultItems(rpcbean.getResultItems().parallelStream()
                    .map(ResultItem::convert)
                    .collect(Collectors.toList()));
        }
        if (rpcbean.isSetOperateTime()) {
            bean.setOperateTime(rpcbean.getOperateTime());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.ApplyItem convertRPCBean(ApplyItem bean) {
        top.itcat.rpc.service.model.ApplyItem rpcbean = new top.itcat.rpc.service.model.ApplyItem();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getApplyId() != null) {
            rpcbean.setApplyId(bean.getApplyId());
        }
        if (bean.getChargeItemId() != null) {
            rpcbean.setChargeItemId(bean.getChargeItemId());
        }
        if (bean.getMedicalDoctorId() != null) {
            rpcbean.setMedicalDoctorId(bean.getMedicalDoctorId());
        }
        rpcbean.setNote(bean.getNote());
        rpcbean.setResult(bean.getResult());
        if (bean.getStatus() != null) {
            rpcbean.setStatus(ApplyItemStatus.findByValue(bean.getStatus()));
        }
        if (bean.getNonmedicalChargeId() != null) {
            rpcbean.setNonmedicalChargeId(bean.getNonmedicalChargeId());
        }
        rpcbean.setChargeItem(ChargeItem.convertRPCBean(bean.getChargeItem()));
        if (bean.getResultItems() != null && !bean.getResultItems().isEmpty()) {
            rpcbean.setResultItems(bean.getResultItems().parallelStream()
                    .map(ResultItem::convertRPCBean)
                    .collect(Collectors.toList()));
        }
        if (bean.getOperateTime() != null) {
            rpcbean.setOperateTime(bean.getOperateTime());
        }
        return rpcbean;
    }

}
