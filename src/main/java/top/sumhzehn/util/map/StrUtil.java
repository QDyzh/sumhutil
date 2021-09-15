package top.sumhzehn.util.map;

import java.io.Serializable;

/**
 * @author SumhZehn
 * 2021/9/15 19:01
 **/
public class StrUtil {

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str);
    }

    public static boolean isNull(String str) {
        return str == null;
    }

    public static String lowerFirst(String str) {
        if(StrUtil.isEmpty(str)) return null;
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}
