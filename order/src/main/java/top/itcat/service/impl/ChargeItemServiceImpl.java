package top.itcat.service.impl;

import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.entity.ChargeItem;
import top.itcat.mapper.ChargeItemMapper;
import top.itcat.service.ChargeItemService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-29
 */
@Service
public class ChargeItemServiceImpl extends BaseServiceImpl<ChargeItemMapper, ChargeItem> implements ChargeItemService {

}
