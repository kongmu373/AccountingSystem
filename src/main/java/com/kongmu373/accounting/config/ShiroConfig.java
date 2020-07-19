package com.kongmu373.accounting.config;

import lombok.val;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    public static final int HASH_ITERATIONS = 1000;

    @Bean
    public WebSecurityManager securityManager(Realm realm) {
        return new DefaultWebSecurityManager(realm);
    }

    /**
     * Shiro Filter, 实现权限相关的拦截.
     * anon: no matter who access
     * authc: required login -> access
     * user: remember me -> access
     * role: role -> access
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(WebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        LinkedHashMap<String, String> shiroFilterDefinitionMap = new LinkedHashMap<>();
        // TODO: consider different HTTP method may need different filter.
        shiroFilterDefinitionMap.put("/v1.0/users", "anon");
        shiroFilterDefinitionMap.put("/v1.0/session", "anon");
        shiroFilterDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public HashedCredentialsMatcher matcher() {
        val matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("SHA-256");
        matcher.setHashIterations(HASH_ITERATIONS);
        matcher.setStoredCredentialsHexEncoded(false);
        return matcher;
    }
}
