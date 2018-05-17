package com.example.migration.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SegmentCategoryServiceTests {

	@Autowired
	private SegmentCategoryService segmentCategoryService;

	@Test
	public void TEST1() {
		segmentCategoryService.test1();
	}

	@Test
	public void TEST2() {
		segmentCategoryService.test2();
	}

	@Test
	public void TEST3() {
		segmentCategoryService.test3();
	}

	@Test
	public void TEST4() {
		segmentCategoryService.test4();
	}

}
