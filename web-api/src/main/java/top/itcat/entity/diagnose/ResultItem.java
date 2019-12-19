package top.itcat.entity.diagnose;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResultItem {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 单项id
     */
    @JSONField(name = "item_id")
    private Long itemId;

    /**
     * 图片路径
     */
    @JSONField(name = "image_path")
    private String imagePath;

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonSetter("item_id")
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @JsonSetter("image_path")
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setItemId(bean.getItemId());
        rpcbean.setImagePath(bean.getImagePath());

        return rpcbean;
    }

}
