package top.sumhzehn.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author SumhZehn
 * 2021/10/29 16:00
 **/
public class AreaUtil {
    public static void main(String[] args) {
        //先读取js文件内容放到string中
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(AreaUtil.class.getResource("/").getPath() + "/static/js/chinaAddress.js"));
            String line = null;
            while (true) {
                if (!((line = reader.readLine()) != null)) break;
                stringBuilder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(stringBuilder.toString());
    }
}
