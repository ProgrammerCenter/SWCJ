package com.midream.sheep.swcj.data;

import com.midream.sheep.swcj.Exception.InterfaceIllegal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author midreamSheep
 * 字符常量池
 * */
public class Constant {
    //默认生成的包名
    public static final String DEFAULT_PACKAGE_NAME = "com";
    private static final Map<String,String> CLASS_KEY_VALUE;
    static {
        CLASS_KEY_VALUE = new HashMap<>();
        CLASS_KEY_VALUE.put("class java.lang.String","String");
        CLASS_KEY_VALUE.put("class [Ljava.lang.String;","String[]");
    }
    private Constant(){}
    public static String getClassName(String key) throws InterfaceIllegal {
        String s = CLASS_KEY_VALUE.get(key);
        if(key.equals("void")){
            throw new InterfaceIllegal("接口返回值不能为void");
        }
        if(s!=null){
            return s;
        }
        return key;
    }
}
