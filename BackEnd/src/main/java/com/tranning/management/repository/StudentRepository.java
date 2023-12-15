package com.tranning.management.repository;

import com.tranning.management.model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("""
                SELECT s FROM Student s
                WHERE s.studentCode LIKE %:keyword% OR s.studentName LIKE %:keyword% OR :keyword IS NULL
            """)
    Page<Student> searchByKeyword(String keyword, Pageable pageable);
}
