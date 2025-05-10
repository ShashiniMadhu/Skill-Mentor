package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.repository.MentorRepository;
import com.skill_mentor.root.skill_mentor_root.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    MentorRepository mentorRepository;

    @Override
    public MentorDTO createMentor(MentorDTO mentorDTO) {
        MentorDTO mentorDTO1 = mentorRepository.createMentor(mentorDTO);
        return mentorDTO1;
    }

    @Override
    public List<MentorDTO> getAllMentors(String subject) {
        return mentorRepository.getAllMentors(subject);
    }

    @Override
    public MentorDTO getMentorById(Integer Id) {
        return mentorRepository.getMentorById(Id);
    }

    @Override
    public MentorDTO updateMentorById(MentorDTO mentorDTO) {
        return mentorRepository.updateMentorById(mentorDTO);
    }

    @Override
    public MentorDTO deleteMentorById(Integer id) {
        return mentorRepository.deleteMentorById(id);
    }

}
