package top.itcat.service.impl;

import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.entity.NonmedicalCharge;
import top.itcat.mapper.NonmedicalChargeMapper;
import top.itcat.service.NonmedicalChargeService;

/**
 * <p>
 * 非药品收费项目 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@Service
public class NonmedicalChargeServiceImpl extends BaseServiceImpl<NonmedicalChargeMapper, NonmedicalCharge> implements NonmedicalChargeService {

}
