package top.itcat.pack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.itcat.rpc.service.model.RoleEnum;

@Component
@Slf4j
public class UserCodeGenerateUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private String key = "code_num:%s";
    private int codeLen = 8;

    public String generateUserCode(RoleEnum role) {
        String prefix = getPrefix(role);

        if (prefix == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder(prefix);
        Long cur;

        try {
            cur = redisTemplate.opsForValue().increment(String.format(key, prefix), 1);

            if (cur == null) {
                log.error("redis incr err, key({})", String.format(key, prefix));
                return null;
            }
        } catch (Exception e) {
            log.error("redis incr err:", e);
            return null;
        }

        String num = Long.toString(cur);

        for (int i = codeLen - prefix.length() - num.length(); i > 0; --i) {
            sb.append("0");
        }

        sb.append(num);
        return sb.toString();
    }

    public String getPrefix(RoleEnum role) {
        switch (role) {
            case Patient:
                return null;
            case Doctor:
                return "OD";
            case Medical_Doctor:
                return "MD";
            case Toll_Collector:
                return "TC";
            case Hospital_Manager:
                return "HM";
            case Pharmacy_Manager:
                return "PM";
            case Account_Clerk:
                return "AC";
            default:
                return null;
        }
    }

    public RoleEnum getRole(String prefix) {
        switch (prefix) {
            case "OD":
                return RoleEnum.Doctor;
            case "MD":
                return RoleEnum.Medical_Doctor;
            case "TC":
                return RoleEnum.Toll_Collector;
            case "HM":
                return RoleEnum.Hospital_Manager;
            case "PM":
                return RoleEnum.Pharmacy_Manager;
            case "AC":
                return RoleEnum.Account_Clerk;
            default:
                return RoleEnum.Patient;
        }
    }
}
