package com.example.migration.repository;

import com.example.migration.entity.Program;
import com.example.migration.entity.ProgramCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends CrudRepository<Program, String> {

    @Query(name = "selectProgramCategoryList")
    List<ProgramCategory> getProgramCategoryList();

    @Modifying(clearAutomatically = true)
    @Query(name = "updateProgramCategoryByCategoryIsNull")
    int updateProgramCategoryByCategoryIsNull();

}
