package top.sumhzehn.http;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUrlHandler {
	
	/**
	 * HTTP GET请求
	 * @param url
	 * @param charset
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String sendGet(String url, String charset, Map<String, String> params) throws Exception {
		return sendGet(url, charset, params, null);
	}
	
	/**
	 *  HTTP GET请求
	 * @param url
	 * @param charset
	 * @param params
	 * @param headMap
	 * @return
	 * @throws Exception
	 */
    public static String sendGet(String url, String charset, Map<String, String> params, Map<String, String> headMap) throws Exception{
    	CloseableHttpClient client = HttpClients.createDefault();
    	// 请求报文
    	String urlNameString = url + "?" + mapParamToString(params, charset);
    	// 初始化
    	HttpGet httpGet = new HttpGet(urlNameString);
    	// 设置请求头
    	if(headMap != null) {
    		for (Map.Entry<String, String> h : headMap.entrySet()) {
        		httpGet.setHeader(h.getKey(), h.getValue());
    		}
    	}
    	try {
			return sendMsg(client.execute(httpGet), charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }

    public static String sendPost(String url, String charset, Map<String, String> params, Map<String, String> headMap) throws Exception{
    	CloseableHttpClient client = HttpClients.createDefault();
    	HttpPost httpPost = new HttpPost(url);
    	// 设置请求头
    	if(headMap != null) {
    		for (Map.Entry<String, String> h : headMap.entrySet()) {
    			httpPost.setHeader(h.getKey(), h.getValue());
    		}
    	}
    	//设置参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> p : params.entrySet()) {
        	list.add(new BasicNameValuePair(p.getKey(), p.getValue()));
		}
        if(list.size() > 0){
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
            httpPost.setEntity(entity);
        }
        return sendMsg(client.execute(httpPost), charset);
    }
    
    /**
	 * map参数转化为字符串参数
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public static String mapParamToString(Map<String, String> params, String charset) throws Exception{
		StringBuilder buffer = new StringBuilder();
		int i = 0;
		if (params != null) {
			String value = null;
            for (Map.Entry<String, String> entry : params.entrySet()) {
            	value = entry.getValue();
            	if(value == null || "".equals(value)){
            		 continue;
            	}
            	++i;
            	if(i == 1){
                    buffer.append(entry.getKey());
                    buffer.append("=");
                    buffer.append(URLEncoder.encode(entry.getValue(), charset));
            	}
            	else{
            		buffer.append("&");
                    buffer.append(entry.getKey());
                    buffer.append("=");
                    buffer.append(URLEncoder.encode(entry.getValue(), charset));
            	}
            }
        }
		return buffer.toString();
	}
    
    private static String sendMsg(HttpResponse response, String charset) throws Exception {
        String result = null;
        if(response != null){
            HttpEntity resEntity = response.getEntity();
            if(resEntity != null){
                // 因服务器用的gzip编码传输。
                if (resEntity.getContentEncoding() != null) {
                    if("gzip".equalsIgnoreCase(resEntity.getContentEncoding().getValue())){
                        resEntity = new GzipDecompressingEntity(resEntity);
                    } else if("deflate".equalsIgnoreCase(resEntity.getContentEncoding().getValue())){
                        resEntity = new DeflateDecompressingEntity(resEntity);
                    }
                }
                result = EntityUtils.toString(resEntity, charset);
            }
        }
        return result;
    }
}
