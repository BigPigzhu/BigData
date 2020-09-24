package com.shujia;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static void main(String[] args) throws Exception {
        String s = Config.get("dao.class");
        System.out.println(s);

    }

    private static Properties properties;
    static {
         properties = new Properties();

        //加载配置文件
        //FileInputStream fileInputStream = new FileInputStream("java-login\\src\\main\\resources\\config.properties");

        //获取resources  目录下面的文件输入流
        InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("config.properties");

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过ley获取value
     *
     * @param key
     * @return
     */

    public static String get(String key){
        String property = properties.getProperty(key);
        return property;
    }

}
