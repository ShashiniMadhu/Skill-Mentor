package com.skill_mentor.root.skill_mentor_root.mapper;

import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.entity.MentorEntity;

public class MentorEntityDTOMapper {
    public static MentorDTO map(MentorEntity mentorEntity){
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setMentorId(mentorEntity.getMentorId());
        mentorDTO.setFirstName(mentorEntity.getFirstName());
        mentorDTO.setLastName(mentorEntity.getLastName());
        mentorDTO.setAddress(mentorEntity.getAddress());
        mentorDTO.setEmail(mentorEntity.getEmail());
        mentorDTO.setTitle(mentorEntity.getTitle());
        mentorDTO.setProfession(mentorEntity.getProfession());
        mentorDTO.setSubject(mentorEntity.getSubject());
        mentorDTO.setQualification(mentorEntity.getQualification());

        //one to many
//        if (mentorEntity.getClassRoomEntity() != null) {
//            mentorDTO.setClassRoomId(mentorEntity.getClassRoomEntity().getClassRoomId());
//        }

        return mentorDTO;
    }

    public static MentorEntity map(MentorDTO mentorDTO){
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
            //commented out cuz , mapping occurs at service layer
        //mentorEntity.setClassRoomId(mentorDTO.getClassRoomId());
        return mentorEntity;
    }
}
