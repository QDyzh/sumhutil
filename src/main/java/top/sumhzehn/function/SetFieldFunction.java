package top.sumhzehn.function;

import java.lang.reflect.Field;

@FunctionalInterface
public interface SetFieldFunction {
	
	/**
	 * 	反射，特殊属性处理函数
	 * @param o 对象实例
	 * @param field 属性字段
	 * @param value excel读取值 或 null
	 * @return
	 * @throws Exception
	 */
	Boolean setFieldValue(Object o, Field field, Object value) throws Exception;
	default Boolean defaultSetFieldValue() { return false; }
}
