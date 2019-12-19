package top.itcat.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.entity.registrantion.Registration;
import top.itcat.mapper.RegistrationMapper;
import top.itcat.service.RegistrationService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 挂号 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@Service
public class RegistrationServiceImpl extends BaseServiceImpl<RegistrationMapper, Registration> implements RegistrationService {

    @Override
    public Page<Registration> selectUsingDoctorOrDepart(Page<Registration> page,
                                                        Long doctorId,
                                                        Long departId,
                                                        Long curTime) {
        Map<String, Object> map = new HashMap<>(4);


        if (doctorId != null && doctorId > 0) {
            map.put("doctorId", doctorId);
        }

        if (departId != null && departId > 0) {
            map.put("departId", departId);
        }

        if (curTime != null && curTime > 0) {
            map.put("curTime", curTime);
        }

        page.setRecords(this.baseMapper.selectUsingDoctorOrDepart(page, map));
        return page;
    }

    @Override
    public int selectRegistrationCount(Long doctorId, Long departId,
                                       long startTime, long endTime) {
        Map<String, Object> map = new HashMap<>(4);

        if (doctorId != null && doctorId > 0) {
            map.put("doctorId", doctorId);
        }

        if (departId != null && departId > 0) {
            map.put("departId", departId);
        }

        map.put("startTime", startTime);
        map.put("endTime", endTime);

        return SqlHelper.retCount(this.baseMapper
                .selectRegistrationCount(map));
    }
}
