package com.skill_mentor.root.skill_mentor_root.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {

    @NotNull
    @JsonProperty("session_id")
    private Integer sessionId;

    @NotNull
    @JsonProperty("class_room")
    private ClassRoomDTO classRoom;

    @NotNull
    @JsonProperty("mentor")
    private MentorDTO mentor;

    @NotNull
    @JsonProperty("student")
    private StudentDTO student;

    @NotBlank
    @JsonProperty("topic")
    private String topic;

    @NotNull
    @JsonProperty("start_time")
    private Instant startTime;

    @NotNull
    @JsonProperty("end_time")
    private Instant endTime;
}
