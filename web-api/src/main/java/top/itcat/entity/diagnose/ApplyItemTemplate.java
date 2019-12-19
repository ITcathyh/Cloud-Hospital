package top.itcat.entity.diagnose;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.entity.charge.NonmedicalCharge;

@Data
@Accessors(chain = true)
public class ApplyItemTemplate {

    private static final long serialVersionUID = 1L;

    /**
     * 申请表id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 申组套id
     */
    @JSONField(name = "group_id")
    private Long groupId;

    /**
     * 非药品收费id
     */
    @JSONField(name = "nonmedical_id")
    private Long nonmedicalId;

    @JSONField(name = "nonmedical_charge")
    private NonmedicalCharge nonmedicalCharge;

    /**
     * 备注
     */
    @JSONField(name = "note")
    private String note;

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonSetter("group_id")
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @JsonSetter("nonmedical_id")
    public void setNonmedicalId(Long nonmedicalId) {
        this.nonmedicalId = nonmedicalId;
    }

    @JsonSetter("note")
    public void setNote(String note) {
        this.note = note;
    }

    @JsonSetter("nonmedical_charge")
    public void setNonmedicalCharge(NonmedicalCharge nonmedicalCharge) {
        this.nonmedicalCharge = nonmedicalCharge;
    }

    public static ApplyItemTemplate convert(top.itcat.rpc.service.model.ApplyItemTemplate rpcbean) {
        ApplyItemTemplate bean = new ApplyItemTemplate();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetGroupId()) {
            bean.setGroupId(rpcbean.getGroupId());
        }
        if (rpcbean.isSetNonmedicalId()) {
            bean.setNonmedicalId(rpcbean.getNonmedicalId());
        }
        if (rpcbean.isSetNote()) {
            bean.setNote(rpcbean.getNote());
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.ApplyItemTemplate convertRPCBean(ApplyItemTemplate bean) {
        top.itcat.rpc.service.model.ApplyItemTemplate rpcbean = new top.itcat.rpc.service.model.ApplyItemTemplate();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getGroupId() != null) {
            rpcbean.setGroupId(bean.getGroupId());
        }
        if (bean.getNonmedicalId() != null) {
            rpcbean.setNonmedicalId(bean.getNonmedicalId());
        }

        if (bean.getNonmedicalCharge() != null && bean.getNonmedicalId() != null) {
            rpcbean.setNonmedicalId(bean.getNonmedicalId());
//            rpcbean.setnon(NonmedicalCharge.convertRPCBean(bean.getNonmedicalCharge()));
        }

        rpcbean.setNote(bean.getNote());
        return rpcbean;
    }

}
