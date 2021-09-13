package com.bean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import top.sumhzehn.annotation.map.MapIsKey;
import top.sumhzehn.annotation.map.MapIsValue;
import top.sumhzehn.util.map.MapUtil;

@MapIsValue
public class Sumh {
	
	private Integer id;
	
	
	private String name;
	
	public Sumh(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	@MapIsKey
	public String getKey() {
		return this.id + this.name;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Sumh [id=" + id + ", name=" + name + "]";
	}
	
	public static void main(String[] args) {
		List<Sumh> list = Arrays.asList(new Sumh(1, "A"), new Sumh(2, "B"));
		Map<Object, Object> map = MapUtil.listConvertMap(list);
		System.out.println(map);
	}
}
