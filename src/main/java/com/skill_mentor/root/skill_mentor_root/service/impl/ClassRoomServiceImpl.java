package com.skill_mentor.root.skill_mentor_root.service.impl;

import com.skill_mentor.root.skill_mentor_root.dto.ClassRoomDTO;
import com.skill_mentor.root.skill_mentor_root.entity.ClassRoomEntity;
import com.skill_mentor.root.skill_mentor_root.mapper.ClassRoomEntityDTOMapper;
import com.skill_mentor.root.skill_mentor_root.repository.ClassRoomRepository;
import com.skill_mentor.root.skill_mentor_root.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassRoomServiceImpl implements ClassRoomService {
    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Override
    public ClassRoomDTO createClassRoom(ClassRoomDTO classRoomDTO) {
        classRoomDTO.setClassRoomId(null);
        final ClassRoomEntity classRoomEntity = ClassRoomEntityDTOMapper.map(classRoomDTO);
        final ClassRoomEntity savedEntity = classRoomRepository.save(classRoomEntity);
        return ClassRoomEntityDTOMapper.map(savedEntity);
    }

    @Override
    public List<ClassRoomDTO> getAllClassRooms() {
        final List<ClassRoomEntity> classRoomEntities = classRoomRepository.findAll();
        return classRoomEntities.stream().map(ClassRoomEntityDTOMapper::map).toList(); // without any filter parameter
    }

    @Override
    public ClassRoomDTO findClassRoomById(Integer id) {
        Optional <ClassRoomEntity> classRoomEntity = classRoomRepository.findById(id);
        return classRoomEntity.map(ClassRoomEntityDTOMapper::map).orElse(null);
    }

    @Override
    public ClassRoomDTO deleteClassRoomById(Integer id) {
        ClassRoomEntity classRoomEntity = classRoomRepository.findById(id).orElse(null);
        classRoomRepository.deleteById(id);
        return ClassRoomEntityDTOMapper.map(classRoomEntity);
    }

//    @Override
//    public ClassRoomDTO getClassRoomWithMentor(Integer classRoomId) {
//        //fetch the classroom entity from the repository
//        ClassRoomEntity classRoomEntity = classRoomRepository.findById(classRoomId).orElse(null);
//
//        if(classRoomEntity != null && classRoomEntity.getMentor() != null){
//            //Exactly access the mentor property to trigger lazy loading
//            classRoomEntity.getMentor();
//        }
//        ClassRoomDTO classRoomDTO = ClassRoomEntityDTOMapper.map(classRoomEntity);
//        return classRoomDTO;
//    }

    @Override
    public ClassRoomDTO updateClassRoom(ClassRoomDTO classRoomDTO) {
        Optional<ClassRoomEntity> classRoomEntity = classRoomRepository.findById(classRoomDTO.getClassRoomId());
        if (classRoomEntity.isEmpty()) {
            throw new RuntimeException("ClassRoom not found");
        }
        ClassRoomEntity updatedEntity = classRoomEntity.get();
        updatedEntity.setTitle(classRoomDTO.getTitle());
        updatedEntity.setSessionFee(classRoomDTO.getSessionFee());
        updatedEntity.setEnrolledStudentCount(classRoomDTO.getEnrolledStudentCount());
        ClassRoomEntity savedEntity = classRoomRepository.save(updatedEntity);
        return ClassRoomEntityDTOMapper.map(savedEntity);
    }
}
