package com.kongmu373.accounting.config;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class CustomPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {

    private static final String DEFAULT_PATH_SEPARATOR = "/";

    @Override
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        }

        String requestUri = getPathWithinApplication(request);

        // in spring web, the requestUri "/resource/menus" ---- "resource/menus/" bose can access the resource
        // but the pathPattern match "/resource/menus" can not match "resource/menus/"
        // user can use requestUri + "/" to simply bypassed chain filter, to bypassed shiro protect
        if (requestUri != null && !DEFAULT_PATH_SEPARATOR.equals(requestUri)
                && requestUri.endsWith(DEFAULT_PATH_SEPARATOR)) {
            requestUri = requestUri.substring(0, requestUri.length() - 1);
        }


        //the 'chain names' in this implementation are actually path patterns defined by the user.  We just use them
        //as the chain name for the FilterChainManager's requirements
        for (String pathPattern : filterChainManager.getChainNames()) {
            // If the path does match, then pass on to the subclass implementation for specific checks:
            if (isHttpRequestMatched(pathPattern, requestUri, request)) {
                return filterChainManager.proxy(originalChain, pathPattern);
            }
        }

        return null;
    }

    private boolean isHttpRequestMatched(String chain, String currentPath, ServletRequest request) {
        val array = chain.split("::");
        val url = array[0];
        boolean isHttpMethodMatched = true;
        if (array.length > 1) {
            val methodInRequest = ((HttpServletRequest) request).getMethod().toUpperCase();
            val method = array[1];
            isHttpMethodMatched = method.equals(methodInRequest);
        }
        return pathMatches(url, currentPath) && isHttpMethodMatched;
    }
}
