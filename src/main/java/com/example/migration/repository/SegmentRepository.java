package com.example.migration.repository;

import com.example.migration.entity.Segment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SegmentRepository extends CrudRepository<Segment, String> {
}
