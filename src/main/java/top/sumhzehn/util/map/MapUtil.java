package top.sumhzehn.util.map;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.sumhzehn.annotation.map.MapIsKey;
import top.sumhzehn.annotation.map.MapIsValue;
import top.sumhzehn.util.CommUtil;

public class MapUtil {

	/**
	 * list转map
	 * 指定key属性，转换为 key, Object
	 * @param list 数据
	 * @param keyFieldName 属性名称
	 * @return
	 */
	public static <T> Map<Object, Object> listConvertMap(List<T> list, String keyFieldName) {
		if(CommUtil.listIsEmpty(list)) throw new MapException("List is no element");
		Class<?> clz = list.get(0).getClass();
		try {
			Field f = clz.getDeclaredField(keyFieldName);
			Map<Object, Object> res = new HashMap<>();
			for(T e: list) {
				res.put(f.get(e), e);
			}
			return res;
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new MapException("Field not found | " + keyFieldName, e);
		}
	}

	/**
	 * list转map
	 * 通过MapIsKey,MapIsValue注解完成
	 * MapIsKey支持使用在无参方法，属性
	 * MapIsValue支持使用在类，属性，无参方法
	 * @param list 数据
	 * @return
	 */
	public static <T> Map<Object, Object> listConvertMap(List<T> list) throws MapException{
		if(CommUtil.listIsEmpty(list)) throw new MapException("List is no element");
		Class<?> clz = list.get(0).getClass();
		// 获取Key 和 Value
		MapBean key = null;
		MapBean value = clz.isAnnotationPresent(MapIsValue.class) ? new MapBean(clz) : null;
		Field[] fields = clz.getDeclaredFields();
		for (Field f : fields) {
			if(f.isAnnotationPresent(MapIsValue.class)) value = new MapBean(f);
			if(f.isAnnotationPresent(MapIsKey.class)) key = new MapBean(f);
		}
		Method[] methods = clz.getDeclaredMethods();
		for (Method m : methods) {
			if((m.isAnnotationPresent(MapIsValue.class) || m.isAnnotationPresent(MapIsKey.class))
				&& m.getParameterTypes().length > 0) throw new MapException("Not support Having paramter Method used");
			if(m.isAnnotationPresent(MapIsValue.class)) value = new MapBean(m);
			if(m.isAnnotationPresent(MapIsKey.class)) key = new MapBean(m);
		}
		if(key == null || value == null) {
			throw new MapException("No use MapIsValue or MapIsKey annotation");
		}
		// 填充
		Map<Object, Object> res = new HashMap<>();
		for (T o: list) {
			res.put(MapBean.getValue(key, o), MapBean.getValue(value, o));
		}
		return res;
	}

}