package com.tranning.management.repository;

import com.tranning.management.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);

    @Query("""
                SELECT u FROM User u
                WHERE u.username LIKE %:keyword% OR :keyword IS NULL
            """)
    Page<User> searchByKeyword(String keyword, Pageable pageable);
}
