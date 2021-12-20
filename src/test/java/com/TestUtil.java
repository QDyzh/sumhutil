package com;

import com.bean.Sumh;
import org.junit.Test;
import top.sumhzehn.poi.AbstractPOIFile;
import top.sumhzehn.test.build.Builder;
import top.sumhzehn.test.build.ConcreteBuilder;
import top.sumhzehn.test.build.Director;
import top.sumhzehn.test.build.Product;
import top.sumhzehn.util.excel.DefaultExcelFile;
import top.sumhzehn.util.excel.ExcelUtil;
import top.sumhzehn.util.map.MapUtil;

import java.util.*;

/**
 * @author SumhZehn
 * 2021/9/13 17:47
 **/
public class TestUtil {

    @Test
    public void mapUtil() {
        List<Sumh> list = Arrays.asList(new Sumh(1, "A"), new Sumh(2, "B"));
        Map<Object, Object> map = MapUtil.listConvertMap(list);
        map = MapUtil.listConvertMap(list, "id");
        System.out.println(map);
    }

    @Test
    public void ExcelTest() {
        List<Sumh> list = new ArrayList<>();
        for (int i = 0; i <10000; i++) {
            list.add(new Sumh(i+1, "张" + i));
        }
        ExcelUtil.exportDefaultFile("D:\\DOCUMENT\\测试.xlsx", list);
    }

    public static void main(String[] args) {

    }

    @Test
    public void build() {
        Builder builder = new ConcreteBuilder();
        Director d = new Director().setBuilder(builder);
        Product p = d.construct();
        System.out.println(p.toString());
    }
}
