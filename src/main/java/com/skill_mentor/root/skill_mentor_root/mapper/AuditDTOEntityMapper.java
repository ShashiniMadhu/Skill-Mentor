package com.skill_mentor.root.skill_mentor_root.mapper;

import com.skill_mentor.root.skill_mentor_root.dto.AuditDTO;
import com.skill_mentor.root.skill_mentor_root.entity.SessionEntity;

public class AuditDTOEntityMapper {
    public static AuditDTO map(SessionEntity sessionEntity) {
        if (sessionEntity == null) {
            return null;
        }

        AuditDTO auditDTO = new AuditDTO();
        auditDTO.setSessionId(sessionEntity.getSessionId());
        auditDTO.setStartTime(sessionEntity.getStartTime());
        auditDTO.setDate(sessionEntity.getDate());
        auditDTO.setTopic(sessionEntity.getTopic());

        // Safe mapping for StudentEntity
        if (sessionEntity.getStudentEntity() != null) {
            auditDTO.setStudentId(sessionEntity.getStudentEntity().getStudentId());
            auditDTO.setStudentFirstName(sessionEntity.getStudentEntity().getFirstName());
            auditDTO.setStudentLastName(sessionEntity.getStudentEntity().getLastName());
            auditDTO.setStudentEmail(sessionEntity.getStudentEntity().getEmail());
            auditDTO.setStudentPhoneNumber(sessionEntity.getStudentEntity().getPhoneNumber());
        }

        // Safe mapping for ClassRoomEntity
        if (sessionEntity.getClassRoomEntity() != null) {
            auditDTO.setClassTitle(sessionEntity.getClassRoomEntity().getTitle());
            // If fee is stored in ClassRoomEntity
            // auditDTO.setFee(sessionEntity.getClassRoomEntity().getSessionFee());
        }

        // Safe mapping for MentorEntity
        if (sessionEntity.getMentorEntity() != null) {
            auditDTO.setMentorId(sessionEntity.getMentorEntity().getMentorId());
            auditDTO.setMentorFirstName(sessionEntity.getMentorEntity().getFirstName());
            auditDTO.setMentorLastName(sessionEntity.getMentorEntity().getLastName());
            auditDTO.setMentorPhoneNumber(sessionEntity.getMentorEntity().getPhoneNumber());
            // If fee is stored in MentorEntity
            auditDTO.setFee(sessionEntity.getMentorEntity().getSessionFee());
        }

        return auditDTO;
    }
}