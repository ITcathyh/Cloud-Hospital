package top.itcat.aop.multipledb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class DataSourceContextHolder {
    private static Map<DataSourceType, List<String>> sources = new HashMap<>(2);
    private static ThreadLocal<String> contextHolder = new InheritableThreadLocal<>();
    private static AtomicInteger count = new AtomicInteger(0);

    /**
     * 设置数据源
     *
     * @param db
     */
    public static void setDataSource(String db) {
        contextHolder.set(db);
    }

    public static void setDataSource(DataSourceType type) {
        List<String> list = sources.get(type);

        if (list.size() > 0) {
            int c = count.incrementAndGet();
            contextHolder.set(list.get(c % list.size()));

            if (c > sources.size() << 2) {
                count.set(0);
            }
        }

    }

    /**
     * 取得当前数据源
     *
     * @return
     */
    public static String getDataSource() {
        return contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clear() {
        contextHolder.remove();
    }

    public static synchronized void addSource(DataSourceType type, String source) {
        List<String> list = sources.get(type);

        if (list == null) {
            list = new ArrayList<>();
        }

        list.add(source);
        sources.put(type, list);
    }
}