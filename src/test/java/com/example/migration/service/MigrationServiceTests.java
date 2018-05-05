package com.example.migration.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MigrationServiceTests {

	@Autowired
	private MigrationService migrationService;

	@Autowired
	private Migration2Service migration2Service;

	@Test
	public void TEST() {
		migrationService.migration();
	}

	@Test
	public void TEST2() {
		migration2Service.migration();
	}

}
