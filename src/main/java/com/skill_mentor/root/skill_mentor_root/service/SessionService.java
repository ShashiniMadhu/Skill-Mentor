package com.skill_mentor.root.skill_mentor_root.service;

import com.skill_mentor.root.skill_mentor_root.dto.SessionDTO;
import java.util.List;

public interface SessionService {

    /**
     * Create a new session
     * @param sessionDTO the session data to create
     * @return the created session DTO
     */
    SessionDTO createSession(SessionDTO sessionDTO);

    /**
     * Get a session by its ID
     * @param sessionId the ID of the session to retrieve
     * @return the session DTO if found, null otherwise
     */
    SessionDTO getSessionById(Integer sessionId);

    /**
     * Get all sessions
     * @return list of all session DTOs
     */
    List<SessionDTO> getAllSessions();

    /**
     * Get all sessions for a specific student
     * @param studentId the ID of the student
     * @return list of session DTOs for the student
     */
    List<SessionDTO> getSessionsByStudentId(Integer studentId);

    /**
     * Update an existing session
     * @param sessionDTO the session data to update
     * @return the updated session DTO
     */
    SessionDTO updateSession(SessionDTO sessionDTO);

    /**
     * Delete a session by its ID
     * @param id the ID of the session to delete
     * @return the deleted session DTO
     */
    SessionDTO deleteSessionById(Integer id);
}