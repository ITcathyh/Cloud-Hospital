package top.itcat.rpc.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class ZookeeperUtil {
//    private static String ZK_HOST = "47.100.217.150:2181";
        private static String ZK_HOST = "localhost:2181";
    private static int timeOut = 4000;

    public static void register(String root, String child, int port, Watcher watcher) throws IOException, KeeperException, InterruptedException {
        register(root, child, ZK_HOST, port, watcher);
    }

    public static void register(String root, String child,
                                String zkHost, int port, Watcher watcher) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = getZooKeeper(zkHost, watcher);
        Stat stat = zk.exists(root, watcher != null);

        if (stat == null) {
            zk.create(root, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        String path = root + child + port;
        zk.create(path, path.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    public static ZooKeeper getZooKeeper(String zkHost, Watcher watcher) throws IOException {
        return new ZooKeeper(zkHost, timeOut, watcher);
    }

    public static ZooKeeper getZooKeeper(Watcher watcher) throws IOException {
        return new ZooKeeper(ZK_HOST, timeOut, watcher);
    }

    public static String getZkHost() {
        return ZK_HOST;
    }
}
