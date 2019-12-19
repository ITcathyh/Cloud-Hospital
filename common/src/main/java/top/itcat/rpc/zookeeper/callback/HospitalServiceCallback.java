package top.itcat.rpc.zookeeper.callback;

import org.apache.thrift.protocol.TProtocol;
import org.apache.zookeeper.KeeperException;
import top.itcat.rpc.service.diagnose.DiagnoseService;
import top.itcat.rpc.service.hospital.HospitalService;
import top.itcat.rpc.thrift.ProtocolUtil;
import top.itcat.rpc.zookeeper.ConnectionPool;

import java.io.IOException;

public class HospitalServiceCallback implements ZookeeperCallBack<HospitalService.Client> {
    @Override
    public boolean add(ConnectionPool<HospitalService.Client> pool, String host) throws InterruptedException, IOException, KeeperException {
//        TProtocol protocol = ProtocolUtil.getTProtocol(host);
//
//        if (protocol != null) {
//            pool.add(new HospitalService.Client(protocol), host);
//            return true;
//        }
        ProtocolUtil.addPooInstance(pool, host, HospitalService.Client.class.getName());
        return true;
    }
}