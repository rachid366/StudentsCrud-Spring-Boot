package com.rachid.Crud.repos;

import com.rachid.Crud.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    Page<Student> findByNameContains(String name, Pageable pageable);
}
