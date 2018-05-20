package com.example.migration.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusinessCategoryServiceTests {

	@Autowired
	private BusinessCategoryService businessCategoryService;

	@Test
	public void TEST1() {
		businessCategoryService.test1();
	}

	@Test
	public void TEST2() {
		businessCategoryService.test2();
	}

	@Test
	public void TEST3() {
		businessCategoryService.test3();
	}

}
