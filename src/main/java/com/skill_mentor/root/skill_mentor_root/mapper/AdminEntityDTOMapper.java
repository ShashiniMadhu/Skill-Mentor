package com.skill_mentor.root.skill_mentor_root.mapper;

import com.skill_mentor.root.skill_mentor_root.dto.AdminDTO;
import com.skill_mentor.root.skill_mentor_root.entity.AdminEntity;

public class AdminEntityDTOMapper {

    public static AdminEntity map(AdminDTO adminDTO) {
        if (adminDTO == null) {
            return null;
        }

        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAdminId(adminDTO.getAdminId());
        adminEntity.setClerkUserId(adminDTO.getClerkUserId());
        adminEntity.setEmail(adminDTO.getEmail());
        adminEntity.setPassword(adminDTO.getPassword());
        adminEntity.setRole(adminDTO.getRole());

        return adminEntity;
    }

    public static AdminDTO map(AdminEntity adminEntity) {
        if (adminEntity == null) {
            return null;
        }

        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminId(adminEntity.getAdminId());
        adminDTO.setClerkUserId(adminEntity.getClerkUserId());
        adminDTO.setEmail(adminEntity.getEmail());
        adminDTO.setPassword(adminEntity.getPassword());
        adminDTO.setRole(adminEntity.getRole());

        return adminDTO;
    }
}
