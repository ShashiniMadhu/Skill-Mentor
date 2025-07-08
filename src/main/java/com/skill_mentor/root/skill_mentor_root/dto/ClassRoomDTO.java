package com.skill_mentor.root.skill_mentor_root.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoomDTO {

    @NotNull
    @JsonProperty("class_room_id")
    private Integer classRoomId;

    @NotBlank
    @JsonProperty("title")
    private String title;
    //private Double sessionFee;

    @NotNull
    @JsonProperty("enrolled_student_count")
    private Integer enrolledStudentCount;

    //one-one between mentor and classroom
    @NotNull
    @JsonProperty("mentor") //Rename as your prefer
    private MentorDTO mentorDTO;

    //one-many between classRoom and session
    @NotNull
    @JsonProperty("sessions")
    private List<SessionDTO> sessionDTOs;
}
