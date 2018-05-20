package com.example.migration.repository;

import com.example.migration.entity.BusinessCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessCategoryRepository extends CrudRepository<BusinessCategory, Long> {
}
