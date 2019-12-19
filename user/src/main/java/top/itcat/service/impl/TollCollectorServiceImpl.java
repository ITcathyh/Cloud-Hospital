package top.itcat.service.impl;

import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.service.TollCollectorService;
import top.itcat.entity.TollCollector;
import top.itcat.mapper.TollCollectorMapper;

/**
 * <p>
 * 挂号收费员 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Service
public class TollCollectorServiceImpl extends BaseServiceImpl<TollCollectorMapper, TollCollector> implements TollCollectorService {
}
