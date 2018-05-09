package com.example.migration.repository;

import com.example.migration.dto.ProgramDto;
import com.example.migration.entity.Program;
import com.example.migration.entity.ProgramCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProgramRepository extends CrudRepository<Program, String> {

    @Query(name = "selectProgramCategoryList")
    List<ProgramCategory> getProgramCategoryList();

    @Modifying(clearAutomatically = true)
    @Query(name = "updateProgramCategoryByCategoryIsNull")
    int updateProgramCategoryByCategoryIsNull();

    @Query(name = "selectProgramDtoList")
    List<ProgramDto> selectProgramDtoList();

    @Query(name = "selectProgramAndCategoryList")
    List<Object[]> selectProgramAndCategoryArrayList();

    @Query(name = "selectProgramAndCategoryList")
    List<Program> selectProgramAndCategoryList();

    @Query(name = "selectProgramAndCategoryWrapperList")
    List<Object[]> selectProgramAndCategoryWrapperList();

    @Query("SELECT p.id FROM Program p WHERE p.id IN :idList")
    Set<String> findIdSetByIdList(@Param("idList") Set<String> idList);

}
