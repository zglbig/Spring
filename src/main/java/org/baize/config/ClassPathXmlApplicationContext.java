package org.baize.config;

import org.baize.logic.BeanFactery;
import org.baize.logic.SelectAnnotationClass;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者： 白泽
 * 时间： 2017/10/31.
 * 描述：
 */
public class ClassPathXmlApplicationContext implements BeanFactery {
    private Map<String,Object> beanByName = new HashMap<>();
    private Map<Class<?>,Object> beanByClazz = new HashMap<>();
    public ClassPathXmlApplicationContext(String path) {
        beanByName = SelectAnnotationClass.getIocClazz(path);
        for (Map.Entry<String,Object> e:beanByName.entrySet()) {
            beanByClazz.put(e.getValue().getClass(),e.getValue());
        }
    }
    @Override
    public Object getBean(String beanName) {
        if(!beanName.contains(beanName))
            new RuntimeException("没有"+beanName+"对用的对象，看看你传入的名称是否和注入的类名是否一致");
        return beanByName.get(beanName);
    }
    @Override
    public <T> T getBean(Class<T> t) {
        if(beanByClazz.containsKey(t))
            new RuntimeException("没有"+t+"对用的对象，看看你传入的名称是否和注入的类名是否一致");
        return (T)beanByClazz.get(t);
    }
}
