package top.itcat.service.impl;

import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.entity.MedicalRecordTemplate;
import top.itcat.mapper.MedicalRecordTemplateMapper;
import top.itcat.service.MedicalRecordTemplateService;

/**
 * <p>
 * 病历模板 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-11
 */
@Service
public class MedicalRecordTemplateServiceImpl extends BaseServiceImpl<MedicalRecordTemplateMapper, MedicalRecordTemplate> implements MedicalRecordTemplateService {

}
