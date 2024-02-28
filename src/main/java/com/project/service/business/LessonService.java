package com.project.service.business;

import com.project.exception.ConflictException;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.request.business.LessonRequest;
import com.project.payload.response.business.LessonResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    public ResponseMessage<LessonResponse> saveLesson(LessonRequest lessonRequest) {

        //!!! LessonName unique mi kontrolü
        isLessonExistByLessonName(lessonRequest.getLessonName());
        //!!! DTO --> POJO

    }

    private boolean isLessonExistByLessonName(String lessonName){ //JAVA , java , Java

        boolean lessonExists = lessonRepository.existsLessonByLessonNameEqualsIgnoreCase(lessonName);
        if (lessonExists){
            throw new ConflictException(String.format(ErrorMessages.LESSON_ALREADY_EXIST_WITH_LESSON_NAME, lessonName));
        } else {
            return false;
        }
    }
}
