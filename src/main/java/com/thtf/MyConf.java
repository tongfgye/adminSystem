package com.thtf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//@Component
@Configuration
@PropertySource("classpath:/my.prop")//classpath:/application-prod.yml   classpath:config/my.prop
public class MyConf {
	@Value("${aaa.a}")
	private String a;
	@Value("${aaa.b}")
	private String b;
	@Value("${aaa.c}")
	private String c;

	public void show() {
		System.out.println("a --- > " + a);
		System.out.println("b --- > " + b);
		System.out.println("c --- > " + c);
	}

}
