package com;

import com.bean.Sumh;
import org.junit.Test;
import top.sumhzehn.util.map.MapUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author SumhZehn
 * 2021/9/13 17:47
 **/
public class TestUtil {

    @Test
    public void mapUtil() {
        List<Sumh> list = Arrays.asList(new Sumh(1, "A"), new Sumh(2, "B"));
        Map<Object, Object> map = MapUtil.listConvertMap(list);
        System.out.println(map);
    }
}
