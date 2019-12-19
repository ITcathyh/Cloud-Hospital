package top.itcat.bean.diagnose.prescription;

import top.itcat.entity.medical.Prescription;

public class UpdatePrescriptionReq {
    private Prescription prescription;

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }
}
