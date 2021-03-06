package com.qianbao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
public class QianbaoAbsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(QianbaoAbsBackendApplication.class, args);
	}
}
