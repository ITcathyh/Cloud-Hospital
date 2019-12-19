package top.itcat.pack;

import org.springframework.stereotype.Service;
import top.itcat.entity.BaseUser;
import top.itcat.rpc.service.model.*;

@Service
public class UserPack {
    private User packBaseRpcUser(BaseUser oriUser, RoleEnum role) {
        User user = new User();

        user.setId(oriUser.getId());
        user.setCode(oriUser.getCode());
        user.setRole(role);

        if (oriUser.getDepartmentId() != null) {
            user.setDepartId(oriUser.getDepartmentId());
        }

        user.setRealName(oriUser.getRealname());

        return user;
    }

    public PackedUser packUser(BaseUser oriUser, RoleEnum role) {
        if (oriUser == null) {
            return null;
        }

        PackedUser packedUser = new PackedUser();
        packedUser.setUser(packBaseRpcUser(oriUser, role));

        return packedUser;
    }

    public PackedUser packDoctor(PackedUser packedUser) {
        if (packedUser == null) {
            return null;
        }

        packedUser.setTitle(DoctorTitleEnum.Director);
        packedUser.setDescription("dr");

        return packedUser;
    }

    public OutpatientDoctor packOutpatientDoctor(top.itcat.entity.Doctor oriDr) {
        OutpatientDoctor user = new OutpatientDoctor();

        user.setUser(packBaseRpcUser(oriDr, RoleEnum.Doctor));
        user.setDescription(oriDr.getDescription());
        user.setTitle(DoctorTitleEnum.findByValue(oriDr.getJobTitle()));
        user.setInSchedual(oriDr.getInScheduling() == 1);

        return user;
    }

    public MedicalDoctor packMedicalDoctor(top.itcat.entity.Doctor medicalDoctor) {
        MedicalDoctor user = new MedicalDoctor();

        user.setUser(packBaseRpcUser(medicalDoctor, RoleEnum.Medical_Doctor));
        user.setDescription(medicalDoctor.getDescription());
        user.setTitle(DoctorTitleEnum.findByValue(medicalDoctor.getJobTitle()));
        return user;
    }

//    public OutpatientDoctor packOutpatientDoctor(top.itcat.entity.OutpatientDoctor oriDr) {
//        OutpatientDoctor user = new OutpatientDoctor();
//
//        user.setUser(packBaseRpcUser(oriDr, RoleEnum.Doctor));
//        user.setDescription(oriDr.getDescription());
//        user.setTitle(DoctorTitleEnum.findByValue(oriDr.getJobTitle()));
//        user.setInSchedual(oriDr.getInScheduling() == 1);
//
//        return user;
//    }

    public Patient packPatient(top.itcat.entity.Patient oriDr) {
        Patient user = new Patient();
//        user.setUser(packBaseRpcUser(oriDr, RoleEnum.Patient));

        BasePatientInfo basePatientInfo = new BasePatientInfo();
        basePatientInfo.setIdNum(oriDr.getIdentityCardNo());
        basePatientInfo.setName(oriDr.getPatientName());
        basePatientInfo.setGender(GenderEnum.findByValue(oriDr.getGender()));
        basePatientInfo.setAge(oriDr.getAge());
        basePatientInfo.setAddress(oriDr.getFamilyAddress());
        basePatientInfo.setBirth(oriDr.getBirthday());
        basePatientInfo.setPhone(oriDr.getPhone());

        user.setUserInfo(basePatientInfo);

        return user;
    }

    public TollCollector packTollCollector(top.itcat.entity.TollCollector tollCollector) {
        TollCollector user = new TollCollector();

        user.setUser(packBaseRpcUser(tollCollector, RoleEnum.Toll_Collector));

        return user;
    }

    public PharmacyManager packPharmacyManager(top.itcat.entity.PharmacyManager pharmacyManager) {
        PharmacyManager user = new PharmacyManager();

        user.setUser(packBaseRpcUser(pharmacyManager, RoleEnum.Pharmacy_Manager));

        return user;
    }

//    public MedicalDoctor packMedicalDoctor(top.itcat.entity.MedicalDoctor medicalDoctor) {
//        MedicalDoctor user = new MedicalDoctor();
//
//        user.setUser(packBaseRpcUser(medicalDoctor, RoleEnum.Medical_Doctor));
//        user.setDescription(medicalDoctor.getDescription());
//        user.setTitle(DoctorTitleEnum.findByValue(medicalDoctor.getJobTitle()));
//        return user;
//    }

    public HospitalManager packHospitalManager(top.itcat.entity.HospitalManager hospitalManager) {
        HospitalManager user = new HospitalManager();

        user.setUser(packBaseRpcUser(hospitalManager, RoleEnum.Hospital_Manager));

        return user;
    }

    public AccountClerk packAccountClerk(top.itcat.entity.AccountClerk accountClerk) {
        AccountClerk user = new AccountClerk();

        user.setUser(packBaseRpcUser(accountClerk, RoleEnum.Doctor));

        return user;
    }
}
