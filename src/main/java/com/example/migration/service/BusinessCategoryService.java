package com.example.migration.service;

import com.example.migration.entity.BusinessCategory;
import com.example.migration.entity.Campaign;
import com.example.migration.entity.CampaignCategory;
import com.example.migration.entity.Category;
import com.example.migration.repository.BusinessCategoryRepository;
import com.example.migration.repository.CampaignCategoryRepository;
import com.example.migration.repository.CampaignRepository;
import com.example.migration.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;

@Service
public class BusinessCategoryService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BusinessCategoryRepository businessCategoryRepository;

    @Transactional
    public void test1() {
        Category root = categoryRepository.
                findCategoryByRootName(Category.BUSINESS_CATEGORY_ROOT_NAME);
        if(root == null) {
            entityManager.createNativeQuery("INSERT INTO category(`name`, `is_used`) value('BUSINESS', 1)").executeUpdate();
            entityManager.createNativeQuery("UPDATE category SET `full_path`=id, `full_path_name`=`name` WHERE `name`='BUSINESS' AND `parent` IS NULL").executeUpdate();
        }
    }

    public void test2() {
        Campaign campaign = getCampaign();
        Category category = getAndCreateCategory(campaign.getName());

        BusinessCategory businessCategory = new BusinessCategory();
        businessCategory.setCampaign(campaign);
        businessCategory.setCategory(category);

        businessCategoryRepository.save(businessCategory);

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

        return campaign;
    }

    private Category getAndCreateCategory(String businessName) {
        Category category = categoryRepository.
                findByRootNameAndName(Category.BUSINESS_CATEGORY_ROOT_NAME, businessName);

        if(category == null) {
            Category root = categoryRepository.
                    findCategoryByRootName(Category.BUSINESS_CATEGORY_ROOT_NAME);
            category = new Category();
            category.setName(businessName);
            category.setParent(root);
            category.setCreatedAt(new Date());
            categoryRepository.save(category);
        }

        return category;
    }

}
