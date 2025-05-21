//package com.skill_mentor.root.skill_mentor_root.controller;
//
//import com.skill_mentor.root.skill_mentor_root.dto.SessionDTO;
//import com.skill_mentor.root.skill_mentor_root.service.SessionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/session")
//public class SessionController {
//
//    @Autowired
//    SessionService sessionService;
//
//    @PostMapping
//    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTO sessionDTO){
//        SessionDTO createdSession = sessionService.createSession(sessionDTO);
//        if (createdSession != null) {
//            return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//}
