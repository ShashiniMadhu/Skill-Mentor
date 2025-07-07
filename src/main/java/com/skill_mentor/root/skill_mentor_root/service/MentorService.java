package com.skill_mentor.root.skill_mentor_root.service;

import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.entity.MentorEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MentorService {

    MentorEntity createMentor(MentorDTO mentorDTO);

    //one to many
//    List<MentorDTO> getAllMentors(String subject);

    //many to many
    List<MentorDTO> getAllMentors(List<String> firstName,List<String> subject);


    MentorDTO getMentorById(Integer Id);

    MentorDTO updateMentorById(MentorDTO mentorDTO);

    MentorDTO deleteMentorById(Integer id);

}
