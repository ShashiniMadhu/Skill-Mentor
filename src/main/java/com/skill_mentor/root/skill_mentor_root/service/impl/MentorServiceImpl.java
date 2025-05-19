package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.entity.MentorEntity;
import com.skill_mentor.root.skill_mentor_root.entity.StudentEntity;
import com.skill_mentor.root.skill_mentor_root.mapper.MentorEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.mapper.StudentEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.repository.MentorRepository;
import com.skill_mentor.root.skill_mentor_root.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    MentorRepository mentorRepository;

    @Override
    public MentorDTO createMentor(MentorDTO mentorDTO) {
        mentorDTO.setMentorId(null);
        final MentorEntity mentorEntity = MentorEntityDTOMapper.map(mentorDTO);
        final MentorEntity savedEntity = mentorRepository.save(mentorEntity);
        return MentorEntityDTOMapper.map(savedEntity);
    }

    @Override
    public List<MentorDTO> getAllMentors(String subject) {
        final List<MentorEntity> mentorEntities = mentorRepository.findAll();
        return mentorEntities.stream()
                .filter(mentor->subject == null || Objects.equals(subject,mentor.getSubject()))
                .map(MentorEntityDTOMapper::map)
                .toList();
    }

    @Override
    public MentorDTO getMentorById(Integer Id) {
        Optional<MentorEntity> mentorEntity = mentorRepository.findById(Id);
        return mentorEntity.map(MentorEntityDTOMapper::map).orElse(null);
    }

    @Override
    public MentorDTO updateMentorById(MentorDTO mentorDTO) {
        MentorEntity mentorEntity = mentorRepository.findById(mentorDTO.getMentorId()).orElse(null);
        if(mentorEntity != null){
            mentorEntity.setFirstName(mentorDTO.getFirstName());
            mentorEntity.setLastName(mentorDTO.getLastName());
            mentorEntity.setAddress(mentorDTO.getAddress());
            mentorEntity.setEmail(mentorDTO.getEmail());
            mentorEntity.setTitle(mentorDTO.getTitle());
            mentorEntity.setProfession(mentorDTO.getProfession());
            mentorEntity.setSubject(mentorDTO.getSubject());
            mentorEntity.setQualification(mentorDTO.getQualification());
            MentorEntity updateEntity = mentorRepository.save(mentorEntity);
            return MentorEntityDTOMapper.map(updateEntity);
        }
        return null;
    }

    @Override
    public MentorDTO deleteMentorById(Integer id) {
        MentorEntity mentorEntity = mentorRepository.findById(id).orElse(null);
        mentorRepository.deleteById(id);
        return MentorEntityDTOMapper.map(mentorEntity);
    }
}
