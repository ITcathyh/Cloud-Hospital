package top.itcat.rpc.zookeeper.callback;

import org.apache.thrift.protocol.TProtocol;
import org.apache.zookeeper.KeeperException;
import top.itcat.rpc.service.order.OrderService;
import top.itcat.rpc.thrift.ProtocolUtil;
import top.itcat.rpc.zookeeper.ConnectionPool;

import java.io.IOException;

public class OrderServiceCallBack implements ZookeeperCallBack<OrderService.Client> {
    @Override
    public boolean add(ConnectionPool<OrderService.Client> pool, String host) throws InterruptedException, IOException, KeeperException {
        TProtocol protocol = ProtocolUtil.getTProtocol(host);

        if (protocol != null) {
            pool.add(new OrderService.Client(protocol), host);
            return true;
        }

        ProtocolUtil.addPooInstance(pool, host, OrderService.Client.class.getName());
        return true;
    }
}

