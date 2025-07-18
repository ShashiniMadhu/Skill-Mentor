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

        // Use simplified mappings to avoid circular references
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
        sessionDTO.setStartTime(sessionEntity.getStartTime());
        sessionDTO.setEndTime(sessionEntity.getEndTime());
        return sessionDTO;
    }

    // Alternative mapping with full details (use carefully to avoid circular references)
    public static SessionDTO mapWithFullDetails(SessionEntity sessionEntity) {
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setSessionId(sessionEntity.getSessionId());

        // Full mapping - use only when you're sure there won't be circular references
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
        sessionDTO.setStartTime(sessionEntity.getStartTime());
        sessionDTO.setEndTime(sessionEntity.getEndTime());
        return sessionDTO;
    }

    public static SessionEntity map(SessionDTO sessionDTO, ClassRoomEntity classRoomEntity,
                                    MentorEntity mentorEntity, StudentEntity studentEntity) {
        return new SessionEntity(
                sessionDTO.getSessionId(),
                classRoomEntity,
                mentorEntity,
                studentEntity,
                sessionDTO.getTopic(),
                sessionDTO.getStartTime(),
                sessionDTO.getEndTime()
        );
    }
}