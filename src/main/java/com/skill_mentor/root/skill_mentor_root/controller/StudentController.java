package com.skill_mentor.root.skill_mentor_root.controller;
import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/student")
public class StudentController {

    @PostMapping()
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO){
        studentDTO.setFirstName(studentDTO.getFirstName()+" modified");
        return new ResponseEntity<>(studentDTO,HttpStatus.OK);
    }
}
