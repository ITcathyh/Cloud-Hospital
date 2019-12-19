package top.itcat.bean.charge;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
public class SubmitSiteRegistrationReq {
    private Long schedulePlanId;
    private Boolean needBook;
    private String name;
    private String idNum;
    private String phone;
    private Integer birthday;
    private String address;
    private Integer gender;
    private Integer age;
    private double actuallyPaid;
    private Long billingCategoryId;

    @JsonSetter("schedule_plan_id")
    public void setSchedulePlanId(Long schedulePlanId) {
        this.schedulePlanId = schedulePlanId;
    }

    @JsonSetter("need_book")
    public void setNeedBook(Boolean needBook) {
        this.needBook = needBook;
    }

    @JsonSetter("id_num")
    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    @JsonSetter("actually_paid")
    public void setActuallyPaid(double actuallyPaid) {
        this.actuallyPaid = actuallyPaid;
    }

    @JsonSetter("billing_category_id")
    public void setBillingCategoryId(Long billingCategoryId) {
        this.billingCategoryId = billingCategoryId;
    }
}
