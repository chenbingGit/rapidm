package com.chenbing.rapidm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//不启动web容器
//@SpringBootApplication(exclude = {DispatcherServletAutoConfiguration.class})
@SpringBootApplication
public class RapidMApplication {
	public static void main(String[] args) {
		SpringApplication.run(RapidMApplication.class, args);
	}

}
