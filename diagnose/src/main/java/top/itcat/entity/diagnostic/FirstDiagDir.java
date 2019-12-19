package top.itcat.entity.diagnostic;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 一级诊断目录
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@Data
@Accessors(chain = true)
@TableName("first_diag_dir")
public class FirstDiagDir extends Model<FirstDiagDir> {

    private static final long serialVersionUID = 1L;

    /**
     * 一级诊断目录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 一级诊断目录名称
     */
    private String name;
    /**
     * 一级诊断目录有效值
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static FirstDiagDir convert(top.itcat.rpc.service.model.FirstDiagDir rpcbean) {
        FirstDiagDir bean = new FirstDiagDir();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetName()) {
            bean.setName(rpcbean.getName());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.FirstDiagDir convertRPCBean(FirstDiagDir bean) {
        top.itcat.rpc.service.model.FirstDiagDir rpcbean = new top.itcat.rpc.service.model.FirstDiagDir();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setName(bean.getName());
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}