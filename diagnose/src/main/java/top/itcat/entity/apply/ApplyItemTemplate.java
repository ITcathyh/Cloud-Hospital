package top.itcat.entity.apply;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 申请单项
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-11
 */
@Data
@Accessors(chain = true)
@TableName("apply_item_template")
public class ApplyItemTemplate extends Model<ApplyItemTemplate> {

    private static final long serialVersionUID = 1L;

    /**
     * 申请表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 申组套id
     */
    @TableField("group_id")
    private Long groupId;
    /**
     * 非药品收费id
     */
    @TableField("nonmedical_id")
    private Long nonmedicalId;
    /**
     * 备注
     */
    private String note;
    /**
     * 申请单项有效性
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static ApplyItemTemplate convert(top.itcat.rpc.service.model.ApplyItemTemplate rpcbean) {
        if (rpcbean == null) {
            return null;
        }
        ApplyItemTemplate bean = new ApplyItemTemplate();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetNote()) {
            bean.setNote(rpcbean.getNote());
        }
        if (rpcbean.isSetGroupId()) {
            bean.setGroupId(rpcbean.getGroupId());
        }
        if (rpcbean.isSetNonmedicalId()) {
            bean.setNonmedicalId(rpcbean.getNonmedicalId());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.ApplyItemTemplate convertRPCBean(ApplyItemTemplate bean) {
        if (bean == null) {
            return null;
        }
        top.itcat.rpc.service.model.ApplyItemTemplate rpcbean = new top.itcat.rpc.service.model.ApplyItemTemplate();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setNote(bean.getNote());
        if (bean.getGroupId() != null) {
            rpcbean.setGroupId(bean.getGroupId());
        }
        if (bean.getNonmedicalId() != null) {
            rpcbean.setNonmedicalId(bean.getNonmedicalId());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }
}
