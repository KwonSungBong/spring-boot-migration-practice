package com.example.migration.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "CategoryResult",
                classes = {
                        @ConstructorResult(
                                targetClass = ProgramCategory.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "name", type = String.class),
                                        @ColumnResult(name = "createdAt", type = Date.class),
                                        @ColumnResult(name = "modifiedAt", type = Date.class),
                                        @ColumnResult(name = "isUsed", type = String.class),
                                        @ColumnResult(name = "rootName", type = String.class),
                                        @ColumnResult(name = "parent", type = Category.class)
                                }
                        )
                }
        )
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name    = "selectCategoryListByRoot",
                query   = "SELECT id, name, parent, created_at, modified_at, is_used " +
                        "FROM (SELECT * FROM category ORDER BY parent, id) category_sorted, " +
                        "(SELECT @pv \\:= (SELECT id FROM category WHERE name='PROGRAM' AND parent IS NULL)) initialisation " +
                        "WHERE FIND_IN_SET(parent, @pv) AND LENGTH(@pv \\:= CONCAT(@pv, ',', id))",
                resultClass = Category.class
        ),
        @NamedNativeQuery(
                name    = "selectCategoryListByRootName",
                query   = "SELECT id, name, parent, created_at, modified_at, is_used " +
                        "FROM (SELECT * FROM category ORDER BY parent, id) category_sorted, " +
                        "(SELECT @pv \\:= (SELECT id FROM category WHERE name=:rootName AND parent IS NULL)) initialisation " +
                        "WHERE FIND_IN_SET(parent, @pv) AND LENGTH(@pv \\:= CONCAT(@pv, ',', id))",
                resultClass = Category.class
        ),
        @NamedNativeQuery(
                name    = "selectCategoryListByRootId",
                query   = "SELECT id, name, parent, created_at, modified_at, is_used " +
                        "FROM (SELECT * FROM category ORDER BY parent, id) category_sorted, " +
                        "(SELECT @pv \\:= :rootId) initialisation " +
                        "WHERE FIND_IN_SET(parent, @pv) AND LENGTH(@pv \\:= CONCAT(@pv, ',', id))",
                resultClass = Category.class
        )
})
@NamedQueries({
        @NamedQuery(
                name="selectProgramCategoryRoot",
                query="SELECT c FROM Category c " +
                        "WHERE c.parent IS NULL " +
                        "AND c.name = '"+Category.PROGRAM_CATEGORY_ROOT_NAME+"'"),
        @NamedQuery(
                name="selectProgramCategoryDepth1",
                query="SELECT c FROM Category c " +
                        "WHERE c.parent.parent IS NULL " +
                        "AND c.parent.name = '"+Category.PROGRAM_CATEGORY_ROOT_NAME+"'"),
        @NamedQuery(
                name="selectProgramCategoryDepth2",
                query="SELECT c FROM Category c " +
                        "JOIN FETCH c.parent " +
                        "WHERE c.parent.parent.parent IS NULL " +
                        "AND c.parent.parent.name = '"+Category.PROGRAM_CATEGORY_ROOT_NAME+"'")
})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "parent")
public class Category {

    public static final String PROGRAM_CATEGORY_ROOT_NAME = "PROGRAM";
    public static final String SEGMENT_CATEGORY_ROOT_NAME = "SEGMENT";
    public static final String CAMPAIGN_CATEGORY_ROOT_NAME = "CAMPAIGN";
    public static final String BUSINESS_CATEGORY_ROOT_NAME = "BUSINESS";
    public static final int CATEGORY_ROOT_DEPTH = 0;
    public static final String SEPARATOR = "|";

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="parent")
    private Category parent;

    private boolean isUsed = true;

//    @CreatedDate
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;

//    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date modifiedAt;

    private String fullPath;
    private String fullPathName;

    public Category(String name, Date createdAt, Category parent) {
        this.name = name;
        this.createdAt = createdAt;
        this.parent = parent;
    }

    @PrePersist
    public void prePersist(){
        setModifiedAt(new Date());
    }

    @PostPersist
    public void postPersist(){
        Category parent = getParent();
        if(parent == null || parent.getFullPath() == null || parent.getFullPathName() == null)
            throw new RuntimeException("Not found category parent");

        fullPath = parent.getFullPath()+SEPARATOR+getId();
        fullPathName = parent.getFullPathName()+SEPARATOR+name;
    }

//    @PrePersist
//    public void prePersist(){
//        System.out.println("prePersist id=" + id);
//    }
//
//    @PostPersist
//    public void postPersist(){
//        System.out.println("postPersist id=" + id);
//    }
//    @PostLoad
//    public void postLoad(){
//        System.out.println("postLoad");
//    }
//
//    @PreRemove
//    public void preRemove(){
//        System.out.println("preRemove");
//    }
//
//    @PostRemove
//    public void postRemove(){
//        System.out.println("postRemove");
//    }
}
