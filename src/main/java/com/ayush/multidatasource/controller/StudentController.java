package com.ayush.multidatasource.controller;

import com.ayush.multidatasource.mapper.StudentMapper;
import com.ayush.multidatasource.model.Student;
import com.ayush.multidatasource.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentRepo studentRepo;

    @Autowired
    private  StudentMapper studentMapper;

    public StudentController(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @PostMapping("/save")
    public ResponseEntity saveStudent(@Valid @RequestBody Student student, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }
        Student student1 = studentRepo.save(student);
        return ResponseEntity.ok(student1);
    }

    @GetMapping("/all")
    public ResponseEntity allStudent(){
        List<Student> all = studentRepo.findAll();
        return ResponseEntity.ok(all);
    }
}
