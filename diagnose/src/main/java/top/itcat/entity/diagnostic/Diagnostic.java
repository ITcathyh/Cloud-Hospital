package top.itcat.entity.diagnostic;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * <p>
 * 诊断
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Document(indexName = "diagnostics", type = "diagnostic")
public class Diagnostic extends Model<Diagnostic> {

    private static final long serialVersionUID = 1L;

    /**
     * 诊断id
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 诊断编号
     */
    @Field(index = FieldIndex.not_analyzed, type = FieldType.String)
    private String code;
    /**
     * 诊断名称
     */
    @Field(index = FieldIndex.analyzed, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", type = FieldType.String)
    private String name;
    /**
     * 诊断目录id
     */
    @TableField("directory_id")
    @Field(type = FieldType.Long)
    private Long directoryId;
    /**
     * 诊断有效�?
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static Diagnostic convert(top.itcat.rpc.service.model.Diagnostic rpcbean) {
        Diagnostic bean = new Diagnostic();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetCode()) {
            bean.setCode(rpcbean.getCode());
        }
        if (rpcbean.isSetName()) {
            bean.setName(rpcbean.getName());
        }
        if (rpcbean.isSetSecondDiagDirId()) {
            bean.setDirectoryId(rpcbean.getSecondDiagDirId());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.Diagnostic convertRPCBean(Diagnostic bean) {
        top.itcat.rpc.service.model.Diagnostic rpcbean = new top.itcat.rpc.service.model.Diagnostic();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setCode(bean.getCode());
        rpcbean.setName(bean.getName());
        if (bean.getDirectoryId() != null) {
            rpcbean.setSecondDiagDirId(bean.getDirectoryId());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}