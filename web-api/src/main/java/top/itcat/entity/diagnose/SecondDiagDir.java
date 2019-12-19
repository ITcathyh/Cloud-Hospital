package top.itcat.entity.diagnose;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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
public class SecondDiagDir {

    private static final long serialVersionUID = 1L;

    /**
     * 二级诊断目录id
     */
    private Long id;
    /**
     * 二级诊断目录名称
     */
    private String name;
    /**
     * 二级诊断目录id
     */
    @JSONField(name = "first_diag_dir_id")
    private Long firstDiagDirId;
    /**
     * 二级诊断目录有效性
     */
    private Integer valid;

    private List<Diagnostic> diagnostics = null;

    @JsonSetter("first_directory_id")
    public void setFirstDiagDirId(Long firstDiagDirId) {
        this.firstDiagDirId = firstDiagDirId;
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

        return rpcbean;
    }

}