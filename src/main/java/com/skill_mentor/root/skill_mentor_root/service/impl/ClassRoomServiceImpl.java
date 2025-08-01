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
import java.util.stream.Collectors;

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

            // NEW: Check if title already exists
            boolean titleExists = classRoomRepository.existsByTitle(classRoomDTO.getTitle().trim());
            if (titleExists) {
                log.error("Classroom with title '{}' already exists", classRoomDTO.getTitle());
                throw new RuntimeException("Classroom with title '" + classRoomDTO.getTitle() + "' already exists");
            }

            // Alternative: Check if title exists for the specific mentor
            // boolean titleExistsForMentor = classRoomRepository.existsByTitleAndMentor_MentorId(
            //     classRoomDTO.getTitle().trim(),
            //     classRoomDTO.getMentorDTO().getMentorId()
            // );
            // if (titleExistsForMentor) {
            //     log.error("Mentor {} already has a classroom with title '{}'",
            //         classRoomDTO.getMentorDTO().getMentorId(), classRoomDTO.getTitle());
            //     throw new RuntimeException("You already have a classroom with this title");
            // }

            classRoomDTO.setClassRoomId(null);
            log.debug("ClassRoomDTO received: {}", classRoomDTO);

            MentorEntity mentorEntity = mentorRepository.findById(classRoomDTO.getMentorDTO().getMentorId())
                    .orElseThrow(() -> {
                        log.error("Mentor not found with ID: {}", classRoomDTO.getMentorDTO().getMentorId());
                        return new RuntimeException("Mentor not found with ID: " + classRoomDTO.getMentorDTO().getMentorId());
                    });

            final ClassRoomEntity classRoomEntity = ClassRoomEntityDTOMapper.map(classRoomDTO);
            classRoomEntity.setMentor(mentorEntity);

            if (classRoomEntity.getEnrolledStudentCount() == null) {
                classRoomEntity.setEnrolledStudentCount(0);
                log.debug("Defaulting enrolled student count to 0");
            }

            final ClassRoomEntity savedEntity = classRoomRepository.save(classRoomEntity);
            log.info("Classroom created with ID: {} at datasource:{}", savedEntity.getClassRoomId(), this.datasource);
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
                    log.error("ClassRoom not found with ID: {} from datasource:{}", id, this.datasource);
                    return new ClassRoomException("ClassRoom not found with ID: " + id);
                });

        // FIXED: Handle the Many-to-One relationship properly
        if (classRoomEntity.getMentor() != null) {
            log.debug("Removing classroom from mentor before deletion...");
            MentorEntity mentor = classRoomEntity.getMentor();

            // Remove this classroom from the mentor's classroom list
            if (mentor.getClassRooms() != null) {
                mentor.getClassRooms().remove(classRoomEntity);
                mentorRepository.save(mentor);
            }

            // Set mentor to null in classroom
            classRoomEntity.setMentor(null);
        }

        // Handle sessions - this part remains the same
        if (classRoomEntity.getSessions() != null && !classRoomEntity.getSessions().isEmpty()) {
            log.debug("Detaching {} sessions from classroom before deletion...", classRoomEntity.getSessions().size());
            List<SessionEntity> sessions = new ArrayList<>(classRoomEntity.getSessions()); // Create copy to avoid ConcurrentModificationException
            for (SessionEntity session : sessions) {
                session.setClassRoomEntity(null);
                sessionRepository.save(session);
            }
            classRoomEntity.getSessions().clear();
        }

        // Create response DTO before deletion
        ClassRoomDTO responseDTO = ClassRoomEntityDTOMapper.map(classRoomEntity);

        classRoomRepository.deleteById(id);
        log.info("Classroom with ID {} deleted successfully", id);

        return responseDTO;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "classroomByMentorCache", key = "#mentorId")
    public List<ClassRoomDTO> findClassRoomsByMentorId(Integer mentorId) {
        log.info("Fetching classrooms by mentor ID: {}", mentorId);

        // Validate mentor ID
        if (mentorId == null || mentorId <= 0) {
            log.error("Invalid mentor ID: {}", mentorId);
            throw new IllegalArgumentException("Mentor ID must be a positive number");
        }

        // Check if mentor exists
        if (!mentorRepository.existsById(mentorId)) {
            log.error("Mentor not found with ID: {}", mentorId);
            throw new RuntimeException("Mentor not found with ID: " + mentorId);
        }

        // CHANGED: Find all classrooms by mentor ID
        List<ClassRoomEntity> classRoomEntities = classRoomRepository.findByMentor_MentorId(mentorId);

        if (classRoomEntities.isEmpty()) {
            log.warn("No classrooms found for mentor ID: {}", mentorId);
            return new ArrayList<>();
        }

        log.info("Found {} classrooms for mentor ID: {}", classRoomEntities.size(), mentorId);

        return classRoomEntities.stream()
                .map(ClassRoomEntityDTOMapper::map)
                .collect(Collectors.toList());
    }

}