package top.sumhzehn.util.map;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.sumhzehn.annotation.map.MapIsKey;
import top.sumhzehn.annotation.map.MapIsValue;

public class MapUtil {
	
	public static <T> Map<Object, Object> listConvertMap(List<T> list) throws MapException{
		if(list == null || list.size() < 1) return null;
		Class<?> clz = list.get(0).getClass();
		// 获取Key 和 Value
		if(clz.isAnnotationPresent(MapIsKey.class)) throw new MapException("Not allowed use MapIsKey in Class");
		MapBean key = null;
		MapBean value = clz.isAnnotationPresent(MapIsValue.class) ? isMapAnnotation(clz, MapIsValue.class) : null;
		Field[] fields = clz.getDeclaredFields();
		for (Field f : fields) {
			if(f.isAnnotationPresent(MapIsValue.class)) value = isMapAnnotation(f, clz);
			if(f.isAnnotationPresent(MapIsKey.class)) key = isMapAnnotation(f, clz);
		}
		Method[] methods = clz.getDeclaredMethods();
		for (Method m : methods) {
			if(m.isAnnotationPresent(MapIsValue.class)) value = isMapAnnotation(m, MapIsValue.class);
			if(m.isAnnotationPresent(MapIsKey.class)) key = isMapAnnotation(m, MapIsKey.class);
		}
		if(key == null || value == null) {
			throw new MapException("No use MapIsValue or MapIsKey annotation");
		}
		// 填充
		Map<Object, Object> res = new HashMap<>();
		for (T o: list) {
			res.put(getValue(key, o), getValue(value, o));
		}
		return res;
	}
	
	/**
	 *	 注解校验 获取 map转换Key 和 Value
	 * @param o 校验对象： Class，Field，Method
	 * @param clz: 类 Class 
	 * @return
	 */
	private static MapBean isMapAnnotation(Object o, Class<?> clz) throws MapException {
		if(o instanceof Class || o instanceof Method) {
			return new MapBean(o);
		} else if(o instanceof Field) { // 属性
			String fieldName = null;
			try {
				fieldName = ((Field)o).getName();
				Method m = new PropertyDescriptor(fieldName, clz).getReadMethod();
				if (m != null) return new MapBean(m);
				throw new MapException("Get method failed | fieldName is '" + fieldName + "'");
			} catch (SecurityException | IntrospectionException e) {
				throw new MapException("Get method failed | fieldName is '" + fieldName + "'", e);
			}
		} 
		return null;
	}
	
	private static Object getValue(MapBean item, Object o) throws MapException {
		if(item.getType() instanceof Class) return o;
		if(item.getType() instanceof Method) {
			Method m = (Method) item.getType();
			try {
				return m.invoke(o, new Object[] {});
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new MapException("Method invoke failed", e);
			}
		} 
		throw new MapException("No type '" + item.getType() + "' in MapBean");
	}
	
}