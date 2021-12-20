package com.bean;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import top.sumhzehn.annotation.excel.ExcelTitle;
import top.sumhzehn.annotation.map.MapIsKey;
import top.sumhzehn.annotation.map.MapIsValue;
import top.sumhzehn.util.map.MapUtil;

public class Sumh {

	@MapIsKey
	@ExcelTitle("唯一ID")
	private Integer id;

	@ExcelTitle("姓名")
	private String name;

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
		return "Sumh [id=" + id + ", name=" + name + "]";
	}
	
	public static void main(String[] args) {
		Class<Sumh> clz = Sumh.class;
		Field f = null;
		try {
			f = clz.getDeclaredField("id");
			f.setAccessible(true);
			Object o = clz.newInstance();
			f.set(o, 131);
			System.out.println(((Sumh)o).isKey());
		} catch (NoSuchFieldException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
