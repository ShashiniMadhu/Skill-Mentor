package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.entity.ClassRoomEntity;
import com.skill_mentor.root.skill_mentor_root.entity.MentorEntity;
import com.skill_mentor.root.skill_mentor_root.entity.SessionEntity;
import com.skill_mentor.root.skill_mentor_root.mapper.MentorEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.repository.ClassRoomRepository;
import com.skill_mentor.root.skill_mentor_root.repository.MentorRepository;
import com.skill_mentor.root.skill_mentor_root.repository.SessionRepository;
import com.skill_mentor.root.skill_mentor_root.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    MentorRepository mentorRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public MentorDTO createMentor(MentorDTO mentorDTO) {
        // Check if mentor already has a classroom assigned
        if (mentorDTO.getClassRoomId() != null) {
            Optional<ClassRoomEntity> classRoomOpt = classRoomRepository.findById(mentorDTO.getClassRoomId());
            if (classRoomOpt.isPresent()) {
                ClassRoomEntity classRoom = classRoomOpt.get();
                if (classRoom.getMentor() != null) {
                    throw new RuntimeException("ClassRoom with ID " + mentorDTO.getClassRoomId() + " already has a mentor assigned");
                }
            } else {
                throw new RuntimeException("ClassRoom not found with ID: " + mentorDTO.getClassRoomId());
            }
        }

        // Create and save the mentor
        MentorEntity mentorEntity = MentorEntityDTOMapper.map(mentorDTO);
        MentorEntity savedMentor = mentorRepository.save(mentorEntity);

        // If classRoomId is provided, establish the bidirectional relationship
        if (mentorDTO.getClassRoomId() != null) {
            Optional<ClassRoomEntity> classRoomOpt = classRoomRepository.findById(mentorDTO.getClassRoomId());
            if (classRoomOpt.isPresent()) {
                ClassRoomEntity classRoom = classRoomOpt.get();
                // Set bidirectional relationship
                classRoom.setMentor(savedMentor);
                savedMentor.setClassRoom(classRoom);

                // Save both entities to maintain consistency
                classRoomRepository.save(classRoom);
                savedMentor = mentorRepository.save(savedMentor);
            }
        }

        return MentorEntityDTOMapper.map(savedMentor);
    }

    @Override
    public List<MentorDTO> getAllMentors(String subject) {
        final List<MentorEntity> mentorEntities = mentorRepository.findAll();
        return mentorEntities.stream()
                .filter(mentor -> subject == null || Objects.equals(subject, mentor.getSubject()))
                .map(MentorEntityDTOMapper::map)
                .toList();
    }

    @Override
    public MentorDTO getMentorById(Integer id) {
        Optional<MentorEntity> mentorEntity = mentorRepository.findById(id);
        return mentorEntity.map(MentorEntityDTOMapper::map).orElse(null);
    }

    @Override
    public MentorDTO updateMentorById(MentorDTO mentorDTO) {
        Optional<MentorEntity> mentorEntityOpt = mentorRepository.findById(mentorDTO.getMentorId());
        if (mentorEntityOpt.isEmpty()) {
            throw new RuntimeException("Mentor not found with ID: " + mentorDTO.getMentorId());
        }

        MentorEntity mentorEntity = mentorEntityOpt.get();

        // Update basic fields
        mentorEntity.setFirstName(mentorDTO.getFirstName());
        mentorEntity.setLastName(mentorDTO.getLastName());
        mentorEntity.setAddress(mentorDTO.getAddress());
        mentorEntity.setEmail(mentorDTO.getEmail());
        mentorEntity.setTitle(mentorDTO.getTitle());
        mentorEntity.setProfession(mentorDTO.getProfession());
        mentorEntity.setSubject(mentorDTO.getSubject());
        mentorEntity.setQualification(mentorDTO.getQualification());

        // Handle classroom relationship change
        if (mentorDTO.getClassRoomId() != null) {
            // Check if mentor is changing classrooms
            if (mentorEntity.getClassRoom() == null ||
                    !mentorEntity.getClassRoom().getClassRoomId().equals(mentorDTO.getClassRoomId())) {

                // Remove from old classroom if exists
                if (mentorEntity.getClassRoom() != null) {
                    ClassRoomEntity oldClassRoom = mentorEntity.getClassRoom();
                    oldClassRoom.setMentor(null);
                    mentorEntity.setClassRoom(null);
                    classRoomRepository.save(oldClassRoom);
                }

                // Add to new classroom
                Optional<ClassRoomEntity> newClassRoomOpt = classRoomRepository.findById(mentorDTO.getClassRoomId());
                if (newClassRoomOpt.isPresent()) {
                    ClassRoomEntity newClassRoom = newClassRoomOpt.get();
                    if (newClassRoom.getMentor() != null) {
                        throw new RuntimeException("ClassRoom with ID " + mentorDTO.getClassRoomId() + " already has a mentor assigned");
                    }
                    newClassRoom.setMentor(mentorEntity);
                    mentorEntity.setClassRoom(newClassRoom);
                    classRoomRepository.save(newClassRoom);
                } else {
                    throw new RuntimeException("ClassRoom not found with ID: " + mentorDTO.getClassRoomId());
                }
            }
        } else {
            // Remove from current classroom if classRoomId is null
            if (mentorEntity.getClassRoom() != null) {
                ClassRoomEntity currentClassRoom = mentorEntity.getClassRoom();
                currentClassRoom.setMentor(null);
                mentorEntity.setClassRoom(null);
                classRoomRepository.save(currentClassRoom);
            }
        }

        MentorEntity updatedEntity = mentorRepository.save(mentorEntity);
        return MentorEntityDTOMapper.map(updatedEntity);
    }

    @Override
    public MentorDTO deleteMentorById(Integer id) {
        Optional<MentorEntity> mentorEntityOpt = mentorRepository.findById(id);
        if (mentorEntityOpt.isEmpty()) {
            throw new RuntimeException("Mentor not found with ID: " + id);
        }

        MentorEntity mentorEntity = mentorEntityOpt.get();

        // Remove mentor from classroom before deletion
        if (mentorEntity.getClassRoom() != null) {
            ClassRoomEntity classRoom = mentorEntity.getClassRoom();
            classRoom.setMentor(null);
            classRoomRepository.save(classRoom);
        }

        // Update all sessions to remove mentor reference before deletion
        if (mentorEntity.getSessions() != null && !mentorEntity.getSessions().isEmpty()) {
            List<SessionEntity> sessions = mentorEntity.getSessions();
            for (SessionEntity session : sessions) {
                session.setMentorEntity(null);
                sessionRepository.save(session);
            }
        }

        mentorRepository.deleteById(id);
        return MentorEntityDTOMapper.map(mentorEntity);
    }
}