package top.itcat.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);

        return isNum.matches();
    }
}
