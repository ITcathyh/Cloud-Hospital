package top.itcat.rpc.zookeeper.callback;

import org.apache.thrift.protocol.TProtocol;
import org.apache.zookeeper.KeeperException;
import top.itcat.rpc.service.user.UserService;
import top.itcat.rpc.thrift.ProtocolUtil;
import top.itcat.rpc.zookeeper.ConnectionPool;

import java.io.IOException;

public class UserServiceCallBack implements ZookeeperCallBack<UserService.Client> {
    @Override
    public boolean add(ConnectionPool<UserService.Client> pool, String host) throws InterruptedException, IOException, KeeperException {
        TProtocol protocol = ProtocolUtil.getTProtocol(host);

        if (protocol != null) {
            pool.add(new UserService.Client(protocol), host);
            return true;
        }

        ProtocolUtil.addPooInstance(pool, host, UserService.Client.class.getName());
        return true;
    }
}
