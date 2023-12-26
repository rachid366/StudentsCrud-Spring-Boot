package com.rachid.Crud.service;

import com.rachid.Crud.model.Student;
import com.rachid.Crud.repos.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private StudentRepo studentRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }
    public List<Student> getAll(){
        return studentRepo.findAll();
    }
    public void save(Student student){
         studentRepo.save(student);
    }

    public Student get(Long id) {
        return studentRepo.findById(id).get();
    }

    public void delete(long id) {
        studentRepo.deleteById(id);
    }
}
