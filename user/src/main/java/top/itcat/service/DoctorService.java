package top.itcat.service;

import com.baomidou.mybatisplus.service.IService;
import top.itcat.entity.Doctor;

import java.util.List;

/**
 * <p>
 * 医生 服务类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-14
 */
public interface DoctorService extends IService<Doctor> {
    List<Long> selectOutpatientDoctorIds(Long departId);
    List<Long> selectMedicalDoctorIds(Long departId);

}
