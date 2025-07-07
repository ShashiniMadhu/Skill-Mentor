package com.skill_mentor.root.skill_mentor_root.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ClassRoomDTO {
    private Integer classRoomId;
    private String title;
//    private Double sessionFee;
    private Integer enrolledStudentCount;
    //one-one between mentor and classroom
    @JsonProperty("mentor") //Rename as your prefer
    private MentorDTO mentorDTO;
    //one-many between classRoom and session
    @JsonProperty("sessions")
    private List<SessionDTO> sessionDTOs;


    public ClassRoomDTO() {
    }

    public ClassRoomDTO(Integer classRoomId, String name, Integer enrolledStudentCount, MentorDTO mentorId, List<SessionDTO> sessionDTOs) {
        this.classRoomId = classRoomId;
        this.title = name;
//        this.sessionFee = sessionFee;
        this.enrolledStudentCount = enrolledStudentCount;
        this.mentorDTO = mentorId;
        this.sessionDTOs = sessionDTOs;
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

    public MentorDTO getMentor() {
        return mentorDTO;
    }

    public void setMentor(MentorDTO mentorId) {
        this.mentorDTO = mentorId;
    }

    public List<SessionDTO> getSessionDTOs() {
        return sessionDTOs;
    }

    public void setSessionDTOs(List<SessionDTO> sessionDTOs) {
        this.sessionDTOs = sessionDTOs;
    }
}
