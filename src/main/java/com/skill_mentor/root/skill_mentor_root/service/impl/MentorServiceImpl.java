package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.entity.ClassRoomEntity;
import com.skill_mentor.root.skill_mentor_root.entity.MentorEntity;
import com.skill_mentor.root.skill_mentor_root.entity.SessionEntity;
import com.skill_mentor.root.skill_mentor_root.exception.MentorException;
import com.skill_mentor.root.skill_mentor_root.mapper.MentorEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.repository.ClassRoomRepository;
import com.skill_mentor.root.skill_mentor_root.repository.MentorRepository;
import com.skill_mentor.root.skill_mentor_root.repository.SessionRepository;
import com.skill_mentor.root.skill_mentor_root.service.MentorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MentorServiceImpl implements MentorService {

    @Value("${spring.datasource.url}")
    private String datasource;

    @Autowired
    MentorRepository mentorRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"mentorCache", "allMentorsCache"}, allEntries = true)
    public MentorDTO createMentor(MentorDTO mentorDTO) throws MentorException {
        log.info("Creating mentor: {}", mentorDTO.getFirstName());

        // HASH PASSWORD BEFORE SAVING
        if (StringUtils.hasText(mentorDTO.getPassword())) {
            String hashedPassword = passwordEncoder.encode(mentorDTO.getPassword());
            mentorDTO.setPassword(hashedPassword); // <--- HERE IS THE PROBLEM
            log.debug("Password hashed for mentor: {}", mentorDTO.getFirstName());
        }

        if (mentorDTO.getClassRoomId() != null) {
            ClassRoomEntity classRoom = classRoomRepository.findById(mentorDTO.getClassRoomId())
                    .orElseThrow(() -> {
                        log.error("ClassRoom not found with ID: {} at datasource:{}", mentorDTO.getClassRoomId(),this.datasource);
                        return new MentorException("ClassRoom not found with ID?: at datasource:{}" + mentorDTO.getClassRoomId() + this.datasource);
                    });

            if (classRoom.getMentor() != null) {
                log.error("ClassRoom ID {} already has a mentor assigned", mentorDTO.getClassRoomId());
                throw new RuntimeException("ClassRoom with ID " + mentorDTO.getClassRoomId() + " already has a mentor assigned");
            }
        }

        MentorEntity mentorEntity = MentorEntityDTOMapper.map(mentorDTO);
        MentorEntity savedMentor = mentorRepository.save(mentorEntity);
        log.debug("Mentor saved: {}", savedMentor.getMentorId());

        if (mentorDTO.getClassRoomId() != null) {
            ClassRoomEntity classRoom = classRoomRepository.findById(mentorDTO.getClassRoomId())
                    .orElseThrow(() -> new MentorException("ClassRoom not found with ID?: " + mentorDTO.getClassRoomId()));

            classRoom.setMentor(savedMentor);
            savedMentor.setClassRoom(classRoom);

            classRoomRepository.save(classRoom);
            savedMentor = mentorRepository.save(savedMentor);
            log.info("Mentor assigned to classroom ID {}", mentorDTO.getClassRoomId());
        }

        // Don't return the hashed password in response
        MentorDTO responseDTO = MentorEntityDTOMapper.map(savedMentor);
        responseDTO.setPassword(null); // Hide password in response
        return responseDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "allMentorsCache", key = "'allMentors'")
    public List<MentorDTO> getAllMentors(String subject) {
        log.info("Fetching all mentors with subject filter: {}", subject);
        List<MentorDTO> mentors = mentorRepository.findAll().stream()
                .filter(mentor -> subject == null || Objects.equals(subject, mentor.getSubject()))
                .map(MentorEntityDTOMapper::map)
                .collect(Collectors.toList());
        log.info("Found {} mentors", mentors.size());
        return mentors;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "mentorCache", key = "#id")
    public MentorDTO findMentorById(Integer id) throws MentorException {
        log.info("Fetching mentor by ID: {}", id);
        return mentorRepository.findById(id)
                .map(MentorEntityDTOMapper::map)
                .orElseThrow(() -> {
                    log.error("Mentor not found with ID: {}", id);
                    return new MentorException("Mentor not found with ID: " + id);
                });
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "allMentorsCache", allEntries = true)
    @CachePut(value = "mentorCache", key = "#mentorDTO.mentorId")
    public MentorDTO updateMentorById(MentorDTO mentorDTO) throws MentorException {
        log.info("Updating mentor with ID: {}", mentorDTO.getMentorId());

        final MentorEntity mentorEntity = mentorRepository.findById(mentorDTO.getMentorId())
                .orElseThrow(() -> {
                    log.error("Cannot update. Mentor not found with ID: {}", mentorDTO.getMentorId());
                    return new MentorException("Cannot update. Mentor not found with ID: " + mentorDTO.getMentorId());
                });

        mentorEntity.setFirstName(mentorDTO.getFirstName());
        mentorEntity.setLastName(mentorDTO.getLastName());
        mentorEntity.setAddress(mentorDTO.getAddress());
        mentorEntity.setEmail(mentorDTO.getEmail());
        mentorEntity.setTitle(mentorDTO.getTitle());
        mentorEntity.setProfession(mentorDTO.getProfession());
        mentorEntity.setSubject(mentorDTO.getSubject());
        mentorEntity.setQualification(mentorDTO.getQualification());
        mentorEntity.setPhoneNumber(mentorDTO.getPhoneNumber());
        mentorEntity.setSessionFee(mentorDTO.getSessionFee());

        // HASH PASSWORD ONLY IF IT'S PROVIDED AND NOT EMPTY
        if (StringUtils.hasText(mentorDTO.getPassword())) {
            String hashedPassword = passwordEncoder.encode(mentorDTO.getPassword());
            mentorEntity.setPassword(hashedPassword);
            log.debug("Password updated and hashed for mentor: {}", mentorDTO.getMentorId());
        }

        mentorEntity.setRole(mentorDTO.getRole());

        // ... rest of your classroom logic remains the same ...

        MentorEntity updatedEntity = mentorRepository.save(mentorEntity);
        log.info("Mentor with ID {} updated successfully", updatedEntity.getMentorId());

        // Don't return the hashed password in response
        MentorDTO responseDTO = MentorEntityDTOMapper.map(updatedEntity);
        responseDTO.setPassword(null); // Hide password in response
        return responseDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"mentorCache", "allMentorsCache"}, allEntries = true)
    public MentorDTO deleteMentorById(Integer id) throws MentorException {
        log.info("Deleting mentor with ID: {}", id);
        final MentorEntity mentorEntity = mentorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Mentor not found with ID: {}", id);
                    return new MentorException("Cannot delete. Mentor not found with ID: " + id);
                });

        if (mentorEntity.getClassRoom() != null) {
            ClassRoomEntity classRoom = mentorEntity.getClassRoom();
            classRoom.setMentor(null);
            classRoomRepository.save(classRoom);
            log.debug("Mentor removed from classroom ID {}", classRoom.getClassRoomId());
        }

        if (mentorEntity.getSessions() != null && !mentorEntity.getSessions().isEmpty()) {
            for (SessionEntity session : mentorEntity.getSessions()) {
                session.setMentorEntity(null);
                sessionRepository.save(session);
            }
            log.debug("Mentor removed from all associated sessions");
        }

        mentorRepository.deleteById(id);
        log.info("Mentor with ID {} deleted successfully", id);
        return MentorEntityDTOMapper.map(mentorEntity);
    }
}
