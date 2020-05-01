package com.whdvlf94.myspringboot.runner;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseRunner implements ApplicationRunner {
	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public void run(ApplicationArguments args) throws Exception{
		try(Connection connection = dataSource.getConnection()) {
			DatabaseMetaData metaData = connection.getMetaData();
			System.out.println("URL = " + metaData.getURL());
			System.out.println("User = " + metaData.getUserName());
			System.out.println("DataSource Class Name = " + metaData.getClass().getName());
//			
//			//CUSTOMER Table 생성, h2 Console에서 확인 
//			String sql = "CREATE TABLE CUSTOMER (ID INTEGER NOT NULL, name VARCHAR(255), PRIMARY\r\n" + "KEY (id))"; 
//			Statement statement = connection.createStatement();
//			statement.executeUpdate(sql);
//			
//			//jdbcTemplate 이용해서 Table 값 추가
//			jdbcTemplate.execute("insert into customer values(1,'jdbcTemplate')");
		}
	}

}
