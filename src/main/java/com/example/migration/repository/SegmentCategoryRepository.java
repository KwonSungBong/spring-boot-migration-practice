package com.example.migration.repository;

import com.example.migration.entity.SegmentCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SegmentCategoryRepository extends CrudRepository<SegmentCategory, String> {
}
