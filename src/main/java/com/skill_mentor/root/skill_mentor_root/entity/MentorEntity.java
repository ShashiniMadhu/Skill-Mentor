package com.skill_mentor.root.skill_mentor_root.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name="mentor")
@NoArgsConstructor
@AllArgsConstructor
public class MentorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentor_id")
    private Integer mentorId;

    @NotBlank(message = "First name must not be blank")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Address must not be blank")
    @Column(name = "address", nullable = false)
    private String address;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must not be blank")
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "Title must not be blank")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "Profession must not be blank")
    @Column(name = "profession", nullable = false)
    private String profession;

    @NotBlank(message = "Subject must not be blank")
    @Column(name = "subject", nullable = false)
    private String subject;

    @NotBlank(message = "Phone number must not be blank")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotBlank(message = "Qualification must not be blank")
    @Column(name = "qualification", nullable = false)
    private String qualification;

    @NotNull(message = "Session fee must not be null")
    @Min(value = 0, message = "Session fee must be non-negative")
    @Column(name = "session_fee", nullable = false)
    private Double sessionFee;

    // One-to-Many relationship with ClassRoom (mapped by mentor in ClassRoomEntity)
    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ClassRoomEntity> classRooms;

    // One-to-Many relationship with Sessions
    @OneToMany(mappedBy = "mentorEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SessionEntity> sessions;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "bio")
    private String bio;

}