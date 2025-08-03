package com.skill_mentor.root.skill_mentor_root.service;

import com.skill_mentor.root.skill_mentor_root.dto.AdminDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    // Add these to StudentService.java interface
    AdminDTO linkClerkUser(String email, String clerkUserId);
    AdminDTO findByClerkUserId(String clerkUserId);
    AdminDTO findByEmail(String email);
    AdminDTO createAdmin(AdminDTO adminDTO);
}
