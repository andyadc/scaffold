package com.andyadc.scaffold.util.test;

import com.andyadc.scaffold.util.serialize.SerializationUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author andaicheng
 * @version 2017/5/8
 */
public class SerializationUtilsTest {

    @Test
    public void testSerialize() {
        byte[] bytes = SerializationUtils.serialize("adc");

        String byteStr = Arrays.toString(bytes);
        System.out.println(byteStr);
    }

    @Test
    public void testDeserialize() {

    }
}
