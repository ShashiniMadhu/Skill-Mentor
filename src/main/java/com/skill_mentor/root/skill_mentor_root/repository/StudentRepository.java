package com.skill_mentor.root.skill_mentor_root.repository;

import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class StudentRepository {
    private final List<StudentDTO> students = new ArrayList<>();

    public StudentDTO createStudent(StudentDTO studentDTO) {
        students.add(studentDTO);
        return studentDTO;
    }

//    public List<StudentDTO> getAllStudents() {
//        return students.stream().filter(stu->age == null || stu.getAge().equals(age)).toList();
//    }

    public StudentDTO getStudentById(Integer id){
        Optional<StudentDTO> studentDTOOptional  = students.stream().filter(stu-> Objects.equals(stu.getStudentId(), id)).findFirst();
        return studentDTOOptional.orElse(null);
    }

//    public StudentDTO updateStudentById(StudentDTO studentDTO){
//        Optional<StudentDTO> studentDTO1 = students.stream().filter(stu-> stu.getStudentId().equals(studentDTO.getStudentId())).findFirst();
//        StudentDTO updatedStudent = studentDTO1.orElse(null);
//        updatedStudent.setLastName(studentDTO.getLastName());
//        students.add(0, studentDTO);
//        return updatedStudent;
//    }

    public StudentDTO updateStudentById(StudentDTO studentDTO){
        Optional<StudentDTO> studentDTO1 = students.stream().filter(stu-> stu.getStudentId().equals(studentDTO.getStudentId())).findFirst();
        StudentDTO updatedStudent = studentDTO1.orElse(null);
        if (updatedStudent != null) {
            updatedStudent.setLastName(studentDTO.getLastName());
            // Replace this line:
            // students.add(0, studentDTO);

            // With this line:
            students.replaceAll(s -> s.getStudentId().equals(studentDTO.getStudentId()) ? updatedStudent : s);
        }
        return updatedStudent;
    }

    public StudentDTO deleteStudentById(Integer id){
        StudentDTO studentDTO = students.get(id);
        students.remove(studentDTO);
        return studentDTO;
    }
}