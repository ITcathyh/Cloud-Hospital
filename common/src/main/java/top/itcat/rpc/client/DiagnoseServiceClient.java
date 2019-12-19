package top.itcat.rpc.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import top.itcat.exception.CommonException;
import top.itcat.exception.EmptyResultException;
import top.itcat.exception.InvalidParamException;
import top.itcat.exception.NotEnoughException;
import top.itcat.rpc.service.diagnose.*;
import top.itcat.rpc.thrift.ThriftClient;
import top.itcat.rpc.thrift.ThriftUtil;
import top.itcat.rpc.zookeeper.CommonWatcher;
import top.itcat.rpc.zookeeper.ConnectionPool;
import top.itcat.rpc.zookeeper.callback.DiagnoseServiceCallBack;

/**
 * Diagnose服务Client
 * 封装RPC请求
 *
 * @author ITcathyh
 */
@Slf4j
public class DiagnoseServiceClient {
    private ConnectionPool<DiagnoseService.Client> servicePool;

    public DiagnoseServiceClient(int instanceNum) {
        String root = "/" + DiagnoseService.class.getSimpleName();

        try {
            CommonWatcher<DiagnoseService.Client> watcher = ThriftUtil.initRpc(root, new DiagnoseServiceCallBack(),
                    DiagnoseService.Client.class.getName(), instanceNum);
            servicePool = watcher.getPool();
        } catch (Exception e) {
            log.error("DiagnoseServiceClient init err:", e);
        }
    }

    public DiagnoseServiceClient() {
        this(2);
    }

    public GetFirstDiagDirResponse getFirstDiagDir(GetFirstDiagDirRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetFirstDiagDirResponse rsp = client.getFirstDiagDir(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getFirstDiagDir err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateFirstDiagDirResponse addOrUpdateFirstDiagDir(AddOrUpdateFirstDiagDirRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdateFirstDiagDirResponse rsp = client.addOrUpdateFirstDiagDir(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (NotEnoughException e) {
            throw e;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateFirstDiagDir err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public CancelRegistrationResponse cancelRegistration(CancelRegistrationRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            CancelRegistrationResponse rsp = client.cancelRegistration(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (InvalidParamException | EmptyResultException e) {
            throw e;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("cancelRegistration err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateRegistrationResponse addOrUpdateRegistration(AddOrUpdateRegistrationRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();
        AddOrUpdateRegistrationResponse rsp = null;

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            rsp = client.addOrUpdateRegistration(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (InvalidParamException | EmptyResultException e) {
            if (rsp != null) {
                return rsp;
            }
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateRegistration err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetRegistrationResponse getRegistration(GetRegistrationRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetRegistrationResponse rsp = client.getRegistration(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getRegistration err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateScheduleRuleResponse addOrUpdateScheduleRule(AddOrUpdateScheduleRuleRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdateScheduleRuleResponse rsp = client.addOrUpdateScheduleRule(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateScheduleRule err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetScheduleRuleResponse getScheduleRule(GetScheduleRuleRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetScheduleRuleResponse rsp = client.getScheduleRule(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getScheduleRule err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateSchedulePlanResponse addOrUpdateSchedulePlan(AddOrUpdateSchedulePlanRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdateSchedulePlanResponse rsp = client.addOrUpdateSchedulePlan(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (InvalidParamException e) {
            throw e;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateSchedulePlan err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetSchedulePlanResponse getSchedulePlan(GetSchedulePlanRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetSchedulePlanResponse rsp = client.getSchedulePlan(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getSchedulePlan err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateDiagnosticResponse addOrUpdateDiagnostic(AddOrUpdateDiagnosticRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdateDiagnosticResponse rsp = client.addOrUpdateDiagnostic(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateDiagnostic err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetDiagnosticResponse getDiagnostic(GetDiagnosticRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetDiagnosticResponse rsp = client.getDiagnostic(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getDiagnostic err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateSecondDiagDirResponse addOrUpdateSecondDiagDir(AddOrUpdateSecondDiagDirRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdateSecondDiagDirResponse rsp = client.addOrUpdateSecondDiagDir(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateSecondDiagDir err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetSecondDiagDirResponse getSecondDiagDir(GetSecondDiagDirRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetSecondDiagDirResponse rsp = client.getSecondDiagDir(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getSecondDiagDir err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetRegistrationCountResponse getRegistrationCount(Long doctorId, Long departId,
                                                             long startTime, long endTime) {
        GetRegistrationCountRequest req = new GetRegistrationCountRequest();

        if (doctorId != null) {
            req.setDoctorId(doctorId);
        }

        if (departId != null) {
            req.setDepartId(departId);
        }

        req.setStartTime(startTime);
        req.setEndTime(endTime);

        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetRegistrationCountResponse rsp = client.getRegistrationCount(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getRegistrationCount err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateMedicalRecordTemplateResponse addOrUpdateMedicalRecordTemplate(AddOrUpdateMedicalRecordTemplateRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdateMedicalRecordTemplateResponse rsp = client.addOrUpdateMedicalRecordTemplate(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (InvalidParamException e) {
            throw new InvalidParamException();
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateMedicalRecordTemplate err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetMedicalRecordTemplateResponse getMedicalRecordTemplate(GetMedicalRecordTemplateRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetMedicalRecordTemplateResponse rsp = client.getMedicalRecordTemplate(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getMedicalRecordTemplate err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateMedicalRecordResponse addOrUpdateMedicalRecord(AddOrUpdateMedicalRecordRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();
        AddOrUpdateMedicalRecordResponse rsp = null;

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            rsp = client.addOrUpdateMedicalRecord(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (InvalidParamException e) {
            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateMedicalRecord err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetMedicalRecordResponse getMedicalRecord(GetMedicalRecordRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetMedicalRecordResponse rsp = client.getMedicalRecord(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getMedicalRecord err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateCommonlyUsedDiagnosticResponse addOrUpdateCommonlyUsedDiagnostic(AddOrUpdateCommonlyUsedDiagnosticRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdateCommonlyUsedDiagnosticResponse rsp = client.addOrUpdateCommonlyUsedDiagnostic(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateCommonlyUsedDiagnostic err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetCommonlyUsedDiagnosticResponse getCommonlyUsedDiagnostic(GetCommonlyUsedDiagnosticRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetCommonlyUsedDiagnosticResponse rsp = client.getCommonlyUsedDiagnostic(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getCommonlyUsedDiagnostic err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateApplyResponse addOrUpdateApply(AddOrUpdateApplyRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();
        AddOrUpdateApplyResponse rsp = null;

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            rsp = client.addOrUpdateApply(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (NotEnoughException | InvalidParamException e) {
            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateApply err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetApplyResponse getApply(GetApplyRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetApplyResponse rsp = client.getApply(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getApply err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateApplyGroupResponse addOrUpdateApplyGroup(AddOrUpdateApplyGroupRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdateApplyGroupResponse rsp = client.addOrUpdateApplyGroup(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateApplyGroup err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetApplyGroupResponse getApplyGroup(GetApplyGroupRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetApplyGroupResponse rsp = client.getApplyGroup(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getApplyGroup err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetApplyItemResponse getApplyItem(GetApplyItemRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetApplyItemResponse rsp = client.getApplyItem(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getApplyItem err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public EnterApplyItemResultResponse enterApplyItemResult(EnterApplyItemResultRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            EnterApplyItemResultResponse rsp = client.enterApplyItemResult(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (InvalidParamException e) {
            throw e;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("enterApplyItemResult err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public UpdateApplyItemResponse updateApplyItem(UpdateApplyItemRequest req) {
        ThriftClient<DiagnoseService.Client> thriftClient = servicePool.getClientInstance();

        try {
            DiagnoseService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            UpdateApplyItemResponse rsp = client.updateApplyItem(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("updateApplyItem err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }
}