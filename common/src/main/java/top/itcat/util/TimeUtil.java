package top.itcat.util;

import org.joda.time.DateTime;
import top.itcat.cache.manage.CacheManager;
import top.itcat.cache.manage.DefaultCacheManager;

public class TimeUtil {
    private static CacheManager cacheManager = new DefaultCacheManager();

    public static DateTime getTodayZeroTime() {
        Object o = cacheManager.get("today_zero_time");

        if (o == null) {
            DateTime dateTime = new DateTime().withMillisOfDay(0);
            o = dateTime;
            DateTime nextDay = new DateTime().plusDays(1).withHourOfDay(0).plusMillis(-1);
            cacheManager.set("today_zero_time", dateTime, 0, (int)
                    (nextDay.getMillis() - System.currentTimeMillis()));
        }

        return (DateTime) o;
    }

    public static void main(String[] args) {
        System.out.println(getTodayZeroTime());
        System.out.println(getTodayZeroTime());
        System.out.println(getTodayZeroTime());
    }
}
