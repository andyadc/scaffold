package com.andyadc.scaffold.aes;

import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * @author andy.an
 * @since 2018/1/18
 */
public class AESTest {

    @Test
    public void testAES() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();
            System.out.println(secretKey.getAlgorithm());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
