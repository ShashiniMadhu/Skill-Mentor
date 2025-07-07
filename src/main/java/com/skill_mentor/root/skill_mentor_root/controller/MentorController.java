package com.skill_mentor.root.skill_mentor_root.controller;

import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;
import com.skill_mentor.root.skill_mentor_root.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/mentor")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @PostMapping
    public ResponseEntity<MentorDTO> createMentor(@RequestBody MentorDTO mentorDTO){
        MentorDTO savedDTO = mentorService.createMentor(mentorDTO);
        return new ResponseEntity<>(savedDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MentorDTO>> getAllMentors(@RequestParam(required = false) String subject){
        List<MentorDTO> mentorDTOS = mentorService.getAllMentors(subject);
        return new ResponseEntity<>(mentorDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorDTO> getMentorById(@PathVariable Integer Id){
        MentorDTO mentor = mentorService.getMentorById(Id);
        return new ResponseEntity<>(mentor, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<MentorDTO> updateMentor(@RequestBody MentorDTO mentorDTO) {
        mentorService.updateMentorById(mentorDTO);
        return new ResponseEntity<>(mentorDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MentorDTO> deleteMentor(@PathVariable Integer id){
        MentorDTO mentor = mentorService.deleteMentorById(id);
        return new ResponseEntity<>(mentor, HttpStatus.OK);
    }

}