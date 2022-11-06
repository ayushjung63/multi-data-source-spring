package com.ayush.multidatasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.ayush.multidatasource.mapper")
@SpringBootApplication
public class MultiDataSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiDataSourceApplication.class, args);
	}

}
