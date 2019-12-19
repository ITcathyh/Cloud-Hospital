package top.itcat.concurrent.hanlder;

import org.apache.log4j.Logger;

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Logger log = Logger.getLogger(this.getClass().getSimpleName());

    private static class DeafultInstance {
        static MyUncaughtExceptionHandler instance = new MyUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("UncaughtException,Thread:" + t.getName(), e);
        e.printStackTrace();
    }

    public static MyUncaughtExceptionHandler getDefault() {
        return DeafultInstance.instance;
    }
}
