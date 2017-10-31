package org.baize;

import org.baize.config.ClassPathXmlApplicationContext;
import org.baize.logic.BeanFactery;
import org.baize.test.C;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        BeanFactery ctx = new ClassPathXmlApplicationContext("org.baize.test");
        System.out.println(ctx.getBean("c"));
        System.out.println(ctx.getBean(C.class));
    }
}
