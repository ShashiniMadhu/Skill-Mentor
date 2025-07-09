package com.skill_mentor.root.skill_mentor_root.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)//exclude null fields from the JSON output
public class ClassRoomDTO {

    @JsonProperty("class_room_id")
    private Integer classRoomId;

    @NotBlank(message = "Title must not be blank")
    @JsonProperty("title")
    private String title;
    //private Double sessionFee;

    @NotNull(message = "Enrolled student count must not be null")
    @JsonProperty("enrolled_student_count")
    private Integer enrolledStudentCount;

    //one-one between mentor and classroom
    @JsonProperty("mentor") //Rename as your prefer
    private MentorDTO mentorDTO;

    //one-many between classRoom and session
    @JsonProperty("sessions")
    private List<SessionDTO> sessionDTOs;
}
