package com.skill_mentor.root.skill_mentor_root.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminDTO {

    @JsonProperty("admin_id")
    private Integer adminId;

    @NotBlank(message = "Clerk user ID must not be blank")
    @JsonProperty("clerk_user_id")
    private String clerkUserId;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email must not be blank")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @JsonProperty("password")
    private String password;

    @JsonProperty("role")
    private String role = "admin";  // Default value
}
