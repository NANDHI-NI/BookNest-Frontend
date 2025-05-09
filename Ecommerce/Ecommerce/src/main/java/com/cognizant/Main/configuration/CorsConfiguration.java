//package com.cognizant.Main.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfiguration {
//
//	public WebMvcConfigurer corsConfigurer()
//	{
//		return new WebMvcConfigurer() {
//			public void addCorsMapping(CorsRegistry corsRegistry) {
//				corsRegistry
//				.addMapping("/**")
//				.allowedOrigins("http://localhost:4200")
//				.allowedMethods("GET","POST,PUT,DELETE")
//				.allowedHeaders("*");
//			}
//		};
//	}
//}
