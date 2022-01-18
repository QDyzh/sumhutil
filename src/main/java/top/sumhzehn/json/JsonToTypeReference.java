package top.sumhzehn.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import top.sumhzehn.util.StrUtil;

/**
 *	序列化对象到json
 *	反序列化json数据到对象
 *	为了提高json转化效率将mapper对象单例静态化,只有需要自己设置日期格式时才重新创建mapper对象
 */
public class JsonToTypeReference {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static ObjectMapper mapper = null;

    /**
     * 	创建静态变量
     * @return
     */
    @SuppressWarnings("deprecation")
	public static ObjectMapper buildObjectMapper() {
        if(mapper == null) {
            mapper = new ObjectMapper();
            // 处理非标准JSON格式串(微信返回信息中的串)
            mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
            mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
            mapper.setDateFormat(sdf);
        }
        return mapper;
    }

    
    /**
     *	 创建静态变量
     * @return
     */
    @SuppressWarnings("deprecation")
	public static ObjectMapper buildObjectMapper(SimpleDateFormat sd) {
		ObjectMapper mapper = new ObjectMapper();
        //处理非标准JSON格式串(微信返回信息中的串)
        mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        mapper.setDateFormat(sd);
        return mapper;
    }

    /**
     * json数据转化成List<Map<String, Object>>对象
     * @param json
     * @return
     */
    public static List<Map<String, Object>> jsonToList(String json) {
        return jsonToList(json, null);
    }
    
    /**
     * json数据转化成List<Map<String, Object>>对象
     * @param json
     * @return
     */
    public static List<Map<String, Object>> jsonToList(String json, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> list = null;
        try {
            if(StrUtil.isNotEmpty(dateFormat)) {
                mapper.setDateFormat(new SimpleDateFormat(dateFormat));
            }
            else {
                mapper.setDateFormat(sdf);
            }
            list = mapper.readValue(json, new TypeReference<ArrayList<Map<String, Object>>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * json数据转化成List<Map<String, String>>对象
     * @param json
     * @return
     */
    public static List<Map<String, String>> jsonToListMapString(String json) {
        return jsonToListMapString(json, null);
    }
    
    /**
     * json数据转化成List<Map<String, String>>对象
     * @param json
     * @return
     */
    public static List<Map<String, String>> jsonToListMapString(String json, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> list = null;
        try {
            if(StrUtil.isNotEmpty(dateFormat)) {
                mapper.setDateFormat(new SimpleDateFormat(dateFormat));
            }
            else {
                mapper.setDateFormat(sdf);
            }
            list = mapper.readValue(json, new TypeReference<ArrayList<Map<String, String>>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * json数据转化成Map<String, Object>对象
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMap(String json) {
        Map<String, Object> map = null;
        try {
            map = buildObjectMapper().readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * json数据转化成Map<String, String>对象
     * @param json
     * @return
     */
    public static Map<String, String> jsonToStringMap(String json) {
        Map<String, String> map = null;
        try {
            map = buildObjectMapper().readValue(json, new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * json数据转化成Map<String, Object>对象
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMap(String json, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
            if(StrUtil.isNotEmpty(dateFormat)){
                mapper.setDateFormat(new SimpleDateFormat(dateFormat));
            }
            map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * json数据转化成Entity对象
     * @param json
     * @param entity
     * @return
     */
    public static <T> T jsonToEntity(String json, Class<T> entity) {
        try {
            return buildObjectMapper().readValue(json, entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json数据转化成Entity对象
     * @param json
     * @param entity
     * @return
     */
    public static <T> T jsonToEntity(String json, Class<T> entity, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if(StrUtil.isNotEmpty(dateFormat)){
                mapper.setDateFormat(new SimpleDateFormat(dateFormat));
            }
            return mapper.readValue(json, entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json数据转化成EntityList对象
     * @param json
     * @param entity
     * @return
     */
    public static <T> List<T> jsonToEntityList(String json, Class<T> entity) {
        TypeFactory t = TypeFactory.defaultInstance();
        try {
            return buildObjectMapper().readValue(json, t.constructCollectionType(ArrayList.class, entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json数据转化成EntityList对象
     * @param json
     * @param entity
     * @return
     */
    public static <T> List<T> jsonToEntityList(String json, Class<T> entity, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory t = TypeFactory.defaultInstance();
        try {
            if(StrUtil.isNotEmpty(dateFormat)){
                mapper.setDateFormat(new SimpleDateFormat(dateFormat));
            }
            return mapper.readValue(json, t.constructCollectionType(ArrayList.class, entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 将对象转换为JSON字符串
     * @param o
     * @return
     */
    public static String objectToJson(Object o){
    	String re = "";
    	try {
    		re = buildObjectMapper().writeValueAsString(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return re;
    }
    
    public static String objectToJson(Object o, String dateFormat){
    	String re = "";
    	ObjectMapper mapper = new ObjectMapper();
    	if(StrUtil.isNotEmpty(dateFormat)) {
            mapper.setDateFormat(new SimpleDateFormat(dateFormat));
        }
        else {
            mapper.setDateFormat(sdf);
        }
    	try {
    		mapper.writeValueAsString(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return re;
    }
    
}
