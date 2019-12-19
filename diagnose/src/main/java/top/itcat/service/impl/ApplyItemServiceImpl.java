package top.itcat.service.impl;

import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.entity.apply.ApplyItem;
import top.itcat.mapper.ApplyItemMapper;
import top.itcat.service.ApplyItemService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-04
 */
@Service
public class ApplyItemServiceImpl extends BaseServiceImpl<ApplyItemMapper, ApplyItem> implements ApplyItemService {

}
