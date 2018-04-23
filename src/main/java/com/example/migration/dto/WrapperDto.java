package com.example.migration.dto;

import lombok.Data;

@Data
public class WrapperDto {
    private String programId;
    private Long categoryId;
    private String fullName;

    public WrapperDto() {
    }

    public WrapperDto(String programId, Long categoryId, String name, String categoryName) {
        this.programId = programId;
        this.categoryId = categoryId;
        this.fullName = name + "|" + categoryName;
    }

}
