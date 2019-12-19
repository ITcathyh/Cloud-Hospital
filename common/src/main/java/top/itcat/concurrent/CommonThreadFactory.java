package top.itcat.concurrent;

import java.util.concurrent.ThreadFactory;

public class CommonThreadFactory implements ThreadFactory {
    private static final class Instance {
        private static CommonThreadFactory instance = new CommonThreadFactory();
    }

    private CommonThreadFactory() {
    }

    public static CommonThreadFactory getInstance() {
        return Instance.instance;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new ThreadWithEH(r);
    }
}
