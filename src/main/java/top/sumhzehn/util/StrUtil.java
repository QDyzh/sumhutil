package top.sumhzehn.util;

/**
 * @author SumhZehn
 * 2021/9/15 19:01
 * 字符串工具类
 **/
public class StrUtil {

    private final static String DEFAULT_REG = "\\{.*?\\}";

    /**
     * 字符串为空
     * @param str 字符串
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 字符串不为空
     * @param str 字符串
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str);
    }

    /**
     * 字符串为NULL
     * @param str 字符串
     */
    public static boolean isNull(String str) {
        return str == null;
    }

    /**
     * 字符串首字母小写
     * @param str 字符串
     */
    public static String lowerFirst(String str) {
        if(StrUtil.isEmpty(str)) return null;
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}
