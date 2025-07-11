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

    @JsonProperty("session_id")
    private Integer sessionId;

    @NotNull(message = "Classroom must not be null")
    @JsonProperty("class_room")
    private ClassRoomDTO classRoom;

    @NotNull(message = "Mentor must not be null")
    @JsonProperty("mentor")
    private MentorDTO mentor;

    @NotNull(message = "Student must not be null")
    @JsonProperty("student")
    private StudentDTO student;

    @NotBlank(message = "Topic must not be blank")
    @JsonProperty("topic")
    private String topic;

    @NotNull(message = "Start time must not be null")
    @JsonProperty("start_time")
    private Instant startTime;

    @NotNull(message = "End time must not be null")
    @JsonProperty("end_time")
    private Instant endTime;
}
