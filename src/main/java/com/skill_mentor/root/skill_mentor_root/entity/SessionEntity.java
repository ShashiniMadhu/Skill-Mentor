package com.skill_mentor.root.skill_mentor_root.entity;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "session")
@NoArgsConstructor
@AllArgsConstructor
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer sessionId;

    @NotNull(message = "Classroom must not be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_room_id", referencedColumnName = "class_room_id", nullable = false)
    private ClassRoomEntity classRoomEntity;

    @NotNull(message = "Mentor must not be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mentor_id", referencedColumnName = "mentor_id", nullable = false)
    private MentorEntity mentorEntity;

    @NotNull(message = "Student must not be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id",nullable = false)
    private StudentEntity studentEntity;

    @NotBlank(message = "Topic must not be blank")
    @Column(name = "topic", nullable = false)
    private String topic;

    @NotNull(message = "Date must not be null")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull(message = "Start time must not be null")
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "status")
    private String status;

    @Column(name = "session_link")
    private String sessionLink;

    @Column(name = "slip_link")
    private String slipLink;
}
