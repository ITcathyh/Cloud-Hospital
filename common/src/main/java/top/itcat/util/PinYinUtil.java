package top.itcat.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音工具
 *
 * @author huangyuhang
 */
public class PinYinUtil {
    private static HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();

    static {
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    /**
     * 获取中文拼音首字母
     *
     * @param word 中文单词
     * @return String
     */
    public static String getFirstPinYin(String word) {
        char[] charArray = word.toCharArray();
        StringBuilder pinyin = new StringBuilder();

        try {
            for (char c : charArray) {
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] hanyuPinyinStringArray = PinyinHelper.
                            toHanyuPinyinStringArray(c, defaultFormat);

                    if (hanyuPinyinStringArray != null) {
                        pinyin.append(hanyuPinyinStringArray[0].charAt(0));
                    }
                } else {
                    pinyin.append(Character.toUpperCase(c));
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }

        return pinyin.toString();
    }
}
