package top.itcat.entity.apply;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.ApplyItemStatus;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("apply_item")
public class ApplyItem extends Model<ApplyItem> {

    private static final long serialVersionUID = 1L;

    /**
     * 申请单项id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 申请id
     */
    @TableField("apply_id")
    private Long applyId;
    /**
     * 收费项目id（通过收费项目可查询相关信息）
     */
    @TableField("charge_item_id")
    private Long chargeItemId;
    /**
     * 非药品id
     */
    @TableField("nonmedical_charge_id")
    private Long nonmedicalChargeId;
    /**
     * 执行医生id
     */
    @TableField("medical_doctor_id")
    private Long medicalDoctorId;
    /**
     * 备注
     */
    private String note;
    /**
     * 结果
     */
    private String result;
    /**
     * 申请状态0未登记1已登记2已完成3作废
     */
    private Integer status;
    /**
     * 申请单项有效性
     */
    private Integer valid;

    @TableField(exist = false)
    private List<ResultItem> resultItems;

    @TableField("operate_time")
    private Long operateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static ApplyItem convert(top.itcat.rpc.service.model.ApplyItem rpcbean) {
        if (rpcbean == null) {
            return null;
        }
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
        if (rpcbean.isSetNonmedicalChargeId()) {
            bean.setNonmedicalChargeId(rpcbean.getNonmedicalChargeId());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }
//        if (rpcbean.getResultItem() != null) {
//            bean.setResultItem(rpcbean.getResultItem());
//        }
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
        if (bean == null) {
            return null;
        }
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
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }
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
