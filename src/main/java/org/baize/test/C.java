package org.baize.test;

import org.baize.annotation.Autowired;
import org.baize.annotation.PostConstruct;
import org.baize.annotation.Service;

/**
 * 作者： 白泽
 * 时间： 2017/10/31.
 * 描述：
 */
@Service("c")
public class C {
    @Autowired
    private A a;
    @Autowired(name = "b")
    private B b;

    @Override
    public String toString() {
        return "C{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
    @PostConstruct
    private void adfsghdraegd(){
        System.out.println("我是初始化调用");
    }
    @PostConstruct
    public void asd(){
        System.out.println("我是公共方法");
    }
    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
