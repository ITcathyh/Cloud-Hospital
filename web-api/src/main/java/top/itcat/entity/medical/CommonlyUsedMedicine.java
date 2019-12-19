package top.itcat.entity.medical;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.MedicineCategoryEnum;
import top.itcat.rpc.service.model.SuitableRangeEnum;

@Data
@Accessors(chain = true)
public class CommonlyUsedMedicine {
    private static final long serialVersionUID = 1L;

    /**
     * 诊断id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 创建的医生的科室的id
     */
    @JSONField(name = "department_id")
    private Long departmentId;

    /**
     * 创建的医生id
     */
    @JSONField(name = "doctor_id")
    private Long doctorId;

    /**
     * 药品id(具体某一个病的id)
     */
    @JSONField(name = "medical_id")
    private Long medicalId;

    /**
     * 常用药品适用范围  个人、科室、全院
     */
    @JSONField(name = "suitable_range")
    private Integer suitableRange;

    /**
     * 0西药1中成药2中药
     */
    @JSONField(name = "catalog")
    private Integer catalog;
    private Medicine medicine;

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonSetter("department_id")
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @JsonSetter("doctor_id")
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @JsonSetter("medical_id")
    public void setMedicalId(Long medicalId) {
        this.medicalId = medicalId;
    }

    @JsonSetter("suitable_range")
    public void setSuitableRange(Integer suitableRange) {
        this.suitableRange = suitableRange;
    }

    @JsonSetter("catalog")
    public void setCatalog(Integer catalog) {
        this.catalog = catalog;
    }

    public static CommonlyUsedMedicine convert(top.itcat.rpc.service.model.CommonlyUsedMedicine rpcbean) {
        CommonlyUsedMedicine bean = new CommonlyUsedMedicine();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetDepartmentId()) {
            bean.setDepartmentId(rpcbean.getDepartmentId());
        }
        if (rpcbean.isSetDoctorId()) {
            bean.setDoctorId(rpcbean.getDoctorId());
        }
        if (rpcbean.isSetMedicalId()) {
            bean.setMedicalId(rpcbean.getMedicalId());
        }
        if (rpcbean.isSetSuitableRange()) {
            bean.setSuitableRange(rpcbean.getSuitableRange().getValue());
        }
        if (rpcbean.isSetCatalog()) {
            bean.setCatalog(rpcbean.getCatalog().getValue());
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.CommonlyUsedMedicine convertRPCBean(CommonlyUsedMedicine bean) {
        top.itcat.rpc.service.model.CommonlyUsedMedicine rpcbean = new top.itcat.rpc.service.model.CommonlyUsedMedicine();

        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }

        if (bean.getDepartmentId() != null && bean.getDepartmentId() != 0) {
            rpcbean.setDepartmentId(bean.getDepartmentId());
        }

        if (bean.getDoctorId() != null && bean.getDoctorId() != 0) {
            rpcbean.setDoctorId(bean.getDoctorId());
        }

        if (bean.getMedicalId() != null) {
            rpcbean.setMedicalId(bean.getMedicalId());
        }

        if (bean.getSuitableRange() != null) {
            rpcbean.setSuitableRange(SuitableRangeEnum.findByValue(bean.getSuitableRange()));
        }

        if (bean.getCatalog() != null) {
            rpcbean.setCatalog(MedicineCategoryEnum.findByValue(bean.getCatalog()));
        }

//        if (bean.getMedicine() != null) {
//            bean.setMedicalId(bean.getMedicine().getId());
//        }

        return rpcbean;
    }

}
