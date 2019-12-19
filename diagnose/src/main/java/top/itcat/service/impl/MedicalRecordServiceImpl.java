package top.itcat.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.entity.MedicalRecord;
import top.itcat.mapper.MedicalRecordMapper;
import top.itcat.service.MedicalRecordService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-03
 */
@Service
public class MedicalRecordServiceImpl extends BaseServiceImpl<MedicalRecordMapper, MedicalRecord> implements MedicalRecordService {

    @Override
    public Page<MedicalRecord> selectUsingIdNum(Page<MedicalRecord> page, String idNum, Long medicalRecordNo) {
        Map<String, Object> map = new HashMap<>();

        if (!StringUtils.isEmpty(idNum)) {
            map.put("idNum", idNum);
        }

        if (medicalRecordNo != null) {
            map.put("medicalRecordNo", medicalRecordNo);
        }

        page.setRecords(this.baseMapper.selectUsingIdNum(page, map));
        return page;
    }
}
