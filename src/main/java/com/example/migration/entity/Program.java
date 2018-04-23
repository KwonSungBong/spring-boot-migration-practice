package com.example.migration.entity;

import com.example.migration.dto.ProgramDto;
import com.example.migration.dto.WrapperDto;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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
        ),
        @SqlResultSetMapping(
                name = "ProgramDtoResult",
                classes = {
                        @ConstructorResult(
                                targetClass = ProgramDto.class,
                                columns = {
                                        @ColumnResult(name = "id", type = String.class),
                                        @ColumnResult(name = "name", type = String.class),
                                        @ColumnResult(name = "originalName", type = String.class),
                                        @ColumnResult(name = "category1Name", type = String.class),
                                        @ColumnResult(name = "category2Name", type = String.class),
                                        @ColumnResult(name = "director", type = String.class),
                                        @ColumnResult(name = "cast", type = String.class),
                                        @ColumnResult(name = "grade", type = Integer.class),
                                        @ColumnResult(name = "summary", type = String.class),
                                        @ColumnResult(name = "production", type = String.class),
                                        @ColumnResult(name = "productYear", type = Integer.class),
                                        @ColumnResult(name = "productCountry", type = String.class),

                                        @ColumnResult(name = "categoryId", type = Long.class),
                                        @ColumnResult(name = "categoryName", type = String.class),
                                        @ColumnResult(name = "categoryIsUsed", type = Boolean.class),
                                        @ColumnResult(name = "categoryCreatedAt", type = Date.class),
                                        @ColumnResult(name = "categoryModifiedAt", type = Date.class)
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "ProgramAndCategoryResult",
                entities={
                        @EntityResult(entityClass = Program.class, fields = {
                                @FieldResult(name="id", column="id"),
                                @FieldResult(name="name", column="name"),
                                @FieldResult(name="originalName", column="originalName"),
                                @FieldResult(name="category1Name", column="category1Name"),
                                @FieldResult(name="category2Name", column="category2Name"),
                                @FieldResult(name="director", column="director"),
                                @FieldResult(name="cast", column="cast"),
                                @FieldResult(name="grade", column="grade"),
                                @FieldResult(name="summary", column="summary"),
                                @FieldResult(name="production", column="production"),
                                @FieldResult(name="productYear", column="productYear"),
                                @FieldResult(name="productCountry", column="productCountry"),
                                @FieldResult(name="category", column="category")
                        }),
                        @EntityResult(entityClass = Category.class, fields = {
                                @FieldResult(name="id", column="categoryId"),
                                @FieldResult(name="name", column="categoryName"),
                                @FieldResult(name="parent", column="categoryParent"),
                                @FieldResult(name="isUsed", column="categoryIsUsed"),
                                @FieldResult(name="createdAt", column="categoryCreatedAt"),
                                @FieldResult(name="modifiedAt", column="categoryModifiedAt")
                        })
                }
        ),
        @SqlResultSetMapping(
                name = "ProgramAndCategoryWrapperResult",
                entities={
                        @EntityResult(entityClass = Program.class, fields = {
                                @FieldResult(name="id", column="id"),
                                @FieldResult(name="name", column="name"),
                                @FieldResult(name="originalName", column="originalName"),
                                @FieldResult(name="category1Name", column="category1Name"),
                                @FieldResult(name="category2Name", column="category2Name"),
                                @FieldResult(name="director", column="director"),
                                @FieldResult(name="cast", column="cast"),
                                @FieldResult(name="grade", column="grade"),
                                @FieldResult(name="summary", column="summary"),
                                @FieldResult(name="production", column="production"),
                                @FieldResult(name="productYear", column="productYear"),
                                @FieldResult(name="productCountry", column="productCountry"),
                                @FieldResult(name="category", column="category")
                        }),
                        @EntityResult(entityClass = Category.class, fields = {
                                @FieldResult(name="id", column="categoryId"),
                                @FieldResult(name="name", column="categoryName"),
                                @FieldResult(name="parent", column="categoryParent"),
                                @FieldResult(name="isUsed", column="categoryIsUsed"),
                                @FieldResult(name="createdAt", column="categoryCreatedAt"),
                                @FieldResult(name="modifiedAt", column="categoryModifiedAt")
                        })
                }
                ,
                classes = {
                        @ConstructorResult(
                                targetClass = WrapperDto.class,
                                columns = {
                                        @ColumnResult(name = "id", type = String.class),
                                        @ColumnResult(name = "categoryId", type = Long.class),
                                        @ColumnResult(name = "name", type = String.class),
                                        @ColumnResult(name = "categoryName", type = String.class)
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
                name    = "updateProgramCategoryByCategoryIsNull",
                query   = "UPDATE program a " +
                        "JOIN (SELECT a.id AS category_id, b.name AS category1_name, a.name AS category2_name " +
                        "FROM category a  " +
                        "JOIN category b  " +
                        "ON a.parent = b.id " +
                        "JOIN category c " +
                        "ON b.parent = c.id AND c.parent IS NULL AND c.name = 'PROGRAM') b " +
                        "ON a.category1_name = b.category1_name AND a.category2_name = b.category2_name AND a.category IS NULL " +
                        "SET a.category = b.category_id"
        ),
        @NamedNativeQuery(
                name = "selectProgramDtoList",
                query = "SELECT a.id AS id, a.cast AS cast, a.category1_name AS category1Name, a.category2_name AS category2Name, " +
                        "a.director AS director, a.grade AS grade, a.name AS name, a.original_name AS originalName, a.production AS production, " +
                        "a.product_country AS productCountry, a.product_year AS productYear, a.summary AS summary," +
                        "b.id AS categoryId, b.name AS categoryName, b.is_used AS categoryIsUsed, " +
                        "b.created_at AS categoryCreatedAt, b.modified_at AS categoryModifiedAt " +
                        "FROM program a " +
                        "JOIN category b " +
                        "ON a.category = b.id",
                resultSetMapping = "ProgramDtoResult"
        ),
        @NamedNativeQuery(
                name = "selectProgramAndCategoryList",
                query = "SELECT a.id AS id, a.cast AS cast, a.category1_name AS category1Name, a.category2_name AS category2Name, " +
                        "a.director AS director, a.grade AS grade, a.name AS name, a.original_name AS originalName, a.production AS production, " +
                        "a.product_country AS productCountry, a.product_year AS productYear, a.summary AS summary, a.category AS category, " +
                        "b.id AS categoryId, b.name AS categoryName, b.parent AS categoryParent, b.is_used AS categoryIsUsed, " +
                        "b.created_at AS categoryCreatedAt, b.modified_at AS categoryModifiedAt " +
                        "FROM program a " +
                        "JOIN category b " +
                        "ON a.category = b.id",
                resultSetMapping = "ProgramAndCategoryResult"
        ),
        @NamedNativeQuery(
                name = "selectProgramAndCategoryWrapperList",
                query = "SELECT a.id AS id, a.cast AS cast, a.category1_name AS category1Name, a.category2_name AS category2Name, " +
                        "a.director AS director, a.grade AS grade, a.name AS name, a.original_name AS originalName, a.production AS production, " +
                        "a.product_country AS productCountry, a.product_year AS productYear, a.summary AS summary, a.category AS category, " +
                        "b.id AS categoryId, b.name AS categoryName, b.parent AS categoryParent, b.is_used AS categoryIsUsed, " +
                        "b.created_at AS categoryCreatedAt, b.modified_at AS categoryModifiedAt " +
                        "FROM program a " +
                        "JOIN category b " +
                        "ON a.category = b.id",
                resultSetMapping = "ProgramAndCategoryWrapperResult"
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
