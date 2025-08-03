package com.skill_mentor.root.skill_mentor_root.controller;

import com.skill_mentor.root.skill_mentor_root.common.Constants;
import com.skill_mentor.root.skill_mentor_root.dto.SessionDTO;
import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;
import com.skill_mentor.root.skill_mentor_root.exception.StudentException;
import com.skill_mentor.root.skill_mentor_root.service.SessionService;
import com.skill_mentor.root.skill_mentor_root.service.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173"}) // Updated CORS
@Slf4j
@Validated
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

    @GetMapping("/{studentId}/sessions")
    public ResponseEntity<List<SessionDTO>> getSessionsByStudentId(@PathVariable Integer studentId) {
        List<SessionDTO> sessions = sessionService.getSessionsByStudentId(studentId);
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @GetMapping(value = "/student/by-email/{email}", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<StudentDTO> findByEmail(@PathVariable String email) {
        final StudentDTO student = studentService.findByEmail(email);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping(value = "/student/detect-role", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<Map<String, String>> detectUserRole(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        try {
            // Check if user exists in student table
            studentService.findByEmail(email);
            return ResponseEntity.ok(Map.of("role", "student"));
        } catch (StudentException e) {
            // Check if user exists in admin table (you might want to inject AdminService here)
            try {
                // You'll need to inject AdminService or create a separate endpoint
                return ResponseEntity.ok(Map.of("role", "unknown"));
            } catch (Exception ex) {
                return ResponseEntity.ok(Map.of("role", "unknown"));
            }
        }
    }

    @PostMapping(value = "/student/link-clerk", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<StudentDTO> linkClerkUser(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String clerkUserId = request.get("clerkUserId");

        final StudentDTO student = studentService.linkClerkUser(email, clerkUserId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(value = "/student/by-clerk/{clerkUserId}", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<StudentDTO> findByClerkUserId(@PathVariable String clerkUserId) {
        final StudentDTO student = studentService.findByClerkUserId(clerkUserId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
}