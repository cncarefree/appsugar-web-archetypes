package org.appsugar.controller;

import java.util.Arrays;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * controller层配置
 * @author NewYoung
 * 2016年6月25日上午10:23:54
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableWebMvc
public class ControllerConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/*");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * 保证shiro验证
	 */
	@DependsOn("lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator autoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}

	@Bean
	public FilterRegistrationBean openEntityManagerInViewFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new OpenEntityManagerInViewFilter());
		bean.setUrlPatterns(Arrays.asList("/*"));
		bean.setAsyncSupported(true);
		bean.setName("openEntityManagerInViewFilter");
		bean.setOrder(1);
		return bean;
	}

	/**
	 * security filter
	 * @return
	 */
	@Bean
	public FilterRegistrationBean delegatingFilterProxy() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new DelegatingFilterProxy());
		bean.setUrlPatterns(Arrays.asList("/*"));
		bean.setName("shiroFilter");
		bean.addInitParameter("targetFilterLifecycle", "true");
		bean.setAsyncSupported(true);
		bean.setOrder(2);
		return bean;
	}

	/**
	 * 编码filter
	 */
	@Bean
	public FilterRegistrationBean characterEncodingFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new CharacterEncodingFilter());
		bean.setUrlPatterns(Arrays.asList("/*"));
		bean.addInitParameter("encoding", "UTF-8");
		bean.setAsyncSupported(true);
		bean.setName("characterEncodingFilter");
		bean.setOrder(3);
		return bean;
	}

}
