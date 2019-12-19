package top.itcat.service.impl;

import top.itcat.entity.PharmacyManager;
import top.itcat.mapper.PharmacyManagerMapper;
import top.itcat.service.PharmacyManagerService;
import top.itcat.aop.multipledb.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 药房操作员 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Service
public class PharmacyManagerServiceImpl extends BaseServiceImpl<PharmacyManagerMapper, PharmacyManager> implements PharmacyManagerService {

}
