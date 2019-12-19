package top.itcat.entity.diagnostic;

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
 * 二级诊断目录
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@Data
@Accessors(chain = true)
@TableName("second_diag_dir")
public class SecondDiagDir extends Model<SecondDiagDir> {

    private static final long serialVersionUID = 1L;

    /**
     * 二级诊断目录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 二级诊断目录名称
     */
    private String name;
    /**
     * 二级诊断目录id
     */
    @TableField("first_diag_dir_id")
    private Long firstDiagDirId;
    /**
     * 二级诊断目录有效性
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static SecondDiagDir convert(top.itcat.rpc.service.model.SecondDiagDir rpcbean) {
        SecondDiagDir bean = new SecondDiagDir();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetName()) {
            bean.setName(rpcbean.getName());
        }
        if (rpcbean.isSetFirstDiagDirId()) {
            bean.setFirstDiagDirId(rpcbean.getFirstDiagDirId());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.SecondDiagDir convertRPCBean(SecondDiagDir bean) {
        top.itcat.rpc.service.model.SecondDiagDir rpcbean = new top.itcat.rpc.service.model.SecondDiagDir();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setName(bean.getName());
        if (bean.getFirstDiagDirId() != null) {
            rpcbean.setFirstDiagDirId(bean.getFirstDiagDirId());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}