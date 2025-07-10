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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ClassRoomServiceImpl implements ClassRoomService {
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
        try {
            // Validate required fields
            if (classRoomDTO.getTitle() == null || classRoomDTO.getTitle().trim().isEmpty()) {
                throw new RuntimeException("ClassRoom title is required");
            }

            if (classRoomDTO.getMentorDTO() == null || classRoomDTO.getMentorDTO().getMentorId() == null) {
                throw new RuntimeException("Mentor information is required");
            }

            // Set ID to null for new creation
            classRoomDTO.setClassRoomId(null);

            // Find mentor
            MentorEntity mentorEntity = mentorRepository.findById(classRoomDTO.getMentorDTO().getMentorId())
                    .orElseThrow(() -> new RuntimeException("Mentor not found with ID: " + classRoomDTO.getMentorDTO().getMentorId()));

            // Check if mentor already has a classroom (one-to-one relationship)
            if (mentorEntity.getClassRoom() != null) {
                throw new RuntimeException("Mentor with ID " + mentorEntity.getMentorId() + " already has a classroom assigned");
            }

            // Create classroom entity
            final ClassRoomEntity classRoomEntity = ClassRoomEntityDTOMapper.map(classRoomDTO);
            classRoomEntity.setMentor(mentorEntity);

            // Set default values if not provided
            if (classRoomEntity.getEnrolledStudentCount() == null) {
                classRoomEntity.setEnrolledStudentCount(0);
            }

            final ClassRoomEntity savedEntity = classRoomRepository.save(classRoomEntity);
            return ClassRoomEntityDTOMapper.map(savedEntity);

        } catch (Exception e) {
            System.err.println("Error creating classroom: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create classroom: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "allClassroomsCache", key = "'allClassrooms'")
    public List<ClassRoomDTO> getAllClassRooms() {
        List<ClassRoomEntity> classRoomEntities = classRoomRepository.findAll();
        return classRoomEntities.stream()
                .map(ClassRoomEntityDTOMapper::map)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "classroomCache", key = "#id")
    public ClassRoomDTO findClassRoomById(Integer id) {
        Optional<ClassRoomEntity> classRoomEntityOpt = classRoomRepository.findById(id);
        if (classRoomEntityOpt.isEmpty()) {
            throw new ClassRoomException("ClassRoom not found");
        }
        return ClassRoomEntityDTOMapper.map(classRoomEntityOpt.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"classroomCache", "allClassroomsCache"}, allEntries = true)
    public ClassRoomDTO deleteClassRoomById(Integer id) {
        final ClassRoomEntity classRoomEntity = classRoomRepository.findById(id)
                .orElseThrow(() -> new ClassRoomException("ClassRoom not found with ID: " + id));

        // Remove mentor relationship before deletion
        if (classRoomEntity.getMentor() != null) {
            MentorEntity mentor = classRoomEntity.getMentor();
            mentor.setClassRoom(null);
            mentorRepository.save(mentor);
            classRoomEntity.setMentor(null);
        }

        // Handle sessions before deletion
        if (classRoomEntity.getSessions() != null && !classRoomEntity.getSessions().isEmpty()) {
            List<SessionEntity> sessions = classRoomEntity.getSessions();
            for (SessionEntity session : sessions) {
                session.setClassRoomEntity(null);
                sessionRepository.save(session);
            }
        }

        classRoomRepository.deleteById(id);
        return ClassRoomEntityDTOMapper.map(classRoomEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "allClassroomsCache", allEntries = true)
    @CachePut(value = "classroomCache", key = "#classRoomDTO.classRoomId")
    public ClassRoomDTO updateClassRoom(ClassRoomDTO classRoomDTO) {
        Optional<ClassRoomEntity> classRoomEntityOpt = classRoomRepository.findById(classRoomDTO.getClassRoomId());
        if (classRoomEntityOpt.isEmpty()) {
            throw new ClassRoomException("ClassRoom not found with ID: " + classRoomDTO.getClassRoomId());
        }

        final ClassRoomEntity classRoomEntity = classRoomEntityOpt.get();

        // Update basic fields
        classRoomEntity.setTitle(classRoomDTO.getTitle());
        classRoomEntity.setEnrolledStudentCount(classRoomDTO.getEnrolledStudentCount());

        // Update mentor if provided
        if (classRoomDTO.getMentorDTO() != null && classRoomDTO.getMentorDTO().getMentorId() != null) {
            Optional<MentorEntity> mentorEntityOpt = mentorRepository.findById(classRoomDTO.getMentorDTO().getMentorId());
            if (mentorEntityOpt.isPresent()) {
                classRoomEntity.setMentor(mentorEntityOpt.get());
            }
        }

        final ClassRoomEntity savedEntity = classRoomRepository.save(classRoomEntity);
        return ClassRoomEntityDTOMapper.map(savedEntity);
    }
}
