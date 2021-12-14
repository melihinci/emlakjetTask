package com.emlakjet.task.mi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
@SpringBootApplication
public class Application {

	@Autowired
	DataSourceProperties appConf;

	@DependsOn("dataSourceProperties")
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create()
				.driverClassName(appConf.getDriverClassName())
				.url(appConf.getUrl())
				.username(appConf.getUsername())
				.password(appConf.getPassword())
				.type(com.mysql.cj.jdbc.MysqlDataSource.class);
		return dataSourceBuilder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
