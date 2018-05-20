package com.example.migration.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue(Category.BUSINESS_CATEGORY_ROOT_NAME)
public class BusinessCategory extends RelationCategory {

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="campaign")
    private Campaign campaign;

}
