package top.itcat.entity.diagnose;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.DoctorDiagnosticCatalogEnum;
import top.itcat.rpc.service.model.SuitableRangeEnum;

@Data
@Accessors(chain = true)
public class CommonlyUsedDiagnostic {

    private static final long serialVersionUID = 1L;

    /**
     * 诊断id
     */
    @JSONField(name = "id")
    private Long id;

    @JSONField(name = "department_id")
    private Long departmentId;
    /**
     * 创建的医生id
     */
    @JSONField(name = "doctor_id")
    private Long doctorId;

    /**
     * 诊断id(具体某一个病的id)
     */
    @JSONField(name = "diagnostic_id")
    private Long diagnosticId;
    private Diagnostic diagnostic;

    /**
     * 病历模板适用范围  个人、科室、全院
     */
    @JSONField(name = "suitable_range")
    private Integer suitableRange;

    /**
     * 0中医诊断1西医诊断
     */
    @JSONField(name = "catalog")
    private Integer catalog;

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

    @JsonSetter("diagnostic_id")
    public void setDiagnosticId(Long diagnosticId) {
        this.diagnosticId = diagnosticId;
    }

    @JsonSetter("suitable_range")
    public void setSuitableRange(Integer suitableRange) {
        this.suitableRange = suitableRange;
    }

    @JsonSetter("catalog")
    public void setCatalog(Integer catalog) {
        this.catalog = catalog;
    }

    public static CommonlyUsedDiagnostic convert(top.itcat.rpc.service.model.CommonlyUsedDiagnostic rpcbean) {
        CommonlyUsedDiagnostic bean = new CommonlyUsedDiagnostic();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetDoctorId()) {
            bean.setDoctorId(rpcbean.getDoctorId());
        }
        if (rpcbean.isSetDiagnosticId()) {
            bean.setDiagnosticId(rpcbean.getDiagnosticId());
        }
        if (rpcbean.isSetSuitableRange()) {
            bean.setSuitableRange(rpcbean.getSuitableRange().getValue());
        }
        if (rpcbean.isSetCatalog()) {
            bean.setCatalog(rpcbean.getCatalog().getValue());
        }
        if (rpcbean.isSetDepartmentId()) {
            bean.setDepartmentId(rpcbean.getDepartmentId());
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.CommonlyUsedDiagnostic convertRPCBean(CommonlyUsedDiagnostic bean) {
        top.itcat.rpc.service.model.CommonlyUsedDiagnostic rpcbean = new top.itcat.rpc.service.model.CommonlyUsedDiagnostic();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getDoctorId() != null && bean.getDoctorId() != 0) {
            rpcbean.setDoctorId(bean.getDoctorId());
        }

        if (bean.getDepartmentId() != null && bean.getDepartmentId() != 0) {
            rpcbean.setDepartmentId(bean.getDepartmentId());
        }
        if (bean.getDiagnosticId() != null) {
            rpcbean.setDiagnosticId(bean.getDiagnosticId());
        }
        if (bean.getSuitableRange() != null) {
            rpcbean.setSuitableRange(SuitableRangeEnum.findByValue(bean.getSuitableRange()));
        }
        if (bean.getCatalog() != null) {
            rpcbean.setCatalog(DoctorDiagnosticCatalogEnum.findByValue(bean.getCatalog()));
        }
//        if (bean.getDepartmentId() != null) {
//            rpcbean.setDepartmentId(bean.getDepartmentId());
//        }
//        if (bean.getDiagnostic() != null && bean.getDiagnostic().getId() != null) {
//            rpcbean.setDiagnosticId(bean.getDiagnostic().getId());
//        }

        return rpcbean;
    }

}
