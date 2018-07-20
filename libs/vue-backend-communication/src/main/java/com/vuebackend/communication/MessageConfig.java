package com.vuebackend.communication;

import com.vuebackend.communication.messageconverters.CreateTokenRequestConverter;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MessageConfig implements WebMvcConfigurer {

    @Bean
	public HttpMessageConverters addCustomConverters() {
        HttpMessageConverter<?> additional = new CreateTokenRequestConverter();
		return new HttpMessageConverters(additional);
	}
}