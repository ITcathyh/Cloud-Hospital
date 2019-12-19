package top.itcat.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.ibatis.jdbc.SQL;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryUtil {
    public static String SHA1(String word) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(word.getBytes());
            return Hex.encodeHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String SHA1(String word, String salt) {
        if (word == null || salt == null) {
            return null;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(word.getBytes());
            return Hex.encodeHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
