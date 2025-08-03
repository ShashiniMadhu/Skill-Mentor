package com.skill_mentor.root.skill_mentor_root.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "admin")
@NoArgsConstructor
@AllArgsConstructor
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Integer adminId;

    @NotBlank(message = "Clerk user ID must not be blank")
    @Column(name = "clerk_user_id", nullable = false)
    private String clerkUserId;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email must not be blank")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role = "admin";
}
