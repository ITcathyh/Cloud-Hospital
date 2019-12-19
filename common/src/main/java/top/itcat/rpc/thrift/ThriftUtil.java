package top.itcat.rpc.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import top.itcat.exception.CommonException;
import top.itcat.exception.ExceptionFactory;
import top.itcat.rpc.base.BaseResp;
import top.itcat.rpc.service.metrics.MetricsService;
import top.itcat.rpc.zookeeper.CommonWatcher;
import top.itcat.rpc.zookeeper.ConnectionPool;
import top.itcat.rpc.zookeeper.ZookeeperUtil;
import top.itcat.rpc.zookeeper.callback.ZookeeperCallBack;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static top.itcat.rpc.thrift.ProtocolUtil.addPooInstance;
import static top.itcat.rpc.thrift.ProtocolUtil.getNormalTProtocol;

@SuppressWarnings("unchecked")
public class ThriftUtil {
    private static final BaseResp successRsp = new BaseResp("success", 0);

    public static void beginService(TProcessor tprocessor, int port) {
        beginService(tprocessor, port, Executors.newFixedThreadPool(4), 2);
    }

    public static void beginService(TProcessor tprocessor,
                                    int port,
                                    ExecutorService pool,
                                    int selectorThreadsNum) {
        try {
            TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
            TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(serverTransport);
            args.selectorThreads(selectorThreadsNum);
            args.processor(tprocessor);
            args.protocolFactory(new TCompactProtocol.Factory());
            args.transportFactory(new TFramedTransport.Factory());
            args.processorFactory(new TProcessorFactory(tprocessor));
            args.executorService(pool);

            TThreadedSelectorServer server = new TThreadedSelectorServer(args);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public static BaseResp getBaseResp() {
        return successRsp;
    }

    public static BaseResp getBaseResp(CommonException ce) {
        return new BaseResp(ce.getMessage(), ce.getCode());
    }

    public static void checkResponse(int statusCode) throws CommonException {
        if (statusCode != 0) {
            throw ExceptionFactory.getCommonExceptionByCode(statusCode);
        }
    }

    public static <T extends TServiceClient>
    CommonWatcher<T> initRpc(String root,
                             ZookeeperCallBack<T> callback,
                             String className,
                             int instanceNum)
            throws IOException, KeeperException, InterruptedException {
        CommonWatcher<T> watcher = new CommonWatcher<>(root);
        ZooKeeper zk = ZookeeperUtil.getZooKeeper(watcher);
        ConnectionPool<T> pool = watcher.getPool();

        for (String host : zk.getChildren(root, true)) {
            addPooInstance(pool, host, className, instanceNum);
        }

        watcher.setZk(zk);
        watcher.setCallBack(callback);
        return watcher;
    }

    public static <T extends TServiceClient> CommonWatcher<T> initRpc(String root, ZookeeperCallBack<T> callback, String className)
            throws IOException, KeeperException, InterruptedException {
        return initRpc(root, callback, className, 2);
    }

    public static MetricsService.Client getMetricsClient() {
        return getMetricsClient("127.0.0.1", 8888);
    }

    public static MetricsService.Client getMetricsClient(String ip, int port) {
        TProtocol protocol = getNormalTProtocol(ip, port);
        return new MetricsService.Client(protocol);
    }
}
