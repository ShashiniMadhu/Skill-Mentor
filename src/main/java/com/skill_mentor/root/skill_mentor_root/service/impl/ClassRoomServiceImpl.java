package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.ClassRoomDTO;
import com.skill_mentor.root.skill_mentor_root.entity.ClassRoomEntity;
import com.skill_mentor.root.skill_mentor_root.entity.MentorEntity;
import com.skill_mentor.root.skill_mentor_root.entity.SessionEntity;
import com.skill_mentor.root.skill_mentor_root.exception.ClassRoomException;
import com.skill_mentor.root.skill_mentor_root.mapper.ClassRoomEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.repository.ClassRoomRepository;
import com.skill_mentor.root.skill_mentor_root.repository.MentorRepository;
import com.skill_mentor.root.skill_mentor_root.repository.SessionRepository;
import com.skill_mentor.root.skill_mentor_root.service.ClassRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class ClassRoomServiceImpl implements ClassRoomService {

    @Value("${spring.datasource.url}")
    private String datasource;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"classroomCache", "allClassroomsCache"}, allEntries = true)
    public ClassRoomDTO createClassRoom(ClassRoomDTO classRoomDTO) {
        log.info("Creating new classroom...");

        // Add debug logging
        log.debug("Received ClassRoomDTO: {}", classRoomDTO);
        log.debug("MentorDTO: {}", classRoomDTO.getMentorDTO());
        if (classRoomDTO.getMentorDTO() != null) {
            log.debug("Mentor ID: {}", classRoomDTO.getMentorDTO().getMentorId());
        }

        try {
            if (classRoomDTO.getTitle() == null || classRoomDTO.getTitle().trim().isEmpty()) {
                log.error("Classroom title is missing.");
                throw new RuntimeException("ClassRoom title is required");
            }

            if (classRoomDTO.getMentorDTO() == null || classRoomDTO.getMentorDTO().getMentorId() == null) {
                log.error("Mentor information is missing. MentorDTO: {}", classRoomDTO.getMentorDTO());
                throw new RuntimeException("Mentor information is required");
            }

            // Rest of your existing code...
            classRoomDTO.setClassRoomId(null);
            log.debug("ClassRoomDTO received: {}", classRoomDTO);

            MentorEntity mentorEntity = mentorRepository.findById(classRoomDTO.getMentorDTO().getMentorId())
                    .orElseThrow(() -> {
                        log.error("Mentor not found with ID: {}", classRoomDTO.getMentorDTO().getMentorId());
                        return new RuntimeException("Mentor not found with ID: " + classRoomDTO.getMentorDTO().getMentorId());
                    });

            if (mentorEntity.getClassRoom() != null) {
                log.error("Mentor with ID {} already has a classroom assigned", mentorEntity.getMentorId());
                throw new RuntimeException("Mentor with ID " + mentorEntity.getMentorId() + " already has a classroom assigned");
            }

            final ClassRoomEntity classRoomEntity = ClassRoomEntityDTOMapper.map(classRoomDTO);
            classRoomEntity.setMentor(mentorEntity);

            if (classRoomEntity.getEnrolledStudentCount() == null) {
                classRoomEntity.setEnrolledStudentCount(0);
                log.debug("Defaulting enrolled student count to 0");
            }

            final ClassRoomEntity savedEntity = classRoomRepository.save(classRoomEntity);
            log.info("Classroom created with ID: {} at datasource:{}", savedEntity.getClassRoomId(),this.datasource);
            return ClassRoomEntityDTOMapper.map(savedEntity);

        } catch (Exception e) {
            log.error("Error creating classroom: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create classroom: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "allClassroomsCache", key = "'allClassrooms'")
    public List<ClassRoomDTO> getAllClassRooms() {
        log.info("Fetching all classrooms... from datasource:{}",this.datasource);
        List<ClassRoomEntity> classRoomEntities = classRoomRepository.findAll();
        log.info("Found {} classrooms", classRoomEntities.size());
        return classRoomEntities.stream()
                .map(ClassRoomEntityDTOMapper::map)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "classroomCache", key = "#id")
    public ClassRoomDTO findClassRoomById(Integer id) {
        log.info("Fetching classroom by ID: {}", id);
        Optional<ClassRoomEntity> classRoomEntityOpt = classRoomRepository.findById(id);
        if (classRoomEntityOpt.isEmpty()) {
            log.error("ClassRoom not found with ID: {}", id);
            throw new ClassRoomException("ClassRoom not found");
        }
        log.debug("ClassRoom found: {}", classRoomEntityOpt.get());
        return ClassRoomEntityDTOMapper.map(classRoomEntityOpt.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"classroomCache", "allClassroomsCache"}, allEntries = true)
    public ClassRoomDTO deleteClassRoomById(Integer id) {
        log.info("Deleting classroom with ID: {}", id);
        final ClassRoomEntity classRoomEntity = classRoomRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("ClassRoom not found with ID: {} from datasource:{}",this.datasource, id);
                    return new ClassRoomException("ClassRoom not found with ID: " + id);
                });

        if (classRoomEntity.getMentor() != null) {
            log.debug("Detaching mentor from classroom before deletion...");
            MentorEntity mentor = classRoomEntity.getMentor();
            mentor.setClassRoom(null);
            mentorRepository.save(mentor);
            classRoomEntity.setMentor(null);
        }

        if (classRoomEntity.getSessions() != null && !classRoomEntity.getSessions().isEmpty()) {
            log.debug("Detaching {} sessions from classroom before deletion...", classRoomEntity.getSessions().size());
            List<SessionEntity> sessions = classRoomEntity.getSessions();
            for (SessionEntity session : sessions) {
                session.setClassRoomEntity(null);
                sessionRepository.save(session);
            }
        }

        classRoomRepository.deleteById(id);
        log.info("Classroom with ID {} deleted successfully", id);
        return ClassRoomEntityDTOMapper.map(classRoomEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "allClassroomsCache", allEntries = true)
    @CachePut(value = "classroomCache", key = "#classRoomDTO.classRoomId")
    public ClassRoomDTO updateClassRoom(ClassRoomDTO classRoomDTO) {
        log.info("Updating classroom with ID: {}", classRoomDTO.getClassRoomId());
        Optional<ClassRoomEntity> classRoomEntityOpt = classRoomRepository.findById(classRoomDTO.getClassRoomId());
        if (classRoomEntityOpt.isEmpty()) {
            log.error("ClassRoom not found with ID: {}", classRoomDTO.getClassRoomId());
            throw new ClassRoomException("ClassRoom not found with ID: " + classRoomDTO.getClassRoomId());
        }

        final ClassRoomEntity classRoomEntity = classRoomEntityOpt.get();

        classRoomEntity.setTitle(classRoomDTO.getTitle());
        classRoomEntity.setEnrolledStudentCount(classRoomDTO.getEnrolledStudentCount());
        log.debug("Updated title and student count for classroom ID {}", classRoomDTO.getClassRoomId());

        if (classRoomDTO.getMentorDTO() != null && classRoomDTO.getMentorDTO().getMentorId() != null) {
            Optional<MentorEntity> mentorEntityOpt = mentorRepository.findById(classRoomDTO.getMentorDTO().getMentorId());
            if (mentorEntityOpt.isPresent()) {
                classRoomEntity.setMentor(mentorEntityOpt.get());
                log.debug("Mentor updated for classroom ID {}", classRoomDTO.getClassRoomId());
            }
        }

        final ClassRoomEntity savedEntity = classRoomRepository.save(classRoomEntity);
        log.info("Classroom updated with ID: {}", savedEntity.getClassRoomId());
        return ClassRoomEntityDTOMapper.map(savedEntity);
    }
}