package com.skill_mentor.root.skill_mentor_root.controller;

import com.skill_mentor.root.skill_mentor_root.common.Constants;
import com.skill_mentor.root.skill_mentor_root.dto.LiteSessionDTO;
import com.skill_mentor.root.skill_mentor_root.dto.SessionDTO;
import com.skill_mentor.root.skill_mentor_root.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/academic")
public class SessionController {

    @Autowired
    private SessionService sessionService;

//    @PostMapping
//    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTO sessionDTO){
//        SessionDTO createdSession = sessionService.createSession(sessionDTO);
//        if(createdSession != null){
//            return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
//        }else{
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    //For LiteDTO
    @PostMapping(value = "/session", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ResponseEntity<LiteSessionDTO> createSession(@RequestBody LiteSessionDTO sessionDTO){
        final LiteSessionDTO createdSession = sessionService.createSession(sessionDTO);
        if(createdSession != null){
            return ResponseEntity.ok(createdSession);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<SessionDTO> getSessionById(@PathVariable Integer sessionId) {
        SessionDTO sessionDTO = sessionService.getSessionById(sessionId);
        if (sessionDTO != null) {
            return new ResponseEntity<>(sessionDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/session", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<List<SessionDTO>> getAllSessions() {
        final List<SessionDTO> sessions = sessionService.getAllSessions();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<SessionDTO> updateSession(@RequestBody SessionDTO sessionDTO) {
        SessionDTO sessionDTO1 = sessionService.updateSession(sessionDTO);
        return new ResponseEntity<>(sessionDTO1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SessionDTO> deleteSession(@PathVariable Integer id) {
        SessionDTO session = sessionService.deleteSessionById(id);
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

}
