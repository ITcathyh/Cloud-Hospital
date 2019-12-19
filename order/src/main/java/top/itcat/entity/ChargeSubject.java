package top.itcat.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.CatalogEnum;

import java.io.Serializable;

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
@TableName("charge_subject")
public class ChargeSubject extends Model<ChargeSubject> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 费用科目
     */
    private String code;
    /**
     * 名字
     */
    private String name;
    /**
     * 0.药品 1.非药品 2.挂号 3.待定
     */
    private Integer catalog;
    /**
     * 有效性
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static ChargeSubject convert(top.itcat.rpc.service.model.ChargeSubject rpcbean) {
        ChargeSubject bean = new ChargeSubject();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetCode()) {
            bean.setCode(rpcbean.getCode());
        }
        if (rpcbean.isSetName()) {
            bean.setName(rpcbean.getName());
        }
        if (rpcbean.isSetCatalog()) {
            bean.setCatalog(rpcbean.getCatalog().getValue());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.ChargeSubject convertRPCBean(ChargeSubject bean) {
        top.itcat.rpc.service.model.ChargeSubject rpcbean = new top.itcat.rpc.service.model.ChargeSubject();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setCode(bean.getCode());
        rpcbean.setName(bean.getName());
        if (bean.getCatalog() != null) {
            rpcbean.setCatalog(CatalogEnum.findByValue(bean.getCatalog()));
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}