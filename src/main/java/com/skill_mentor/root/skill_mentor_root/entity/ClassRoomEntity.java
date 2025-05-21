package com.skill_mentor.root.skill_mentor_root.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "classroom")
public class ClassRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_room_id")
    private Integer classRoomId;

    @Column(name = "title")
    private String title;

    @Column(name = "session_fee")
    private Double sessionFee;

    @Column(name = "enrolled_student_count")
    private Integer enrolledStudentCount;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "mentor_id")
    private MentorEntity mentor;

    public ClassRoomEntity() {
    }

    public ClassRoomEntity(Integer classRoomId, String name, Double sessionFee, Integer enrolledStudentCount, MentorEntity mentorId) {
        this.classRoomId = classRoomId;
        this.title = name;
        this.sessionFee = sessionFee;
        this.enrolledStudentCount = enrolledStudentCount;
        this.mentor = mentor;
    }

    public Integer getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(Integer classRoomId) {
        this.classRoomId = classRoomId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getSessionFee() {
        return sessionFee;
    }

    public void setSessionFee(Double sessionFee) {
        this.sessionFee = sessionFee;
    }

    public Integer getEnrolledStudentCount() {
        return enrolledStudentCount;
    }

    public void setEnrolledStudentCount(Integer enrolledStudentCount) {
        this.enrolledStudentCount = enrolledStudentCount;
    }

    public MentorEntity getMentor() {
        return mentor;
    }

    public MentorEntity setMentor(MentorEntity mentorId) {
        this.mentor = mentorId;
        return mentorId;
    }
}
