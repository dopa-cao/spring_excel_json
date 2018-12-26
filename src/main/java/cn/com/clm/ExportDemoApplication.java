package cn.com.clm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"cn.com"})
public class ExportDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExportDemoApplication.class, args);
	}

}

