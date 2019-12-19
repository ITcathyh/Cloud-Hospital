package top.itcat.service.impl;

import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.entity.diagnostic.SecondDiagDir;
import top.itcat.mapper.SecondDiagDirMapper;
import top.itcat.service.SecondDiagDirService;

/**
 * <p>
 * 二级诊断目录 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@Service
public class SecondDiagDirServiceImpl extends BaseServiceImpl<SecondDiagDirMapper, SecondDiagDir> implements SecondDiagDirService {

}
