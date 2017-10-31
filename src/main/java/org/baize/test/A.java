package org.baize.test;

import org.baize.annotation.Service;

/**
 * 作者： 白泽
 * 时间： 2017/10/31.
 * 描述：
 */
@Service
public class A {
    private String name = "asdasd";

    @Override
    public String toString() {
        return "A{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
