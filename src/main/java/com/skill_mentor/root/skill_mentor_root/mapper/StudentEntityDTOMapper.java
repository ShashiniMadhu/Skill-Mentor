package com.skill_mentor.root.skill_mentor_root.mapper;

import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;
import com.skill_mentor.root.skill_mentor_root.entity.StudentEntity;

public class StudentEntityDTOMapper {
    public static StudentDTO map(StudentEntity studentEntity) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(studentEntity.getStudentId());
        studentDTO.setFirstName(studentEntity.getFirstName());
        studentDTO.setLastName(studentEntity.getLastName());
        studentDTO.setEmail(studentEntity.getEmail());
        studentDTO.setPhoneNumber(studentEntity.getPhoneNumber());
        studentDTO.setAddress(studentEntity.getAddress());
        studentDTO.setAge(studentEntity.getAge());
        studentDTO.setPassword(studentEntity.getPassword());
        studentDTO.setRole(studentEntity.getRole());
        return studentDTO;
    }

    public static StudentEntity map(StudentDTO studentDTO) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setStudentId(studentDTO.getStudentId());
        studentEntity.setFirstName(studentDTO.getFirstName());
        studentEntity.setLastName(studentDTO.getLastName());
        studentEntity.setEmail(studentDTO.getEmail());
        studentEntity.setPhoneNumber(studentDTO.getPhoneNumber());
        studentEntity.setAddress(studentDTO.getAddress());
        studentEntity.setAge(studentDTO.getAge());
        studentEntity.setPassword(studentDTO.getPassword());
        studentEntity.setRole(studentDTO.getRole());
        return studentEntity;
    }
}