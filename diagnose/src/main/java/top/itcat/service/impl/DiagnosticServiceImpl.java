package top.itcat.service.impl;

import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.entity.diagnostic.Diagnostic;
import top.itcat.mapper.DiagnosticMapper;
import top.itcat.service.DiagnosticService;

/**
 * <p>
 * 诊断 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@Service
public class DiagnosticServiceImpl extends BaseServiceImpl<DiagnosticMapper, Diagnostic> implements DiagnosticService {

}
