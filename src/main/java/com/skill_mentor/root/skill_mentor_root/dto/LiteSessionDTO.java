package com.skill_mentor.root.skill_mentor_root.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LiteSessionDTO {

    @JsonProperty("session_id")
    private Integer sessionId;

    @NotNull(message = "Student ID must not be null")
    @JsonProperty("student_id")
    private Integer studentId;

    @NotNull(message = "Classroom ID must not be null")
    @JsonProperty("class_room_id")
    private Integer classRoomId;

    @NotNull(message = "Mentor ID must not be null")
    @JsonProperty("mentor_id")
    private Integer mentorId;

    @NotBlank(message = "Topic must not be blank")
    @JsonProperty("topic")
    private String topic;

    @NotNull(message = "Date must not be null")
    @JsonProperty("date")
    private LocalDate date;

    @NotNull(message = "Start time must not be null")
    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("status")
    private String status;

    @JsonProperty("session_link")
    private String sessionLink;
}
