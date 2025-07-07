package com.skill_mentor.root.skill_mentor_root.service;

import com.skill_mentor.root.skill_mentor_root.dto.SessionDTO;

import java.util.List;

public interface SessionService {

    /**
     * Creates a new session.
     *
     * @param sessionDTO the session data transfer object containing session details
     * @return the created session data transfer object
     */
    SessionDTO createSession(SessionDTO sessionDTO);

    /**
     * Retrieves a session by its ID.
     *
     * @param sessionId the ID of the session
     * @return the session DTO, or null if not found
     */
    SessionDTO getSessionById(Integer sessionId);

    /**
     * Retrieves all sessions.
     *
     * @return a list of session DTOs
     */
    List<SessionDTO> getAllSessions();

    /**
     * Retrieves all sessions for a given student ID.
     *
     * @param studentId the ID of the student
     * @return a list of session DTOs
     */
    List<SessionDTO> getSessionsByStudentId(Integer studentId);

    /**
     * Updates an existing session.
     *
     * @param sessionDTO the updated session data
     * @return the updated session DTO
     */
    SessionDTO updateSession(SessionDTO sessionDTO);

    /**
     * Deletes a session by its ID.
     *
     * @param id the ID of the session to delete
     * @return the deleted session DTO
     */
    SessionDTO deleteSessionById(Integer id);
}
