package com.skill_mentor.root.skill_mentor_root.service;

import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MentorService {

    MentorDTO createMentor(MentorDTO mentorDTO);

    List<MentorDTO> getAllMentors(String subject);

    MentorDTO findMentorById(Integer Id);

    MentorDTO updateMentorById(MentorDTO mentorDTO);

    MentorDTO deleteMentorById(Integer id);

}
