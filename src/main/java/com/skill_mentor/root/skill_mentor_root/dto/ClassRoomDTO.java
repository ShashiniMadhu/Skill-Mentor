package com.skill_mentor.root.skill_mentor_root.dto;

import java.util.ArrayList;
import java.util.List;

public class ClassRoomDTO {
    private Integer classRoomId;
    private String title;
    private Double sessionFee;
    private Integer enrolledStudentCount;

//    @JsonProperty("mentor") //Rename as your prefer
//    private MentorDTO mentorDTO;

    //one to many example
    private List<MentorDTO> mentorDTOList;

    public ClassRoomDTO() {
    }

    public ClassRoomDTO(Integer classRoomId, String name, Double sessionFee, Integer enrolledStudentCount, List<MentorDTO> mentorDTOList) {
        this.classRoomId = classRoomId;
        this.title = name;
        this.sessionFee = sessionFee;
        this.enrolledStudentCount = enrolledStudentCount;
        //this.mentorDTO = mentorId;
        this.mentorDTOList = mentorDTOList;
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

//    public MentorDTO getMentor() {
//        return mentorDTO;
//    }
//
//    public void setMentor(MentorDTO mentorId) {
//        this.mentorDTO = mentorId;
//    }


    public List<MentorDTO> getMentorDTOList() {
        return mentorDTOList;
    }

    public void setMentorDTOList(List<MentorDTO> mentorDTOList) {
        this.mentorDTOList = mentorDTOList;
    }
}
