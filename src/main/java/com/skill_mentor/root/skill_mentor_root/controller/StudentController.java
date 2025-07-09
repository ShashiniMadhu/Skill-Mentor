package com.skill_mentor.root.skill_mentor_root.controller;

import com.skill_mentor.root.skill_mentor_root.common.Constants;
import com.skill_mentor.root.skill_mentor_root.dto.SessionDTO;
import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;
import com.skill_mentor.root.skill_mentor_root.exception.StudentException;
import com.skill_mentor.root.skill_mentor_root.service.SessionService;
import com.skill_mentor.root.skill_mentor_root.service.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/academic")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SessionService sessionService;

    @PostMapping(value = "/student", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ResponseEntity<StudentDTO> createStudent(@RequestBody @Valid StudentDTO studentDTO) {
        final StudentDTO savedDTO = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(savedDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/student", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<List<StudentDTO>> getAllStudents(
            //@RequestParam(required = false)String address,
            @RequestParam(required = false)List<String> addresses,//when filter from multiple addresses
            @RequestParam(required = false)Integer age
    ) {
        final List<StudentDTO> studentDTOS = studentService.getAllStudents(addresses,age);
        return new ResponseEntity<>(studentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/student/{id}", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<StudentDTO> findStudentById(@PathVariable @Min(0) Integer id) throws StudentException {
        final StudentDTO student = studentService.findStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping(value = "/student", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody @Valid StudentDTO studentDTO) {
        final StudentDTO student = studentService.updateStudentById(studentDTO);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @DeleteMapping(value = "/student/{id}", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<StudentDTO> deleteStudent(@PathVariable @Min(0) Integer id){
        final StudentDTO student = studentService.deleteStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    // NEW: Get all sessions for a specific student
    @GetMapping("/{studentId}/sessions")
    public ResponseEntity<List<SessionDTO>> getSessionsByStudentId(@PathVariable Integer studentId) {
        List<SessionDTO> sessions = sessionService.getSessionsByStudentId(studentId);
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

}