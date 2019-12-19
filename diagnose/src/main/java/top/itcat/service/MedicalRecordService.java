package top.itcat.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import top.itcat.entity.MedicalRecord;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-03
 */
public interface MedicalRecordService extends IService<MedicalRecord> {
    Page<MedicalRecord> selectUsingIdNum(Page<MedicalRecord> page,
                                         String idNum,
                                         Long medicalRecordNo);
}
