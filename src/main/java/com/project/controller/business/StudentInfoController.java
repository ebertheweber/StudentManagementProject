package com.project.controller.business;

import com.project.payload.request.business.StudentInfoRequest;
import com.project.payload.request.business.UpdateStudentInfoRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.StudentInfoResponse;
import com.project.service.business.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("studentInfo")
@RequiredArgsConstructor
public class StudentInfoController {

    private final StudentInfoService studentInfoService;

    @PostMapping("/save") // http://localhost:8080/studentInfo/save + POST + JSON
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public ResponseMessage<StudentInfoResponse> saveStudentInfo(HttpServletRequest httpServletRequest,
                                                                @RequestBody @Valid StudentInfoRequest studentInfoRequest){
        return studentInfoService.saveStudentInfo(httpServletRequest, studentInfoRequest);
    }

    @DeleteMapping("/delete/{studentInfoId}") // http://localhost:8080/studentInfo/delete/1
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    public ResponseMessage delete(@PathVariable Long studentInfoId){
        return studentInfoService.deleteStudentInfo(studentInfoId);
    }
    // TODO student info
    // NOT: ODEV ---> getAllWithPage    yöneticiler tetikleyecek    / getAllStudentInfoByPage

    // NOT: ODEV ---> getStudentInfoByStudentId() yoneticiler tetikleyecek     /getByuStudentId/{studentId}

    // NOT: ODEV ---> getStudentInfoById()  yöneticiler tetikleyecek  /get/{studentInfoId}

    @PutMapping("/update/{studentInfoId}") // http://localhost:8080/studentInfo/update/2
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    public ResponseMessage<StudentInfoResponse> update(@RequestBody @Valid UpdateStudentInfoRequest studentInfoRequest,
                                                       @PathVariable Long studentInfoId) {
        return studentInfoService.update(studentInfoRequest, studentInfoId);
    }

    // !!! -> Bir ogretmen kendi ogrencilerinin bilgilerini almak isterse :
    @GetMapping("/getAllForTeacher") // http://localhost:8080/studentInfo/getAllForTeacher
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public ResponseEntity<Page<StudentInfoResponse>> getAllForTeacher(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ) {
        return new ResponseEntity<>(studentInfoService.getAllForTeacher(httpServletRequest, page, size), HttpStatus.OK);
    }

    // !!! --> bir ogrenci kendi bilgilerini almak isterse
    @GetMapping("/getAllForStudent") // http://localhost:8080/studentInfo/getAllForStudent
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<Page<StudentInfoResponse>> getAllForStudent(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ) {
        return new ResponseEntity<>(studentInfoService.getAllForStudent(httpServletRequest, page, size), HttpStatus.OK);
    }
}
