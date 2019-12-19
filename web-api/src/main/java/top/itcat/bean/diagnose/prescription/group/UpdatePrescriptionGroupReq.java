package top.itcat.bean.diagnose.prescription.group;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.medical.PrescriptionGroup;

public class UpdatePrescriptionGroupReq {
    private PrescriptionGroup prescriptionGroup;

    @JsonSetter("prescription_group")
    public void setPrescriptionGroup(PrescriptionGroup prescriptionGroup) {
        this.prescriptionGroup = prescriptionGroup;
    }

    public PrescriptionGroup getPrescriptionGroup() {
        return prescriptionGroup;
    }
}
