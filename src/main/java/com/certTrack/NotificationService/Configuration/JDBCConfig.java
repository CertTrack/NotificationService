package com.certTrack.NotificationService.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JDBCConfig {
	@Bean
	public JdbcTemplate jdbcTemplate(javax.sql.DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
