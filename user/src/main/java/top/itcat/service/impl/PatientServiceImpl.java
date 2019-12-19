package top.itcat.service.impl;

import top.itcat.entity.Patient;
import top.itcat.mapper.PatientMapper;
import top.itcat.service.PatientService;
import top.itcat.aop.multipledb.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 患者 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Service
public class PatientServiceImpl extends BaseServiceImpl<PatientMapper, Patient> implements PatientService {

    @Override
    public List<Patient> selectPatient(String searchKey) {
        Map<String, Object> map = new HashMap<>(2);

        if (searchKey != null) {
            map.put("searchKey", searchKey);
        }

        return this.baseMapper.selectPatient(map);
    }
}
