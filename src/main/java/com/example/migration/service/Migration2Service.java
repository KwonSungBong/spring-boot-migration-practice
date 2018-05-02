package com.example.migration.service;

import com.example.migration.entity.Category;
import com.example.migration.entity.Program;
import com.example.migration.repository.CategoryRepository;
import com.example.migration.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;

import static com.example.migration.entity.Category.CATEGORY_ROOT_DEPTH;
import static com.example.migration.entity.Category.PROGRAM_CATEGORY_ROOT_NAME;
import static com.example.migration.entity.Program.PROGRAM_CATEGORY_MAX_DEPTH;
import static com.example.migration.entity.Program.PROGRAM_CATEGORY_MIN_DEPTH;

@Service
public class Migration2Service {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void migration() {
        List<Program> programList = (List<Program>) programRepository.findAll();
        saveCategoryAndUpdateProgramCategory(programList);

        System.out.println();
    }

    public void saveCategoryAndUpdateProgramCategory(List<Program> programList) {
        final Date now = new Date();
        Map<String, Category>[] programCategoryDepthArray = getProgramCategoryDepthArray();

        BiFunction<Integer, String, Category> getParentProgramCategory = (depth, categorykey) -> {
            if(depth < PROGRAM_CATEGORY_MIN_DEPTH || depth > PROGRAM_CATEGORY_MAX_DEPTH)
                throw new RuntimeException("Could not find category parent");
            return programCategoryDepthArray[depth-1].get(categorykey);
        };

        Set<Category> insertCategorySet = new HashSet<>();
        for(Program program : programList) {
            for(int depth = PROGRAM_CATEGORY_MIN_DEPTH; depth <= PROGRAM_CATEGORY_MAX_DEPTH; depth++) {
                Map<String, Category> programCategoryMap = programCategoryDepthArray[depth];
                final String categoryKey = program.getCategoryFullPathName(depth);

                if(!programCategoryMap.containsKey(categoryKey)) {
                    Category programCategory = new Category();
                    programCategory.setParent(getParentProgramCategory
                            .apply(depth, program.getCategoryParentFullPathName(depth)));
                    programCategory.setName(program.getCategoryName(depth));
                    programCategory.setCreatedAt(now);

                    programCategoryMap.put(categoryKey, programCategory);
                    insertCategorySet.add(programCategory);
                }
            }

            program.setCategory(programCategoryDepthArray[PROGRAM_CATEGORY_MAX_DEPTH]
                    .get(program.getCategoryFullPathName(PROGRAM_CATEGORY_MAX_DEPTH)));
        }

        categoryRepository.save(insertCategorySet);
        programRepository.save(programList);
    }


    private Map<String, Category>[] getProgramCategoryDepthArray() {
        final Category programCategoryRoot = categoryRepository.findCategoryByRootName(PROGRAM_CATEGORY_ROOT_NAME);
        if(programCategoryRoot == null)
            throw new RuntimeException("Could not find program category root");

        List<Category> programCategoryDepth1List = categoryRepository.findCategoryDepth1ByRootName(PROGRAM_CATEGORY_ROOT_NAME);
        List<Category> programCategoryDepth2List = categoryRepository.findCategoryDepth2ByRootName(PROGRAM_CATEGORY_ROOT_NAME);

        Map<String, Category>[] programCategoryDepthArray = new Map[3];
        programCategoryDepthArray[CATEGORY_ROOT_DEPTH] = new HashMap<>();
        programCategoryDepthArray[1] = new HashMap<>();
        programCategoryDepthArray[2] = new HashMap<>();

        programCategoryDepthArray[CATEGORY_ROOT_DEPTH].put(programCategoryRoot.getFullPathName(), programCategoryRoot);
        for(Category programCategory : programCategoryDepth1List) {
            programCategoryDepthArray[1] .put(programCategory.getFullPathName(), programCategory);
        }
        for(Category programCategory : programCategoryDepth2List) {
            programCategoryDepthArray[2] .put(programCategory.getFullPathName(), programCategory);
        }

        return programCategoryDepthArray;
    }

}
