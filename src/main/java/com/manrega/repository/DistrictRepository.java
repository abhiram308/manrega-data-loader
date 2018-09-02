package com.manrega.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DistrictRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void addDistrict(String sql) {
		jdbcTemplate.execute(sql);
	}
}
