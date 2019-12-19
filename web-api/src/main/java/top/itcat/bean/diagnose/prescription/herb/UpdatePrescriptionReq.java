package top.itcat.bean.diagnose.prescription.herb;

import top.itcat.entity.medical.PrescriptionHerb;

public class UpdatePrescriptionReq {
    private PrescriptionHerb prescription;

    public PrescriptionHerb getPrescription() {
        return prescription;
    }

    public void setPrescription(PrescriptionHerb prescription) {
        this.prescription = prescription;
    }
}
