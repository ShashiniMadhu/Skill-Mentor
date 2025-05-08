package com.skill_mentor.root.skill_mentor_root.repository;

import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private List<StudentDTO> students = new ArrayList<>();

    public StudentDTO createStudent(StudentDTO studentDTO){
        students.add(studentDTO);
        return studentDTO;
    }
}
