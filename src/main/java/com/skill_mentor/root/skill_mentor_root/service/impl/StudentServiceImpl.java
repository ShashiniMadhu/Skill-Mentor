package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;
import com.skill_mentor.root.skill_mentor_root.entity.StudentEntity;
import com.skill_mentor.root.skill_mentor_root.mapper.StudentEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.repository.StudentRepository;
import com.skill_mentor.root.skill_mentor_root.service.StudentService;
import jakarta.transaction.Transactional;
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
        studentDTO.setStudentId(null);
        final StudentEntity studentEntity = StudentEntityDTOMapper.map(studentDTO);
        final StudentEntity savedEntity = studentRepository.save(studentEntity);
        return StudentEntityDTOMapper.map(savedEntity);
    }

//    //Method "createSturdent" works with query method of the repository
//    @Override
//    @Transactional
//    public StudentDTO createStudent(StudentDTO studentDTO) {
//        // Set studentId to null to ensure a new record is created
//        studentDTO.setStudentId(null);
//        // Use the custom saveStudent method
//        studentRepository.saveStudent(
//                studentDTO.getFirstName(),
//                studentDTO.getLastName(),
//                studentDTO.getEmail(),
//                studentDTO.getPhoneNumber(),
//                studentDTO.getAddress(),
//                studentDTO.getAge()
//        );
//        return studentDTO;
//    }


    @Override
    public List<StudentDTO> getAllStudents(List<String> addresses,Integer age) {
        final List<StudentEntity> studentEntities = studentRepository.findAll();
        //return studentEntities.stream().map(StudentEntityDTOMapper::map).toList(); // without any filter parameter
        return studentEntities.stream()
                .filter(student->addresses == null || addresses.contains(student.getAddress()))//keep "contains" because of list of addresses
                .filter(student->age == null || Objects.equals(age,student.getAge()))
                .map(StudentEntityDTOMapper::map)
                .toList();
    }

    @Override
    public StudentDTO getStudentById(Integer id) {
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        return studentEntity.map(StudentEntityDTOMapper::map).orElse(null);
    }

    @Override
    public StudentDTO updateStudentById(StudentDTO studentDTO) {
        StudentEntity studentEntity = studentRepository.findById(studentDTO.getStudentId()).orElse(null);
        if(studentEntity != null){
            studentEntity.setFirstName(studentDTO.getFirstName());
            studentEntity.setLastName(studentDTO.getLastName());
            studentEntity.setEmail(studentDTO.getEmail());
            studentEntity.setPhoneNumber(studentDTO.getPhoneNumber());
            studentEntity.setAddress(studentDTO.getAddress());
            studentEntity.setAge(studentDTO.getAge());
            StudentEntity updateEntity = studentRepository.save(studentEntity);
            return StudentEntityDTOMapper.map(updateEntity);
        }
        return null;
    }

    @Override
    public StudentDTO deleteStudentById(Integer id) {
        //studentRepository.deleteById();
        StudentEntity studentEntity = studentRepository.findById(id).orElse(null);
        studentRepository.deleteById(id);
        return StudentEntityDTOMapper.map(studentEntity);
    }
}