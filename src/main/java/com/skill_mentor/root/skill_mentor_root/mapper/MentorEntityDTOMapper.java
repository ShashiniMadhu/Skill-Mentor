package com.skill_mentor.root.skill_mentor_root.mapper;

import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.entity.MentorEntity;

public class MentorEntityDTOMapper {
    public static MentorDTO map(MentorEntity mentorEntity) {
        if (mentorEntity == null) return null;

        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setMentorId(mentorEntity.getMentorId());
        mentorDTO.setFirstName(mentorEntity.getFirstName());
        mentorDTO.setLastName(mentorEntity.getLastName());
        mentorDTO.setEmail(mentorEntity.getEmail());
        mentorDTO.setProfession(mentorEntity.getProfession());
        mentorDTO.setAddress(mentorEntity.getAddress());
        mentorDTO.setTitle(mentorEntity.getTitle());
        mentorDTO.setSubject(mentorEntity.getSubject());
        mentorDTO.setQualification(mentorEntity.getQualification());

        // THIS IS THE KEY FIX: properly setting the classRoomId from the relationship
        if (mentorEntity.getClassRoom() != null) {
            mentorDTO.setClassRoomId(mentorEntity.getClassRoom().getClassRoomId());
        }

        return mentorDTO;
    }

    public static MentorEntity map(MentorDTO mentorDTO) {
        if (mentorDTO == null) return null;

        MentorEntity mentorEntity = new MentorEntity();
        mentorEntity.setMentorId(mentorDTO.getMentorId());
        mentorEntity.setFirstName(mentorDTO.getFirstName());
        mentorEntity.setLastName(mentorDTO.getLastName());
        mentorEntity.setEmail(mentorDTO.getEmail());
        mentorEntity.setProfession(mentorDTO.getProfession());
        mentorEntity.setAddress(mentorDTO.getAddress());
        mentorEntity.setTitle(mentorDTO.getTitle());
        mentorEntity.setSubject(mentorDTO.getSubject());
        mentorEntity.setQualification(mentorDTO.getQualification());

        // Note: We don't set the ClassRoom here to avoid circular references
        // If needed, it should be set in the service layer

        return mentorEntity;
    }
}
