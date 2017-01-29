package cane.brothers.spring.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cane.brothers.spring.Application;
import cane.brothers.spring.servlet.GoogleAuthorizationServlet;
import cane.brothers.spring.servlet.GoogleCallbackServlet;

@Configuration
public class WebXml extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	@Bean
	public ServletRegistrationBean AuthorizationServletRegistrationBean(){
	    return new ServletRegistrationBean(new GoogleAuthorizationServlet(),"/ask");
	}
	
	@Bean
	public ServletRegistrationBean CallbackServletRegistrationBean(){
	    return new ServletRegistrationBean(new GoogleCallbackServlet(),"/oauth2callback");
	}
}
