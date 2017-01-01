package com.andyadc.scaffold.showcase.security;

import com.andyadc.scaffold.showcase.entity.AuthUser;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author andaicheng
 */
@Service
public class PasswordHelper {
    private static final Logger LOG = LoggerFactory.getLogger(PasswordHelper.class);

    private static final RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static final String algorithmName = "SHA-1";
    private static final int hashIterations = 1024;

    private static final HashedCredentialsMatcher matcher;

    static {
        matcher = new HashedCredentialsMatcher(algorithmName);
        matcher.setHashIterations(hashIterations);
    }

    public void encryptPassword(AuthUser user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String password = new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), hashIterations).toHex();
        user.setPassword(password);
    }

    public boolean verifyPassword(AuthUser user, String plainPassword) {
        try {
            AuthenticationToken token = new UsernamePasswordToken(user.getAccount(), plainPassword);
            AuthenticationInfo info = new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), "verifyPassword");
            return matcher.doCredentialsMatch(token, info);
        } catch (Exception e) {
            LOG.error("verifyPassword error", e);
        }
        return false;
    }
}
