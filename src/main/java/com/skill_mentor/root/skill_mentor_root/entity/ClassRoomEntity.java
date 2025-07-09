package com.skill_mentor.root.skill_mentor_root.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "classroom")
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_room_id")
    private Integer classRoomId;

    @NotNull(message = "Title must not be null")
    @Column(name = "title", nullable = false)
    private String title;

    //@Column(name = "session_fee")
    //private Double sessionFee;

    @NotNull(message = "Enrolled student count must not be null")
    @Column(name = "enrolled_student_count", nullable = false)
    private Integer enrolledStudentCount;

    // One-to-One relationship with Mentor
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mentor_id", referencedColumnName = "mentor_id")
    private MentorEntity mentor;

    // One-to-Many relationship with Sessions
    @OneToMany(mappedBy = "classRoomEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SessionEntity> sessions;

}
