package top.sumhzehn.util.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import top.sumhzehn.function.SetFieldFunction;
import top.sumhzehn.util.StrUtil;

/**
 * SumhZehn
 * 2021/9/17 0:04
 * Excel文件工具
 **/
public class ExcelUtil {

    public static void exportDefaultFile(String filename, List<?> datas) {
        new DefaultExcelFile().exportFile(filename, datas);
    }
    
    /**
     * 	常规Excel解析转换List
     * @param sheet 表格sheet
     * @param firstRowNum 开始行
     * @param firstCellIndex 开始列
     * @param fields 字段 
     * @param clz 转换类
     * @return
     * @throws Exception
     */
    public static <T> List<T> importFile(Sheet sheet, int firstRowNum, int firstCellIndex,String[] fields, Class<T> clz) throws Exception {
    	return importFile(sheet, firstRowNum, firstCellIndex, fields, clz, null);
    }

    /**
     * 	常规Excel解析转换List
     * @param sheet 表格sheet
     * @param firstRowNum 开始行
     * @param firstCellIndex 开始列
     * @param fields 字段 格式：xx@：标识不从Excel读取
     * @param clz 转换类
     * @param func 特殊字段处理函数
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public static <T> List<T> importFile(Sheet sheet, int firstRowNum, int firstCellIndex,String[] fields, Class<T> clz, SetFieldFunction func) throws Exception {
        List<T> list = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum(); // 最后一行
        for (int i = firstRowNum; i <= lastRowNum; i++) {
            try {
                Row row = sheet.getRow(i);
                Object obj = clz.newInstance();
                int fCellIndex = firstCellIndex;
                for (int j = 0; j < fields.length; j++) {
                    if(StrUtil.isEmpty(fields[j])) continue;
                    // 判断是否读取 Excel 表
                    boolean readExcel = fields[j].indexOf("@") < 0;
                    if (!readExcel) fields[j] = fields[j].substring(0, fields[j].indexOf("@"));
                    Field f = clz.getDeclaredField(fields[j]);
                    if(f == null) throw new Exception("Field [" + fields[j] + "] is not exist!");
                    if (func == null || !func.setFieldValue(obj, f, readExcel ? getCellValue(f, row.getCell(fCellIndex)) : null)) { // 自定义设置值
                    	f.setAccessible(true);
                        f.set(obj, getCellValue(f, row.getCell(fCellIndex)));
                    }
                    if (readExcel) fCellIndex++;
                }
                list.add((T) obj);
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new Exception("第" + ( i + 1 ) + "行数据转换错误！");
            }
        }
        return list;
    }
    
    public static Object getCellValue(Field field, Cell cell) {
    	if (cell == null) return null;
        if (CellType.NUMERIC.equals(cell.getCellType())) {
        	if (Integer.class.equals(field.getType())) { return (int)cell.getNumericCellValue(); }
        	if (Double.class.equals(field.getType())) { return cell.getNumericCellValue(); }
        	return null;
        } else if (CellType.STRING.equals(cell.getCellType())) {
            return cell.getStringCellValue();
        }
        return null;
    }
    
}
