package top.itcat.entity.diagnose;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 诊断
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@Data
@Accessors(chain = true)
public class Diagnostic {

    private static final long serialVersionUID = 1L;

    /**
     * 诊断id
     */
    private Long id;
    /**
     * 诊断编号
     */
    private String code;
    /**
     * 诊断名称
     */
    private String name;
    /**
     * 诊断目录id
     */
    @JSONField(name = "second_directory_id")
    private Long directoryId;
    /**
     * 诊断有效性
     */
    private Integer valid;

    @JsonSetter("second_directory_id")
    public void setDirectoryId(Long directoryId) {
        this.directoryId = directoryId;
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

        return rpcbean;
    }

}