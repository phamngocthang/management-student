package com.tranning.management.repository;

import com.tranning.management.model.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentInfoRepository extends JpaRepository<StudentInfo, Integer> {
}
