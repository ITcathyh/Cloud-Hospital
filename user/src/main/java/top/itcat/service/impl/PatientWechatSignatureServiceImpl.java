package top.itcat.service.impl;

import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.entity.PatientWechatSignature;
import top.itcat.mapper.PatientWechatSignatureMapper;
import top.itcat.service.PatientWechatSignatureService;

/**
 * <p>
 * 患者微信签名 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-25
 */
@Service
public class PatientWechatSignatureServiceImpl extends BaseServiceImpl<PatientWechatSignatureMapper, PatientWechatSignature> implements PatientWechatSignatureService {

}
