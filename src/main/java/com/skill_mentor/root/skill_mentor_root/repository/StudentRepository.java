package com.skill_mentor.root.skill_mentor_root.repository;


import com.skill_mentor.root.skill_mentor_root.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    Optional<StudentEntity> findByEmail(String email);
    Optional<StudentEntity> findByClerkUserId(String clerkUserId);
}