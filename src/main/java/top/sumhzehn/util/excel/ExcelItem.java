package top.sumhzehn.util.excel;

import top.sumhzehn.annotation.excel.ExcelTitle;
import top.sumhzehn.poi.POIFileException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * SumhZehn
 * 2021/9/16 22:11
 **/
public class ExcelItem {
    private String name;
    private Field field;

    public ExcelItem(){}

    public ExcelItem(String name, Field field) {
        this.name = name;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    /**
     * 通过ExcelTitle注解,获取标题名称和需要转换的属性
     * @param clz
     * @return
     * @throws POIFileException
     */
    public static List<ExcelItem> getExcelItemList(Class<?> clz) throws POIFileException{
        Field[] fields = clz.getDeclaredFields();
        List<ExcelItem> list = new ArrayList<>();
        for (Field f: fields) {
            if(f.isAnnotationPresent(ExcelTitle.class)) {
               String title = f.getAnnotation(ExcelTitle.class).value();
               list.add(new ExcelItem(title, f));
            }
        }
        return list;
    }

    /**
     * 获取包含标题名称的列
     * @param clz
     * @param titles
     * @return
     * @throws POIFileException
     */
    public static List<ExcelItem> getExcelItemList(Class<?> clz, String[] titles) throws POIFileException{
        Field[] fields = clz.getDeclaredFields();
        List<ExcelItem> list = new ArrayList<>();
        for (int i = 0; i < fields.length - 1; i++) {
            list.add(new ExcelItem(titles[i], fields[i]));
        }
        return list;
    }
}
