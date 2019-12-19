package top.itcat.rpc.zookeeper.callback;

import org.apache.thrift.TServiceClient;
import org.apache.zookeeper.KeeperException;
import top.itcat.rpc.zookeeper.ConnectionPool;

import java.io.IOException;

public interface ZookeeperCallBack<T extends TServiceClient> {
    public boolean add(ConnectionPool<T> pool, String host) throws InterruptedException, IOException, KeeperException;
}
