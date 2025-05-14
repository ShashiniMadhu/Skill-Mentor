package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dao.StudentDAO;
import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;
import com.skill_mentor.root.skill_mentor_root.repository.StudentRepository;
import com.skill_mentor.root.skill_mentor_root.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentDAO studentDAO;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        StudentDTO studentDTO1 = studentDAO.createStudent(studentDTO);
        return studentDTO1;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Override
    public StudentDTO getStudentById(Integer id) {
        return studentRepository.getStudentById(id);
    }

    @Override
    public StudentDTO updateStudentById(StudentDTO studentDTO) {
        return studentRepository.updateStudentById(studentDTO);
    }

    @Override
    public StudentDTO deleteStudentById(Integer id) {
        return studentRepository.deleteStudentById(id);
    }
}