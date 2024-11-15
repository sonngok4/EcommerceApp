package com.example.ecommerce_app;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EcommerceAppApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	void contextLoads() {
	}

	@Test
	public void testDatabaseConnection() {
		try (Connection conn = dataSource.getConnection()) {
			assertTrue(conn.isValid(1));
		} catch (Exception e) {
			fail("Database connection failed: " + e.getMessage());
		}
	}
}