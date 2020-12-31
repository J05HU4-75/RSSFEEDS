package com.demo.rssfeeds.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.demo.rssfeeds.webmvc.service.RSSFeedService;

/**
 * This classes handles Spring Webmvc configuration
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean("feedService")
	public RSSFeedService feedService() {
		return new RSSFeedService();
	}

}
