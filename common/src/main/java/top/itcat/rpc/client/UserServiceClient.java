package top.itcat.rpc.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import top.itcat.exception.CommonException;
import top.itcat.exception.InvalidParamException;
import top.itcat.rpc.service.model.*;
import top.itcat.rpc.service.user.*;
import top.itcat.rpc.thrift.ThriftClient;
import top.itcat.rpc.thrift.ThriftUtil;
import top.itcat.rpc.zookeeper.CommonWatcher;
import top.itcat.rpc.zookeeper.ConnectionPool;
import top.itcat.rpc.zookeeper.callback.UserServiceCallBack;

import java.util.List;

/**
 * User服务Client
 * 封装RPC请求
 *
 * @author ITcathyh
 */
@Slf4j
public class UserServiceClient {
//    public UserServiceClient() {
//        String root = "/" + UserService.class.getSimpleName();
//
//        try {
//            CommonWatcher<UserService.Client> watcher = ThriftUtil.initRpc(root, new UserServiceCallBack(),
//                    UserService.Client.class.getName());
//            servicePool = watcher.getPool();
//        } catch (Exception e) {
//            log.error("UserServiceClient init err:", e);
//        }
//    }

    public UserServiceClient(int instanceNum) {
        String root = "/" + UserService.class.getSimpleName();

        try {
            CommonWatcher<UserService.Client> watcher = ThriftUtil.initRpc(root, new UserServiceCallBack(),
                    UserService.Client.class.getName(), instanceNum);
            servicePool = watcher.getPool();
        } catch (Exception e) {
            log.error("UserServiceClient init err:", e);
        }
    }

    public UserServiceClient() {
        this(2);
    }

    private ConnectionPool<UserService.Client> servicePool;

    public MGetDoctorResponse mGetDoctor(MGetDoctorRequest req) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            MGetDoctorResponse rsp = client.mGetDoctor(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("mGetDoctor err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public boolean addOrUpdateDoctor(List<OutpatientDoctor> users) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return false;
            }

            AddOrUpdateDoctorRequest req = new AddOrUpdateDoctorRequest();
            req.setUsers(users);
            AddOrUpdateDoctorResponse rsp = client.addOrUpdateDoctor(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return true;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateDoctor err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return false;
    }

    public MGetAccountClerkResponse mGetAccountClerk(MGetAccountClerkRequest req) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            MGetAccountClerkResponse rsp = client.mGetAccountClerk(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("mGetAccountClerk err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public boolean addOrUpdateAccountClerk(List<AccountClerk> users) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return false;
            }

            AddOrUpdateAccountClerkRequest req = new AddOrUpdateAccountClerkRequest();
            req.setUsers(users);
            AddOrUpdateAccountClerkResponse rsp = client.addOrUpdateAccountClerk(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return true;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateAccountClerk err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return false;
    }

    public MGetMedicalDoctorResponse mGetMedicalDoctor(MGetMedicalDoctorRequest req) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            MGetMedicalDoctorResponse rsp = client.mGetMedicalDoctor(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("mGetMedicalDoctor err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public boolean addOrUpdateMedicalDoctor(List<MedicalDoctor> users) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return false;
            }

            AddOrUpdateMedicalDoctorRequest req = new AddOrUpdateMedicalDoctorRequest();
            req.setUsers(users);
            AddOrUpdateMedicalDoctorResponse rsp = client.addOrUpdateMedicalDoctor(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return true;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateMedicalDoctor err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return false;
    }

    public MGetTollCollectorResponse mGetTollCollector(MGetTollCollectorRequest req) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            MGetTollCollectorResponse rsp = client.mGetTollCollector(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("mGetTollCollector err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public boolean addOrUpdateTollCollector(List<TollCollector> users) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return false;
            }

            AddOrUpdateTollCollectorRequest req = new AddOrUpdateTollCollectorRequest();
            req.setUsers(users);
            AddOrUpdateTollCollectorResponse rsp = client.addOrUpdateTollCollector(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return true;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateTollCollector err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return false;
    }

    public MGetHospitalManagerResponse mGetHospitalManager(MGetHospitalManagerRequest req) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            MGetHospitalManagerResponse rsp = client.mGetHospitalManager(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("mGetHospitalManager err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public boolean addOrUpdateHospitalManager(List<HospitalManager> users) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return false;
            }

            AddOrUpdateHospitalManagerRequest req = new AddOrUpdateHospitalManagerRequest();
            req.setUsers(users);
            AddOrUpdateHospitalManagerResponse rsp = client.addOrUpdateHospitalManager(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return true;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateHospitalManager err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return false;
    }

    public MGetPharmacyManagerResponse mGetPharmacyManager(MGetPharmacyManagerRequest req) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            MGetPharmacyManagerResponse rsp = client.mGetPharmacyManager(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("mGetPharmacyManager err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public boolean addOrUpdatePharmacyManager(List<PharmacyManager> users) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return false;
            }

            AddOrUpdatePharmacyManagerRequest req = new AddOrUpdatePharmacyManagerRequest();
            req.setUsers(users);
            AddOrUpdatePharmacyManagerResponse rsp = client.addOrUpdatePharmacyManager(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return true;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdatePharmacyManager err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return false;
    }

    public PackedUser getUserByUsernameAndPassword(String username, String password, RoleEnum role) {
        GetUserByUsernameAndPasswordRequest request = new GetUserByUsernameAndPasswordRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setRole(role);

        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetUserByUsernameAndPasswordResponse rsp = client.getUserByUsernameAndPassword(request);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp.getUser();
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getUserByUsernameAndPassword err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetUserIdsResponse getUserIds(GetUserIdsRequest req) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetUserIdsResponse rsp = client.getUserIds(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getUserIds err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public MGetPatientResponse mGetPatient(MGetPatientRequest req) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            MGetPatientResponse rsp = client.mGetPatient(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("mGetPatient err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdatePatientResponse addOrUpdatePatient(AddOrUpdatePatientRequest req) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdatePatientResponse rsp = client.addOrUpdatePatient(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdatePatient err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public EditPwdResponse editPwd(EditPwdRequest req) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();
        EditPwdResponse rsp = null;

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            rsp = client.editPwd(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (InvalidParamException e) {
            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("editPwd err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdatePatientWechatSignatureResponse addOrUpdatePatientWechatSignature(AddOrUpdatePatientWechatSignatureRequest req) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdatePatientWechatSignatureResponse rsp = client.addOrUpdatePatientWechatSignature(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdatePatientWechatSignature err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetPatientWechatSignatureResponse getPatientWechatSignature(GetPatientWechatSignatureRequest req) {
        ThriftClient<UserService.Client> thriftClient = servicePool.getClientInstance();

        try {
            UserService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetPatientWechatSignatureResponse rsp = client.getPatientWechatSignature(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getPatientWechatSignature err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }
}
