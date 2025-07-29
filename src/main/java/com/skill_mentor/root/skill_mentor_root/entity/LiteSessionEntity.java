package com.skill_mentor.root.skill_mentor_root.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name="session")
@NoArgsConstructor
@AllArgsConstructor
public class LiteSessionEntity {

    @Id
    @Column(name="session_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sessionId;

    @NotNull(message = "Student ID must not be null")
    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @NotNull(message = "Classroom ID must not be null")
    @Column(name = "class_room_id", nullable = false)
    private Integer classRoomId;

    @NotNull(message = "Mentor ID must not be null")
    @Column(name = "mentor_id", nullable = false)
    private Integer mentorId;

    @NotBlank(message = "Topic must not be blank")
    @Column(name = "topic", nullable = false)
    private String topic;

    @NotNull(message = "Date must not be null")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull(message = "Start time must not be null")
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

}
