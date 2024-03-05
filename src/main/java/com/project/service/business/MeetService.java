package com.project.service.business;

import com.project.entity.concretes.user.User;
import com.project.payload.request.business.MeetRequest;
import com.project.payload.response.business.MeetResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.MeetRepository;
import com.project.service.helper.MethodHelper;
import com.project.service.validator.DateTimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final MethodHelper methodHelper;
    private final DateTimeValidator dateTimeValidator;

    public ResponseMessage<MeetResponse> saveMeet(HttpServletRequest httpServletRequest, MeetRequest meetRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User advisorTeacher = methodHelper.isUserExistByUserName(username);
        methodHelper.checkAdvisor(advisorTeacher);

        //!!! Yeni Meet saatlerinde çakışma var mı kontrolü
        dateTimeValidator.checkTimeWithException(meetRequest.getStartTime(), meetRequest.getStopTime());

        //!!! AdvTeacher'ın mevcut meetleri ile çakışma var mı?

    }

    private

}
