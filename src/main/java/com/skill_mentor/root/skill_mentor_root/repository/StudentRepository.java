package com.skill_mentor.root.skill_mentor_root.repository;


import com.skill_mentor.root.skill_mentor_root.entity.StudentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
//    @Modifying
//    @Transactional
//    @Query(value = "INSERT INTO STUDENT (first_name, last_name, email, phone_number, address, age) VALUES (:firstname, :lastname, :email, :phoneNumber, :address, :age)", nativeQuery = true)
//    void saveStudent(
//            @Param("firstname") String firstname,
//            @Param("lastname") String lastName,
//            @Param("email") String email,
//            @Param("phoneNumber") String phoneNumber,
//            @Param("address") String address,
//            @Param("age") Integer age
//    );
}