package top.itcat.rpc.thrift;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import top.itcat.rpc.zookeeper.ConnectionPool;

import java.lang.reflect.Constructor;

/**
 * RPC请求协议生成工具类
 *
 * @author ITcathyh
 */
public class ProtocolUtil {

    /**
     * 生成Framed格式请求协议
     *
     * @param host 服务地址 192.0.0.1:8080
     * @return TProtocol
     */
    public static TProtocol getTProtocol(String host) {
        String[] serverArr = host.split(":");
        String ip = serverArr[0];
        int port = Integer.parseInt(serverArr[1]);
        TSocket socket = new TSocket(ip, port);
//        socket.setTimeout(3000);
        TTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TCompactProtocol(transport);

        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }

        return protocol;
    }

    /**
     * 生成二进制格式请求协议
     *
     * @param ip   服务ip
     * @param port 服务端口
     * @return TProtocol
     */
    public static TProtocol getNormalTProtocol(String ip, int port) {
        TTransport transport = new TSocket(ip, port);
        TProtocol protocol = new TBinaryProtocol(transport);

        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }

        return protocol;
    }

    /**
     * 增加连接池实例
     *
     * @param pool      连接池
     * @param host      主机
     * @param className 类名
     * @param <T>       TServiceClient
     */
    public static <T extends TServiceClient> void addPooInstance(ConnectionPool<T> pool,
                                                                 String host,
                                                                 String className) {
        addPooInstance(pool, host, className, 2);
    }

    /**
     * 增加连接池实例
     *
     * @param pool        连接池
     * @param host        主机
     * @param className   类名
     * @param <T>         TServiceClient
     * @param instanceNum 生成实例个数
     */
    public static <T extends TServiceClient> void addPooInstance(ConnectionPool<T> pool,
                                                                 String host,
                                                                 String className,
                                                                 int instanceNum) {
        for (int i = 0; i < instanceNum; ++i) {
            TProtocol protocol = getTProtocol(host);

            if (protocol != null) {
                pool.add(getClient(className, protocol), host);
            }
        }
    }

    /**
     * 使用反射生成Client
     *
     * @param className 类名
     * @param protocol  协议
     * @param <T>       TServiceClient
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T extends TServiceClient> T getClient(String className, TProtocol protocol) {
        Class clazz;
        try {
            clazz = Class.forName(className);
            Constructor<?>[] cons = clazz.getConstructors();
            Constructor<?> con = cons[0];
            return (T) con.newInstance(protocol);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
