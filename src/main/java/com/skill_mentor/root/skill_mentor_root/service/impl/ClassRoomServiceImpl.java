package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.ClassRoomDTO;
import com.skill_mentor.root.skill_mentor_root.entity.ClassRoomEntity;
import com.skill_mentor.root.skill_mentor_root.entity.MentorEntity;
import com.skill_mentor.root.skill_mentor_root.mapper.ClassRoomEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.repository.ClassRoomRepository;
import com.skill_mentor.root.skill_mentor_root.repository.MentorRepository;
import com.skill_mentor.root.skill_mentor_root.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassRoomServiceImpl implements ClassRoomService {
    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Override
    public ClassRoomDTO createClassRoom(ClassRoomDTO classRoomDTO) {
        classRoomDTO.setClassRoomId(null);

        ClassRoomEntity classRoomEntity = ClassRoomEntityDTOMapper.map(classRoomDTO);

        // If mentor is provided, set the relationship
        if (classRoomDTO.getMentor() != null && classRoomDTO.getMentor().getMentorId() != null) {
            Optional<MentorEntity> mentorEntityOpt = mentorRepository.findById(classRoomDTO.getMentor().getMentorId());
            if (mentorEntityOpt.isPresent()) {
                classRoomEntity.setMentor(mentorEntityOpt.get());
            }
        }

        ClassRoomEntity savedEntity = classRoomRepository.save(classRoomEntity);
        return ClassRoomEntityDTOMapper.map(savedEntity);
    }

    @Override
    public List<ClassRoomDTO> getAllClassRooms() {
        List<ClassRoomEntity> classRoomEntities = classRoomRepository.findAll();
        return classRoomEntities.stream()
                .map(ClassRoomEntityDTOMapper::map)
                .toList();
    }

    @Override
    public ClassRoomDTO findClassRoomById(Integer id) {
        Optional<ClassRoomEntity> classRoomEntityOpt = classRoomRepository.findById(id);
        return classRoomEntityOpt.map(ClassRoomEntityDTOMapper::map).orElse(null);
    }

    @Override
    public ClassRoomDTO deleteClassRoomById(Integer id) {
        Optional<ClassRoomEntity> classRoomEntityOpt = classRoomRepository.findById(id);
        if (classRoomEntityOpt.isEmpty()) {
            throw new RuntimeException("ClassRoom not found with ID: " + id);
        }

        ClassRoomEntity classRoomEntity = classRoomEntityOpt.get();
        classRoomRepository.deleteById(id);
        return ClassRoomEntityDTOMapper.map(classRoomEntity);
    }

    @Override
    public ClassRoomDTO updateClassRoom(ClassRoomDTO classRoomDTO) {
        Optional<ClassRoomEntity> classRoomEntityOpt = classRoomRepository.findById(classRoomDTO.getClassRoomId());
        if (classRoomEntityOpt.isEmpty()) {
            throw new RuntimeException("ClassRoom not found with ID: " + classRoomDTO.getClassRoomId());
        }

        ClassRoomEntity classRoomEntity = classRoomEntityOpt.get();

        // Update basic fields
        classRoomEntity.setTitle(classRoomDTO.getTitle());
        classRoomEntity.setSessionFee(classRoomDTO.getSessionFee());
        classRoomEntity.setEnrolledStudentCount(classRoomDTO.getEnrolledStudentCount());

        // Update mentor if provided
        if (classRoomDTO.getMentor() != null && classRoomDTO.getMentor().getMentorId() != null) {
            Optional<MentorEntity> mentorEntityOpt = mentorRepository.findById(classRoomDTO.getMentor().getMentorId());
            if (mentorEntityOpt.isPresent()) {
                classRoomEntity.setMentor(mentorEntityOpt.get());
            }
        }

        ClassRoomEntity savedEntity = classRoomRepository.save(classRoomEntity);
        return ClassRoomEntityDTOMapper.map(savedEntity);
    }
}