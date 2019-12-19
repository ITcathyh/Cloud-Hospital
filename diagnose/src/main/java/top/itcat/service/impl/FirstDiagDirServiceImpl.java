package top.itcat.service.impl;

import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.entity.diagnostic.FirstDiagDir;
import top.itcat.mapper.FirstDiagDirMapper;
import top.itcat.service.FirstDiagDirService;

/**
 * <p>
 * 一级诊断目录 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@Service
public class FirstDiagDirServiceImpl extends BaseServiceImpl<FirstDiagDirMapper, FirstDiagDir> implements FirstDiagDirService {

}
