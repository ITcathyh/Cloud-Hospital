package top.itcat.service.impl;

import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.entity.apply.Apply;
import top.itcat.mapper.ApplyMapper;
import top.itcat.service.ApplyService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-04
 */
@Service
public class ApplyServiceImpl extends BaseServiceImpl<ApplyMapper, Apply> implements ApplyService {

}
