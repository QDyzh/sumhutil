package top.sumhzehn.util.map;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MapBean {
	
	private Object type;
	
	public MapBean() {
		super();
	}
	
	public MapBean(Object type) {
		super();
		this.type = type;
	}
	
	public Object getType() {
		return type;
	}

	public static Object getValue(MapBean item, Object o) throws MapException {
		if(item.getType() instanceof Class) return o;
		if(item.getType() instanceof Method) {
			Method m = (Method) item.getType();
			try {
				return m.invoke(o);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new MapException("Method invoke failed", e);
			}
		}
		if(item.getType() instanceof Field) {
			Field f = (Field) item.getType();
			f.setAccessible(true);
			try {
				return f.get(o);
			} catch (IllegalAccessException e) {
				throw new MapException("Fiele get value failed", e);
			}
		}
		throw new MapException("No type '" + item.getType() + "' in MapBean");
	}
}
