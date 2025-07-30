package com.skill_mentor.root.skill_mentor_root.mapper;

import com.skill_mentor.root.skill_mentor_root.dto.LiteSessionDTO;
import com.skill_mentor.root.skill_mentor_root.entity.LiteSessionEntity;

public class LiteSessionEntityDTOMapper {

    public static LiteSessionEntity map(LiteSessionDTO liteSessionDTO){
        if(liteSessionDTO == null){
            return null;
        }
        LiteSessionEntity liteSessionEntity = new LiteSessionEntity();
        liteSessionEntity.setSessionId(liteSessionDTO.getSessionId());
        liteSessionEntity.setStudentId(liteSessionDTO.getStudentId());
        liteSessionEntity.setClassRoomId(liteSessionDTO.getClassRoomId());
        liteSessionEntity.setMentorId(liteSessionDTO.getMentorId());
        liteSessionEntity.setTopic(liteSessionDTO.getTopic());
        liteSessionEntity.setStartTime(liteSessionDTO.getStartTime());
        liteSessionEntity.setDate(liteSessionDTO.getDate());
        liteSessionEntity.setStatus(liteSessionDTO.getStatus());
        liteSessionEntity.setSessionLink(liteSessionDTO.getSessionLink());
        return liteSessionEntity;
    }

    public static LiteSessionDTO map(LiteSessionEntity liteSessionEntity) {
        if (liteSessionEntity == null) {
            return null;
        }
        LiteSessionDTO liteSessionDTO = new LiteSessionDTO();
        liteSessionDTO.setSessionId(liteSessionEntity.getSessionId());
        liteSessionDTO.setStudentId(liteSessionEntity.getStudentId());
        liteSessionDTO.setClassRoomId(liteSessionEntity.getClassRoomId());
        liteSessionDTO.setMentorId(liteSessionEntity.getMentorId());
        liteSessionDTO.setTopic(liteSessionEntity.getTopic());
        liteSessionDTO.setStartTime(liteSessionEntity.getStartTime());
        liteSessionDTO.setDate(liteSessionEntity.getDate());
        liteSessionDTO.setStatus(liteSessionEntity.getStatus());
        liteSessionDTO.setSessionLink(liteSessionEntity.getSessionLink());
        return liteSessionDTO;
    }
}
