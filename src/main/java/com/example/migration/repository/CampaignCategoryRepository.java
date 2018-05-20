package com.example.migration.repository;

import com.example.migration.entity.CampaignCategory;
import com.example.migration.entity.SegmentCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignCategoryRepository extends CrudRepository<CampaignCategory, Long> {
}
