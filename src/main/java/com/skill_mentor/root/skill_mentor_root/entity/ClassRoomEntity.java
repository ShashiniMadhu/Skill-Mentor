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

    @Column(name = "session_fee")
    private Double sessionFee;

    @Column(name = "enrolled_student_count")
    private Integer enrolledStudentCount;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn (referencedColumnName = "mentor_id")
//    private MentorEntity mentor;

    //one to one mapping example
    @OneToMany(mappedBy = "classRoomEntity")
    private List<MentorEntity> mentorEntityList;

    public ClassRoomEntity() {
    }

    public ClassRoomEntity(Integer classRoomId, String name, Double sessionFee, Integer enrolledStudentCount, List<MentorEntity> mentorEntityList) {
        this.classRoomId = classRoomId;
        this.title = name;
        this.sessionFee = sessionFee;
        this.enrolledStudentCount = enrolledStudentCount;
        //this.mentor = mentor;
        this.mentorEntityList = mentorEntityList;
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

//    public MentorEntity getMentor() {
//        return mentor;
//    }
//
//    public void setMentor(MentorEntity mentor) {
//        this.mentor = mentor;
//    }


    public List<MentorEntity> getMentorEntityList() {
        return mentorEntityList;
    }

    public void setMentorEntityList(List<MentorEntity> mentorEntityList) {
        this.mentorEntityList = mentorEntityList;
    }
}
