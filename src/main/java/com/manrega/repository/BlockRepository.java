package com.manrega.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BlockRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void addBlock(String sql) {
		jdbcTemplate.execute(sql);
	}
}