package top.itcat.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CommonThreadPoolFactory {
    private static AtomicInteger count = new AtomicInteger(0);

    public static ExecutorService newThreadPool() {
        return newThreadPool(1);
    }

    public static ExecutorService newThreadPool(int coreSize) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().
                setNameFormat("common-thread-pool-" + count.incrementAndGet()).build();
        return new ThreadPoolExecutor(coreSize,
                200, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(5),
                namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    static class DefaultThreadPoolInstance {
        static ExecutorService threadPool = newThreadPool(4);
    }

    public static ExecutorService getDefaultThreadPool() {
        return DefaultThreadPoolInstance.threadPool;
    }
}
