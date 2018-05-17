package com.example.migration.service;

import com.example.migration.entity.Category;
import com.example.migration.entity.Segment;
import com.example.migration.entity.SegmentCategory;
import com.example.migration.repository.CategoryRepository;
import com.example.migration.repository.SegmentCategoryRepository;
import com.example.migration.repository.SegmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SegmentCategoryService {

    @Autowired
    private SegmentRepository segmentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SegmentCategoryRepository segmentCategoryRepository;

    public void test1() {
        Segment segment = new Segment();
        segment.setName("test2");
        segment.setConfiguration("test5");

        Category category = categoryRepository.
                findByRootNameAndName(Category.SEGMENT_CATEGORY_ROOT_NAME, segment.getName());

        if(category == null) {
            Category root = categoryRepository.findCategoryByRootName(Category.SEGMENT_CATEGORY_ROOT_NAME);
            category.setName(segment.getName());
            category.setParent(root);
            category.setCreatedAt(new Date());

            SegmentCategory segmentCategory = new SegmentCategory();
            segmentCategory.setSegment(segment);
            segmentCategory.setCategory(category);

            segmentCategoryRepository.save(segmentCategory);
        } else {
            SegmentCategory segmentCategory = new SegmentCategory();
            segmentCategory.setSegment(segment);
            segmentCategory.setCategory(category);

            segmentCategoryRepository.save(segmentCategory);
        }

        System.out.println();
    }

    public void test2() {
        Segment segment = getSegment();
        Category category = getAndCreateCategory(segment.getName());

        SegmentCategory segmentCategory = new SegmentCategory();
        segmentCategory.setSegment(segment);
        segmentCategory.setCategory(category);

        segmentCategoryRepository.save(segmentCategory);

        System.out.println();
    }

    private Segment getSegment() {
        Segment segment = new Segment();
        segment.setName("test9");
        segment.setConfiguration("test9");

        return segment;
    }

    private Category getAndCreateCategory(String segmentName) {
        Category category = categoryRepository.
                findByRootNameAndName(Category.SEGMENT_CATEGORY_ROOT_NAME, segmentName);

        if(category == null) {
            Category root = categoryRepository.
                    findCategoryByRootName(Category.SEGMENT_CATEGORY_ROOT_NAME);
            category = new Category();
            category.setName(segmentName);
            category.setParent(root);
            category.setCreatedAt(new Date());
        }

        return category;
    }

    public void test3() {
        Iterable<Segment> segmentIterable = segmentRepository.findAll();

        System.out.println();
    }

    public void test4() {
        Iterable<SegmentCategory> segmentCategoryIterable = segmentCategoryRepository.findAll();

        System.out.println();
    }

}
