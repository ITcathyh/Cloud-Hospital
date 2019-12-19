package top.itcat.bean.diagnose.prescription.herb;

import top.itcat.entity.medical.PrescriptionHerb;

import java.util.List;

public class AddPrescriptionHerbReq {
    private List<PrescriptionHerb> list;

    public List<PrescriptionHerb> getList() {
        return list;
    }

    public void setList(List<PrescriptionHerb> list) {
        this.list = list;
    }
}
