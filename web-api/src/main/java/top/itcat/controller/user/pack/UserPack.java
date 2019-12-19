package top.itcat.controller.user.pack;

import org.springframework.stereotype.Service;
import top.itcat.entity.user.BaseUser;
import top.itcat.rpc.service.model.*;

@Service
public class UserPack {
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

    public OutpatientDoctor packOutpatientDoctor(top.itcat.entity.user.OutpatientDoctor oriDr) {
        OutpatientDoctor user = new OutpatientDoctor();

        user.setUser(packBaseRpcUser(oriDr, RoleEnum.Doctor));
        user.setDescription(oriDr.getDescription());
        user.setTitle(DoctorTitleEnum.findByValue(oriDr.getJobTitle()));
        user.setInSchedual(oriDr.getInScheduling() == 1);

        return user;
    }

    public TollCollector packTollCollector(top.itcat.entity.user.TollCollector tollCollector) {
        TollCollector user = new TollCollector();

        user.setUser(packBaseRpcUser(tollCollector, RoleEnum.Toll_Collector));

        return user;
    }

    public PharmacyManager packPharmacyManager(top.itcat.entity.user.PharmacyManager pharmacyManager) {
        PharmacyManager user = new PharmacyManager();

        user.setUser(packBaseRpcUser(pharmacyManager, RoleEnum.Pharmacy_Manager));

        return user;
    }

    public MedicalDoctor packMedicalDoctor(top.itcat.entity.user.MedicalDoctor medicalDoctor) {
        MedicalDoctor user = new MedicalDoctor();

        user.setUser(packBaseRpcUser(medicalDoctor, RoleEnum.Medical_Doctor));
        user.setDescription(medicalDoctor.getDescription());
        user.setTitle(DoctorTitleEnum.findByValue(medicalDoctor.getJobTitle()));
        return user;
    }

    public HospitalManager packHospitalManagerr(top.itcat.entity.user.HospitalManager hospitalManager) {
        HospitalManager user = new HospitalManager();

        user.setUser(packBaseRpcUser(hospitalManager, RoleEnum.Hospital_Manager));

        return user;
    }

    public AccountClerk packAccountClerk(top.itcat.entity.user.AccountClerk accountClerk) {
        AccountClerk user = new AccountClerk();

        user.setUser(packBaseRpcUser(accountClerk, RoleEnum.Account_Clerk));

        return user;
    }

    private User packBaseRpcUser(BaseUser oriUser, RoleEnum role) {
        User user = new User();

        if (oriUser.getId() != null) {
            user.setId(oriUser.getId());
        }

        user.setCode(oriUser.getCode());
        user.setRole(role);

        if (oriUser.getDepartmentId() != null) {
            user.setDepartId(oriUser.getDepartmentId());
        }

        user.setRealName(oriUser.getRealname());

        return user;
    }
}
