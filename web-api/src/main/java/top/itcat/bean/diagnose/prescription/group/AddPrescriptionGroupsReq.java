package top.itcat.bean.diagnose.prescription.group;

import lombok.Data;
import top.itcat.entity.medical.PrescriptionGroup;

import java.util.List;

@Data
public class AddPrescriptionGroupsReq {
    private List<PrescriptionGroup> list;
}
