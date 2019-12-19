package top.itcat.rpc.zookeeper.callback;

import org.apache.zookeeper.KeeperException;
import top.itcat.rpc.service.medical.MedicalService;
import top.itcat.rpc.thrift.ProtocolUtil;
import top.itcat.rpc.zookeeper.ConnectionPool;

import java.io.IOException;

public class MedicalServiceCallBack implements ZookeeperCallBack<MedicalService.Client> {
    @Override
    public boolean add(ConnectionPool<MedicalService.Client> pool, String host) throws InterruptedException, IOException, KeeperException {
//        TProtocol protocol = ProtocolUtil.getTProtocol(host);
//
//        if (protocol != null) {
//            pool.add(new MedicalService.Client(protocol), host);
//            return true;
//        }

        ProtocolUtil.addPooInstance(pool, host, MedicalService.Client.class.getName());
        return true;
    }
}