package com.skill_mentor.root.skill_mentor_root.controller;
import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;

import com.skill_mentor.root.skill_mentor_root.service.StudentService;
import com.skill_mentor.root.skill_mentor_root.service.impl.StudentServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/student")
public class StudentController {

    @Autowired
    private StudentService  studentService;

    @PostMapping()
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO){
        studentDTO.setFirstName(studentDTO.getFirstName()+" modified");
        studentService.createStudent(studentDTO);
        return new ResponseEntity<>(studentDTO,HttpStatus.OK);
    }
}
