package com.skill_mentor.root.skill_mentor_root.service;

import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;

public interface StudentService {
    /**
     * To create student
     * @param studentDTO student DTO
     * @return created student
     */
    StudentDTO createStudent(StudentDTO studentDTO);
}
