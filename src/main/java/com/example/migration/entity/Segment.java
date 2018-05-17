package com.example.migration.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@ToString(exclude = "segmentCategory")
public class Segment {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String description;

    @NotNull
    @Column(length = 4000)
    private String configuration;

    @OneToOne
    private SegmentCategory segmentCategory;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date modifiedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = new Date();
    }

}