package com.example.migration.service;

import com.example.migration.entity.Category;
import com.example.migration.entity.Program;
import com.example.migration.entity.ProgramCategory;
import com.example.migration.repository.CategoryRepository;
import com.example.migration.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class MigrationService {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void migration() {
        importProgramCategory();

        System.out.println();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @Transactional
    public void importProgramCategory() {
        List<Category> rootCategoryList = categoryRepository.findProgramCategoryRoot();
        if(rootCategoryList.size() == 0) return;

        final Date now = new Date();
        final String SEPARATOR = "|";

        Category rootProgramCategory = rootCategoryList.get(0);
        List<Category> categoryList = categoryRepository.selectCategoryListByRoot(rootProgramCategory.getId());
        List<Program> programList = (List<Program>) programRepository.findAll();

        Map<String, Category> programCategoryDepth1Map = new HashMap<>();
        Map<String, Category> programCategoryDepth2Map = new HashMap<>();

        for(Category programCategory : categoryList) {
            if(programCategory.getParent().getId() == rootProgramCategory.getId()) {
                programCategoryDepth1Map.put(programCategory.getName(), programCategory);
            } else if(programCategory.getParent().getParent().getId() == rootProgramCategory.getId()) {
                programCategoryDepth2Map.put(programCategory.getParent().getName()+SEPARATOR+programCategory.getName(), programCategory);

            }
        }

//        List<Category> programCategoryDepth1List = categoryRepository.findProgramCategoryDepth1();
//        List<Category> programCategoryDepth2List = categoryRepository.findProgramCategoryDepth2();
//
//        Map<String, Category> programCategoryDepth1Map = new HashMap<>();
//        Map<String, Category> programCategoryDepth2Map = new HashMap<>();
//
//        for(Category programCategory : programCategoryDepth1List) {
//            programCategoryDepth1Map.put(programCategory.getName(), programCategory);
//        }
//        for(Category programCategory : programCategoryDepth2List) {
//            programCategoryDepth2Map.put(programCategory.getParent().getName()+SEPARATOR+programCategory.getName(), programCategory);
//        }

        List<Category> insertCategoryList = new ArrayList<>();
        List<String> duplicationCategoryDepth1NameList = new ArrayList<>();
        List<String> duplicationCategoryDepth2NameList = new ArrayList<>();

        for(Program program : programList) {
            String categoryDepth1Key = program.getCategory1Name();
            String categoryDepth2Key = program.getCategory1Name()+SEPARATOR+program.getCategory2Name();

            if(programCategoryDepth1Map.get(categoryDepth1Key) == null &&
                    !duplicationCategoryDepth1NameList.contains(categoryDepth1Key)) {
                Category programCategoryDepth1 = new Category();
                programCategoryDepth1.setParent(rootProgramCategory);
                programCategoryDepth1.setName(program.getCategory1Name());
                programCategoryDepth1.setCreatedAt(now);

                insertCategoryList.add(programCategoryDepth1);
                programCategoryDepth1Map.put(program.getCategory1Name(), programCategoryDepth1);

                duplicationCategoryDepth1NameList.add(categoryDepth1Key);
            }
            if(programCategoryDepth2Map.get(categoryDepth2Key) == null &&
                    !duplicationCategoryDepth2NameList.contains(categoryDepth2Key)) {
                Category programCategoryDepth2 = new Category();
                programCategoryDepth2.setParent(programCategoryDepth1Map.get(program.getCategory1Name()));
                programCategoryDepth2.setName(program.getCategory2Name());
                programCategoryDepth2.setCreatedAt(now);

                insertCategoryList.add(programCategoryDepth2);

                duplicationCategoryDepth2NameList.add(categoryDepth2Key);
            }
        }

        categoryRepository.save(insertCategoryList);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private void saveProgramCategory(List<Program> programList) {
        final Date now = new Date();
        final String SEPARATOR = "|";
        List<Category> programCategoryDepth2List = categoryRepository.findProgramCategoryDepth2();

        Category rootProgramCategory = programCategoryDepth2List.get(0).getParent().getParent();
        Map<String, Category> programCategoryDepth1Map = new HashMap<>();
        Map<String, Category> programCategoryDepth2Map = new HashMap<>();

        for(Category programCategory : programCategoryDepth2List) {
            programCategoryDepth1Map.put(programCategory.getParent().getName(), programCategory.getParent());
            programCategoryDepth2Map.put(programCategory.getParent().getName()+SEPARATOR+programCategory.getName(), programCategory);
        }

        List<Category> insertCategoryList = new ArrayList<>();
        List<String> duplicationCategoryDepth1NameList = new ArrayList<>();
        List<String> duplicationCategoryDepth2NameList = new ArrayList<>();

        for(Program program : programList) {
            String categoryDepth1Key = program.getCategory1Name();
            String categoryDepth2Key = program.getCategory1Name()+SEPARATOR+program.getCategory2Name();

            if(programCategoryDepth1Map.get(categoryDepth1Key) == null &&
                    !duplicationCategoryDepth1NameList.contains(categoryDepth1Key)) {
                Category programCategoryDepth1 = new Category();
                programCategoryDepth1.setParent(rootProgramCategory);
                programCategoryDepth1.setName(program.getCategory1Name());
                programCategoryDepth1.setCreatedAt(now);

                insertCategoryList.add(programCategoryDepth1);
                programCategoryDepth1Map.put(program.getCategory1Name(), programCategoryDepth1);

                duplicationCategoryDepth1NameList.add(categoryDepth1Key);
            }
            if(programCategoryDepth2Map.get(categoryDepth2Key) == null &&
                    !duplicationCategoryDepth2NameList.contains(categoryDepth2Key)) {
                Category programCategoryDepth2 = new Category();
                programCategoryDepth2.setParent(programCategoryDepth1Map.get(program.getCategory1Name()));
                programCategoryDepth2.setName(program.getCategory2Name());
                programCategoryDepth2.setCreatedAt(now);

                insertCategoryList.add(programCategoryDepth2);

                duplicationCategoryDepth2NameList.add(categoryDepth2Key);
            }
        }

        categoryRepository.save(insertCategoryList);
    }

    private void saveProgramAndProgramCategory(List<Program> programList) {
        final Date now = new Date();
        final String SEPARATOR = "|";
        List<Category> programCategoryDepth2List = categoryRepository.findProgramCategoryDepth2();

        Category rootProgramCategory = programCategoryDepth2List.get(0).getParent().getParent();
        Map<String, Category> programCategoryDepth1Map = new HashMap<>();
        Map<String, Category> programCategoryDepth2Map = new HashMap<>();

        for(Category programCategory : programCategoryDepth2List) {
            programCategoryDepth1Map.put(programCategory.getParent().getName(), programCategory.getParent());
            programCategoryDepth2Map.put(programCategory.getParent().getName()+SEPARATOR+programCategory.getName(), programCategory);
        }

        List<Category> insertCategoryList = new ArrayList<>();
        List<String> duplicationCategoryDepth1NameList = new ArrayList<>();
        List<String> duplicationCategoryDepth2NameList = new ArrayList<>();

        for(Program program : programList) {
            String categoryDepth1Key = program.getCategory1Name();
            String categoryDepth2Key = program.getCategory1Name()+SEPARATOR+program.getCategory2Name();

            if(programCategoryDepth1Map.get(categoryDepth1Key) == null &&
                    !duplicationCategoryDepth1NameList.contains(categoryDepth1Key)) {
                Category programCategoryDepth1 = new Category();
                programCategoryDepth1.setParent(rootProgramCategory);
                programCategoryDepth1.setName(program.getCategory1Name());
                programCategoryDepth1.setCreatedAt(now);

                insertCategoryList.add(programCategoryDepth1);
                programCategoryDepth1Map.put(program.getCategory1Name(), programCategoryDepth1);

                duplicationCategoryDepth1NameList.add(categoryDepth1Key);
            }
            if(programCategoryDepth2Map.get(categoryDepth2Key) == null &&
                    !duplicationCategoryDepth2NameList.contains(categoryDepth2Key)) {
                Category programCategoryDepth2 = new Category();
                programCategoryDepth2.setParent(programCategoryDepth1Map.get(program.getCategory1Name()));
                programCategoryDepth2.setName(program.getCategory2Name());
                programCategoryDepth2.setCreatedAt(now);

                insertCategoryList.add(programCategoryDepth2);
                programCategoryDepth2Map.put(categoryDepth2Key, programCategoryDepth2);

                duplicationCategoryDepth2NameList.add(categoryDepth2Key);
            }

            program.setCategory(programCategoryDepth2Map.get(categoryDepth2Key));
        }

        categoryRepository.save(insertCategoryList);
        programRepository.save(programList);
    }

    @Transactional
    public void createProgramCategory() {

        List<Category> rootList = categoryRepository.findProgramCategoryRoot();
        if(rootList.size() > 0) return;

        final Date now = new Date();
        Category root = new Category();
        root.setName("PROGRAM");
        root.setCreatedAt(now);
        categoryRepository.save(root);

        List<ProgramCategory> programCategoryList = programRepository.getProgramCategoryList();

        List<Category> category1List = programCategoryList.stream()
                .map(ProgramCategory::getCategory1Name)
                .distinct()
                .map(category1Name -> new Category(category1Name, now, root))
                .collect(toList());

        categoryRepository.save(category1List);

        Map<String, Category> category1NameIdMap = category1List.stream()
                .collect(toMap(Category::getName, Function.identity()));

        List<Category> category2List = programCategoryList.stream()
                .map(programCategory -> {
                    Category category = new Category();
                    category.setName(programCategory.getCategory2Name());
                    category.setCreatedAt(now);
                    category.setParent(category1NameIdMap.get(programCategory.getCategory1Name()));
                    return category;
                }).collect(toList());

        categoryRepository.save(category2List);

        System.out.println();
    }

    @Transactional
    public void updateProgramCategoryByCategoryIsNull() {
        programRepository.updateProgramCategoryByCategoryIsNull();

        System.out.println();
    }

}
