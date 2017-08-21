package com.andyadc.scaffold.showcase.test;

import java.io.Serializable;

/**
 * 枚举单例
 *
 * @author andy.an
 * @since 2017/8/17
 */
public enum EnumSingleton implements Serializable {

    INSTANCE;

    public void say(String str) {
        System.out.println(str);
    }
}
