package com.manrega.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PanchayatRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void addPanchayat(String sql) {
		jdbcTemplate.execute(sql);
	}
}
