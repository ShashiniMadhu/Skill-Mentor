package com.skill_mentor.root.skill_mentor_root.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorDTO {

    @NotNull
    @JsonProperty("mentorId")
    private Integer mentorId;

    @NotBlank
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank
    @JsonProperty("address")
    private String address;

    @Email
    @NotBlank
    @JsonProperty("email")
    private String email;

    @NotBlank
    @JsonProperty("title")
    private String title;

    @NotBlank
    @JsonProperty("profession")
    private String profession;

    @NotBlank
    @JsonProperty("subject")
    private String subject;

    @NotBlank
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank
    @JsonProperty("qualification")
    private String qualification;

    @NotNull
    @Min(0)
    @JsonProperty("session_fee")
    private Double sessionFee;

    @NotNull
    @JsonProperty("class_room_id")
    private Integer classRoomId;

    // List of sessions conducted by this mentor
    @NotNull
    @JsonProperty("sessions")
    private List<SessionDTO> sessionDTOs;

}