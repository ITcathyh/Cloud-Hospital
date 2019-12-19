package top.itcat.service.impl;

import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.entity.BillingCategory;
import top.itcat.mapper.BillingCategoryMapper;
import top.itcat.service.BillingCategoryService;

/**
 * <p>
 * 结算类别 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@Service
public class BillingCategoryServiceImpl extends BaseServiceImpl<BillingCategoryMapper, BillingCategory> implements BillingCategoryService {

}
