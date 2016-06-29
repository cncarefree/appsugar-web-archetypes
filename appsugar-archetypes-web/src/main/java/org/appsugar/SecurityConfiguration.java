package org.appsugar;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.appsugar.security.ShiroRealm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro安全配置
 * @author NewYoung
 * 2016年6月25日上午11:02:58
 */
@Configuration
public class SecurityConfiguration {
	/**
	 * shiro 安全管理
	 */
	@Bean
	public DefaultWebSecurityManager securityManager(ShiroRealm shiroRealm, CacheManager shiroCacheManager) {
		DefaultWebSecurityManager stm = new DefaultWebSecurityManager();
		stm.setRealm(shiroRealm);
		stm.setCacheManager(shiroCacheManager);
		return stm;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
		filter.setSecurityManager(securityManager);
		filter.setLoginUrl("/login");
		filter.setSuccessUrl("/main");
		filter.setFilterChainDefinitionMap(filterChainDefinitionMap());
		return filter;
	}

	public Map<String, String> filterChainDefinitionMap() {
		Map<String, String> map = new HashMap<>();
		map.put("/static/**", "anon");
		map.put("/assets/**", "anon");
		map.put("/webjars/**", "anon");
		map.put("/login", "authc");
		map.put("/logout", "logout");
		map.put("/**", "user");
		return map;
	}
}
