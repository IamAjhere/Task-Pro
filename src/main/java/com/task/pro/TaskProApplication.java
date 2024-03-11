package com.task.pro;

import com.task.pro.config.SecretConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableConfigurationProperties(SecretConfiguration.class)
public class TaskProApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaskProApplication.class, args);
	}
}
