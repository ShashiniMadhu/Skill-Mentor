package com.skill_mentor.root.skill_mentor_root.controller;

import com.skill_mentor.root.skill_mentor_root.common.Constants;
import com.skill_mentor.root.skill_mentor_root.dto.AdminDTO;
import com.skill_mentor.root.skill_mentor_root.exception.AdminException;
import com.skill_mentor.root.skill_mentor_root.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173"}) // Updated CORS
@RestController
@RequestMapping(value = "/academic") // Fixed: Added full path
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/admin/link-clerk", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<AdminDTO> linkClerkUser(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String clerkUserId = request.get("clerkUserId");

        final AdminDTO admin = adminService.linkClerkUser(email, clerkUserId);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/by-clerk/{clerkUserId}", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<AdminDTO> findByClerkUserId(@PathVariable String clerkUserId) {
        final AdminDTO admin = adminService.findByClerkUserId(clerkUserId);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/by-email/{email}", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<AdminDTO> findByEmail(@PathVariable String email) {
        final AdminDTO admin = adminService.findByEmail(email);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PostMapping(value = "/admin", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminDTO adminDTO) {
        final AdminDTO createdAdmin = adminService.createAdmin(adminDTO);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }

    // Update the detect-role method to check both tables
    @PostMapping(value = "/admin/detect-role", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<Map<String, String>> detectUserRole(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        try {
            // Check if user exists in admin table first
            adminService.findByEmail(email);
            return ResponseEntity.ok(Map.of("role", "admin"));
        } catch (AdminException e) {
            // If not admin, it could be student or unknown
            // You might want to inject StudentService here or call student endpoint
            return ResponseEntity.ok(Map.of("role", "unknown"));
        }
    }

}
