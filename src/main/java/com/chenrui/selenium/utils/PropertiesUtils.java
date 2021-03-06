package com.chenrui.selenium.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {
    private static Map<String,String> configures=new HashMap<String,String>();
    static{
        loadProperties();
    }

    private PropertiesUtils(){}

    /**
     * 加载配置文件资源
     */
    private static void loadProperties(){
        String[] properties = {"webelement.properties","cookie.properties"};
        try {
            for (String configure : properties) {
                Properties propertyFiles = getProperties(configure);
                for(Object key:propertyFiles.keySet()){
                    configures.put(key.toString(), propertyFiles.getProperty(key.toString()));
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Properties getProperties(String configure) throws IOException {
        InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream(configure);
        Properties propertyFiles=new Properties();
        propertyFiles.load(inputStream);
        return propertyFiles;
    }

    /**
     * 根据配置文件的key，返回对应的配置值
     * @param configKey
     * @return
     */
    public static String getConfig(String configKey){
        return configures.get(configKey);
    }
    /**
     * 描述：根据配置文件的key，返回对应的配置值，如果configKey不存在,或者值为空的字符串，则返回默认值defaultValue
     * @param configKey
     * @param defaultValue
     * @return
     */
    public static String getConfig(String configKey,String defaultValue){
        String v=configures.get(configKey);
        if(v==null || "".equals(v.trim())){
            return defaultValue;
        }
        return v;
    }
    /**
     * 描述：属性文件中是否包含键configKey，包含返回true
     * @param configKey
     * @return
     */
    public static boolean contentKey(String configKey){
        return configures.containsKey(configKey);
    }

    /**
     * 描述：根据配置文件的key，返回对应的配置值，如果configKey不存在,或者值为空的字符串，则返回默认值defaultValue
     * @param configKey
     * @param defaultValue
     * @param clazz  需要返回的数据类型：支持Integer.class，Double.class，Boolean.class，Float.class，Long.class，String.class
     * @return
     */
    public static <E> E getConfig(String configKey,E defaultValue,Class<E> clazz){
        E ret = defaultValue;
        String v=configures.get(configKey);
        if(v==null || "".equals(v.trim())){
            return ret;
        }
        if(clazz.equals(Integer.class)){
            try{ret =clazz.cast(Integer.valueOf(v));}catch(Exception e){}
        }else if(clazz.equals(Double.class)){
            try{ret =clazz.cast(Double.valueOf(v));}catch(Exception e){}
        }else if(clazz.equals(Boolean.class)){
            try{ret =clazz.cast("1".equals(v)?true:Boolean.valueOf(v));}catch(Exception e){}
        }else if(clazz.equals(Float.class)){
            try{ret =clazz.cast(Float.valueOf(v));}catch(Exception e){}
        }else if(clazz.equals(Long.class)){
            try{ret =clazz.cast(Long.valueOf(v));}catch(Exception e){}
        }else if(clazz.equals(String.class)){
            try{ret =clazz.cast(String.valueOf(v));}catch(Exception e){}
        }
        return ret;
    }

    /**
     * 根据配置文件的key，修改对应的值
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean updateConfig(String key, String value,String fileName) {
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
            configures.put(key, value);
            String filePath = "./src/main/resources/";
            filePath += fileName + ".properties";
            // 更新配置文件里的内容
            try {
                OutputStream out = new FileOutputStream(new File(filePath));
                Properties properties = new Properties();
                properties.setProperty(key, value);
                properties.store(out,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }
}