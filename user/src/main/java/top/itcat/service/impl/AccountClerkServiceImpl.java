package top.itcat.service.impl;

import top.itcat.entity.AccountClerk;
import top.itcat.mapper.AccountClerkMapper;
import top.itcat.service.AccountClerkService;
import top.itcat.aop.multipledb.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 财务管理员 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Service
public class AccountClerkServiceImpl extends BaseServiceImpl<AccountClerkMapper, AccountClerk> implements AccountClerkService {

}
