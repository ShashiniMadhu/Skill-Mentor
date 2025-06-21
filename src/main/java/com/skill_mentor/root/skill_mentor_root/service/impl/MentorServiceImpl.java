package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.ClassRoomDTO;
import com.skill_mentor.root.skill_mentor_root.dto.MentorDTO;
import com.skill_mentor.root.skill_mentor_root.entity.ClassRoomEntity;
import com.skill_mentor.root.skill_mentor_root.entity.MentorEntity;
import com.skill_mentor.root.skill_mentor_root.mapper.ClassRoomEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.mapper.MentorEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.repository.ClassRoomRepository;
import com.skill_mentor.root.skill_mentor_root.repository.MentorRepository;
import com.skill_mentor.root.skill_mentor_root.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    MentorRepository mentorRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

//    @Override
//    public MentorDTO createMentor(MentorDTO mentorDTO) {
//        // Step 1: Create and save the mentor first
//        MentorEntity mentorEntity = MentorEntityDTOMapper.map(mentorDTO);
//        MentorEntity savedMentor = mentorRepository.save(mentorEntity);
//        // Step 2: If classRoomId is provided, update the classroom with the saved mentor
//        if (!Objects.isNull(mentorDTO.getClassRoomId())) {
//            Optional<ClassRoomEntity> optionalClassRoomEntity = classRoomRepository.findById(mentorDTO.getClassRoomId());
//            if (optionalClassRoomEntity.isPresent()) {
//                ClassRoomEntity classRoomEntity = optionalClassRoomEntity.get();
//                classRoomEntity.setMentor(savedMentor); // Use the SAVED mentor entity
//                classRoomRepository.save(classRoomEntity);
//            }
//        }
//        return MentorEntityDTOMapper.map(savedMentor);
//    }

//    //one to many example of Mentor-Classroom
//    @Override
//    public MentorDTO createMentor(MentorDTO mentorDTO) {
//        // Map DTO to Entity
//        MentorEntity mentorEntity = MentorEntityDTOMapper.map(mentorDTO);
//        // Set classroom relationship if classRoomId is provided
//        if (mentorDTO.getClassRoomId() != null) {
//            Optional<ClassRoomEntity> optionalClassRoomEntity = classRoomRepository.findById(mentorDTO.getClassRoomId());
//            if (optionalClassRoomEntity.isPresent()) {
//                ClassRoomEntity classRoomEntity = optionalClassRoomEntity.get();
//                mentorEntity.setClassRoomEntity(classRoomEntity);
//            } else {
//                throw new RuntimeException("ClassRoom with ID " + mentorDTO.getClassRoomId() + " not found");
//            }
//        }
//        // Save the mentor entity
//        MentorEntity savedEntity = mentorRepository.save(mentorEntity);
//        // Return mapped DTO
//        return MentorEntityDTOMapper.map(savedEntity);
//    }

    //many to many example
    @Override
    public MentorEntity createMentor(MentorDTO mentorDTO){
        MentorEntity mentorEntity = MentorEntityDTOMapper.map(mentorDTO);
        if(mentorDTO.getClassRoomIds().size()>0){
            List<ClassRoomEntity> classRoomEntities = classRoomRepository.findAllById(mentorDTO.getClassRoomIds());
            if(classRoomEntities.size()>0){
                mentorEntity.setClassRoomEntities(classRoomEntities);
            }
        }
        MentorEntity savedEntity = mentorRepository.save(mentorEntity);
        MentorDTO savedMentorDTO = MentorEntityDTOMapper.map(savedEntity);
        if(savedEntity.getClassRoomEntities().size()>0){
            List<ClassRoomDTO> savedDTOs = savedEntity.getClassRoomEntities().stream().map(ClassRoomEntityDTOMapper::map).toList();
            savedMentorDTO.setClassRoomDTOList(savedDTOs);
            for(ClassRoomDTO classRoomDTO:savedMentorDTO.getClassRoomDTOList()){
                ClassRoomEntity classRoomEntity = classRoomRepository.findById(classRoomDTO.getClassRoomId()).orElse(null);
                if(classRoomEntity != null){
                    classRoomEntity.getMentorEntityList().add(savedEntity);
                    classRoomRepository.save(classRoomEntity);
                }
            }
        }
        return savedEntity;
    }

//    //one to many
//    @Override
//    public List<MentorDTO> getAllMentors(String subject) {
//        final List<MentorEntity> mentorEntities = mentorRepository.findAll();
//        return mentorEntities.stream()
//                .filter(mentor->subject == null || Objects.equals(subject,mentor.getSubject()))
//                .map(MentorEntityDTOMapper::map)
//                .toList();
//    }

    //many to many
    @Override
    public List<MentorDTO> getAllMentors(List<String> firstNames,List<String> subjects){
        List<MentorEntity> mentorEntities = mentorRepository.findAll();
        return mentorEntities.stream().map(entity->{
            List<ClassRoomDTO> classRoomDTOS = entity.getClassRoomEntities().stream().map(ClassRoomEntityDTOMapper::map).toList();
            MentorDTO mentorDTO = MentorEntityDTOMapper.map(entity);
            mentorDTO.setClassRoomDTOList(classRoomDTOS);
            return mentorDTO;
        }).toList();
    }

    @Override
    public MentorDTO getMentorById(Integer Id) {
        Optional<MentorEntity> mentorEntity = mentorRepository.findById(Id);
        return mentorEntity.map(MentorEntityDTOMapper::map).orElse(null);
    }

//    @Override
//    public MentorDTO updateMentorById(MentorDTO mentorDTO) {
//        MentorEntity mentorEntity = mentorRepository.findById(mentorDTO.getMentorId()).orElse(null);
//        if(mentorEntity != null){
//            mentorEntity.setFirstName(mentorDTO.getFirstName());
//            mentorEntity.setLastName(mentorDTO.getLastName());
//            mentorEntity.setAddress(mentorDTO.getAddress());
//            mentorEntity.setEmail(mentorDTO.getEmail());
//            mentorEntity.setTitle(mentorDTO.getTitle());
//            mentorEntity.setProfession(mentorDTO.getProfession());
//            mentorEntity.setSubject(mentorDTO.getSubject());
//            mentorEntity.setQualification(mentorDTO.getQualification());
//            MentorEntity updateEntity = mentorRepository.save(mentorEntity);
//            return MentorEntityDTOMapper.map(updateEntity);
//        }
//        return null;
//    }

    //one to many example of Mentor-Classroom
    @Override
    public MentorDTO updateMentorById(MentorDTO mentorDTO) {
//        Optional<MentorEntity> mentorEntityOptional = mentorRepository.findById(mentorDTO.getMentorId());
//        if(mentorEntityOptional.isPresent()){
//            MentorEntity mentorEntity = mentorEntityOptional.get();
//            mentorEntity.setFirstName(mentorDTO.getFirstName());
//            mentorEntity.setLastName(mentorDTO.getLastName());
//            mentorEntity.setAddress(mentorDTO.getAddress());
//            mentorEntity.setEmail(mentorDTO.getEmail());
//            mentorEntity.setTitle(mentorDTO.getTitle());
//            mentorEntity.setProfession(mentorDTO.getProfession());
//            mentorEntity.setSubject(mentorDTO.getSubject());
//            mentorEntity.setQualification(mentorDTO.getQualification());
//            ClassRoomEntity classRoomEntity = null;
//            if(mentorDTO.getClassRoomId() != null){
//                Optional<ClassRoomEntity> optionalClassRoomEntity = classRoomRepository.findById(mentorDTO.getClassRoomId());
//                if (optionalClassRoomEntity.isPresent()) {
//                    classRoomEntity = optionalClassRoomEntity.get();
//                }
//            }
//            mentorEntity.setClassRoomEntity(classRoomEntity);
//            MentorEntity updatedMentor = mentorRepository.save(mentorEntity);
//            return MentorEntityDTOMapper.map(updatedMentor);
//        }
        return null;
    }

    @Override
    public MentorDTO deleteMentorById(Integer id) {
        MentorEntity mentorEntity = mentorRepository.findById(id).orElse(null);
        mentorRepository.deleteById(id);
        return MentorEntityDTOMapper.map(mentorEntity);
    }
}
