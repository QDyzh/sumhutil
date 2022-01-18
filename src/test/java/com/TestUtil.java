package com;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.bean.Sumh;

import top.sumhzehn.util.excel.ExcelUtil;
import top.sumhzehn.util.map.MapUtil;

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
        for (int i = 0; i <200000; i++) {
            list.add(new Sumh(i+1, "张大同的子孙" + i + "号"));
        }
        long startTime = System.currentTimeMillis();
        ExcelUtil.exportDefaultFile("D:\\测试.xlsx", list);
        System.out.println("导入数据完成！ 耗时：" + (System.currentTimeMillis() - startTime)/1000.0 + "s");
    }

    @Test
    public void readData() throws Exception {
		// 测试读取数据
		try {
			XSSFWorkbook wb =new XSSFWorkbook(new FileInputStream("D:\\测试.xlsx"));
			XSSFSheet sheet = wb.getSheetAt(0);
			Date date = new Date();
			List<Sumh> list = ExcelUtil.importFile(sheet, 1, 0, new String[]{"id", "name", "date@"}, Sumh.class, (object, field, value) -> {
				// 测试不从excel读取值
				if ("date".equals(field.getName())) {
					field.setAccessible(true);
					field.set(object, date);
					return true;
				}
				// 测试处理从excel读取值
				if ("name".equals(field.getName())) {
					field.setAccessible(true);
					field.set(object, value + "[dealName]");
					return true;
				}
				return false;
			});
			System.out.println(list);
			// 常规数据读取
//			List<Sumh> list2 = ExcelUtil.importFile(sheet, 4, 0, new String[]{"id", "name"}, Sumh.class);
//			System.out.println(list2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
