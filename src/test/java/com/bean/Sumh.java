package com.bean;

import java.util.Date;

import top.sumhzehn.annotation.excel.ExcelTitle;
import top.sumhzehn.annotation.map.MapIsKey;
import top.sumhzehn.annotation.map.MapIsValue;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
