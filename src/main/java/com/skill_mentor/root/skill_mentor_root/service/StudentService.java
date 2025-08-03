package com.skill_mentor.root.skill_mentor_root.service;

import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing students.
 */
@Service
public interface StudentService {

    /**
     * Creates a new student.
     *
     * @param studentDTO the data transfer object containing student details
     * @return the created StudentDTO object
     */
    StudentDTO createStudent(StudentDTO studentDTO);

    /**
     * Retrieves all students, optionally filtered by age, firstName, address
     *
     * @return a list of StudentDTO objects representing the students
     */
    List<StudentDTO> getAllStudents(List<String> addresses,Integer age);

    /**
     * Retrieves a student by their ID.
     *
     * @param id the ID of the student to retrieve
     * @return a StudentDTO object representing the student
     */
    StudentDTO findStudentById(Integer id);

    /**
     * Updates an existing student's details.
     *
     * @param studentDTO the data transfer object containing updated student details
     * @return a StudentDTO object representing the updated student
     */
    StudentDTO updateStudentById(StudentDTO studentDTO);

    /**
     * Deletes a student by their ID.
     *
     * @param id the ID of the student to delete
     * @return a StudentDTO object representing the deleted student
     */
    StudentDTO deleteStudentById(Integer id);

    /**
     * Links a student to a clerk user by email.
     *
     * @param email the email of the student
     * @param clerkUserId the ID of the clerk user to link
     * @return a StudentDTO object representing the updated student
     */
    StudentDTO linkClerkUser(String email, String clerkUserId);

    /**
     * Finds a student by their clerk user ID.
     *
     * @param clerkUserId the clerk user ID to search for
     * @return a StudentDTO object representing the student, or null if not found
     */
    StudentDTO findByClerkUserId(String clerkUserId);

    /**
     * Finds a student by their email.
     *
     * @param email the email to search for
     * @return a StudentDTO object representing the student, or null if not found
     */
    StudentDTO findByEmail(String email);
}