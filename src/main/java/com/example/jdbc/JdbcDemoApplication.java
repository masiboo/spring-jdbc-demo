package com.example.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class JdbcDemoApplication {

	@Autowired
	JdbcService jdbcService;

	public static void main(String[] args) {
		SpringApplication.run(JdbcDemoApplication.class, args);
	}

	@PostConstruct
	void test(){
		var con = JdbcService.getConnection();
		System.out.println(con.toString());

		jdbcService.insertIntoPostgresql();
	}

}
