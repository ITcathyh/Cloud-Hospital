package top.itcat.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.DoctorDiagnosticCatalogEnum;
import top.itcat.rpc.service.model.MedicineCategoryEnum;
import top.itcat.rpc.service.model.SuitableRangeEnum;

/**
 * <p>
 * 诊断
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-11
 */
@Data
@Accessors(chain = true)
@TableName("commonly_used_diagnostic")
public class CommonlyUsedDiagnostic extends Model<CommonlyUsedDiagnostic> {

    private static final long serialVersionUID = 1L;

    /**
     * 诊断id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建的医生id
     */
    @TableField("doctor_id")
    private Long doctorId;
    /**
     * 诊断id(具体某一个病的id)
     */
    @TableField("diagnostic_id")
    private Long diagnosticId;
    /**
     * 适用范围  个人、科室、全院
     */
    @TableField("suitable_range")
    private Integer suitableRange;
    /**
     * 0中医诊断1西医诊断
     */
    private Integer catalog;
    /**
     * 病历有效性
     */
    private Integer valid;

    @TableField("department_id")
    private Long departmentId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static CommonlyUsedDiagnostic convert(top.itcat.rpc.service.model.CommonlyUsedDiagnostic rpcbean) {
        if (rpcbean == null) {
            return null;
        }
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
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }
        if (rpcbean.isSetDepartmentId()) {
            bean.setDepartmentId(rpcbean.getDepartmentId());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.CommonlyUsedDiagnostic convertRPCBean(CommonlyUsedDiagnostic bean) {
        if (bean == null) {
            return null;
        }
        top.itcat.rpc.service.model.CommonlyUsedDiagnostic rpcbean = new top.itcat.rpc.service.model.CommonlyUsedDiagnostic();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getDoctorId() != null) {
            rpcbean.setDoctorId(bean.getDoctorId());
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
        if (bean.getDepartmentId() != null) {
            rpcbean.setDepartmentId(bean.getDepartmentId());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}