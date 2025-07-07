package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.LiteSessionDTO;
import com.skill_mentor.root.skill_mentor_root.dto.SessionDTO;
import com.skill_mentor.root.skill_mentor_root.entity.ClassRoomEntity;
import com.skill_mentor.root.skill_mentor_root.entity.LiteSessionEntity;
import com.skill_mentor.root.skill_mentor_root.entity.MentorEntity;
import com.skill_mentor.root.skill_mentor_root.entity.SessionEntity;
import com.skill_mentor.root.skill_mentor_root.entity.StudentEntity;
import com.skill_mentor.root.skill_mentor_root.mapper.LiteSessionEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.mapper.SessionEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.repository.ClassRoomRepository;
import com.skill_mentor.root.skill_mentor_root.repository.LiteSessionRepository;
import com.skill_mentor.root.skill_mentor_root.repository.MentorRepository;
import com.skill_mentor.root.skill_mentor_root.repository.SessionRepository;
import com.skill_mentor.root.skill_mentor_root.repository.StudentRepository;
import com.skill_mentor.root.skill_mentor_root.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements SessionService {
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
    public LiteSessionDTO createSession(LiteSessionDTO sessionDTO) {
        final LiteSessionEntity liteSessionEntity = LiteSessionEntityDTOMapper.map(sessionDTO);
        final LiteSessionEntity savedEntity =  liteSessionRepository.save(liteSessionEntity);
        return LiteSessionEntityDTOMapper.map(savedEntity);
    }

    @Override
    public SessionDTO getSessionById(Integer sessionId) {
        Optional<SessionEntity> sessionEntityOpt = sessionRepository.findById(sessionId);
        return sessionEntityOpt.map(SessionEntityDTOMapper::map).orElse(null);
    }

    @Override
    public List<SessionDTO> getAllSessions() {
        List<SessionEntity> sessionEntities = sessionRepository.findAll();
        return sessionEntities.stream()
                .map(SessionEntityDTOMapper::map)
                .collect(Collectors.toList());
    }

    // NEW: Get all sessions for a specific student
    @Override
    public List<SessionDTO> getSessionsByStudentId(Integer studentId) {
        // Validate student exists
        Optional<StudentEntity> studentEntityOpt = studentRepository.findById(studentId);
        if (studentEntityOpt.isEmpty()) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }

        List<SessionEntity> sessionEntities = sessionRepository.findByStudentEntityStudentId(studentId);
        return sessionEntities.stream()
                .map(SessionEntityDTOMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public SessionDTO updateSession(SessionDTO sessionDTO) {
        Optional<SessionEntity> optionalSessionEntity = sessionRepository.findById(sessionDTO.getSessionId());

        if (optionalSessionEntity.isEmpty()) {
            throw new RuntimeException("Session not found with ID: " + sessionDTO.getSessionId());
        }

        SessionEntity sessionEntity = optionalSessionEntity.get();

        // Update basic fields
        sessionEntity.setStartTime(sessionDTO.getStartTime());
        sessionEntity.setEndTime(sessionDTO.getEndTime());

        // Update classroom if provided
        if (sessionDTO.getClassRoom() != null && sessionDTO.getClassRoom().getClassRoomId() != null) {
            ClassRoomEntity classRoomEntity = classRoomRepository.findById(sessionDTO.getClassRoom().getClassRoomId())
                    .orElseThrow(() -> new RuntimeException("ClassRoom not found with ID: " + sessionDTO.getClassRoom().getClassRoomId()));

            sessionEntity.setClassRoomEntity(classRoomEntity);

            // Update mentor based on classroom (since classroom has one mentor)
            MentorEntity mentorEntity = classRoomEntity.getMentor();
            if (mentorEntity == null) {
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
        return SessionEntityDTOMapper.map(updatedSession);
    }

    @Override
    public SessionDTO deleteSessionById(Integer id) {
        Optional<SessionEntity> sessionEntityOpt = sessionRepository.findById(id);
        if (sessionEntityOpt.isEmpty()) {
            throw new RuntimeException("Session not found with ID: " + id);
        }

        SessionEntity sessionEntity = sessionEntityOpt.get();
        sessionRepository.deleteById(id);
        return SessionEntityDTOMapper.map(sessionEntity);
    }
}