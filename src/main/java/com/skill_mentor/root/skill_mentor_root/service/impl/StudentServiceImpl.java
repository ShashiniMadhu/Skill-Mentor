package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;
import com.skill_mentor.root.skill_mentor_root.entity.StudentEntity;
import com.skill_mentor.root.skill_mentor_root.exception.StudentException;
import com.skill_mentor.root.skill_mentor_root.mapper.StudentEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.repository.StudentRepository;
import com.skill_mentor.root.skill_mentor_root.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        // Set studentId to null to ensure a new record is created
        if (studentDTO == null) {
            throw new IllegalArgumentException("Student data must not be null.");
        }
        final StudentEntity studentEntity = StudentEntityDTOMapper.map(studentDTO);
        final StudentEntity savedEntity = studentRepository.save(studentEntity);
        return StudentEntityDTOMapper.map(savedEntity);
    }

    @Override
    public List<StudentDTO> getAllStudents(final List<String> addresses,Integer age) {
        final List<StudentEntity> studentEntities = studentRepository.findAll();
        //return studentEntities.stream().map(StudentEntityDTOMapper::map).toList(); // without any filter parameter
        return studentEntities.stream()
                .filter(student->addresses == null || addresses.contains(student.getAddress()))//keep "contains" because of list of addresses
                .filter(student->age == null || Objects.equals(age,student.getAge()))
                .map(StudentEntityDTOMapper::map)
                .toList();
    }

    @Override
    public StudentDTO findStudentById(Integer id) {
        return studentRepository.findById(id)
                .map(StudentEntityDTOMapper::map)
                .orElse(null);
    }

    @Override
    public StudentDTO updateStudentById(StudentDTO studentDTO) {
        if (studentDTO == null || studentDTO.getStudentId() == null) {
            throw new IllegalArgumentException("Student ID must not be null for update.");
        }
        final StudentEntity studentEntity = studentRepository.findById(studentDTO.getStudentId())
                .orElseThrow(() -> new StudentException("Cannot update. Student not found with ID: " + studentDTO.getStudentId()));

        studentEntity.setFirstName(studentDTO.getFirstName());
        studentEntity.setLastName(studentDTO.getLastName());
        studentEntity.setEmail(studentDTO.getEmail());
        studentEntity.setPhoneNumber(studentDTO.getPhoneNumber());
        studentEntity.setAddress(studentDTO.getAddress());
        studentEntity.setAge(studentDTO.getAge());

        return StudentEntityDTOMapper.map(studentRepository.save(studentEntity));
    }

    @Override
    public StudentDTO deleteStudentById(final Integer id) {
        final StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException("Cannot delete. Student not found with ID: " + id));
        studentRepository.deleteById(id);
        return StudentEntityDTOMapper.map(studentEntity);
    }
}