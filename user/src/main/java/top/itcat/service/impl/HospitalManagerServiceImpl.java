package top.itcat.service.impl;

import top.itcat.entity.HospitalManager;
import top.itcat.mapper.HospitalManagerMapper;
import top.itcat.service.HospitalManagerService;
import top.itcat.aop.multipledb.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 医院管理员 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Service
public class HospitalManagerServiceImpl extends BaseServiceImpl<HospitalManagerMapper, HospitalManager> implements HospitalManagerService {

}
