package top.itcat.service.impl;

import top.itcat.entity.Doctor;
import top.itcat.mapper.DoctorMapper;
import top.itcat.service.DoctorService;
import top.itcat.aop.multipledb.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医生 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-14
 */
@Service
public class DoctorServiceImpl extends BaseServiceImpl<DoctorMapper, Doctor> implements DoctorService {
    @Override
    public List<Long> selectOutpatientDoctorIds(Long departId) {
        Map<String, Object> map = new HashMap<>(2);

        if (departId != null) {
            map.put("departId", departId);
        }

        return this.baseMapper.selectOutpatientDoctorIds(map);
    }

    @Override
    public List<Long> selectMedicalDoctorIds(Long departId) {
        Map<String, Object> map = new HashMap<>(2);

        if (departId != null) {
            map.put("departId", departId);
        }

        return this.baseMapper.selectMedicalDoctorIds(map);
    }
}
