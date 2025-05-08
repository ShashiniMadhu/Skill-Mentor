package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;
import com.skill_mentor.root.skill_mentor_root.repository.StudentRepository;
import com.skill_mentor.root.skill_mentor_root.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceimpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO){
        StudentDTO studentDTO1 = studentRepository.createStudent(studentDTO);
        return studentDTO;
    }
}
