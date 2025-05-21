package com.skill_mentor.root.skill_mentor_root.mapper;

import com.skill_mentor.root.skill_mentor_root.dto.ClassRoomDTO;
import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.entity.ClassRoomEntity;
import com.skill_mentor.root.skill_mentor_root.entity.MentorEntity;

public class ClassRoomEntityDTOMapper {
    public static ClassRoomDTO map(ClassRoomEntity classRoomEntity){
        if(classRoomEntity == null) return null;

        ClassRoomDTO classRoomDTO = new ClassRoomDTO();
        classRoomDTO.setClassRoomId(classRoomEntity.getClassRoomId());
        classRoomDTO.setTitle(classRoomEntity.getTitle());
        classRoomDTO.setSessionFee(classRoomEntity.getSessionFee());
        classRoomDTO.setEnrolledStudentCount(classRoomEntity.getEnrolledStudentCount());

        // Fixed: Add null check before mapping mentor
        if (classRoomEntity.getMentor() != null) {
            MentorDTO mentorDTO = MentorEntityDTOMapper.map(classRoomEntity.getMentor());
            classRoomDTO.setMentorId(mentorDTO);
        }

        return classRoomDTO;
    }

    public static ClassRoomEntity map(ClassRoomDTO classRoomDTO){
        if(classRoomDTO == null) return null;

        ClassRoomEntity classRoomEntity = new ClassRoomEntity();
        classRoomEntity.setClassRoomId(classRoomDTO.getClassRoomId());
        classRoomEntity.setTitle(classRoomDTO.getTitle());
        classRoomEntity.setSessionFee(classRoomDTO.getSessionFee());
        classRoomEntity.setEnrolledStudentCount(classRoomDTO.getEnrolledStudentCount());

        // Fixed: Add null check before mapping mentor
        if (classRoomDTO.getMentorId() != null) {
            MentorEntity mentorEntity = MentorEntityDTOMapper.map(classRoomDTO.getMentorId());
            classRoomEntity.setMentor(mentorEntity);
        }

        return classRoomEntity;
    }
}
