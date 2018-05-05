package com.chinaunicom.sun.springboot;

import com.chinaunicom.sun.springboot.model.ConfigBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableConfigurationProperties({ConfigBean.class})
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}
