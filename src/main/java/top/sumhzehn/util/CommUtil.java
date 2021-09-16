package top.sumhzehn.util;

import java.util.List;

/**
 * SumhZehn
 * 2021/9/17 0:05
 * 通用工具类
 **/
public class CommUtil {
    /**
     * 数组为空
     * @param arr
     * @return
     */
    public static boolean arrayIsEmpty(Object[] arr) {
        return arr == null || arr.length < 1;
    }

    /**
     * 集合为空
     * @param list
     * @return
     */
    public static boolean listIsEmpty(List<?> list) {
        return list == null || list.size() < 1;
    }
}
