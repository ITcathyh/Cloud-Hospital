package top.itcat.bean.charge;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.charge.BillingCategory;

public class UpdateBillingCategoryReq {
    private BillingCategory billingCategory;

    public BillingCategory getBillingCategory() {
        return billingCategory;
    }

    @JsonSetter("billing_categorie")
    public void setBillingCategory(BillingCategory billingCategory) {
        this.billingCategory = billingCategory;
    }
}
