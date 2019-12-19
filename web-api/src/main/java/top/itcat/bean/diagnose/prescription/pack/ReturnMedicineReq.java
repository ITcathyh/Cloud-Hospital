package top.itcat.bean.diagnose.prescription.pack;

import com.fasterxml.jackson.annotation.JsonSetter;

public class ReturnMedicineReq {
    private Long itemId;
    private Integer category;

    public Long getItemId() {
        return itemId;
    }

    @JsonSetter("item_id")
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
