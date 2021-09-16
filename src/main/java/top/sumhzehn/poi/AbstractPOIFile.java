package top.sumhzehn.poi;

import org.apache.poi.ss.usermodel.Workbook;
import top.sumhzehn.util.StrUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * SumhZehn
 * 2021/9/16 20:51
 * POI操作生成文件：支持 Excel, Word
 * 利用抽象类实现模板方法
 *  导入数据和生成文件格式交由 实现类的importData方法去实现
 **/
public abstract class AbstractPOIFile {

    protected Workbook workbook;

    public AbstractPOIFile importData(String filename, String[] titles, List<?> datas) throws POIFileException {
        if(StrUtil.isEmpty(filename)) throw new POIFileException("Filename is not empty");
        importData(datas, titles);
        createFile(filename);
        return this;
    }

    public AbstractPOIFile exportFile(String filename, List<?> datas) throws POIFileException {
        if(StrUtil.isEmpty(filename)) throw new POIFileException("Filename is not empty");
        importData(datas);
        createFile(filename);
        return this;
    }

    private void createFile(String filename) throws POIFileException {
        try {
            workbook.write(new FileOutputStream(filename));
        } catch (IOException e) {
            throw new POIFileException("File is failed", e);
        } finally {
           if(workbook != null) {
               try {
                   workbook.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }
    }

    protected abstract void importData(List<?> datas) throws POIFileException;

    protected abstract void importData(List<?> datas, String[] titles) throws POIFileException;
}
