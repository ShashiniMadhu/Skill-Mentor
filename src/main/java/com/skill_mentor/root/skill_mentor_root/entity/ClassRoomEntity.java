package com.skill_mentor.root.skill_mentor_root.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "classroom")
public class ClassRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_room_id")
    private Integer classRoomId;

    @Column(name = "title")
    private String title;

//    @Column(name = "session_fee")
//    private Double sessionFee;

    @Column(name = "enrolled_student_count")
    private Integer enrolledStudentCount;

    // One-to-One relationship with Mentor
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mentor_id", referencedColumnName = "mentor_id")
    private MentorEntity mentor;

    // One-to-Many relationship with Sessions
    @OneToMany(mappedBy = "classRoomEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SessionEntity> sessions;

    public ClassRoomEntity() {
    }

    public ClassRoomEntity(Integer classRoomId, String title, Double sessionFee, Integer enrolledStudentCount, MentorEntity mentor, List<SessionEntity> sessions) {
        this.classRoomId = classRoomId;
        this.title = title;
//        this.sessionFee = sessionFee;
        this.enrolledStudentCount = enrolledStudentCount;
        this.mentor = mentor;
        this.sessions = sessions;
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

//    public Double getSessionFee() {
//        return sessionFee;
//    }
//
//    public void setSessionFee(Double sessionFee) {
//        this.sessionFee = sessionFee;
//    }

    public Integer getEnrolledStudentCount() {
        return enrolledStudentCount;
    }

    public void setEnrolledStudentCount(Integer enrolledStudentCount) {
        this.enrolledStudentCount = enrolledStudentCount;
     }

    public MentorEntity getMentor() {
        return mentor;
    }

    public void setMentor(MentorEntity mentor) {
        this.mentor = mentor;
    }

    public List<SessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionEntity> sessions) {
        this.sessions = sessions;
    }
}