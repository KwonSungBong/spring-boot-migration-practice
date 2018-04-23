package com.example.migration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//@NoArgsConstructor
//@AllArgsConstructor
@Data
public class ProgramDto {
    private String id;
    private String name;
    private String originalName;
    private String category1Name;
    private String category2Name;
    private String director;
    private String cast;
    private int grade;
    private String summary;
    private String production;
    private int productYear;
    private String productCountry;
    private CategoryDto category;


    public ProgramDto() {
    }

    public ProgramDto(String id, String name, String originalName, String category1Name, String category2Name, String director, String cast,
                      int grade, String summary, String production, int productYear, String productCountry) {
        this.id = id;
        this.name = name;
        this.originalName = originalName;
        this.category1Name = category1Name;
        this.category2Name = category2Name;
        this.director = director;
        this.cast = cast;
        this.grade = grade;
        this.summary = summary;
        this.production = production;
        this.productYear = productYear;
        this.productCountry = productCountry;
    }

    public ProgramDto(String id, String name, String originalName, String category1Name, String category2Name, String director, String cast,
                      int grade, String summary, String production, int productYear, String productCountry, CategoryDto category) {
        this.id = id;
        this.name = name;
        this.originalName = originalName;
        this.category1Name = category1Name;
        this.category2Name = category2Name;
        this.director = director;
        this.cast = cast;
        this.grade = grade;
        this.summary = summary;
        this.production = production;
        this.productYear = productYear;
        this.productCountry = productCountry;
        this.category = category;
    }

    public ProgramDto(String id, String name, String originalName, String category1Name, String category2Name, String director, String cast,
                      int grade, String summary, String production, int productYear, String productCountry,
                      long categoryId, String categoryName, boolean categoryIsUsed, Date categoryCreatedAt, Date categoryModifiedAt) {
        this.id = id;
        this.name = name;
        this.originalName = originalName;
        this.category1Name = category1Name;
        this.category2Name = category2Name;
        this.director = director;
        this.cast = cast;
        this.grade = grade;
        this.summary = summary;
        this.production = production;
        this.productYear = productYear;
        this.productCountry = productCountry;

        CategoryDto categoryDto = new CategoryDto(categoryId, categoryName, categoryIsUsed, categoryCreatedAt, categoryModifiedAt);
        this.category = categoryDto;
    }
}
