package com.kongmu373.accounting.config;

import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.service.UserInfoService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserRealm extends AuthorizingRealm {

    private final UserInfoService userInfoService;

    @Autowired
    public UserRealm(UserInfoService userInfoService,
                     HashedCredentialsMatcher matcher) {
        super(matcher);
        this.userInfoService = userInfoService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        UserInfoDto currentUser = userInfoService.getUserInfoByUserName(username);
        ByteSource salt = ByteSource.Util.bytes(currentUser.getSalt());
        return new SimpleAuthenticationInfo(currentUser.getUsername(), currentUser.getPassword(), salt, this.getName());
    }

}
