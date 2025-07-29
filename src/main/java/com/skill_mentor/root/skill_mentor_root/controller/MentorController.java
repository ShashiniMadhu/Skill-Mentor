package com.skill_mentor.root.skill_mentor_root.controller;

import com.skill_mentor.root.skill_mentor_root.common.Constants;
import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.dto.SessionDTO;
import com.skill_mentor.root.skill_mentor_root.exception.MentorException;
import com.skill_mentor.root.skill_mentor_root.service.MentorService;
import com.skill_mentor.root.skill_mentor_root.service.SessionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173"}) // Updated CORS
@RestController
@RequestMapping(value = "/academic")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @Autowired
    private SessionService sessionService;

    public MentorController() {}//explicit default constructor
    //optional and can be removed if no other constructors are defined

    @PostMapping(value = "/mentor", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ResponseEntity<MentorDTO> createMentor(@Valid @RequestBody MentorDTO mentorDTO){
        try{
            final MentorDTO savedDTO = mentorService.createMentor(mentorDTO);
            return ResponseEntity.ok(savedDTO);
        }catch(MentorException mentorException){
            return ResponseEntity.badRequest().body(null); // Note: Consider returning error DTO instead
        }
    }

    @GetMapping(value = "/mentor", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<List<MentorDTO>> getAllMentors(@RequestParam(required = false) String subject){
        final List<MentorDTO> mentorDTOS = mentorService.getAllMentors(subject);
        return ResponseEntity.ok(mentorDTOS);
    }

    @GetMapping(value = "/mentor/{id}", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<MentorDTO> findMentorById(@PathVariable @Min(value = 1, message = "Mentor ID must be a positive integer") Integer id){
        try{
            final MentorDTO mentor = mentorService.findMentorById(id);
            return ResponseEntity.ok(mentor);
        }catch(MentorException mentorException){
            return ResponseEntity.badRequest().body(null); // Note: Consider returning error DTO instead
        }
    }

    @PutMapping(value = "/mentor", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ResponseEntity<MentorDTO> updateMentor(@Valid @RequestBody MentorDTO mentorDTO) {
        try{
            final MentorDTO mentor = mentorService.updateMentorById(mentorDTO);
            return ResponseEntity.ok(mentor);
        }catch(MentorException mentorException){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Note: Consider returning error DTO instead
        }
    }

    @DeleteMapping(value = "/mentor/{id}", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<MentorDTO> deleteMentor(@PathVariable @Min(value = 1, message = "Mentor ID must be a positive integer") Integer id){
        try{
            final MentorDTO mentor = mentorService.deleteMentorById(id);
            return ResponseEntity.ok(mentor);
        }catch(MentorException mentorException){
            return ResponseEntity.badRequest().body(null); // Note: Consider returning error DTO instead
        }
    }

    // FIXED: Changed from "/{mentorId}/sessions" to "/mentor/{mentorId}/sessions"
    @GetMapping("/mentor/{mentorId}/sessions")
    public ResponseEntity<List<SessionDTO>> getSessionsByMentorId(@PathVariable Integer mentorId) {
        List<SessionDTO> sessions = sessionService.getSessionsByMentorId(mentorId);
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }
}