package com.skill_mentor.root.skill_mentor_root.controller;
import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;

import com.skill_mentor.root.skill_mentor_root.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/student")
public class StudentController {

    @Autowired
    private StudentService  studentService;

    @PostMapping()
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        studentService.createStudent(studentDTO);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        List<StudentDTO> studentDTOS = studentService.getAllStudents();
        return new ResponseEntity<>(studentDTOS, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<StudentDTO>> getStudentsByParam(@RequestParam(required = true) Integer age){
        List<StudentDTO> studentDTOS = studentService.getStudentsByParam(age);
        return new ResponseEntity<>(studentDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> findStudentById(@PathVariable Integer id){
        StudentDTO student = studentService.getStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO studentDTO){
        StudentDTO student = studentService.updateStudentById(studentDTO);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDTO> deleteStudent(@PathVariable Integer id){
        StudentDTO student = studentService.deleteStudentByid(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
}
