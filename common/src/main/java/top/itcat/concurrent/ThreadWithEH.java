package top.itcat.concurrent;

import top.itcat.concurrent.hanlder.MyUncaughtExceptionHandler;

public class ThreadWithEH extends Thread {
    public ThreadWithEH() {
        this(MyUncaughtExceptionHandler.getDefault());
    }

    public ThreadWithEH(UncaughtExceptionHandler eh) {
        this(null, MyUncaughtExceptionHandler.getDefault());
    }

    public ThreadWithEH(Runnable r) {
        this(r, MyUncaughtExceptionHandler.getDefault());
    }

    public ThreadWithEH(Runnable r, UncaughtExceptionHandler eh) {
        super(r);
        setUncaughtExceptionHandler(eh);

    }
}
