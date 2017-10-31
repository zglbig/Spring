package org.baize.test;

import org.baize.annotation.Service;

/**
 * 作者： 白泽
 * 时间： 2017/10/31.
 * 描述：
 */
@Service
public class B {
    private String valu = "asgdfg";

    @Override
    public String toString() {
        return "B{" +
                "valu='" + valu + '\'' +
                '}';
    }

    public String getValu() {
        return valu;
    }

    public void setValu(String valu) {
        this.valu = valu;
    }
}
