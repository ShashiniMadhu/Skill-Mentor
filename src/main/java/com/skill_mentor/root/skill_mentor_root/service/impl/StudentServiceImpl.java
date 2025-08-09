package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;
import com.skill_mentor.root.skill_mentor_root.entity.StudentEntity;
import com.skill_mentor.root.skill_mentor_root.exception.StudentException;
import com.skill_mentor.root.skill_mentor_root.mapper.StudentEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.repository.StudentRepository;
import com.skill_mentor.root.skill_mentor_root.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Value("${spring.datasource.url}")
    private String datasource;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StudentDTO createStudent(StudentDTO studentDTO) {
        log.info("Creating new student...");
        log.debug("Received StudentDTO: {}", studentDTO);

        if (studentDTO == null) {
            log.error("Failed to create student: input DTO is null.");
            throw new IllegalArgumentException("Student data must not be null.");
        }

        try {
            if (studentDTO.getEmail() == null || studentDTO.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email is required");
            }

            if (studentDTO.getFirstName() == null || studentDTO.getFirstName().trim().isEmpty()) {
                throw new IllegalArgumentException("First name is required");
            }

            if (studentDTO.getLastName() == null || studentDTO.getLastName().trim().isEmpty()) {
                throw new IllegalArgumentException("Last name is required");
            }

            // Check if student already exists with this email
            if (studentRepository.findByEmail(studentDTO.getEmail()).isPresent()) {
                log.error("Student already exists with email: {}", studentDTO.getEmail());
                throw new StudentException("Student already exists with email: " + studentDTO.getEmail());
            }

            // Set default values for optional fields
            if (studentDTO.getPhoneNumber() == null || studentDTO.getPhoneNumber().trim().isEmpty()) {
                studentDTO.setPhoneNumber("Not provided");
            }

            if (studentDTO.getAddress() == null || studentDTO.getAddress().trim().isEmpty()) {
                studentDTO.setAddress("Not provided");
            }

            if (studentDTO.getAge() == null) {
                studentDTO.setAge(18);
            }

            if (studentDTO.getRole() == null || studentDTO.getRole().trim().isEmpty()) {
                studentDTO.setRole("student");
            }

            // Handle password
            String passwordToHash = studentDTO.getPassword();
            if (passwordToHash == null || passwordToHash.trim().isEmpty()) {
                passwordToHash = "ClerkUser123!"; // Strong default password
            }

            String hashedPassword = passwordEncoder.encode(passwordToHash);
            studentDTO.setPassword(hashedPassword);

            log.info("Creating student with email: {} and clerk_user_id: {}",
                    studentDTO.getEmail(), studentDTO.getClerkUserId());

            // Map and save
            final StudentEntity studentEntity = StudentEntityDTOMapper.map(studentDTO);

            log.debug("StudentEntity before save: clerkUserId={}, email={}, firstName={}",
                    studentEntity.getClerkUserId(), studentEntity.getEmail(), studentEntity.getFirstName());

            final StudentEntity savedEntity = studentRepository.save(studentEntity);
            log.info("Student created successfully with ID: {} at datasource: {}",
                    savedEntity.getStudentId(), this.datasource);

            // Return response without password
            StudentDTO responseDTO = StudentEntityDTOMapper.map(savedEntity);
            responseDTO.setPassword(null);
            return responseDTO;

        } catch (Exception e) {
            log.error("Error creating student with email {}: {}", studentDTO.getEmail(), e.getMessage(), e);
            if (e instanceof StudentException) {
                throw e;
            }
            throw new StudentException("Failed to create student: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
//    @Cacheable(value = "allStudentsCache", key = "'allStudents'")
    public List<StudentDTO> getAllStudents(final List<String> addresses,Integer age) {
        log.info("Fetching all students with filters: addresses={}, ages={}", addresses, age);
        final List<StudentEntity> studentEntities = studentRepository.findAll();
        //return studentEntities.stream().map(StudentEntityDTOMapper::map).toList(); // without any filter parameter
        log.info("Found {} students after filtering from datasource:{}", studentEntities.size(),this.datasource);
        return studentEntities.stream()
                .filter(student->addresses == null || addresses.contains(student.getAddress()))//keep "contains" because of list of addresses
                .filter(student->age == null || Objects.equals(age,student.getAge()))
                .map(StudentEntityDTOMapper::map)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
//    @Cacheable(value = "studentCache", key = "#id")
    public StudentDTO findStudentById(Integer id) {
        log.info("Fetching student by ID: {}", id);
        return studentRepository.findById(id)
                .map(student -> {
                    log.debug("Student found: {}", student);
                    return StudentEntityDTOMapper.map(student);
                })
                .orElseThrow(() -> {
                    log.error("Student not found with ID: {}", id);
                    return new StudentException("Student not found with ID: " + id);
                });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
//    @CacheEvict(value = "allStudentsCache", allEntries = true)
//    @CachePut(value = "studentCache", key = "#studentDTO.studentId")
    public StudentDTO updateStudentById(StudentDTO studentDTO) {
        log.info("Updating student...");
        if (studentDTO == null || studentDTO.getStudentId() == null) {
            log.error("Failed to update student: DTO or studentId is null.");
            throw new IllegalArgumentException("Student ID must not be null for update.");
        }
        log.debug("Updating student with ID: {}", studentDTO.getStudentId());
        final StudentEntity studentEntity = studentRepository.findById(studentDTO.getStudentId())
                .orElseThrow(() -> {
                    log.error("Cannot update. Student not found with ID: {}", studentDTO.getStudentId());
                    return new StudentException("Cannot update. Student not found with ID: " + studentDTO.getStudentId());
                });

        studentEntity.setFirstName(studentDTO.getFirstName());
        studentEntity.setLastName(studentDTO.getLastName());
        studentEntity.setEmail(studentDTO.getEmail());
        studentEntity.setPhoneNumber(studentDTO.getPhoneNumber());
        studentEntity.setAddress(studentDTO.getAddress());
        studentEntity.setAge(studentDTO.getAge());
        studentEntity.setClerkUserId(studentDTO.getClerkUserId());

        // HASH PASSWORD ONLY IF IT'S PROVIDED AND NOT EMPTY
        if (StringUtils.hasText(studentDTO.getPassword())) {
            String hashedPassword = passwordEncoder.encode(studentDTO.getPassword());
            studentEntity.setPassword(hashedPassword);
            log.debug("Password updated and hashed for student: {}", studentDTO.getStudentId());
        }

        studentEntity.setRole(studentDTO.getRole());

        StudentEntity updated = studentRepository.save(studentEntity);
        log.info("Student updated with ID: {}", updated.getStudentId());

        // Don't return the hashed password in response
        StudentDTO responseDTO = StudentEntityDTOMapper.map(updated);
        responseDTO.setPassword(null); // Hide password in response
        return responseDTO;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
//    @CacheEvict(value = {"studentCache", "allStudentsCache"}, allEntries = true)
    public StudentDTO deleteStudentById(final Integer id) {
        log.info("Deleting student with ID: {}", id);
        final StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cannot delete. Student not found with ID: {} from data-source:{}", id,this.datasource);
                    return new StudentException("Cannot delete. Student not found with ID: " + id);
                });
        studentRepository.delete(studentEntity);
        log.info("Student with ID {} deleted successfully", id);
        return StudentEntityDTOMapper.map(studentEntity);
    }

    // Add these methods to StudentServiceImpl.java

    @Override
    public StudentDTO linkClerkUser(String email, String clerkUserId) {
        log.info("Linking Clerk user {} with email {}", clerkUserId, email);

        StudentEntity student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new StudentException("Student not found with email: " + email));

        student.setClerkUserId(clerkUserId);
        StudentEntity saved = studentRepository.save(student);

        return StudentEntityDTOMapper.map(saved);
    }

    @Override
    public StudentDTO findByClerkUserId(String clerkUserId) {
        log.info("Finding student by Clerk user ID: {}", clerkUserId);

        return studentRepository.findByClerkUserId(clerkUserId)
                .map(StudentEntityDTOMapper::map)
                .orElseThrow(() -> new StudentException("Student not found with Clerk ID: " + clerkUserId));
    }

    @Override
    public StudentDTO findByEmail(String email) {
        log.info("Finding student by email: {}", email);

        return studentRepository.findByEmail(email)
                .map(StudentEntityDTOMapper::map)
                .orElseThrow(() -> new StudentException("Student not found with email: " + email));
    }
}