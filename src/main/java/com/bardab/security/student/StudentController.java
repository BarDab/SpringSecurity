package com.bardab.security.student;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping ("api/v1/students")
public class StudentController {


    public static List<Student> studentList = Arrays.asList(
            new Student(1,"James Bond"),
            new Student(2,"Duffy Duck"),
            new Student(3, "Frodo Baggins")
    );


    @GetMapping (path = "{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId){

        return studentList.stream().filter(
                student-> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(()->new IllegalStateException("Student with id: "+studentId+" does not exist"));
    }


}
