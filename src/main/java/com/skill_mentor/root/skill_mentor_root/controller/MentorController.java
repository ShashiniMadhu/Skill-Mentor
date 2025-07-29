package com.skill_mentor.root.skill_mentor_root.controller;

import com.skill_mentor.root.skill_mentor_root.common.Constants;
import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.exception.MentorException;
import com.skill_mentor.root.skill_mentor_root.service.MentorService;
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

    public MentorController() {}//explicit default constructor
    //optional and can be removed if no other constructors are defined

    @PostMapping(value = "/mentor", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ResponseEntity<?> createMentor(@Valid @RequestBody MentorDTO mentorDTO){
        try{
            final MentorDTO savedDTO = mentorService.createMentor(mentorDTO);
            //return new ResponseEntity<>(savedDTO, HttpStatus.OK);
            return ResponseEntity.ok(savedDTO);
        }catch(MentorException mentorException){
            return ResponseEntity.badRequest().body(mentorException.getMessage());
        }
    }

    @GetMapping(value = "/mentor", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<List<MentorDTO>> getAllMentors(@RequestParam(required = false) String subject){
        final List<MentorDTO> mentorDTOS = mentorService.getAllMentors(subject);
        return ResponseEntity.ok(mentorDTOS);
    }

    @GetMapping(value = "/mentor/{id}", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<?> findMentorById(@PathVariable @Min(value = 1, message = "Mentor ID must be a positive integer") Integer id){
        try{
            final MentorDTO mentor = mentorService.findMentorById(id);
            return ResponseEntity.ok(mentor);
        }catch(MentorException mentorException){
            return ResponseEntity.badRequest().body(mentorException.getMessage());
        }
    }

    @PutMapping(value = "/mentor", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ResponseEntity<?> updateMentor(@Valid @RequestBody MentorDTO mentorDTO) {
        try{
            final MentorDTO mentor = mentorService.updateMentorById(mentorDTO);
            return ResponseEntity.ok(mentor);
        }catch(MentorException mentorException){
            return new ResponseEntity<>(mentorException.getMessage(), HttpStatus.NOT_FOUND);        }
    }

    @DeleteMapping(value = "/mentor/{id}", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<?> deleteMentor(@PathVariable @Min(value = 1, message = "Mentor ID must be a positive integer") Integer id){
        try{
            final MentorDTO mentor = mentorService.deleteMentorById(id);
            return ResponseEntity.ok(mentor);
        }catch(MentorException mentorException){
            return ResponseEntity.badRequest().body(mentorException.getMessage());
        }
    }

}