package com.bean;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import top.sumhzehn.annotation.excel.ExcelTitle;
import top.sumhzehn.annotation.map.MapIsKey;
import top.sumhzehn.annotation.map.MapIsValue;
import top.sumhzehn.util.excel.ExcelUtil;

public class Sumh {

	@MapIsKey
	@ExcelTitle("唯一ID")
	private Integer id;

	@ExcelTitle("姓名")
	private String name;
	
	private Date date;

	public Sumh() {}

	public Sumh(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@MapIsValue
	public String isKey() {
		return this.id + this.name;
	}

	@Override
	public String toString() {
		return "Sumh [id=" + id + ", name=" + name + ", date=" + date + "]";
	}

	public static void main(String[] args) throws Exception {
		try {
			XSSFWorkbook wb =new XSSFWorkbook(new FileInputStream("F:\\import-test.xlsx"));
			XSSFSheet sheet = wb.getSheetAt(0);
			Date date = new Date();
			List<Sumh> list = ExcelUtil.importFile(sheet, 4, 0, new String[]{"id", "name", "date@"}, Sumh.class, (object, field, value) -> {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
