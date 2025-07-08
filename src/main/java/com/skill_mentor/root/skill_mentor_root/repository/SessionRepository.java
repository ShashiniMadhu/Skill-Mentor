package com.skill_mentor.root.skill_mentor_root.repository;

import com.skill_mentor.root.skill_mentor_root.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity,Integer> {
    // Find all sessions for a specific student
    List<SessionEntity> findByStudentEntityStudentId(Integer studentId);

    @Query(value = "SELECT m.mentor_id AS mentorId, " +
            "CONCAT(m.first_name, ' ', m.last_name) AS mentorName, " +
            "(COUNT(s.session_id) * m.session_fee) AS totalFee " +
            "FROM session s " +
            "JOIN mentor m ON s.mentor_id = m.mentor_id " +
            "WHERE (:startTime IS NULL OR s.start_time >= :startTime) " +
            "AND (:endTime IS NULL OR s.start_time <= :endTime) " +
            "GROUP BY m.mentor_id, m.first_name, m.last_name, m.session_fee " +
            "ORDER BY totalFee DESC", nativeQuery = true)
    List<Object[]> findMentorPayments(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
