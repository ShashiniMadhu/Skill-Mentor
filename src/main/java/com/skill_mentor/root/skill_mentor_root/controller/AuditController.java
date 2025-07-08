package com.skill_mentor.root.skill_mentor_root.controller;

import com.skill_mentor.root.skill_mentor_root.dto.AuditDTO;
import com.skill_mentor.root.skill_mentor_root.dto.PaymentDTO;
import com.skill_mentor.root.skill_mentor_root.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/audit")
public class AuditController {
    @Autowired
    private SessionService sessionService;

    @GetMapping
    public ResponseEntity<List<AuditDTO>> getAllAudits(){
        List<AuditDTO> auditDTOs = sessionService.getAllAudits();
        return new ResponseEntity<>(auditDTOs, HttpStatus.OK);
    }

    @GetMapping("/mentor-payments")
    public ResponseEntity<List<PaymentDTO>> findMentorPayments(@RequestParam(name = "startDate", required = false) String startDate,
                                                               @RequestParam(name = "endDate", required = false) String endDate){
        List<PaymentDTO> auditDTOS = sessionService.findMentorPayments(startDate, endDate);
        return new ResponseEntity<>(auditDTOS, HttpStatus.OK);
    }
}
