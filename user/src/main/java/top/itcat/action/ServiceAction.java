package top.itcat.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itcat.constant.Const;
import top.itcat.entity.*;
import top.itcat.exception.InvalidParamException;
import top.itcat.exception.code.CodeConst;
import top.itcat.rpc.service.model.RoleEnum;
import top.itcat.rpc.service.user.AddOrUpdatePatientWechatSignatureRequest;
import top.itcat.service.*;
import top.itcat.util.EncryUtil;

@Service
@Slf4j
public class ServiceAction {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private HospitalManagerService hospitalManagerService;
    @Autowired
    private AccountClerkService accountClerkService;
    @Autowired
    private PharmacyManagerService pharmacyManagerService;
    @Autowired
    private TollCollectorService tollCollectorService;
    @Autowired
    private PatientWechatSignatureService patientWechatSignatureService;

    @SuppressWarnings("unchecked")
    public boolean editPwd(RoleEnum role, Long id, String oldPwd, String newPwd) {
        String checkPwd = null;
        Wrapper wrapper = new EntityWrapper().eq("id", id).eq("valid", 1);

        switch (role) {
            case Doctor:
            case Medical_Doctor:
                checkPwd = doctorService.selectOne(wrapper).getPassword();
                break;
            case Account_Clerk:
                checkPwd = accountClerkService.selectOne(wrapper).getPassword();
                break;
            case Pharmacy_Manager:
                checkPwd = pharmacyManagerService.selectOne(wrapper).getPassword();
                break;
            case Hospital_Manager:
                checkPwd = hospitalManagerService.selectOne(wrapper).getPassword();
                break;
            case Toll_Collector:
                checkPwd = tollCollectorService.selectOne(wrapper).getPassword();
                break;
            case Patient:
                break;
            default:
                throw new InvalidParamException();
        }

        oldPwd = EncryUtil.SHA1(oldPwd, Const.USER_PWD_SALT);
        log.warn("oldPwd:{}", oldPwd);
        log.warn("checkPwd:{}", checkPwd);

        if (!checkPwd.equals(oldPwd)) {
            throw new InvalidParamException("密码错误", "密码错误", CodeConst.InvalidParamExceptionCode.getValue());
        }

        newPwd = EncryUtil.SHA1(newPwd, Const.USER_PWD_SALT);
        wrapper = new EntityWrapper().eq("id", id).eq("password", oldPwd).eq("valid", 1);

        switch (role) {
            case Doctor:
            case Medical_Doctor: {
                Doctor user = new Doctor();
                user.setPassword(newPwd);
                if (!doctorService.update(user, wrapper)) {
                    return false;
                }
                break;
            }
            case Account_Clerk: {
                AccountClerk user = new AccountClerk();
                user.setPassword(newPwd);
                if (!accountClerkService.update(user, wrapper)) {
                    return false;
                }
                break;
            }
            case Pharmacy_Manager: {
                PharmacyManager user = new PharmacyManager();
                user.setPassword(newPwd);
                if (!pharmacyManagerService.update(user, wrapper)) {
                    return false;
                }
                break;
            }
            case Hospital_Manager: {
                HospitalManager user = new HospitalManager();
                user.setPassword(newPwd);
                if (!hospitalManagerService.update(user, wrapper)) {
                    return false;
                }
                break;
            }
            case Toll_Collector: {
                TollCollector user = new TollCollector();
                user.setPassword(newPwd);
                if (!tollCollectorService.update(user, wrapper)) {
                    return false;
                }
                break;
            }
            case Patient:
                break;
        }

        return true;
    }

    public void addOrUpdatePatientWechatSignature(AddOrUpdatePatientWechatSignatureRequest req) {
        PatientWechatSignature signature = patientWechatSignatureService.selectOne(new EntityWrapper<PatientWechatSignature>()
                .eq("signature", req.getSignature().getSignature())
                .eq("valid", 1));

        if (signature != null) {
            signature.setSignature(req.getSignature().getSignature());
            signature.setIdentityCardNo(req.getSignature().getIdentityCardNo());
        } else {
            signature = PatientWechatSignature.convert(req.getSignature());
        }

        patientWechatSignatureService.insertOrUpdate(signature);
    }
}
