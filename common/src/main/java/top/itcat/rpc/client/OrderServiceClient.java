package top.itcat.rpc.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import top.itcat.exception.CommonException;
import top.itcat.exception.InternalException;
import top.itcat.exception.InvalidParamException;
import top.itcat.rpc.service.order.*;
import top.itcat.rpc.thrift.ThriftClient;
import top.itcat.rpc.thrift.ThriftUtil;
import top.itcat.rpc.zookeeper.CommonWatcher;
import top.itcat.rpc.zookeeper.ConnectionPool;
import top.itcat.rpc.zookeeper.callback.OrderServiceCallBack;

/**
 * Order服务Client
 * 封装RPC请求
 *
 * @author ITcathyh
 */
@Slf4j
public class OrderServiceClient {
//    public OrderServiceClient() {
//        String root = "/" + OrderService.class.getSimpleName();
//
//        try {
//            CommonWatcher<OrderService.Client> watcher = ThriftUtil.initRpc(root, new OrderServiceCallBack(),
//                    OrderService.Client.class.getName(),8);
//            servicePool = watcher.getPool();
//        } catch (Exception e) {
//            log.error("OrderServiceClient init err:", e);
//        }
//    }

    public OrderServiceClient(int instanceNum) {
        String root = "/" + OrderService.class.getSimpleName();

        try {
            CommonWatcher<OrderService.Client> watcher = ThriftUtil.initRpc(root, new OrderServiceCallBack(),
                    OrderService.Client.class.getName(), instanceNum);
            servicePool = watcher.getPool();
        } catch (Exception e) {
            log.error("OrderServiceClient init err:", e);
        }
    }

    public OrderServiceClient() {
        this(5);
    }

    private ConnectionPool<OrderService.Client> servicePool;

    public GetChargeItemResponse getChargeItem(GetChargeItemRequest req) {
        ThriftClient<OrderService.Client> thriftClient = servicePool.getClientInstance();

        try {
            OrderService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetChargeItemResponse rsp = client.getChargeItem(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getChargeItem err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateChargeItemResponse addOrUpdateChargeItem(AddOrUpdateChargeItemRequest req) {
        ThriftClient<OrderService.Client> thriftClient = servicePool.getClientInstance();

        try {
            OrderService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdateChargeItemResponse rsp = client.addOrUpdateChargeItem(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateChargeItem err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetDayKnotItemResponse getDayKnotItem(GetDayKnotItemRequest req) {
        ThriftClient<OrderService.Client> thriftClient = servicePool.getClientInstance();

        try {
            OrderService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetDayKnotItemResponse rsp = client.getDayKnotItem(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getDayKnotItem err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetDayKnotResponse getDayKnot(GetDayKnotRequest req) {
        ThriftClient<OrderService.Client> thriftClient = servicePool.getClientInstance();

        try {
            OrderService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetDayKnotResponse rsp = client.getDayKnot(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getDayKnot err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateDayKnotResponse addOrUpdateDayKnot(AddOrUpdateDayKnotRequest req) {
        ThriftClient<OrderService.Client> thriftClient = servicePool.getClientInstance();

        try {
            OrderService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdateDayKnotResponse rsp = client.addOrUpdateDayKnot(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateDayKnot err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetChargeSubjectResponse getChargeSubject(GetChargeSubjectRequest req) {
        ThriftClient<OrderService.Client> thriftClient = servicePool.getClientInstance();

        try {
            OrderService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetChargeSubjectResponse rsp = client.getChargeSubject(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getChargeSubject err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public AddOrUpdateChargeSubjectResponse addOrUpdateChargeSubject(AddOrUpdateChargeSubjectRequest req) {
        ThriftClient<OrderService.Client> thriftClient = servicePool.getClientInstance();

        try {
            OrderService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            AddOrUpdateChargeSubjectResponse rsp = client.addOrUpdateChargeSubject(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("addOrUpdateChargeSubject err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public SettleChargeResponse settleCharge(SettleChargeRequest req) {
        ThriftClient<OrderService.Client> thriftClient = servicePool.getClientInstance();

        try {
            OrderService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            SettleChargeResponse rsp = client.settleCharge(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("settleCharge err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public CancelChargeResponse cancelCharge(CancelChargeRequest req) throws CommonException {
        ThriftClient<OrderService.Client> thriftClient = servicePool.getClientInstance();

        try {
            OrderService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            CancelChargeResponse rsp = client.cancelCharge(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (InternalException | TException | NullPointerException e) {
            log.error("cancelCharge err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public PayChargeResponse payCharge(PayChargeRequest req) {
        ThriftClient<OrderService.Client> thriftClient = servicePool.getClientInstance();

        try {
            OrderService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            PayChargeResponse rsp = client.payCharge(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (InvalidParamException e) {
            throw e;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("payCharge err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }

    public GetIndividualAmountResponse getIndividualAmount(GetIndividualAmountRequest req) {
        ThriftClient<OrderService.Client> thriftClient = servicePool.getClientInstance();

        try {
            OrderService.Client client = thriftClient.getClient();

            if (client == null) {
                return null;
            }

            GetIndividualAmountResponse rsp = client.getIndividualAmount(req);
            ThriftUtil.checkResponse(rsp.BaseResp.StatusCode);

            return rsp;
        } catch (CommonException | TException | NullPointerException e) {
            log.error("getIndividualAmount err:", e);
        } finally {
            if (thriftClient != null) {
                servicePool.addClient(thriftClient);
            }
        }

        return null;
    }
}