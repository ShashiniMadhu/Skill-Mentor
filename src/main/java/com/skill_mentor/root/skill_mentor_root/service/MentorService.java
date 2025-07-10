package com.skill_mentor.root.skill_mentor_root.service;

import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.exception.MentorException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MentorService {

    MentorDTO createMentor(MentorDTO mentorDTO) throws MentorException;

    List<MentorDTO> getAllMentors(String subject);

    MentorDTO findMentorById(Integer Id) throws MentorException;

    MentorDTO updateMentorById(MentorDTO mentorDTO) throws MentorException;

    MentorDTO deleteMentorById(Integer id) throws MentorException;

}
