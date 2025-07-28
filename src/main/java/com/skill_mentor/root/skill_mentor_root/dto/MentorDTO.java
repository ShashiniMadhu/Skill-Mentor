package com.skill_mentor.root.skill_mentor_root.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MentorDTO {

    @JsonProperty("mentorId")
    private Integer mentorId;

    @NotBlank(message = "First name must not be blank")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank(message = "Address must not be blank")
    @JsonProperty("address")
    private String address;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email must not be blank")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Title must not be blank")
    @JsonProperty("title")
    private String title;

    @NotBlank(message = "Profession must not be blank")
    @JsonProperty("profession")
    private String profession;

    @NotBlank(message = "Subject must not be blank")
    @JsonProperty("subject")
    private String subject;

    @NotBlank(message = "Phone number must not be blank")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Qualification must not be blank")
    @JsonProperty("qualification")
    private String qualification;

    @NotNull(message = "Session fee must not be null")
    @Min(value = 0, message = "Session fee must be zero or positive")
    @JsonProperty("session_fee")
    private Double sessionFee;

//    @NotNull(message = "Classroom ID must not be null")
    @JsonProperty("class_room_id")
    private Integer classRoomId;

    // List of sessions conducted by this mentor
//    @NotNull
    @JsonProperty("sessions")
    private List<SessionDTO> sessionDTOs;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @JsonProperty("password")
    private String password;

    @JsonProperty("role")
    private String role;
}