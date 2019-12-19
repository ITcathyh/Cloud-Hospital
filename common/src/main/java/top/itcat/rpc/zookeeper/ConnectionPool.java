package top.itcat.rpc.zookeeper;

import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.itcat.rpc.thrift.ThriftClient;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConnectionPool<T extends TServiceClient> {
    //    private List<ThriftClient<T>> instances;
    private LinkedBlockingQueue<ThriftClient<T>> instances;
    private HashMap<String, Boolean> hostMap;
    private ReadWriteLock lock;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public ConnectionPool() {
        lock = new ReentrantReadWriteLock();
        instances = new LinkedBlockingQueue<>();
        hostMap = new HashMap<>();
    }

    public int getSize() {
        return instances.size();
    }

    /**
     * 获取实例
     * 进行负载均衡
     */
    public ThriftClient<T> getClientInstance() {
        try {
            log.debug("take remain:{}", instances.size());
            return instances.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 取回后放回实例
     */
    public void addClient(ThriftClient<T> client) {
        instances.offer(client);
        log.debug("add remain:{}", instances.size());
    }

    /**
     * 连接新实例
     */
    public void add(T instance, String host) {
//        lock.writeLock().lock();
//        try {
//            instances.add(new ThriftClient<>(instance, host));
//            hostMap.put(host, true);
//        } finally {
//            lock.writeLock().unlock();
//        }

        lock.writeLock().lock();
        try {
            hostMap.put(host, true);
            instances.offer(new ThriftClient<>(instance, host));
        } finally {
            lock.writeLock().unlock();
        }

        log.info(String.format("连接实例:【%s】", host));
    }

    public boolean contains(String host) {
        lock.readLock().lock();
        try {
            return hostMap.getOrDefault(host, false);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void removeNotInList(List<String> list, HashMap<String, Boolean> tmpMap) {
        lock.writeLock().lock();

        try {
            for (String host : hostMap.keySet()) {
                if (!tmpMap.containsKey(host)) {
                    instances.removeIf(tThriftClient -> tThriftClient.getHost().equals(host));
                    hostMap.remove(host);
                    log.info(String.format("断开实例:【%s】", host));
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}
