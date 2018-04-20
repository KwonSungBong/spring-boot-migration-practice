package com.example.migration.entity;

import lombok.Data;

import javax.persistence.*;

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "ProgramCategoryResult",
                classes = {
                        @ConstructorResult(
                                targetClass = ProgramCategory.class,
                                columns = {
                                        @ColumnResult(name = "category1Name", type = String.class),
                                        @ColumnResult(name = "category2Name", type = String.class)
                                }
                        )
                }
        )
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "selectProgramCategoryList",
                query = "SELECT DISTINCT category1_name AS category1Name, category2_name AS category2Name " +
                        "FROM program ORDER BY category1_name, category2_name",
                resultSetMapping = "ProgramCategoryResult"
        ),
        @NamedNativeQuery(
                name    = "updateProgramCategoryList",
                query   = "UPDATE program a " +
                        "JOIN (SELECT a.id AS category_id, b.name AS category1_name, a.name AS category2_name " +
                        "FROM category a  " +
                        "JOIN category b  " +
                        "ON a.parent = b.id " +
                        "JOIN category c " +
                        "ON b.parent = c.id AND c.parent IS NULL AND c.name = 'PROGRAM') b " +
                        "ON a.category1_name = b.category1_name AND a.category2_name = b.category2_name AND a.category IS NULL " +
                        "SET a.category = b.category_id"
        )
})
@Entity
@Data
public class Program {

    @Id
    private String id;

    private String name;

    private String originalName;

    @Column(name = "category1_name")
    private String category1Name;

    @Column(name = "category2_name")
    private String category2Name;

    private String director;

    private String cast;

    private int grade;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private String production;

    private int productYear;

    private String productCountry;

    @ManyToOne
    @JoinColumn(name="category")
    private Category category;

}
