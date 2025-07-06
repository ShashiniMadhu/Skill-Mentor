package com.skill_mentor.root.skill_mentor_root.service;

import com.skill_mentor.root.skill_mentor_root.dto.SessionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SessionService {

    /**
     * Create a new session.
     *
     * @param sessionDTO the session details
     * @return created session
     */
    SessionDTO createSession(SessionDTO sessionDTO);

    /**
     * Get session by ID.
     *
     * @param sessionId the session ID
     * @return sessionDTO or null if not found
     */
    SessionDTO getSessionById(Integer sessionId);

    /**
     * Get all sessions.
     *
     * @return list of all sessions
     */
    List<SessionDTO> getAllSessions();

    /**
     * Update an existing session.
     *
     * @param sessionDTO session with updated data
     * @return updated session
     */
    SessionDTO updateSession(SessionDTO sessionDTO);

    /**
     * Delete a session by ID.
     *
     * @param id session ID
     * @return deleted session
     */
    SessionDTO deleteSessionById(Integer id);

    // NEW: Get all sessions for a specific student
    List<SessionDTO> getSessionsByStudentId(Integer studentId);
}
