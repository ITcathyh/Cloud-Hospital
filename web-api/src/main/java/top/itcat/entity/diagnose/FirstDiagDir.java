package top.itcat.entity.diagnose;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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
public class FirstDiagDir {

    private static final long serialVersionUID = 1L;

    /**
     * 一级诊断目录id
     */
    private Long id;
    /**
     * 一级诊断目录名称
     */
    private String name;
    /**
     * 一级诊断目录有效值
     */
    private Integer valid;
    @JSONField(name = "second_direcs")
    private List<SecondDiagDir> secondDirecs;


    public static FirstDiagDir convert(top.itcat.rpc.service.model.FirstDiagDir rpcbean) {
        FirstDiagDir bean = new FirstDiagDir();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetName()) {
            bean.setName(rpcbean.getName());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.FirstDiagDir convertRPCBean(FirstDiagDir bean) {
        top.itcat.rpc.service.model.FirstDiagDir rpcbean = new top.itcat.rpc.service.model.FirstDiagDir();

        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setName(bean.getName());

        return rpcbean;
    }

}