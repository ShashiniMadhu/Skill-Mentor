package com.skill_mentor.root.skill_mentor_root.controller;

import com.skill_mentor.root.skill_mentor_root.common.Constants;
import com.skill_mentor.root.skill_mentor_root.dto.ClassRoomDTO;
import com.skill_mentor.root.skill_mentor_root.service.ClassRoomService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/academic")
public class ClassRoomController {
    @Autowired
    private ClassRoomService classroomService;

    public ClassRoomController(){}

    @PostMapping(value = "/classroom", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
        public ResponseEntity<ClassRoomDTO> createClassroom(@Valid @RequestBody ClassRoomDTO classroomDTO) {
        final ClassRoomDTO createdClassroom = classroomService.createClassRoom(classroomDTO);
        return ResponseEntity.ok(createdClassroom);
    }

    @GetMapping(value = "/classroom", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<List<ClassRoomDTO>> getAllClassrooms() {
        final List<ClassRoomDTO> classroomDTOS = (List<ClassRoomDTO>) classroomService.getAllClassRooms();
        return ResponseEntity.ok(classroomDTOS);
    }

    @GetMapping(value="classroom/{id}",produces=Constants.APPLICATION_JSON)
    public ResponseEntity<ClassRoomDTO> findClassroomById(@PathVariable @Min(value = 1, message = "Classroom ID must be positive") Integer id) {
        final ClassRoomDTO classroom = classroomService.findClassRoomById(id);
        return ResponseEntity.ok(classroom);
    }

    @PutMapping(value = "/classroom", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ResponseEntity<ClassRoomDTO> updateClassroom(@Valid @RequestBody ClassRoomDTO classroomDTO) {
        final ClassRoomDTO classroom = classroomService.updateClassRoom(classroomDTO);
        return ResponseEntity.ok(classroom);
    }

    @DeleteMapping(value = "/classroom/{id}", produces = Constants.APPLICATION_JSON)
    public ResponseEntity<ClassRoomDTO> deleteClassroom(@PathVariable @Min(value = 1, message = "Classroom ID must be positive") Integer id) {
        final ClassRoomDTO classroom = classroomService.deleteClassRoomById(id);
        return ResponseEntity.ok(classroom);
    }
}
