package com.skill_mentor.root.skill_mentor_root.mapper;

import com.skill_mentor.root.skill_mentor_root.dto.AuditDTO;
import com.skill_mentor.root.skill_mentor_root.entity.SessionEntity;

public class AuditDTOEntityMapper {
    public static AuditDTO map( SessionEntity sessionEntity) {
        if (sessionEntity == null) {
            return null;
        }
        AuditDTO auditDTO = new AuditDTO();
        auditDTO.setSessionId(sessionEntity.getSessionId());
        auditDTO.setStudentId(sessionEntity.getStudentEntity().getStudentId());
        auditDTO.setStudentFirstName(sessionEntity.getStudentEntity().getFirstName());
        auditDTO.setStudentLastName(sessionEntity.getStudentEntity().getLastName());
        auditDTO.setStudentEmail(sessionEntity.getStudentEntity().getEmail());
        auditDTO.setStudentPhoneNumber(sessionEntity.getStudentEntity().getPhoneNumber());
        auditDTO.setClassTitle(sessionEntity.getClassRoomEntity().getTitle());
        auditDTO.setMentorId(sessionEntity.getMentorEntity().getMentorId());
        auditDTO.setMentorFirstName(sessionEntity.getMentorEntity().getFirstName());
        auditDTO.setMentorLastName(sessionEntity.getMentorEntity().getLastName());
        auditDTO.setMentorPhoneNumber(sessionEntity.getMentorEntity().getPhoneNumber());
        auditDTO.setFee(sessionEntity.getClassRoomEntity().getSessionFee());
        auditDTO.setStartTime(sessionEntity.getStartTime());
        auditDTO.setEndTime(sessionEntity.getEndTime());
        auditDTO.setTopic(sessionEntity.getTopic());
        return auditDTO;
    }
}
