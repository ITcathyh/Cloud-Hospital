package top.itcat.rpc.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itcat.action.ServiceAction;
import top.itcat.entity.*;
import top.itcat.exception.InternalException;
import top.itcat.exception.InvalidParamException;
import top.itcat.exception.UnauthorizedException;
import top.itcat.pack.UserCodeGenerateUtil;
import top.itcat.pack.UserPack;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.service.diagnose.GetRegistrationRequest;
import top.itcat.rpc.service.diagnose.GetRegistrationResponse;
import top.itcat.rpc.service.model.PackedUser;
import top.itcat.rpc.service.model.Registration;
import top.itcat.rpc.service.model.RoleEnum;
import top.itcat.rpc.service.user.*;
import top.itcat.rpc.thrift.ThriftUtil;
import top.itcat.service.*;
import top.itcat.util.EncryUtil;
import top.itcat.util.PatternUtil;

import java.util.*;
import java.util.stream.Collectors;

import static top.itcat.constant.Const.USER_PWD_SALT;


@Service("serviceServer")
@Slf4j
@SuppressWarnings("unchecked")
public class ServiceServer implements UserService.Iface {
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
    private PatientService patientService;
    @Autowired
    private UserPack userPack;
    @Autowired
    private UserCodeGenerateUtil userCodeGenerateUtil;
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;
    @Autowired
    private ServiceAction serviceAction;
    @Autowired
    private PatientWechatSignatureService patientWechatSignatureService;

    @Override
    public MGetDoctorResponse mGetDoctor(MGetDoctorRequest req) throws TException {
        MGetDoctorResponse rsp = new MGetDoctorResponse();
        List<Doctor> users;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1);
        int offset = 0;
        int limit = 100;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        wrapper = wrapper.eq("role", 0);

        if (req.isSetUids()) {
            wrapper = wrapper.in("id", req.getUids());
        }

        if (req.isSetTitle()) {
            wrapper = wrapper.eq("job_title", req.getTitle().getValue());
        }

        if (req.isSetDepartId()) {
            wrapper = wrapper.eq("department_id", req.getDepartId());
        }

        if (req.isSetSearchKey()) {
            wrapper = wrapper.andNew()
                    .like("realname", req.getSearchKey())
                    .or()
                    .like("code", req.getSearchKey())
                    .or()
                    .like("description", req.getSearchKey());
        }

        Page<Doctor> page = null;
        try {
            page = doctorService.selectPage(
                    new Page<>(offset, limit), wrapper);
        } catch (Exception e) {
            log.error("getOutpatientDoctor err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        users = page.getRecords();
        page.setTotal(doctorService.selectCount(wrapper));
        List<top.itcat.rpc.service.model.OutpatientDoctor> packedUsers =
                users.parallelStream().map(userPack::packOutpatientDoctor).collect(Collectors.toList());

        rsp.setUsers(packedUsers);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateDoctorResponse addOrUpdateDoctor(AddOrUpdateDoctorRequest req) throws TException {
        AddOrUpdateDoctorResponse rsp = new AddOrUpdateDoctorResponse();
        List<Doctor> users = req.getUsers()
                .parallelStream()
                .map(Doctor::convertOutpatientDoctor)
                .filter(Objects::nonNull)
                .peek(i -> i.setRole(0))
                .collect(Collectors.toList());

        if (users.get(0).getId() == null) {
            for (Doctor user : users) {
                user.setCode(userCodeGenerateUtil
                        .generateUserCode(RoleEnum.Doctor));
                user.setPassword(EncryUtil.SHA1(user.getCode(), USER_PWD_SALT));
            }
        }

        try {
            doctorService.insertOrUpdateBatch(users);
        } catch (Exception e) {
            log.error("addOrUpdateOutpatientDoctor err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
//        rsp.setUsers(users
//                .parallelStream()
//                .map(userPack::packOutpatientDoctor)
//                .collect(Collectors.toList()));
        return rsp;
    }

    @Override
    public MGetMedicalDoctorResponse mGetMedicalDoctor(MGetMedicalDoctorRequest mGetMedicalDoctorRequest) throws TException {
        MGetMedicalDoctorResponse rsp = new MGetMedicalDoctorResponse();
        List<Doctor> users;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1);
        int offset = 0;
        int limit = 100;

        if (mGetMedicalDoctorRequest.isSetOffset()) {
            offset = mGetMedicalDoctorRequest.getOffset();
        }

        if (mGetMedicalDoctorRequest.isSetLimit()) {
            limit = mGetMedicalDoctorRequest.getLimit();
        }

        if (mGetMedicalDoctorRequest.isSetUids()) {
            wrapper = wrapper.in("id", mGetMedicalDoctorRequest.getUids());
        }

        wrapper = wrapper.eq("role", 1);

        if (mGetMedicalDoctorRequest.isSetDepartId()) {
            wrapper = wrapper.eq("department_id", mGetMedicalDoctorRequest.getDepartId());
        }

        if (mGetMedicalDoctorRequest.isSetTitle()) {
            wrapper = wrapper.eq("job_title", mGetMedicalDoctorRequest.getTitle().getValue());
        }

        if (mGetMedicalDoctorRequest.isSetSearchKey()) {
            wrapper = wrapper.andNew()
                    .like("realname", mGetMedicalDoctorRequest.getSearchKey())
                    .or()
                    .like("code", mGetMedicalDoctorRequest.getSearchKey())
                    .or()
                    .like("description", mGetMedicalDoctorRequest.getSearchKey());
        }

        Page<Doctor> page;

        try {
            page = doctorService.selectPage(
                    new Page<>(offset, limit), wrapper);
            page.setTotal(doctorService.selectCount(wrapper));
        } catch (Exception e) {
            log.error("medicalDoctorService.selectPage err,", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        users = page.getRecords();
        List<top.itcat.rpc.service.model.MedicalDoctor> packedUsers =
                users.parallelStream().map(userPack::packMedicalDoctor).collect(Collectors.toList());

        rsp.setUsers(packedUsers);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateMedicalDoctorResponse addOrUpdateMedicalDoctor(AddOrUpdateMedicalDoctorRequest addOrUpdateMedicalDoctorRequest) throws TException {
        AddOrUpdateMedicalDoctorResponse rsp = new AddOrUpdateMedicalDoctorResponse();
        List<Doctor> users = addOrUpdateMedicalDoctorRequest.getUsers()
                .parallelStream()
                .map(Doctor::convertMedicalDoctor)
                .filter(Objects::nonNull)
                .peek(i -> i.setRole(1))
                .collect(Collectors.toList());

        if (users.get(0).getId() == null) {
            for (Doctor user : users) {
                user.setCode(userCodeGenerateUtil
                        .generateUserCode(RoleEnum.Medical_Doctor));
                user.setPassword(EncryUtil.SHA1(user.getCode(), USER_PWD_SALT));
            }
        }

        try {
            doctorService.insertOrUpdateBatch(users);
        } catch (Exception e) {
            log.error("addOrUpdateMedicalDoctor err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
//        rsp.setUsers(users
//                .parallelStream()
//                .map(userPack::packMedicalDoctor)
//                .collect(Collectors.toList()));
        return rsp;
    }

    @Override
    public MGetPatientResponse mGetPatient(MGetPatientRequest req) throws TException {
        MGetPatientResponse rsp = new MGetPatientResponse();
        List<top.itcat.rpc.service.model.Patient> patientList = null;

        try {
            List<String> idNums = new ArrayList<>(1);

            if (req.getSearchKey() != null && PatternUtil.isNumeric(req.getSearchKey())) {
                GetRegistrationRequest rpcReq = new GetRegistrationRequest();
                rpcReq.setSearchKey(req.getSearchKey());
                GetRegistrationResponse rpcRsp = diagnoseServiceClient.getRegistration(rpcReq);

                if (rpcRsp == null) {
                    rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
                    return rsp;
                }

                if (rpcRsp.getBeanListSize() > 0) {
                    idNums = rpcRsp.getBeanList()
                            .parallelStream()
                            .map(Registration::getIdentityCardNo)
                            .collect(Collectors.toList());
                }
            }

            if (req.isSetIdNums()) {
                idNums.addAll(req.getIdNums());
            }

            idNums.add(req.getSearchKey());
            patientList = patientService.selectList(new EntityWrapper<Patient>()
                    .in("identity_card_no", idNums)
                    .or().eq("patient_name", req.getSearchKey())
                    .or().eq("phone", req.getSearchKey()))
                    .parallelStream()
                    .map(userPack::packPatient)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("mGetPatient err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setUsers(patientList);
        rsp.setTotal(patientList.size());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdatePatientResponse addOrUpdatePatient(AddOrUpdatePatientRequest req) throws TException {
        AddOrUpdatePatientResponse rsp = new AddOrUpdatePatientResponse();
        List<Patient> patients = req.getUsers()
                .parallelStream()
                .map(Patient::convertPatient)
                .collect(Collectors.toList());

        try {
            for (Patient patient : patients) {
                Patient old = patientService.selectOne(new EntityWrapper<Patient>()
                        .eq("identity_card_no", patient.getIdentityCardNo())
                        .eq("valid", 1)
                        .setSqlSelect("id"));

                if (old != null) {
                    patient.setId(old.getId());
                }
            }

            patientService.insertOrUpdateBatch(patients);
        } catch (Exception e) {
            log.error("addOrUpdatePatient err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public MGetTollCollectorResponse mGetTollCollector(MGetTollCollectorRequest req) throws TException {
        MGetTollCollectorResponse rsp = new MGetTollCollectorResponse();
        List<TollCollector> users;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1);
        int offset = 0;
        int limit = 100;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetUids()) {
            wrapper = wrapper.in("id", req.getUids());
        }

//        if (req.isSetDepartId()) {
//            wrapper = wrapper.eq("department_id", req.getDepartId());
//        }

        if (req.isSetSearchKey()) {
            wrapper = wrapper.andNew()
                    .like("realname", req.getSearchKey())
                    .or()
                    .like("code", req.getSearchKey());
        }

        Page<TollCollector> page = null;
        try {
            page = tollCollectorService.selectPage(
                    new Page<>(offset, limit), wrapper);
        } catch (Exception e) {
            log.error("getTollCollector err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        users = page.getRecords();
        page.setTotal(tollCollectorService.selectCount(wrapper));
        List<top.itcat.rpc.service.model.TollCollector> packedUsers =
                users.parallelStream().map(userPack::packTollCollector).collect(Collectors.toList());

        rsp.setUsers(packedUsers);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateTollCollectorResponse addOrUpdateTollCollector(AddOrUpdateTollCollectorRequest req) throws
            TException {
        AddOrUpdateTollCollectorResponse rsp = new AddOrUpdateTollCollectorResponse();
        List<TollCollector> users = req.getUsers()
                .parallelStream()
                .map(TollCollector::convertTollCollector)
                .collect(Collectors.toList());

        if (users.get(0).getId() == null) {
            for (TollCollector user : users) {
                user.setCode(userCodeGenerateUtil
                        .generateUserCode(RoleEnum.Toll_Collector));
                user.setPassword(EncryUtil.SHA1(user.getCode(), USER_PWD_SALT));
            }
        }

        try {
            tollCollectorService.insertOrUpdateBatch(users);
        } catch (Exception e) {
            log.error("addOrUpdateTollCollector err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
//        rsp.setUsers(users
//                .parallelStream()
//                .map(userPack::packTollCollector)
//                .collect(Collectors.toList()));
        return rsp;
    }

    @Override
    public MGetAccountClerkResponse mGetAccountClerk(MGetAccountClerkRequest req) throws TException {
        MGetAccountClerkResponse rsp = new MGetAccountClerkResponse();
        List<AccountClerk> users;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1);
        int offset = 0;
        int limit = 100;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetUids()) {
            wrapper = wrapper.in("id", req.getUids());
        }

//        if (req.isSetDepartId()) {
//            wrapper = wrapper.eq("department_id", req.getDepartId());
//        }

        if (req.isSetSearchKey()) {
            wrapper = wrapper.andNew()
                    .like("realname", req.getSearchKey())
                    .or()
                    .like("code", req.getSearchKey());
        }

        Page<AccountClerk> page = null;
        try {
            page = accountClerkService.selectPage(
                    new Page<>(offset, limit), wrapper);
        } catch (Exception e) {
            log.error("getAccountClerk err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        users = page.getRecords();
        page.setTotal(accountClerkService.selectCount(wrapper));
        List<top.itcat.rpc.service.model.AccountClerk> packedUsers =
                users.parallelStream().map(userPack::packAccountClerk).collect(Collectors.toList());

        rsp.setUsers(packedUsers);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateAccountClerkResponse addOrUpdateAccountClerk(AddOrUpdateAccountClerkRequest req) throws
            TException {
        AddOrUpdateAccountClerkResponse rsp = new AddOrUpdateAccountClerkResponse();
        List<AccountClerk> users = req.getUsers()
                .parallelStream()
                .map(AccountClerk::convertAccountClerk)
                .collect(Collectors.toList());

        if (users.get(0).getId() == null) {
            for (AccountClerk user : users) {
                user.setCode(userCodeGenerateUtil
                        .generateUserCode(RoleEnum.Account_Clerk));
                user.setPassword(EncryUtil.SHA1(user.getCode(), USER_PWD_SALT));
            }
        }

        try {
            accountClerkService.insertOrUpdateBatch(users);
        } catch (Exception e) {
            log.error("addOrUpdateAccountClerk err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
//        rsp.setUsers(users
//                .parallelStream()
//                .map(userPack::packAccountClerk)
//                .collect(Collectors.toList()));
        return rsp;
    }

    @Override
    public MGetHospitalManagerResponse mGetHospitalManager(MGetHospitalManagerRequest req) throws TException {
        MGetHospitalManagerResponse rsp = new MGetHospitalManagerResponse();
        List<HospitalManager> users;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1);
        int offset = 0;
        int limit = 100;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetUids()) {
            wrapper = wrapper.in("id", req.getUids());
        }

//        if (req.isSetDepartId()) {
//            wrapper = wrapper.eq("department_id", req.getDepartId());
//        }

        if (req.isSetSearchKey()) {
            wrapper = wrapper.andNew()
                    .like("realname", req.getSearchKey())
                    .or()
                    .like("code", req.getSearchKey());
        }

        Page<HospitalManager> page = null;
        try {
            page = hospitalManagerService.selectPage(
                    new Page<>(offset, limit), wrapper);
        } catch (Exception e) {
            log.error("getHospitalManager err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        users = page.getRecords();
        page.setTotal(hospitalManagerService.selectCount(wrapper));
        List<top.itcat.rpc.service.model.HospitalManager> packedUsers =
                users.parallelStream().map(userPack::packHospitalManager).collect(Collectors.toList());

        rsp.setUsers(packedUsers);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdateHospitalManagerResponse addOrUpdateHospitalManager(AddOrUpdateHospitalManagerRequest req) throws
            TException {
        AddOrUpdateHospitalManagerResponse rsp = new AddOrUpdateHospitalManagerResponse();
        List<HospitalManager> users = req.getUsers()
                .parallelStream()
                .map(HospitalManager::convertHospitalManager)
                .collect(Collectors.toList());

        if (users.get(0).getId() == null) {
            for (HospitalManager user : users) {
                user.setCode(userCodeGenerateUtil
                        .generateUserCode(RoleEnum.Hospital_Manager));
                user.setPassword(EncryUtil.SHA1(user.getCode(), USER_PWD_SALT));
            }
        }

        try {
            hospitalManagerService.insertOrUpdateBatch(users);
        } catch (Exception e) {
            log.error("addOrUpdateHospitalManager err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
//        rsp.setUsers(users
//                .parallelStream()
//                .map(userPack::packHospitalManager)
//                .collect(Collectors.toList()));
        return rsp;
    }

    @Override
    public MGetPharmacyManagerResponse mGetPharmacyManager(MGetPharmacyManagerRequest req) throws TException {
        MGetPharmacyManagerResponse rsp = new MGetPharmacyManagerResponse();
        List<PharmacyManager> users;
        Wrapper wrapper = new EntityWrapper().eq("valid", 1);
        int offset = 0;
        int limit = 100;

        if (req.isSetOffset()) {
            offset = req.getOffset();
        }

        if (req.isSetLimit()) {
            limit = req.getLimit();
        }

        if (req.isSetUids()) {
            wrapper = wrapper.in("id", req.getUids());
        }

//        if (req.isSetDepartId()) {
//            wrapper = wrapper.eq("department_id", req.getDepartId());
//        }

        if (req.isSetSearchKey()) {
            wrapper = wrapper.andNew()
                    .like("realname", req.getSearchKey())
                    .or()
                    .like("code", req.getSearchKey());
        }

        Page<PharmacyManager> page = null;
        try {
            page = pharmacyManagerService.selectPage(
                    new Page<>(offset, limit), wrapper);
        } catch (Exception e) {
            log.error("getPharmacyManager err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }
        users = page.getRecords();
        page.setTotal(pharmacyManagerService.selectCount(wrapper));
        List<top.itcat.rpc.service.model.PharmacyManager> packedUsers =
                users.parallelStream().map(userPack::packPharmacyManager).collect(Collectors.toList());

        rsp.setUsers(packedUsers);
        rsp.setTotal((int) page.getTotal());
        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public AddOrUpdatePharmacyManagerResponse addOrUpdatePharmacyManager(AddOrUpdatePharmacyManagerRequest req) throws
            TException {
        AddOrUpdatePharmacyManagerResponse rsp = new AddOrUpdatePharmacyManagerResponse();
        List<PharmacyManager> users = req.getUsers()
                .parallelStream()
                .map(PharmacyManager::convertPharmacyManager)
                .collect(Collectors.toList());

        if (users.get(0).getId() == null) {
            for (PharmacyManager user : users) {
                user.setCode(userCodeGenerateUtil
                        .generateUserCode(RoleEnum.Pharmacy_Manager));
                user.setPassword(EncryUtil.SHA1(user.getCode(), USER_PWD_SALT));
            }
        }

        try {
            pharmacyManagerService.insertOrUpdateBatch(users);
        } catch (Exception e) {
            log.error("addOrUpdatePharmacyManager err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
//        rsp.setUsers(users
//                .parallelStream()
//                .map(userPack::packPharmacyManager)
//                .collect(Collectors.toList()));
        return rsp;
    }

    @Override
    public GetUserByUsernameAndPasswordResponse getUserByUsernameAndPassword(GetUserByUsernameAndPasswordRequest
                                                                                     req) throws TException {
        GetUserByUsernameAndPasswordResponse rsp = new GetUserByUsernameAndPasswordResponse();
        PackedUser user = null;
        Wrapper wrapper;
        RoleEnum role = userCodeGenerateUtil.getRole(req.getUsername().substring(0, 2));

        if (role == null) {
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InvalidParamException()));
            return rsp;
        }

        if (role == RoleEnum.Patient) {
            wrapper = new EntityWrapper().
                    eq("username", req.getUsername()).
                    eq("password", EncryUtil.SHA1(req.getPassword(), USER_PWD_SALT));
        } else {
            wrapper = new EntityWrapper().
                    eq("code", req.getUsername()).
                    eq("password", EncryUtil.SHA1(req.getPassword(), USER_PWD_SALT));

            if (role == RoleEnum.Doctor) {
                wrapper = wrapper.eq("role", 0);
            } else if (role == RoleEnum.Medical_Doctor) {
                wrapper = wrapper.eq("role", 1);
            }
        }

        switch (role) {
            case Doctor:
                user = userPack.packUser(doctorService.selectOne(wrapper), RoleEnum.Doctor);
                user = userPack.packDoctor(user);
                break;
            case Account_Clerk:
                user = userPack.packUser(accountClerkService.selectOne(wrapper), RoleEnum.Account_Clerk);
                break;
            case Patient:
                break;
            case Medical_Doctor:
                user = userPack.packUser(doctorService.selectOne(wrapper), RoleEnum.Medical_Doctor);
                user = userPack.packDoctor(user);
                break;
            case Toll_Collector:
                user = userPack.packUser(tollCollectorService.selectOne(wrapper), RoleEnum.Toll_Collector);
                break;
            case Hospital_Manager:
                user = userPack.packUser(hospitalManagerService.selectOne(wrapper), RoleEnum.Hospital_Manager);
                break;
            case Pharmacy_Manager:
                user = userPack.packUser(pharmacyManagerService.selectOne(wrapper), RoleEnum.Pharmacy_Manager);
                break;
            default:
                rsp.setBaseResp(ThriftUtil.getBaseResp(new InvalidParamException()));
                return rsp;
        }

        if (user != null) {
            rsp.setUser(user);
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        return rsp;
    }

    @Override
    public GetUserIdsResponse getUserIds(GetUserIdsRequest req) throws TException {
        GetUserIdsResponse rsp = new GetUserIdsResponse();
        Map<RoleEnum, List<Long>> idsMap = new HashMap<>(2);

        try {
            if (!req.isSetRoles() || req.getRoles().contains(RoleEnum.Doctor)) {
                List<Long> ids = doctorService.
                        selectOutpatientDoctorIds(req.isSetDepartId() ? req.getDepartId() : null);
                idsMap.put(RoleEnum.Doctor, ids);
            }

            if (!req.isSetRoles() || req.getRoles().contains(RoleEnum.Medical_Doctor)) {
                List<Long> ids = doctorService.
                        selectMedicalDoctorIds(req.isSetDepartId() ? req.getDepartId() : null);
                idsMap.put(RoleEnum.Medical_Doctor, ids);
            }
        } catch (Exception e) {
            log.error("getUserIds err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
            return rsp;
        }

        rsp.setBaseResp(ThriftUtil.getBaseResp());
        rsp.setIds(idsMap);
        return rsp;
    }

    @Override
    public EditPwdResponse editPwd(EditPwdRequest req) throws TException {
        RoleEnum role = userCodeGenerateUtil.getRole(req.getCode().substring(0, 2));
        EditPwdResponse rsp = new EditPwdResponse();
        rsp.setBaseResp(ThriftUtil.getBaseResp());

        try {
            if (!serviceAction.editPwd(role, req.getId(), req.getOldPwd(), req.getPwd())) {
                rsp.setBaseResp(ThriftUtil.getBaseResp(new UnauthorizedException()));
            }
        } catch (InvalidParamException e) {
            log.info("editPwd wrong:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(e));
        } catch (Exception e) {
            log.error("editPwd err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
        }

        return rsp;
    }

    @Override
    public AddOrUpdatePatientWechatSignatureResponse addOrUpdatePatientWechatSignature(AddOrUpdatePatientWechatSignatureRequest req) throws TException {
        AddOrUpdatePatientWechatSignatureResponse rsp = new AddOrUpdatePatientWechatSignatureResponse();
        rsp.setBaseResp(ThriftUtil.getBaseResp());

        try {
            serviceAction.addOrUpdatePatientWechatSignature(req);
        } catch (Exception e) {
            log.error("addOrUpdatePatientWechatSignature err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
        }

        return rsp;
    }

    @Override
    public GetPatientWechatSignatureResponse getPatientWechatSignature(GetPatientWechatSignatureRequest req) throws TException {
        GetPatientWechatSignatureResponse rsp = new GetPatientWechatSignatureResponse();
        rsp.setBaseResp(ThriftUtil.getBaseResp());

        try {
            top.itcat.rpc.service.model.PatientWechatSignature wechatSignature = PatientWechatSignature.convertRPCBean(patientWechatSignatureService.selectOne(new EntityWrapper<PatientWechatSignature>()
                    .eq("signature", req.getSearchKey())
                    .eq("valid", 1)));
            Patient patient = patientService.selectOne(new EntityWrapper<Patient>()
                    .eq("identity_card_no", wechatSignature.getIdentityCardNo())
                    .eq("valid", 1));

            if (patient != null) {
                rsp.setInfo(userPack.packPatient(patient));
            }

            rsp.setSignature(wechatSignature);
        } catch (Exception e) {
            log.error("getPatientWechatSignature err:", e);
            rsp.setBaseResp(ThriftUtil.getBaseResp(new InternalException()));
        }

        return rsp;
    }

    @Override
    public AddOrUpdatePatientAccountResponse addOrUpdatePatientAccount(AddOrUpdatePatientAccountRequest addOrUpdatePatientAccountRequest) throws TException {
        return null;
    }

    @Override
    public GetPatientAccountResponse getPatientAccount(GetPatientAccountRequest getPatientAccountRequest) throws TException {
        return null;
    }
}
