package top.sumhzehn.util.excel;

import java.util.List;

/**
 * SumhZehn
 * 2021/9/17 0:04
 * Excel文件工具
 **/
public class ExcelUtil {
    public static void exportDefaultFile(String filename, List<?> datas) {
        new DefaultExcelFile().exportFile(filename, datas);
    }
}
