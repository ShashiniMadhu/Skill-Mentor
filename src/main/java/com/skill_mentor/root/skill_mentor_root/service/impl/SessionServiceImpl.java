package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.*;
import com.skill_mentor.root.skill_mentor_root.entity.*;
import com.skill_mentor.root.skill_mentor_root.mapper.*;
import com.skill_mentor.root.skill_mentor_root.repository.*;
import com.skill_mentor.root.skill_mentor_root.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

    @Value("${spring.datasource.url}")
    private String datasource;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LiteSessionRepository liteSessionRepository;

    //    @Override
//    public SessionDTO createSession(SessionDTO sessionDTO) {
//        // Find related entities
//        Optional<ClassRoomEntity> classRoomEntityOpt = classRoomRepository.findById(sessionDTO.getClassRoom().getClassRoomId());
//        Optional<StudentEntity> studentEntityOpt = studentRepository.findById(sessionDTO.getStudent().getStudentId());
//
//        if (classRoomEntityOpt.isEmpty()) {
//            throw new RuntimeException("ClassRoom not found with ID: " + sessionDTO.getClassRoom().getClassRoomId());
//        }
//
//        if (studentEntityOpt.isEmpty()) {
//            throw new RuntimeException("Student not found with ID: " + sessionDTO.getStudent().getStudentId());
//        }
//
//        ClassRoomEntity classRoomEntity = classRoomEntityOpt.get();
//        StudentEntity studentEntity = studentEntityOpt.get();
//
//        // Get mentor from classroom (since classroom has one mentor)
//        MentorEntity mentorEntity = classRoomEntity.getMentor();
//        if (mentorEntity == null) {
//            throw new RuntimeException("No mentor assigned to ClassRoom with ID: " + classRoomEntity.getClassRoomId());
//        }
//
//        // Validate that the mentor in DTO matches the classroom's mentor (if provided)
//        if (sessionDTO.getMentor() != null && sessionDTO.getMentor().getMentorId() != null) {
//            if (!mentorEntity.getMentorId().equals(sessionDTO.getMentor().getMentorId())) {
//                throw new RuntimeException("Mentor ID in session does not match the mentor assigned to the classroom");
//            }
//        }
//
//        SessionEntity sessionEntity = SessionEntityDTOMapper.map(
//                sessionDTO,
//                classRoomEntity,
//                mentorEntity,
//                studentEntity
//        );
//
//        sessionEntity = sessionRepository.save(sessionEntity);
//        return SessionEntityDTOMapper.map(sessionEntity);
//    }

    //for liteDTO
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"sessionCache", "allSessionsCache"}, allEntries = true)
    public LiteSessionDTO createSession(final LiteSessionDTO sessionDTO) {
        log.info("Creating LiteSession...");
        if (sessionDTO == null) {
            log.error("Session data is null.");
            throw new IllegalArgumentException("Session data must not be null.");
        }
        final LiteSessionEntity liteSessionEntity = LiteSessionEntityDTOMapper.map(sessionDTO);
        final LiteSessionEntity savedEntity = liteSessionRepository.save(liteSessionEntity);
        log.info("LiteSession created with ID: {} ata datasource:{}", savedEntity.getSessionId(),this.datasource);
        return LiteSessionEntityDTOMapper.map(savedEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "sessionCache", key = "#sessionId")
    public SessionDTO getSessionById(Integer sessionId) {
        log.info("Getting session by ID: {}", sessionId);
        Optional<SessionEntity> sessionEntityOpt = sessionRepository.findById(sessionId);
        return sessionEntityOpt.map(SessionEntityDTOMapper::map).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "allSessionsCache", key = "'allSessions'")
    public List<SessionDTO> getAllSessions() {
        log.info("Fetching all sessions...");
        List<SessionEntity> sessions = sessionRepository.findAll();
        return sessions.stream()
                .map(SessionEntityDTOMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "allSessionsCache", allEntries = true)
    @CachePut(value = "sessionCache", key = "#sessionDTO.sessionId")
    public SessionDTO updateSession(SessionDTO sessionDTO) {
        log.info("Updating session with ID: {}", sessionDTO.getSessionId());
        Optional<SessionEntity> optionalSessionEntity = sessionRepository.findById(sessionDTO.getSessionId());

        if (optionalSessionEntity.isEmpty()) {
            log.error("Session not found with ID: {}", sessionDTO.getSessionId());
            throw new RuntimeException("Session not found with ID: " + sessionDTO.getSessionId());
        }

        SessionEntity sessionEntity = optionalSessionEntity.get();

        // Update basic fields
        sessionEntity.setDate(sessionDTO.getDate());
        sessionEntity.setStartTime(sessionDTO.getStartTime());
        sessionEntity.setStatus(sessionDTO.getStatus());
        sessionEntity.setSessionLink(sessionDTO.getSessionLink());


        // Update classroom if provided
        if (sessionDTO.getClassRoom() != null && sessionDTO.getClassRoom().getClassRoomId() != null) {
            ClassRoomEntity classRoomEntity = classRoomRepository.findById(sessionDTO.getClassRoom().getClassRoomId())
                    .orElseThrow(() -> new RuntimeException("ClassRoom not found with ID: " + sessionDTO.getClassRoom().getClassRoomId()));

            sessionEntity.setClassRoomEntity(classRoomEntity);

            // Update mentor based on classroom (since classroom has one mentor)
            MentorEntity mentorEntity = classRoomEntity.getMentor();
            if (mentorEntity == null) {
                log.error("No mentor assigned to ClassRoom with ID: {}", classRoomEntity.getClassRoomId());
                throw new RuntimeException("No mentor assigned to ClassRoom with ID: " + classRoomEntity.getClassRoomId());
            }
            sessionEntity.setMentorEntity(mentorEntity);
        }

        // Update student if provided
        if (sessionDTO.getStudent() != null && sessionDTO.getStudent().getStudentId() != null) {
            StudentEntity studentEntity = studentRepository.findById(sessionDTO.getStudent().getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found with ID: " + sessionDTO.getStudent().getStudentId()));
            sessionEntity.setStudentEntity(studentEntity);
        }

        // Save updated entity
        SessionEntity updatedSession = sessionRepository.save(sessionEntity);
        log.info("Session updated with ID: {}", updatedSession.getSessionId());
        return SessionEntityDTOMapper.map(updatedSession);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"sessionCache", "allSessionsCache"}, allEntries = true)
    public SessionDTO deleteSessionById(Integer id) {
        log.info("Deleting session with ID: {}", id);
        Optional<SessionEntity> sessionEntityOpt = sessionRepository.findById(id);
        if (sessionEntityOpt.isEmpty()) {
            log.error("Session not found with ID: {}", id);
            throw new RuntimeException("Session not found with ID: " + id);
        }

        SessionEntity sessionEntity = sessionEntityOpt.get();
        sessionRepository.deleteById(id);
        log.info("Session deleted with ID: {}", id);
        return SessionEntityDTOMapper.map(sessionEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "allAuditsCache", key = "'allAudits'")
    public List<AuditDTO> getAllAudits() {
        log.info("Fetching all audit DTOs...");
        List<SessionEntity> sessions = sessionRepository.findAll();
        return sessions.stream().map(AuditDTOEntityMapper::map).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PaymentDTO> findMentorPayments(String startDate, String endDate) {
        log.info("Finding mentor payments from {} to {}", startDate, endDate);

        if (startDate == null || endDate == null) {
            log.error("Start or end date is null");
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }

        // Format dates to include time if not already present
        String formattedStartDate = startDate.contains(" ") ? startDate : startDate + " 00:00:00";
        String formattedEndDate = endDate.contains(" ") ? endDate : endDate + " 23:59:59";

        List<Object[]> rawResults = sessionRepository.findMentorPayments(formattedStartDate, formattedEndDate);

        if (rawResults == null || rawResults.isEmpty()) {
            log.warn("No payment records found");
            return Collections.emptyList();
        }

        try {
            return rawResults.stream().map(row -> {
                Integer mentorId = ((Number) row[0]).intValue();
                String mentorName = String.valueOf(row[1]);
                Double totalFee = row[2] != null ? ((Number) row[2]).doubleValue() : 0.0;
                return new PaymentDTO(mentorId, mentorName, totalFee);
            }).toList();
        } catch (ClassCastException | ArrayIndexOutOfBoundsException e) {
            log.error("Data format error in mentor payment results: {}", e.getMessage());
            throw new RuntimeException("Data format error in mentor payment results", e);
        }
    }

    // NEW: Get all sessions for a specific student
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SessionDTO> getSessionsByStudentId(Integer studentId) {
        // Validate student exists
        log.info("Getting all sessions for student ID: {}", studentId);
        Optional<StudentEntity> studentEntityOpt = studentRepository.findById(studentId);
        if (studentEntityOpt.isEmpty()) {
            log.error("Student not found with ID: {}", studentId);
            throw new RuntimeException("Student not found with ID: " + studentId);
        }

        List<SessionEntity> sessionEntities = sessionRepository.findByStudentEntityStudentId(studentId);
        log.info("Found {} sessions for student ID {}", sessionEntities.size(), studentId);

        return sessionEntities.stream()
                .map(SessionEntityDTOMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SessionDTO> getSessionsByMentorId(Integer mentorId) {
        // Validate student exists
        log.info("Getting all sessions for mentor ID: {}", mentorId);
        Optional<MentorEntity> mentorEntityOpt = mentorRepository.findById(mentorId);
        if (mentorEntityOpt.isEmpty()) {
            log.error("Mentor not found with ID: {}", mentorId);
            throw new RuntimeException("Mentor not found with ID: " + mentorId);
        }

        List<SessionEntity> sessionEntities = sessionRepository.findByMentorEntityMentorId(mentorId);
        log.info("Found {} sessions for mentor ID {}", sessionEntities.size(), mentorId);

        return sessionEntities.stream()
                .map(SessionEntityDTOMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"sessionCache", "allSessionsCache"}, allEntries = true)
    @CachePut(value = "sessionCache", key = "#sessionId")
    public SessionDTO updateSessionStatusAndLink(Integer sessionId, String status, String sessionLink) {
        log.info("Updating session status and link for ID: {} to status: {} with link: {}", sessionId, status, sessionLink);

        // Validate status
        if (status == null || status.trim().isEmpty()) {
            log.error("Status cannot be null or empty");
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        // Validate allowed status values
        String normalizedStatus = status.trim().toLowerCase();
        if (!isValidStatus(normalizedStatus)) {
            log.error("Invalid status: {}. Allowed values are: accept, accepted, reject, rejected, pending", status);
            throw new IllegalArgumentException("Invalid status. Allowed values are: accept, accepted, reject, rejected, pending");
        }

        // Validate session link if provided
        if (sessionLink != null && !sessionLink.trim().isEmpty() && !isValidUrl(sessionLink.trim())) {
            log.error("Invalid session link format: {}", sessionLink);
            throw new IllegalArgumentException("Invalid session link format. Must be a valid URL.");
        }

        // Find the session
        Optional<SessionEntity> optionalSessionEntity = sessionRepository.findById(sessionId);
        if (optionalSessionEntity.isEmpty()) {
            log.error("Session not found with ID: {}", sessionId);
            throw new RuntimeException("Session not found with ID: " + sessionId);
        }

        SessionEntity sessionEntity = optionalSessionEntity.get();

        // Update status
        String finalStatus = mapStatusToFinalValue(normalizedStatus);
        sessionEntity.setStatus(finalStatus);

        // Update session link if provided
        if (sessionLink != null) {
            String trimmedLink = sessionLink.trim();
            sessionEntity.setSessionLink(trimmedLink.isEmpty() ? null : trimmedLink);
            log.info("Session link updated for ID: {} to: {}", sessionId, trimmedLink);
        }

        // Save the updated entity
        SessionEntity updatedSession = sessionRepository.save(sessionEntity);
        log.info("Session status and link updated successfully for ID: {} to status: {}", sessionId, finalStatus);

        return SessionEntityDTOMapper.map(updatedSession);
    }

    private boolean isValidStatus(String status) {
        return status.equals("accept") ||
                status.equals("accepted") ||
                status.equals("reject") ||
                status.equals("rejected") ||
                status.equals("pending");
    }

    private String mapStatusToFinalValue(String status) {
        switch (status) {
            case "accept":
                return "accepted";
            case "reject":
                return "rejected";
            case "accepted":
            case "rejected":
            case "pending":
                return status;
            default:
                return "pending"; // fallback
        }
    }

    private boolean isValidUrl(String url) {
        try {
            // Basic URL validation
            if (url.startsWith("http://") || url.startsWith("https://")) {
                new java.net.URL(url);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
