package com.example.migration.repository;

import com.example.migration.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findByName(String name);
    List<Category> findByNameAndParentName(String name, String parentName);

    @Query(name = "selectCategoryListByRoot")
    List<Category> selectCategoryListByRoot();

    @Query(name = "selectCategoryListByRootName")
    List<Category> selectCategoryListByRoot(@Param("rootName") String rootName);

    @Query(name = "selectCategoryListByRootId")
    List<Category> selectCategoryListByRoot(@Param("rootId") Long rootId);

    @Query("select c from Category c join fetch c.parent where c.parent.parent.name = 'PROGRAM' AND c.parent.parent.parent IS NULL")
    List<Category> findParentProgram();

    @Query(name = "selectProgramCategoryRoot")
    List<Category> findProgramCategoryRoot();

    @Query(name = "selectProgramCategoryDepth1")
    List<Category> findProgramCategoryDepth1();

    @Query(name = "selectProgramCategoryDepth2")
    List<Category> findProgramCategoryDepth2();

    @Query(value = "SELECT id, name, parent, created_at, modified_at, is_used " +
            "FROM (SELECT * FROM category ORDER BY parent, id) category_sorted, " +
            "(SELECT @pv \\:= (SELECT id FROM category WHERE name='PROGRAM' AND parent IS NULL)) initialisation " +
            "WHERE FIND_IN_SET(parent, @pv) AND LENGTH(@pv \\:= CONCAT(@pv, ',', id))", nativeQuery = true)
    List<Category> test();

    @Query("SELECT c FROM Category c " +
            "WHERE c.parent IS NULL " +
            "AND c.name = :rootName")
    Category findCategoryByRootName(@Param("rootName") String rootName);


    @Query("SELECT c FROM Category c " +
            "WHERE c.parent.parent IS NULL " +
            "AND c.parent.name = :rootName")
    List<Category> findCategoryDepth1ByRootName(@Param("rootName") String rootName);

    @Query("SELECT c FROM Category c JOIN FETCH c.parent " +
            "WHERE c.parent.parent.parent IS NULL " +
            "AND c.parent.parent.name = :rootName")
    List<Category> findCategoryDepth2ByRootName(@Param("rootName") String rootName);

}
