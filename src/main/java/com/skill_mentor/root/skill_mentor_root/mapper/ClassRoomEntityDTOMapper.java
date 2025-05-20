package com.skill_mentor.root.skill_mentor_root.mapper;

import com.skill_mentor.root.skill_mentor_root.dto.ClassRoomDTO;
import com.skill_mentor.root.skill_mentor_root.entity.ClassRoomEntity;

public class ClassRoomEntityDTOMapper {
    public static ClassRoomDTO map(ClassRoomEntity classRoomEntity){
        ClassRoomDTO classRoomDTO = new ClassRoomDTO();
        classRoomDTO.setClassRoomId(classRoomEntity.getClassRoomId());
        classRoomDTO.setTitle(classRoomEntity.getTitle());
        classRoomDTO.setSessionFee(classRoomEntity.getSessionFee());
        classRoomDTO.setEnrolledStudentCount(classRoomEntity.getEnrolledStudentCount());
        classRoomDTO.setMentorId(classRoomEntity.getMentorId());
        return classRoomDTO;
    }

    public static ClassRoomEntity map(ClassRoomDTO classRoomDTO){
        ClassRoomEntity classRoomEntity = new ClassRoomEntity();
        classRoomEntity.setClassRoomId(classRoomDTO.getClassRoomId());
        classRoomEntity.setTitle(classRoomDTO.getTitle());
        classRoomEntity.setSessionFee(classRoomDTO.getSessionFee());
        classRoomEntity.setEnrolledStudentCount(classRoomDTO.getEnrolledStudentCount());
        classRoomEntity.setMentorId(classRoomDTO.getMentorId());
        return classRoomEntity;
    }
}
