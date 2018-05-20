package com.example.migration.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CampaignCategoryServiceTests {

	@Autowired
	private CampaignCategoryService campaignCategoryService;

	@Test
	public void TEST1() {
		campaignCategoryService.test1();
	}

	@Test
	public void TEST2() {
		campaignCategoryService.test2();
	}

	@Test
	public void TEST3() {
		campaignCategoryService.test3();
	}

}
