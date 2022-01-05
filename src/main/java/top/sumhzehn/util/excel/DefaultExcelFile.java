package top.sumhzehn.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import top.sumhzehn.poi.AbstractPOIFile;
import top.sumhzehn.poi.POIFileException;
import top.sumhzehn.util.CommUtil;
import top.sumhzehn.util.StrUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * SumhZehn
 * 2021/9/16 21:29
 * 默认生成Excel实现类
 *  标题：列值
 **/
public class DefaultExcelFile extends AbstractPOIFile {

    /**
     * sheet
     */
    private Sheet sheet;

    private Row row;

    private Cell cell;
    
    private Integer sheet_max_row = 50000;

    public DefaultExcelFile() {
        this(null);
    }

    public DefaultExcelFile(String sheetName) {
        workbook = new XSSFWorkbook();
        sheet = StrUtil.isEmpty(sheetName) ? workbook.createSheet() : workbook.createSheet(sheetName);
    }

    @Override
    protected void importData(List<?> datas) throws POIFileException {
        if(CommUtil.listIsEmpty(datas)) throw new POIFileException("No datas");
        long startTime = System.currentTimeMillis();
        List<ExcelItem> fields = ExcelItem.getExcelItemList(datas.get(0).getClass());
        System.out.println("模板Excel字段获取耗时：" + (System.currentTimeMillis() - startTime)/1000.0 + "s");
        if(CommUtil.listIsEmpty(fields)) throw new POIFileException("Column is Empty");
        startTime = System.currentTimeMillis();
        wirteData(fields, datas);
        System.out.println("数据写入模板属性耗时：" + (System.currentTimeMillis() - startTime)/1000.0 + "s");
    }

    @Override
    protected void importData(List<?> datas, String[] titles) throws POIFileException {
        if(CommUtil.listIsEmpty(datas)) throw new POIFileException("No datas");
        if(CommUtil.arrayIsEmpty(titles)) throw new POIFileException("No title array");
        if(datas.size() != titles.length) throw new POIFileException("Data's size not eq Ttitle's length");
        List<ExcelItem> fields = ExcelItem.getExcelItemList(datas.get(0).getClass(), titles);
        if(CommUtil.listIsEmpty(fields)) throw new POIFileException("Column is Empty");
        wirteData(fields, datas);
    }

    /**
     * 填充 Sheet 标题
     */
    private void fillTitle(List<ExcelItem> titles) {
        for(int i = 0; i < titles.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(titles.get(i).getName());
        }
    }

    /**
     * 写入数据
     * @param titles 标题
     * @param datas 数据
     */
    private <T> void wirteData(List<ExcelItem> titles, List<T> datas) throws POIFileException{
        row = sheet.createRow(0);
        fillTitle(titles);
        // 填充值
        try {
            int rowIndex = 1;
            for(T data: datas) {
                if(rowIndex > sheet_max_row) { // 默认5000行后创建一个新的sheet
                    sheet = workbook.createSheet();
                    row = sheet.createRow(0);
                    fillTitle(titles);
                    rowIndex = 1;
                }
                row = sheet.createRow(rowIndex);
                for(int i = 0; i < titles.size(); i++) {
                    cell = row.createCell(i);
                    Field field = titles.get(i).getField();
                    field.setAccessible(true);
                    cell.setCellValue(String.valueOf(field.get(data)));
//                    cell.setCellValue("测试性能差距");
                }
                rowIndex++;
            }
        } catch (IllegalAccessException e) {
            throw new POIFileException("Fill value failed", e);
        }
    }
}
