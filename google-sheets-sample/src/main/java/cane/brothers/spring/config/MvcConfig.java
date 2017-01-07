package cane.brothers.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cane.brothers.spring.intersept.GoogleSheetsInterceptor;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Bean
    public HandlerInterceptor getControllerInterceptor() {
		HandlerInterceptor c = new GoogleSheetsInterceptor();
        return c;
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getControllerInterceptor()).addPathPatterns("/api/*");
	}
}
