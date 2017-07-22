package com.andyadc.scaffold.showcase.test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Ignore;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author andaicheng
 * @version 2017/7/22
 */
public class JWTTest {

    @Ignore
    @Test
    public void createSign() {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create().withIssuer("andyadc").withExpiresAt(new Date()).sign(algorithm);
            System.out.println(token);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    @Test
    public void verifySign() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhbmR5YWRjIiwiZXhwIjoxNTAwNzEzNzc0fQ.IE5UjDFqXgoJgNv5WGAG1j1N4oGIsu5HzfuTD8rrzqo";
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("andyadc").acceptNotBefore(3600).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String header = decodedJWT.getHeader();
            String payload = decodedJWT.getPayload();
            String signature = decodedJWT.getSignature();
            String tokenS = decodedJWT.getToken();
            System.out.println(header);
            System.out.println(payload);
            System.out.println(signature);
            System.out.println(tokenS);
            System.out.println(decodedJWT.getSubject());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
