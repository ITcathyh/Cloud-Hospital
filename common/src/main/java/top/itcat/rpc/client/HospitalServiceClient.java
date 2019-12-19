package top.itcat.rpc.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import top.itcat.exception.CommonException;
import top.itcat.rpc.service.hospital.*;
import top.itcat.rpc.service.model.BillingCategory;
import top.itcat.rpc.service.model.Department;
import top.itcat.rpc.service.model.NonmedicalCharge;
import top.itcat.rpc.service.model.RegisterationLevel;
import top.itcat.rpc.thrift.ThriftClient;
import top.itcat.rpc.thrift.ThriftUtil;
import top.itcat.rpc.zookeeper.CommonWatcher;
import top.itcat.rpc.zookeeper.ConnectionPool;
import top.itcat.rpc.zookeeper.callback.HospitalServiceCallback;

import java.util.List;

@Slf4j
public class HospitalServiceClient {
    public HospitalServiceClient() {
        this(2);
    }

    public HospitalServiceClient(int instanceNum) {
        String root = "/" + HospitalService.class.getSimpleName();

        try {
            CommonWatcher<HospitalService.Client> watcher = ThriftUtil.initRpc(root, new HospitalServiceCallback(),
                    HospitalService.Client.class.getName(), instanceNum);
            servicePool = watcher.getPool();
        } catch (Exception e) {
            log.error("HospitalServiceClient init err:", e);
        }
    }

    private ConnectionPool<HospitalService.Client> servicePool;

    public GetDepartmentResponse getDepartment(GetDepartmentRequest req) {
        ThriftClient<HospitalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            HospitalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetDepartmentResponse rsp = client.getDepartment(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getDepartment err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public boolean addOrUpdateDepartment(List<Department> entities) {
        ThriftClient<HospitalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            HospitalService.Client client = thriftClient.getClient();

            if (client == null) {
                return false;
            }

            AddOrUpdateDepartmentRequest req = new AddOrUpdateDepartmentRequest(entities);
            AddOrUpdateDepartmentResponse rsp = client.addOrUpdateDepartment(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return true;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateDepartment err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return false;
    }

    public GetBillingCategoryResponse getBillingCategory(GetBillingCategoryRequest req) {
        ThriftClient<HospitalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            HospitalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetBillingCategoryResponse rsp = client.getBillingCategory(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getBillingCategory err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public boolean addOrUpdateBillingCategory(List<BillingCategory> entities) {
        ThriftClient<HospitalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            HospitalService.Client client = thriftClient.getClient();

            if (client == null) {
                return false;
            }

            AddOrUpdateBillingCategoryRequest req = new AddOrUpdateBillingCategoryRequest(entities);
            AddOrUpdateBillingCategoryResponse rsp = client.addOrUpdateBillingCategory(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return true;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateBillingCategory err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return false;
    }

    public GetRegisterationLevelResponse getRegisterationLevel(GetRegisterationLevelRequest req) {
        ThriftClient<HospitalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            HospitalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetRegisterationLevelResponse rsp = client.getRegisterationLevel(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getRegisterationLevel err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public boolean addOrUpdateRegisterationLevel(List<RegisterationLevel> entities) {
        ThriftClient<HospitalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            HospitalService.Client client = thriftClient.getClient();

            if (client == null) {
                return false;
            }

            AddOrUpdateRegisterationLevelRequest req = new AddOrUpdateRegisterationLevelRequest(entities);
            AddOrUpdateRegisterationLevelResponse rsp = client.addOrUpdateRegisterationLevel(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return true;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateRegisterationLevel err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return false;
    }

    public GetNonmedicalChargeResponse getNonmedicalCharge(GetNonmedicalChargeRequest req) {
        ThriftClient<HospitalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            HospitalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetNonmedicalChargeResponse rsp = client.getNonmedicalCharge(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getNonmedicalCharge err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public boolean addOrUpdateNonmedicalCharge(List<NonmedicalCharge> entities) {
        ThriftClient<HospitalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            HospitalService.Client client = thriftClient.getClient();

            if (client == null) {
                return false;
            }

            AddOrUpdateNonmedicalChargeRequest req = new AddOrUpdateNonmedicalChargeRequest(entities);
            AddOrUpdateNonmedicalChargeResponse rsp = client.addOrUpdateNonmedicalCharge(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return true;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateNonmedicalCharge err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return false;
    }

    public GetDepartStatisticalDataResponse getDepartStatistical(GetDepartStatisticalDataRequest req) {
        ThriftClient<HospitalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            HospitalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetDepartStatisticalDataResponse rsp = client.getDepartStatistical(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getDepartStatistical err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetDoctorStatisticalDataResponse getDoctorStatistical(GetDoctorStatisticalDataRequest req) {
        ThriftClient<HospitalService.Client> thriftClient = servicePool.getClientInstance();

        try {
            HospitalService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetDoctorStatisticalDataResponse rsp = client.getDoctorStatistical(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getDoctorStatistical err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }
}