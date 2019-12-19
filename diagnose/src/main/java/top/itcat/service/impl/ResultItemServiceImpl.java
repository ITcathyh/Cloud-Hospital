package top.itcat.service.impl;

import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.entity.apply.ResultItem;
import top.itcat.mapper.ResultItemMapper;
import top.itcat.service.ResultItemService;

/**
 * <p>
 * 单项 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-18
 */
@Service
public class ResultItemServiceImpl extends BaseServiceImpl<ResultItemMapper, ResultItem> implements ResultItemService {

}
