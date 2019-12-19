package top.itcat.service.impl;

import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.entity.PatientRecord;
import top.itcat.mapper.PatientRecordMapper;
import top.itcat.service.PatientRecordService;

/**
 * <p>
 * 患者病历号 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-24
 */
@Service
public class PatientRecordServiceImpl extends BaseServiceImpl<PatientRecordMapper, PatientRecord> implements PatientRecordService {

}
