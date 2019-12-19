package top.itcat.rpc;

import org.apache.zookeeper.KeeperException;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.order.OrderService;
import top.itcat.rpc.service.user.UserService;
import top.itcat.rpc.thrift.ThriftUtil;
import top.itcat.rpc.zookeeper.CommonWatcher;
import top.itcat.rpc.zookeeper.callback.OrderServiceCallBack;

import java.io.IOException;

public final class Clients {
    public void init() throws InterruptedException, IOException, KeeperException {
        initOrderService();
    }

    private void initOrderService() throws IOException, KeeperException, InterruptedException {
        String root = "/" + OrderService.class.getSimpleName();
        CommonWatcher<OrderService.Client> watcher = ThriftUtil.InitRpc(root, new OrderServiceCallBack(),
                OrderService.Client.class.getName());
        OrderServiceClient.setConnectionPool(watcher.getPool());
    }
}
