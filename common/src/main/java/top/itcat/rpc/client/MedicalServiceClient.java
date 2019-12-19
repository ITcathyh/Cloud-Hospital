package top.itcat.rpc.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import top.itcat.exception.CommonException;
import top.itcat.exception.InvalidParamException;
import top.itcat.exception.NotEnoughException;
import top.itcat.rpc.service.medical.*;
import top.itcat.rpc.thrift.ThriftClient;
import top.itcat.rpc.thrift.ThriftUtil;
import top.itcat.rpc.zookeeper.CommonWatcher;
import top.itcat.rpc.zookeeper.ConnectionPool;
import top.itcat.rpc.zookeeper.callback.MedicalServiceCallBack;

/**
 * Medical服务Client
 * 封装RPC请求
 *
 * @author ITcathyh
 */
@Slf4j
public class MedicalServiceClient {
    private ConnectionPool<MedicalService.Client> servicePool;

//    public MedicalServerClient() {
//        String root = "/" + MedicalService.class.getSimpleName();
//
//        try {
//            CommonWatcher<MedicalService.Client> watcher = ThriftUtil.initRpc(root, new MedicalServiceCallBack(),
//                    MedicalService.Client.class.getName());
//            servicePool = watcher.getPool();
//        } catch (Exception e) {
//            log.error("MedicalServiceClient init err:", e);
//        }
//    }

    public MedicalServiceClient(int instanceNum) {
        String root = "/" + MedicalService.class.getSimpleName();

        try {
            CommonWatcher<MedicalService.Client> watcher = ThriftUtil.initRpc(root, new MedicalServiceCallBack(),
                    MedicalService.Client.class.getName(), instanceNum);
            servicePool = watcher.getPool();
        } catch (Exception e) {
            log.error("MedicalServiceClient init err:", e);
        }
    }

    public MedicalServiceClient() {
        this(2);
    }

    public AddOrUpdatePrescriptionGroupResponse addOrUpdatePrescriptionGroup(AddOrUpdatePrescriptionGroupRequest req) {
        ThriftClient<MedicalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            MedicalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdatePrescriptionGroupResponse rsp = client.addOrUpdatePrescriptionGroup(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdatePrescriptionGroup err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetPrescriptionGroupResponse getPrescriptionGroup(GetPrescriptionGroupRequest req) {
        ThriftClient<MedicalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            MedicalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetPrescriptionGroupResponse rsp = client.getPrescriptionGroup(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getPrescriptionGroup err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdatePrescriptionResponse addOrUpdatePrescription(AddOrUpdatePrescriptionRequest req) {
        ThriftClient<MedicalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            MedicalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdatePrescriptionResponse rsp = client.addOrUpdatePrescription(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (NotEnoughException | InvalidParamException e) {
            throw e;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdatePrescription err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetPrescriptionResponse getPrescription(GetPrescriptionRequest req) {
        ThriftClient<MedicalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            MedicalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetPrescriptionResponse rsp = client.getPrescription(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getPrescription err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetPrescriptionHerbResponse getPrescriptionHerb(GetPrescriptionHerbRequest req) {
        ThriftClient<MedicalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            MedicalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetPrescriptionHerbResponse rsp = client.getPrescriptionHerb(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getPrescriptionHerb err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }


    public AddOrUpdatePrescriptionHerbResponse addOrUpdatePrescriptionHerb(AddOrUpdatePrescriptionHerbRequest req) {
        ThriftClient<MedicalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            MedicalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdatePrescriptionHerbResponse rsp = client.addOrUpdatePrescriptionHerb(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (NotEnoughException | InvalidParamException e) {
            throw e;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdatePrescriptionHerb err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public DispenseMedicineResponse dispenseMedicine(DispenseMedicineRequest req) {
        ThriftClient<MedicalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            MedicalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            DispenseMedicineResponse rsp = client.dispenseMedicine(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("dispenseMedicine err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public DrugRepercussionResponse drugRepercussion(DrugRepercussionRequest req) {
        ThriftClient<MedicalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            MedicalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            DrugRepercussionResponse rsp = client.drugRepercussion(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (InvalidParamException e) {
            throw e;
        }catch (CommonException | TException | NullPointerException e) {
            log.error("drugRepercussion err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateMedicineResponse addOrUpdateMedicine(AddOrUpdateMedicineRequest req) {
        ThriftClient<MedicalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            MedicalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdateMedicineResponse rsp = client.addOrUpdateMedicine(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateMedicine err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetMedicineResponse getMedicine(GetMedicineRequest req) {
        ThriftClient<MedicalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            MedicalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetMedicineResponse rsp = client.getMedicine(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getMedicine err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetPrescriptionItemResponse getPrescriptionItem(GetPrescriptionItemRequest req) {
        ThriftClient<MedicalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            MedicalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetPrescriptionItemResponse rsp = client.getPrescriptionItem(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getPrescriptionItem err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateCommonlyUsedMedicineResponse addOrUpdateCommonlyUsedMedicine(AddOrUpdateCommonlyUsedMedicineRequest req) {
        ThriftClient<MedicalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            MedicalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdateCommonlyUsedMedicineResponse rsp = client.addOrUpdateCommonlyUsedMedicine(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateCommonlyUsedMedicine err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetCommonlyUsedMedicineResponse getCommonlyUsedMedicine(GetCommonlyUsedMedicineRequest req) {
        ThriftClient<MedicalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            MedicalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetCommonlyUsedMedicineResponse rsp = client.getCommonlyUsedMedicine(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getCommonlyUsedMedicine err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }
}