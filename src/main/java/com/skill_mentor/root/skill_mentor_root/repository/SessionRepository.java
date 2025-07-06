package com.skill_mentor.root.skill_mentor_root.repository;

import com.skill_mentor.root.skill_mentor_root.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity,Integer> {
    // Find all sessions for a specific student
    List<SessionEntity> findByStudentEntityStudentId(Integer studentId);
}
