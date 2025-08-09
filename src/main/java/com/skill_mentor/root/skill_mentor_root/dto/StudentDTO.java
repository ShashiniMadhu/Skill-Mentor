package com.skill_mentor.root.skill_mentor_root.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO {

    @JsonProperty("student_id")
    private Integer studentId;

    @NotBlank(message = "Clerk user ID must not be blank")
    @JsonProperty("clerk_user_id")
    private String clerkUserId;

    @NotBlank(message = "First name must not be blank")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank(message = "Email must not be blank")
    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("address")
    private String address;

    @Min(value = 18, message = "Age must be at least 18")
    @JsonProperty("age")
    private Integer age;

    @Size(min = 8, message = "Password must be at least 8 characters")
    @JsonProperty("password")
    private String password;

    @JsonProperty("role")
    private String role;
}