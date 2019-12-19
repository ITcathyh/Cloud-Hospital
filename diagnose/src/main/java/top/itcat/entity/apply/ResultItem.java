package top.itcat.entity.apply;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 单项
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-18
 */
@Data
@Accessors(chain = true)
@TableName("result_item")
public class ResultItem extends Model<ResultItem> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 单项id
     */
    @TableField("item_id")
    private Long itemId;
    /**
     * 图片路径
     */
    @TableField("image_path")
    private String imagePath;
    /**
     * 单项有效�?
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static ResultItem convert(top.itcat.rpc.service.model.ResultItem rpcbean) {
        if (rpcbean == null) {
            return null;
        }
        ResultItem bean = new ResultItem();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetItemId()) {
            bean.setItemId(rpcbean.getItemId());
        }
        if (rpcbean.isSetImagePath()) {
            bean.setImagePath(rpcbean.getImagePath());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.ResultItem convertRPCBean(ResultItem bean) {
        if (bean == null) {
            return null;
        }
        top.itcat.rpc.service.model.ResultItem rpcbean = new top.itcat.rpc.service.model.ResultItem();
        rpcbean.setId(bean.getId());
        rpcbean.setItemId(bean.getItemId());
        rpcbean.setImagePath(bean.getImagePath());

        return rpcbean;
    }

}