package com.skill_mentor.root.skill_mentor_root.mapper;

import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.dto.SessionDTO;
import com.skill_mentor.root.skill_mentor_root.entity.MentorEntity;
import com.skill_mentor.root.skill_mentor_root.entity.SessionEntity;
import java.util.List;
import java.util.stream.Collectors;

public class MentorEntityDTOMapper {

    // Full mapping with classroom details
    public static MentorDTO map(MentorEntity mentorEntity) {
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setMentorId(mentorEntity.getMentorId());
        mentorDTO.setFirstName(mentorEntity.getFirstName());
        mentorDTO.setLastName(mentorEntity.getLastName());
        mentorDTO.setAddress(mentorEntity.getAddress());
        mentorDTO.setEmail(mentorEntity.getEmail());
        mentorDTO.setTitle(mentorEntity.getTitle());
        mentorDTO.setProfession(mentorEntity.getProfession());
        mentorDTO.setSubject(mentorEntity.getSubject());
        mentorDTO.setPhoneNumber(mentorEntity.getPhoneNumber());
        mentorDTO.setQualification(mentorEntity.getQualification());
        mentorDTO.setSessionFee(mentorEntity.getSessionFee());
        mentorDTO.setPassword(mentorEntity.getPassword());
        mentorDTO.setRole(mentorEntity.getRole());
        mentorDTO.setBio(mentorEntity.getBio());

        // If you need classRoomId in your DTO, get it from the relationship
        if (mentorEntity.getClassRoom() != null) {
            mentorDTO.setClassRoomId(mentorEntity.getClassRoom().getClassRoomId());
        }

        // Map sessions - avoid circular reference by creating simplified SessionDTOs
        if (mentorEntity.getSessions() != null && !mentorEntity.getSessions().isEmpty()) {
            List<SessionDTO> sessionDTOs = mentorEntity.getSessions().stream()
                    .map(MentorEntityDTOMapper::mapSessionForMentor)
                    .collect(Collectors.toList());
            mentorDTO.setSessionDTOs(sessionDTOs);
        }

        return mentorDTO;
    }

    // Simplified mapping without classroom details (to avoid circular reference)
    public static MentorDTO mapWithoutClassRoom(MentorEntity mentorEntity) {
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setMentorId(mentorEntity.getMentorId());
        mentorDTO.setFirstName(mentorEntity.getFirstName());
        mentorDTO.setLastName(mentorEntity.getLastName());
        mentorDTO.setAddress(mentorEntity.getAddress());
        mentorDTO.setEmail(mentorEntity.getEmail());
        mentorDTO.setTitle(mentorEntity.getTitle());
        mentorDTO.setProfession(mentorEntity.getProfession());
        mentorDTO.setSubject(mentorEntity.getSubject());
        mentorDTO.setPhoneNumber(mentorEntity.getPhoneNumber());
        mentorDTO.setQualification(mentorEntity.getQualification());
        mentorDTO.setSessionFee(mentorEntity.getSessionFee());
        mentorDTO.setSessionFee(mentorEntity.getSessionFee());
        mentorDTO.setPassword(mentorEntity.getPassword());
        mentorDTO.setRole(mentorEntity.getRole());
        mentorDTO.setBio(mentorEntity.getBio());

        // Only set classRoomId, don't map the full classroom object
        if (mentorEntity.getClassRoom() != null) {
            mentorDTO.setClassRoomId(mentorEntity.getClassRoom().getClassRoomId());
        }

        // Don't map sessions to keep it simple
        return mentorDTO;
    }

    public static MentorEntity map(MentorDTO mentorDTO) {
        MentorEntity mentorEntity = new MentorEntity();
        mentorEntity.setMentorId(mentorDTO.getMentorId());
        mentorEntity.setFirstName(mentorDTO.getFirstName());
        mentorEntity.setLastName(mentorDTO.getLastName());
        mentorEntity.setAddress(mentorDTO.getAddress());
        mentorEntity.setEmail(mentorDTO.getEmail());
        mentorEntity.setTitle(mentorDTO.getTitle());
        mentorEntity.setProfession(mentorDTO.getProfession());
        mentorEntity.setSubject(mentorDTO.getSubject());
        mentorEntity.setQualification(mentorDTO.getQualification());
        mentorEntity.setPhoneNumber(mentorDTO.getPhoneNumber());
        mentorEntity.setSessionFee(mentorDTO.getSessionFee());
        mentorEntity.setPassword(mentorDTO.getPassword());
        mentorEntity.setRole(mentorDTO.getRole());
        mentorEntity.setBio(mentorDTO.getBio());

        return mentorEntity;
    }

    // Helper method to map SessionEntity to SessionDTO for mentor (without mentor details to avoid circular reference)
    private static SessionDTO mapSessionForMentor(SessionEntity sessionEntity) {
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setSessionId(sessionEntity.getSessionId());
        sessionDTO.setStartTime(sessionEntity.getStartTime());
        sessionDTO.setDate(sessionEntity.getDate());

        // Use simplified mapping to avoid circular reference
        if (sessionEntity.getClassRoomEntity() != null) {
            sessionDTO.setClassRoom(ClassRoomEntityDTOMapper.mapWithoutMentor(sessionEntity.getClassRoomEntity()));
        }

        if (sessionEntity.getStudentEntity() != null) {
            sessionDTO.setStudent(StudentEntityDTOMapper.map(sessionEntity.getStudentEntity()));
        }

        return sessionDTO;
    }
} 