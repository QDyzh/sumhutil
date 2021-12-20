package top.sumhzehn.util.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import top.sumhzehn.util.StrUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
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

    public static <T> List<T> importFile(Sheet sheet, int firstRowNum, int firstCellIndex, Class<T> clz, String[] fields) throws Exception {
        List<T> list = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum(); // 最后一行
        for (int i = firstRowNum; i <= lastRowNum; i++) {
            try {
                Row row = sheet.getRow(i);
                Object obj = clz.newInstance();
                int fCellIndex = firstCellIndex;
                for (int j = 0; j < fields.length; j++) {
                    if(StrUtil.isEmpty(fields[j])) continue;
                    Field f = clz.getDeclaredField(fields[j]);
                    if(f == null) throw new Exception("字段【" + fields[j] + "】不存在");
                    f.setAccessible(true);
                    f.set(obj, row.getCell(fCellIndex));
                    fCellIndex++;
                }
                list.add((T) obj);
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new Exception("第" + ( i + 1 ) + "行数据转换错误！");
            }
        }
        return list;
    }
}
