package top.itcat.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;
import top.itcat.bean.user.ApiUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JWT Token工具类
 * 用以解析和包装Token
 *
 * @author huangyuhang
 */
public class JwtUtil {
    private static final String ID_RSA = "ITCAT_NEU";

    /**
     * 包装Token
     *
     * @param ttlMillis 有效时间
     * @param user      用户信息
     * @return token
     */
    public static String createJWT(long ttlMillis, ApiUser user) {
        if (ttlMillis < 0) {
            return null;
        }

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Map<String, Object> claims = new HashMap<>(8);

        claims.put("id", Long.toString(user.getId()));
        claims.put("code", user.getCode());
        claims.put("departmentId", Long.toString(user.getDepartmentId()));
        claims.put("description", user.getDescription());
        claims.put("role", user.getRole().getValue());
//        claims.put("realname", user.getRealname());

        if (user.getTitle() != null) {
            claims.put("title", user.getTitle().getValue());
        }

        claims.put("role", user.getRole().getValue());

        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setSubject(user.getCode())
                .signWith(SignatureAlgorithm.HS256, ID_RSA)
                .setExpiration(new Date(nowMillis + ttlMillis)).compact();
    }

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