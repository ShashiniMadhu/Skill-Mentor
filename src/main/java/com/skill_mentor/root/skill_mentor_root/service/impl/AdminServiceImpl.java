package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.AdminDTO;
import com.skill_mentor.root.skill_mentor_root.entity.AdminEntity;
import com.skill_mentor.root.skill_mentor_root.exception.AdminException;
import com.skill_mentor.root.skill_mentor_root.mapper.AdminEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.repository.AdminRepository;
import com.skill_mentor.root.skill_mentor_root.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AdminDTO linkClerkUser(String email, String clerkUserId) {
        log.info("Linking Clerk user {} with email {}", clerkUserId, email);

        AdminEntity student = adminRepository.findByEmail(email)
                .orElseThrow(() -> new AdminException("Admin not found with email: " + email));

        student.setClerkUserId(clerkUserId);
        AdminEntity saved = adminRepository.save(student);

        return AdminEntityDTOMapper.map(saved);
    }

    @Override
    public AdminDTO findByClerkUserId(String clerkUserId) {
        log.info("Finding admin by Clerk user ID: {}", clerkUserId);

        return adminRepository.findByClerkUserId(clerkUserId)
                .map(AdminEntityDTOMapper::map)
                .orElseThrow(() -> new AdminException("Admin not found with Clerk ID: " + clerkUserId));
    }

    @Override
    public AdminDTO findByEmail(String email) {
        log.info("Finding admin by email: {}", email);

        return adminRepository.findByEmail(email)
                .map(AdminEntityDTOMapper::map)
                .orElseThrow(() -> new AdminException("Admin not found with email: " + email));
    }

    // Add this method to your AdminServiceImpl class

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        log.info("Creating new admin...");

        if (adminDTO == null) {
            log.error("Failed to create admin: input DTO is null.");
            throw new IllegalArgumentException("Admin data must not be null.");
        }

        // Check if admin with this email already exists
        try {
            adminRepository.findByEmail(adminDTO.getEmail());
            log.error("Admin with email {} already exists", adminDTO.getEmail());
            throw new AdminException("Admin with email " + adminDTO.getEmail() + " already exists");
        } catch (AdminException e) {
            // Admin doesn't exist, continue with creation
        }

        // HASH PASSWORD BEFORE SAVING (if you're using password encoder)
        if (StringUtils.hasText(adminDTO.getPassword())) {
            String hashedPassword = passwordEncoder.encode(adminDTO.getPassword());
            adminDTO.setPassword(hashedPassword);
            log.debug("Password hashed for admin: {}", adminDTO.getEmail());
        }

        // Set default role if not provided
        if (adminDTO.getRole() == null || adminDTO.getRole().isEmpty()) {
            adminDTO.setRole("admin");
        }

        log.debug("AdminDTO received: {}", adminDTO.getEmail()); // Don't log full DTO with password
        final AdminEntity adminEntity = AdminEntityDTOMapper.map(adminDTO);
        final AdminEntity savedEntity = adminRepository.save(adminEntity);
        log.info("Admin created with ID: {}", savedEntity.getAdminId());

        // Don't return the hashed password in response
        AdminDTO responseDTO = AdminEntityDTOMapper.map(savedEntity);
        responseDTO.setPassword(null); // Hide password in response
        return responseDTO;
    }
}
