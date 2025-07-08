package com.skill_mentor.root.skill_mentor_root.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "title")
    private String title;

    @Column(name = "profession")
    private String profession;

    @Column(name = "subject")
    private String subject;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "session_fee")
    private Double sessionFee;

    // One-to-One relationship with ClassRoom (mapped by mentor in ClassRoomEntity)
    @OneToOne(mappedBy = "mentor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private ClassRoomEntity classRoom;

    // One-to-Many relationship with Sessions
    @OneToMany(mappedBy = "mentorEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SessionEntity> sessions;
}