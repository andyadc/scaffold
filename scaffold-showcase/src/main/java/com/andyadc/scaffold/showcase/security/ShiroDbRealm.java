package com.andyadc.scaffold.showcase.security;

import com.andyadc.scaffold.showcase.entity.AuthUser;
import com.andyadc.scaffold.showcase.service.SystemService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author andaicheng
 */
public class ShiroDbRealm extends AuthorizingRealm {

    private SystemService systemService;

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("user");
        return info;
    }

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        AuthUser authUser = systemService.findByAccount(usernamePasswordToken.getUsername());
        if (authUser == null) {
            throw new UnknownAccountException();
        }
        if (AuthUser.DELETED == authUser.getIsDeleted()) {
            throw new UnknownAccountException();
        }
        if (AuthUser.STATUS_BLOCKED == authUser.getStatus()) {
            throw new LockedAccountException();
        }

        return new SimpleAuthenticationInfo(new ShiroUser(authUser.getId(), authUser.getAccount()), authUser.getPassword(),
                ByteSource.Util.bytes(authUser.getCredentialsSalt()), "Shiro Db Realm");
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public static class ShiroUser implements Serializable {

        private static final long serialVersionUID = 4343812234185635487L;
        private Long id;
        private String account;

        public ShiroUser(Long id, String account) {
            this.id = id;
            this.account = account;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }


        @Override
        public int hashCode() {
            return Objects.hashCode(account);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (account == null) {
                if (other.account != null) {
                    return false;
                }
            } else if (!account.equals(other.account)) {
                return false;
            }
            return true;
        }
    }
}
