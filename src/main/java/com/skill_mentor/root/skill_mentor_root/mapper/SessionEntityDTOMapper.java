package com.skill_mentor.root.skill_mentor_root.mapper;

import com.skill_mentor.root.skill_mentor_root.dto.SessionDTO;
import com.skill_mentor.root.skill_mentor_root.entity.ClassRoomEntity;
import com.skill_mentor.root.skill_mentor_root.entity.MentorEntity;
import com.skill_mentor.root.skill_mentor_root.entity.SessionEntity;
import com.skill_mentor.root.skill_mentor_root.entity.StudentEntity;

public class SessionEntityDTOMapper {

    // Main mapping method - use simplified mappings to avoid circular references
    public static SessionDTO map(SessionEntity sessionEntity) {
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setSessionId(sessionEntity.getSessionId());

        // Use simplified mappings
        if (sessionEntity.getClassRoomEntity() != null) {
            sessionDTO.setClassRoom(ClassRoomEntityDTOMapper.mapWithoutMentor(sessionEntity.getClassRoomEntity()));
        }

        if (sessionEntity.getMentorEntity() != null) {
            sessionDTO.setMentor(MentorEntityDTOMapper.mapWithoutClassRoom(sessionEntity.getMentorEntity()));
        }

        if (sessionEntity.getStudentEntity() != null) {
            sessionDTO.setStudent(StudentEntityDTOMapper.map(sessionEntity.getStudentEntity()));
        }

        sessionDTO.setTopic(sessionEntity.getTopic());
        sessionDTO.setDate(sessionEntity.getDate());
        sessionDTO.setStartTime(sessionEntity.getStartTime());
        sessionDTO.setStatus(sessionEntity.getStatus());
        sessionDTO.setSessionLink(sessionEntity.getSessionLink());
        sessionDTO.setSlipLink(sessionEntity.getSlipLink());

        return sessionDTO;
    }

    // Full mapping - only if you're sure there won't be circular references
    public static SessionDTO mapWithFullDetails(SessionEntity sessionEntity) {
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setSessionId(sessionEntity.getSessionId());

        if (sessionEntity.getClassRoomEntity() != null) {
            sessionDTO.setClassRoom(ClassRoomEntityDTOMapper.map(sessionEntity.getClassRoomEntity()));
        }

        if (sessionEntity.getMentorEntity() != null) {
            sessionDTO.setMentor(MentorEntityDTOMapper.map(sessionEntity.getMentorEntity()));
        }

        if (sessionEntity.getStudentEntity() != null) {
            sessionDTO.setStudent(StudentEntityDTOMapper.map(sessionEntity.getStudentEntity()));
        }

        sessionDTO.setTopic(sessionEntity.getTopic());
        sessionDTO.setDate(sessionEntity.getDate());
        sessionDTO.setStartTime(sessionEntity.getStartTime());
        sessionDTO.setStatus(sessionEntity.getStatus());
        sessionDTO.setSessionLink(sessionEntity.getSessionLink());
        sessionDTO.setSlipLink(sessionEntity.getSlipLink());

        return sessionDTO;
    }

    // Entity mapping from DTO
    public static SessionEntity map(SessionDTO sessionDTO, ClassRoomEntity classRoomEntity,
                                    MentorEntity mentorEntity, StudentEntity studentEntity) {
        return new SessionEntity(
                sessionDTO.getSessionId(),
                classRoomEntity,
                mentorEntity,
                studentEntity,
                sessionDTO.getTopic(),
                sessionDTO.getDate(),
                sessionDTO.getStartTime(),
                sessionDTO.getStatus(),
                sessionDTO.getSessionLink(),
                sessionDTO.getSlipLink()
        );
    }
}
