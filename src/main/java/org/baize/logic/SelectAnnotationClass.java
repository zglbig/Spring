package org.baize.logic;

import org.apache.commons.lang3.StringUtils;
import org.baize.annotation.Autowired;
import org.baize.annotation.PostConstruct;
import org.baize.annotation.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者： 白泽
 * 时间： 2017/10/31.
 * 描述：
 */
public class SelectAnnotationClass {
    /**
     * 获取拥有Service注解的所有类
     * @param path 包路径
     * @return
     */
    public static Map<String,Object> getIocClazz(String path){
        List<Class> clazzs = GetFileAllInit.getClasssFromPackage(path);
        Map<String,Class> beanMap = new HashMap<>();
        if (clazzs.size()<=0)
            return new HashMap<>();
        for (Class c:clazzs) {
            Annotation annotation = c.getAnnotation(Service.class);
            if(annotation instanceof Service){
                Service service = (Service) annotation;
                String key = service.value();
                if(key == null || key.equals(""))
                    key = StringUtils.uncapitalize(c.getSimpleName());
                beanMap.put(key,c);
            }
        }
        return reflectBean(beanMap);
    }

    /**
     * 反射对象
     * @param beans
     */
    private static Map<String,Object> reflectBean(Map<String,Class> beans){
        if (beans.size()<=0) return new HashMap<>();
        Map<String,Object> beanMap = new HashMap<>();
        for (Map.Entry<String,Class> e:beans.entrySet()) {
            Object o = null;
            try {
                o = e.getValue().newInstance();
                if (o != null) {
                    beanMap.put(e.getKey(), o);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return reflectField(beanMap);
    }

    /**
     * 初始化对象
     */
    private static Map<String,Object> reflectField(Map<String,Object> objectMap) {
        if (objectMap.size()<=0)
            return new HashMap<>();
        for (Map.Entry<String,Object> o: objectMap.entrySet() ) {
            Field[] fields = o.getValue().getClass().getDeclaredFields();
            if (fields.length<=0)
                return new HashMap<>();
            for (Field f:fields) {
                Annotation ann = f.getAnnotation(Autowired.class);
                if (ann instanceof Autowired) {
                    Autowired aut = (Autowired) ann;
                    String key =  aut.name();
                    if (key == null||key.equals("")){
                        Class c = (Class) f.getGenericType();
                        key = StringUtils.uncapitalize(c.getSimpleName());
                    }
                    Object bean = objectMap.get(key);
                    if(bean == null)
                        new RuntimeException("注入异常，spring容器中没有"+o.getClass().getSimpleName()+"类中的中的字段没有"+f.getName()+"属性对应的对象");
                    Method setMethod = getWriteMethod(o.getValue(),f.getName());
                    try {
                        setMethod.invoke(o.getValue(),bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            Method[] method = o.getValue().getClass().getDeclaredMethods();
            if(method == null || method.length == 0)
                return new HashMap<>();
            for (Method m:method){
                Annotation[] annotations = m.getDeclaredAnnotations();
                for(Annotation annotation : annotations){
                    if(annotation instanceof PostConstruct){
                        try {
                            m.setAccessible(true);
                            m.invoke(o.getValue());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return objectMap;
    }
    private static Method getWriteMethod(Object beanObj, String name) {
        Method m = null;
        Field ff = null;
        String methodName = "set" + StringUtils.capitalize(name);
        try {
            ff = beanObj.getClass().getDeclaredField(name);
            ff.setAccessible(true);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            m = beanObj.getClass().getMethod(methodName,ff.getType());
        } catch (Exception e) {
           e.printStackTrace();
        }
        return m;
    }
}
