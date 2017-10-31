package org.baize.logic;

public interface BeanFactery {
    Object getBean(String beanName);
    <T> T getBean(Class<T> t);
}
