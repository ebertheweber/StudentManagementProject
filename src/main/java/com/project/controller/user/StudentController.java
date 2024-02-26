package com.project.controller.user;

import com.project.payload.request.user.StudentRequest;
import com.project.payload.request.user.StudentRequestWithoutPassword;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.StudentResponse;
import com.project.service.user.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/save") // http://localhost:8080/student/save  + JSON + POST
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<StudentResponse>> saveStudent(
            @RequestBody @Valid StudentRequest studentRequest){

        return ResponseEntity.ok(studentService.saveStudent(studentRequest));
    }

    //!!! öğrencinin kendi bilgilerinin güncellemesi
    @PatchMapping("/update") // http://localhost:8080/student/update + PATCH + JSON
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<String> updateStudent(
            @RequestBody @Valid StudentRequestWithoutPassword studentRequestWithoutPassword,
            HttpServletRequest request){

        return studentService.updateStudent(studentRequestWithoutPassword,request);
    }

}