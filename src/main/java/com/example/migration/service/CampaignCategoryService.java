package com.example.migration.service;

import com.example.migration.entity.Campaign;
import com.example.migration.entity.CampaignCategory;
import com.example.migration.entity.Category;
import com.example.migration.repository.CampaignCategoryRepository;
import com.example.migration.repository.CampaignRepository;
import com.example.migration.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CampaignCategoryService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CampaignCategoryRepository campaignCategoryRepository;

    @Transactional
    public void test1() {
        Category root = categoryRepository.
                findCategoryByRootName(Category.CAMPAIGN_CATEGORY_ROOT_NAME);
        if(root == null) {
            entityManager.createNativeQuery("INSERT INTO category(`name`, `is_used`) value('CAMPAIGN', 1)").executeUpdate();
            entityManager.createNativeQuery("UPDATE category SET `full_path`=id, `full_path_name`=`name` WHERE `name`='CAMPAIGN' AND `parent` IS NULL").executeUpdate();
        }
    }

    public void test2() {
        Campaign campaign = getCampaign();
        Category category = getAndCreateCategory(campaign.getName());

        CampaignCategory campaignCategory = new CampaignCategory();
        campaignCategory.setCampaign(campaign);
        campaignCategory.setCategory(category);

        campaignCategoryRepository.save(campaignCategory);

        System.out.println();
    }

    @Transactional
    public void test3() {
        Iterable<Campaign> campaignIterable = campaignRepository.findAll();

        System.out.println();
    }

    private Campaign getCampaign() {
        Campaign campaign = new Campaign();
        campaign.setName("test9");
        campaign.setTagList(Arrays.asList("a", "b", "c", "d", "e"));
        return campaign;
    }

    private Category getAndCreateCategory(String campaignName) {
        Category category = categoryRepository.
                findByRootNameAndName(Category.CAMPAIGN_CATEGORY_ROOT_NAME, campaignName);

        if(category == null) {
            Category root = categoryRepository.
                    findCategoryByRootName(Category.CAMPAIGN_CATEGORY_ROOT_NAME);
            category = new Category();
            category.setName(campaignName);
            category.setParent(root);
            category.setCreatedAt(new Date());
            categoryRepository.save(category);
        }

        return category;
    }

}
