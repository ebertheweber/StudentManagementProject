package com.project.controller.business;

import com.project.payload.request.business.LessonProgramRequest;
import com.project.payload.response.business.LessonProgramResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.LessonProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/lessonPrograms")
@RequiredArgsConstructor
public class LessonProgramController {

    private final LessonProgramService lessonProgramService;

    @PostMapping("/save") // http://localhost:8080/lessonPrograms/save  + POST  + JSON
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<LessonProgramResponse> saveLessonProgram(@RequestBody @Valid
                                                                    LessonProgramRequest lessonProgramRequest){
        return lessonProgramService.saveLessonProgram(lessonProgramRequest);
    }

    @GetMapping("/getAll") // http://localhost:8080/lessonPrograms/getAll + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public List<LessonProgramResponse> getAllLessonProgramByList(){
        return lessonProgramService.getAllLessonProgramByList();
    }

    @GetMapping("/getById/{id}") // http://localhost:8080/lessonPrograms/getById/1 + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public LessonProgramResponse getLessonProgramById(@PathVariable Long id){
        return lessonProgramService.getLessonProgramById(id);
    }

    // herhangi bir kullanıcı ataması yapılmamış bütün ders programlarını getireceğiz
    @GetMapping("/getAllUnassigned") // http://localhost:8080/lessonPrograms/getAllUnassigned + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public List<LessonProgramResponse> getAllUnassigned(){
        return lessonProgramService.getAllUnassigned();
    }

    @GetMapping("/getAllAssigned") // http://localhost:8080/lessonPrograms/getAllAssigned + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public List<LessonProgramResponse> getAllAssigned(){
        return lessonProgramService.getAllAssigned();
    }

    //TODO LessonProgram
    //Not: ODEV : delete -->  /delete/{id}

    //Not: ODEV : getAllWithPage --> /getAllLessonProgramByPage

    // bir Ogretmen kendine ait lessonProgramlari getiriyor
    @GetMapping("/getAllLessonProgramByTeacher") // http://localhost:8080/lessonPrograms/getAllLessonProgramByTeacher
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public Set<LessonProgramResponse> getAllLessonProgramByTeacherUsername(HttpServletRequest httpServletRequest){
        return lessonProgramService.getAllLessonProgramByUser(httpServletRequest);
    }

    // bir Ogrenci kendine ait lessonProgramlari getiriyor
    @GetMapping("/getAllLessonProgramByStudent") // http://localhost:8080/lessonPrograms/getAllLessonProgramByStudent
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public Set<LessonProgramResponse> getAllLessonProgramByStudent(HttpServletRequest httpServletRequest){
        return lessonProgramService.getAllLessonProgramByUser(httpServletRequest);
    }

    @GetMapping("/getAllLessonProgramByTeacherId/{teacherId}") // http://localhost:8080/lessonPrograms/getAllLessonProgramByTeacherId/3
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public Set<LessonProgramResponse> getByTeacherId(@PathVariable Long teacherId){
        return lessonProgramService.getByTeacherId(teacherId);
    }

    @GetMapping("/getAllLessonProgramByStudentId/{studentId}") // http://localhost:8080/lessonPrograms/getAllLessonProgramByStudentId/3
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public Set<LessonProgramResponse> getByStudentId(@PathVariable Long studentId){
        return lessonProgramService.getByStudentId(studentId);
    }



}