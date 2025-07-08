package com.skill_mentor.root.skill_mentor_root.mapper;

import com.skill_mentor.root.skill_mentor_root.dto.ClassRoomDTO;
import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.entity.ClassRoomEntity;
import com.skill_mentor.root.skill_mentor_root.entity.MentorEntity;

public class ClassRoomEntityDTOMapper {

    // Full mapping with mentor details
    public static ClassRoomDTO map(ClassRoomEntity classRoomEntity){
        ClassRoomDTO classRoomDTO = new ClassRoomDTO();
        classRoomDTO.setClassRoomId(classRoomEntity.getClassRoomId());
        classRoomDTO.setTitle(classRoomEntity.getTitle());
//        classRoomDTO.setSessionFee(classRoomEntity.getSessionFee());
        classRoomDTO.setEnrolledStudentCount(classRoomEntity.getEnrolledStudentCount());

        if (classRoomEntity.getMentor() != null) {
            // Use the simplified mapping to avoid circular reference
            MentorDTO mentorDTO = MentorEntityDTOMapper.mapWithoutClassRoom(classRoomEntity.getMentor());
            classRoomDTO.setMentor(mentorDTO);
        }
        return classRoomDTO;
    }

    // Simplified mapping without mentor details (to avoid circular reference)
    public static ClassRoomDTO mapWithoutMentor(ClassRoomEntity classRoomEntity){
        ClassRoomDTO classRoomDTO = new ClassRoomDTO();
        classRoomDTO.setClassRoomId(classRoomEntity.getClassRoomId());
        classRoomDTO.setTitle(classRoomEntity.getTitle());
//        classRoomDTO.setSessionFee(classRoomEntity.getSessionFee());
        classRoomDTO.setEnrolledStudentCount(classRoomEntity.getEnrolledStudentCount());
        // Don't map mentor to avoid circular reference
        return classRoomDTO;
    }

    public static ClassRoomEntity map(ClassRoomDTO classRoomDTO){
        ClassRoomEntity classRoomEntity = new ClassRoomEntity();
        classRoomEntity.setClassRoomId(classRoomDTO.getClassRoomId());
        classRoomEntity.setTitle(classRoomDTO.getTitle());
//        classRoomEntity.setSessionFee(classRoomDTO.getSessionFee());
        classRoomEntity.setEnrolledStudentCount(classRoomDTO.getEnrolledStudentCount());
        return classRoomEntity;
    }

    public static ClassRoomEntity map(ClassRoomDTO classRoomDTO, MentorEntity mentorEntity) {
        ClassRoomEntity classRoomEntity = map(classRoomDTO);
        classRoomEntity.setMentor(mentorEntity);
        return classRoomEntity;
    }
}