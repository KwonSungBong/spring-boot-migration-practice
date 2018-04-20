package com.example.migration.repository;

import com.example.migration.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findByName(String name);
    List<Category> findByNameAndParentName(String name, String parentName);

    @Query(name = "selectCategoryListByRoot")
    List<Category> selectCategoryListByRoot();

//    @Query(name = "selectCategoryOneDepthListByRoot")
//    List<Category> selectCategoryOneDepthListByRoot();
//
//    @Query(name = "selectCategoryTwoDepthListByRoot")
//    List<Category> selectCategoryTwoDepthListByRoot();

    @Query("select c from Category c join fetch c.parent where c.parent.parent.name = 'PROGRAM' AND c.parent.parent.parent IS NULL")
    List<Category> findParentProgram();

    @Query(name = "selectProgramCategoryDepth2")
    List<Category> findProgramCategoryDepth2();

}
