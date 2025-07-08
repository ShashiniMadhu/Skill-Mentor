package com.skill_mentor.root.skill_mentor_root.service;

import com.skill_mentor.root.skill_mentor_root.dto.ClassRoomDTO;

import java.util.List;

public interface ClassRoomService {

    ClassRoomDTO createClassRoom(ClassRoomDTO classRoomDTO);

    List<ClassRoomDTO> getAllClassRooms();

    ClassRoomDTO findClassRoomById(Integer id);

    ClassRoomDTO deleteClassRoomById(Integer id);

    ClassRoomDTO updateClassRoom(ClassRoomDTO classRoomDTO);
}
