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
import java.util.Map;
import javax.servlet.Filter;

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
    public ShiroFilterFactoryBean shiroFilterFactoryBean(WebSecurityManager securityManager) throws Exception {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new CustomShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc", new CustomFormAuthenticationFilter());
        LinkedHashMap<String, String> shiroFilterDefinitionMap = new LinkedHashMap<>();
        shiroFilterDefinitionMap.put("/v1.0/session", "anon");
        shiroFilterDefinitionMap.put("/v1.0/tags/**::POST", "authc");
        shiroFilterDefinitionMap.put("/v1.0/tags/**::PUT", "authc");
        shiroFilterDefinitionMap.put("/v1.0/tags/**", "anon");

        shiroFilterDefinitionMap.put("/v1.0/users/**::POST", "anon");
        shiroFilterDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * The bean for HashedCredentialsMatcher.
     *
     * @return HashedCredentialsMatcher bean.
     */
    @Bean
    public HashedCredentialsMatcher matcher() {
        val matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("SHA-256");
        matcher.setHashIterations(HASH_ITERATIONS);
        matcher.setStoredCredentialsHexEncoded(false);
        return matcher;
    }
}
