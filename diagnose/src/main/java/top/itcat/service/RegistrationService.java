package top.itcat.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import top.itcat.entity.registrantion.Registration;

/**
 * <p>
 * 挂号 服务类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
public interface RegistrationService extends IService<Registration> {
    public Page<Registration> selectUsingDoctorOrDepart(Page<Registration> page,
                                                        Long doctorId,
                                                        Long departId,
                                                        Long curTime);

    int selectRegistrationCount(Long doctorId, Long departId,
                                long startTime, long endTime);
}
