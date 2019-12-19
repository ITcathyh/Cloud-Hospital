package top.itcat.rpc.zookeeper.callback;

import org.apache.thrift.protocol.TProtocol;
import org.apache.zookeeper.KeeperException;
import top.itcat.rpc.service.diagnose.DiagnoseService;
import top.itcat.rpc.thrift.ProtocolUtil;
import top.itcat.rpc.zookeeper.ConnectionPool;

import java.io.IOException;

public class DiagnoseServiceCallBack implements ZookeeperCallBack<DiagnoseService.Client> {
    @Override
    public boolean add(ConnectionPool<DiagnoseService.Client> pool, String host) throws InterruptedException, IOException, KeeperException {
//        TProtocol protocol = ProtocolUtil.getTProtocol(host);
////
////        if (protocol != null) {
////            pool.add(new DiagnoseService.Client(protocol), host);
////            return true;
////        }
////
        ProtocolUtil.addPooInstance(pool, host, DiagnoseService.Client.class.getName());
        return true;
    }
}