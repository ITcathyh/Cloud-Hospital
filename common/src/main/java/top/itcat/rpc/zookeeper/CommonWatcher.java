package top.itcat.rpc.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TServiceClient;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import top.itcat.rpc.zookeeper.callback.ZookeeperCallBack;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class CommonWatcher<T extends TServiceClient> implements Watcher {
    private ZooKeeper zk;
    private String root;
    private ConnectionPool<T> pool;
    private ZookeeperCallBack<T> callBack;

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public void setCallBack(ZookeeperCallBack<T> callBack) {
        this.callBack = callBack;
    }

    public CommonWatcher(String root) {
        this(root, new ConnectionPool<>());
    }

    public CommonWatcher(String root, ConnectionPool<T> pool) {
        this.root = root;
        this.pool = pool;
    }

    @Override
    public void process(WatchedEvent event) {
        if (event == null || event.toString() == null) {
            return;
        }

        log.info(root + "触发" + event.toString() + "事件！");

        if (event.getState() == Event.KeeperState.Expired) {
            try {
                zk = new ZooKeeper(ZookeeperUtil.getZkHost(), 4000, this);
            } catch (IOException e) {
                log.warn("fail to connect to zoo keeper", e);
            }
        } else if (event.getType() == Event.EventType.NodeChildrenChanged) {
            List<String> list;

            try {
                list = zk.getChildren(root, true);
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
                return;
            }

            HashMap<String, Boolean> tmpMap = new HashMap<>(list.size());

            for (String host : list) {
                if (!pool.contains(host)) {
                    boolean success = false;

                    for (int i = 0; i <= 3 && !success; i++) {
                        try {
                            success = callBack.add(pool, host);
                        } catch (InterruptedException | IOException | KeeperException e) {
                            if (i > 1) {
                                e.printStackTrace();
                            }
                        }

                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                tmpMap.put(host, true);
            }

            pool.removeNotInList(list, tmpMap);
        }
    }

    public ConnectionPool<T> getPool() {
        return pool;
    }
}
