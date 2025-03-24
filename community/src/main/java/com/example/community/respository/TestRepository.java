package com.example.community.respository;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestRepository {

    private final JdbcTemplate jdbcTemplate;

    public TestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> findAllNames() {
        String sql = "SELECT name FROM test_table";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("name"));
    }
}