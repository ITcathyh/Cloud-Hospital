package top.itcat.service;

import com.baomidou.mybatisplus.service.IService;
import top.itcat.entity.Patient;

import java.util.List;

/**
 * <p>
 * 患者 服务类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
public interface PatientService extends IService<Patient> {
    List<Patient> selectPatient(String searchKey);
}
