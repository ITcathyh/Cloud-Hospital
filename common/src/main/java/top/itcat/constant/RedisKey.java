package top.itcat.constant;

public class RedisKey {
    public static final String SUBMIT_SCHEDULE_SITE_KEY = "schedule_site_remain:%d";
    public static final String DECR_SCHEDULE_REMAIN_LUA = " local val1 = redis.call ('get', KEYS [1]) \n "
            + " if val1 and val1 ~= '0' then \n "
            + " return redis.call ('decr', KEYS[1]) \n "
            + " end \n "
            + " return -1 \n ";
    public final static String IS_DUPL_SUBMIT_SCHEDULE_SITE_KEY = "schedule_site_patient:%d:%s";

    public static final String GET_REGISTRATION_SEQ = " local val1 = redis.call ('incr', KEYS [1]) \n "
            + " if val1 == 1 then \n "
            + " redis.call ('expire', KEYS[1],43200) \n "
            + " end \n "
            + " return val1 \n ";

    public static final String GET_INCRING_SEQ = " local val1 = redis.call ('incr', KEYS [1]) \n "
            + " if val1 == 1 then \n "
            + " redis.call ('expire', KEYS[1],KEYS[2]) \n "
            + " end \n "
            + " return val1 \n ";
}
