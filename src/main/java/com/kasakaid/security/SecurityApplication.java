package com.kasakaid.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/***
 * Tomcat にデプロイするためのアプリケーションを作成するためには、
 * SpringBootServletInitializer を継承して、configure をオーバーライドしないといけない.
 * オーバーライドしないとTomcatデプロイ時に 404 になるっぽい。
 * というか、デプロイ完了後、Tomcat 起動時に、「Spring Boot」みたいなアスキー文字が出てこない。
 * The first step in producing a deployable war file is
 * to provide a SpringBootServletInitializer subclass and override its configure method.
 * This makes use of Spring Framework’s Servlet 3.0 support and allows
 * you to configure your application when it’s launched by the servlet container.
 * Typically, you update your application’s main class to extend SpringBootServletInitializer:
 * https://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html
 */
@SpringBootApplication
public class SecurityApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SecurityApplication.class);
	}
}
