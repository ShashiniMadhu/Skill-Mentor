package com.skill_mentor.root.skill_mentor_root.repository;

import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.dto.StudentDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MentorRepository {
    private final List<MentorDTO> mentors = new ArrayList<>();

    public MentorDTO createMentor(MentorDTO mentorDTO) {
        mentors.add(mentorDTO);
        return mentorDTO;
    }

    public List<MentorDTO> getAllMentors(String subject) {
        return mentors.stream().filter(men->subject == null || men.getSubject().equals(subject)).toList();
    }

    public MentorDTO getMentorById(Integer Id){
        Optional<MentorDTO> mentorDTOOptional = mentors.stream().filter(men->Objects.equals(men.getMentorId(),Id)).findFirst();
        return mentorDTOOptional.orElse(null);
    }

    public MentorDTO updateMentorById(MentorDTO mentorDTO){
        Optional<MentorDTO> mentorDTO1 = mentors.stream().filter(men-> men.getMentorId().equals(mentorDTO.getMentorId())).findFirst();
        MentorDTO updatedMentor = mentorDTO1.orElse(null);
        if (updatedMentor != null) {
            updatedMentor.setLastName(mentorDTO.getLastName());
            // Replace this line:
            // students.add(0, studentDTO);

            // With this line:
            mentors.replaceAll(m -> m.getMentorId().equals(mentorDTO.getMentorId()) ? updatedMentor : m);
        }
        return updatedMentor;
    }

    public MentorDTO deleteMentorById(Integer id){
        MentorDTO mentorDTO = mentors.get(id);
        mentors.remove(mentorDTO);
        return mentorDTO;
    }
}
