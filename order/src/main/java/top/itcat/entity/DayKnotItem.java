package top.itcat.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-29
 */
@Data
@Accessors(chain = true)
@TableName("day_knot_item")
public class DayKnotItem extends Model<DayKnotItem> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 收费项目id
     */
    @TableField("charge_item_id")
    private Long chargeItemId;
    /**
     * 日结单id
     */
    @TableField("day_knot_id")
    private Long dayKnotId;
    /**
     * 费用科目id
     */
    @TableField("charge_subject_id")
    private Long chargeSubjectId;
    /**
     * 是否有效
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static DayKnotItem convert(top.itcat.rpc.service.model.DayKnotItem rpcbean) {
        DayKnotItem bean = new DayKnotItem();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetChargeItemId()) {
            bean.setChargeItemId(rpcbean.getChargeItemId());
        }
        if (rpcbean.isSetDayKnotId()) {
            bean.setDayKnotId(rpcbean.getDayKnotId());
        }
        if (rpcbean.isSetChargeSubjectId()) {
            bean.setChargeSubjectId(rpcbean.getChargeSubjectId());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.DayKnotItem convertRPCBean(DayKnotItem bean) {
        top.itcat.rpc.service.model.DayKnotItem rpcbean = new top.itcat.rpc.service.model.DayKnotItem();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getChargeItemId() != null) {
            rpcbean.setChargeItemId(bean.getChargeItemId());
        }
        if (bean.getDayKnotId() != null) {
            rpcbean.setDayKnotId(bean.getDayKnotId());
        }
        if (bean.getChargeSubjectId() != null) {
            rpcbean.setChargeSubjectId(bean.getChargeSubjectId());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}