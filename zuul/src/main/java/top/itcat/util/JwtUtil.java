package top.itcat.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.util.StringUtils;

/**
 * JWT Token工具类
 * 用以解析和包装Token
 *
 * @author huangyuhang
 */
public class JwtUtil {
    private static final String ID_RSA = "ITCAT_NEU";


    /**
     * 解析token
     *
     * @param token token
     * @return token内容
     */
    public static Claims parseJWT(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        try {
            return Jwts.parser()
                    .setSigningKey(ID_RSA)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
//            e.printStackTrace();
        }

        return null;
    }
}