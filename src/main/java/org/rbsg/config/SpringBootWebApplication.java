package org.rbsg.config;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * 
 * 
 * @author micha
 * 
 * @EnableCaching    : essential if you want anontation cachjing to work
 * @SpringBootApplication  : denotes a Boot Spring config and that we need to look oput for the main methode
 * @bean :will be accessed on server start up 
 * @enableAutoConfiguration  : 
 * @componentScan @ComponentScan({"com.my.package.first","com.my.package.second"})  : used to scan in multiple packages
 * 
 **/


@ComponentScan({"org.rbsg.controller"})
@EnableAutoConfiguration
@EnableCaching
@SpringBootApplication
public class SpringBootWebApplication extends SpringBootServletInitializer {

	final static Logger logger = Logger.getLogger(SpringBootWebApplication.class);
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootWebApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}
	
	    @Bean
	    public CacheManager cacheManager() throws IOException {
	    	logger.info(" **** Test : cache Manager  ***"); 
	        return new EhCacheCacheManager(ehCacheCacheManager().getObject());

	    }



	    @Bean
	    public EhCacheManagerFactoryBean ehCacheCacheManager() throws IOException {
	    	
	    	logger.info(" **** Test : cache Manager Factory ***");
	        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
	        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
	        cmfb.setCacheManagerName("primes");
	        cmfb.setShared(true);
	        cmfb.afterPropertiesSet();

	        return cmfb;
	    }
}