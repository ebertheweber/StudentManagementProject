package com.project.service.business;

import com.project.exception.BadRequestException;
import com.project.exception.ConflictException;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.request.business.EducationTermRequest;
import com.project.payload.response.business.EducationTermResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.EducationTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationTermService {

    private final EducationTermRepository educationTermRepository;

    public ResponseMessage<EducationTermResponse> saveEducationTerm(EducationTermRequest educationTermRequest) {

        validateEducationTermDates(educationTermRequest);

    }

    private void validateEducationTermDatesForRequest(EducationTermRequest educationTermRequest){
        // !!! bu metodda amacimiz requestten gelen registrationDate,StartDate ve endDate arasindaki
        // tarih sirasina gore dogru mu setlenmis onu kontrol etmek

        // registration > start
        if(educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())){
            throw new BadRequestException(ErrorMessages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
        }
        // end > start
        if(educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())) {
            throw new BadRequestException(ErrorMessages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
        }
    }

    private void validateEducationTermDates(EducationTermRequest educationTermRequest){

        validateEducationTermDatesForRequest(educationTermRequest);

        // !!! Bir yil icinde bir tane Guz donemi veya Yaz Donemi olmali kontrolu
        if(educationTermRepository.existsByTermAndYear(educationTermRequest.getTerm(),
                educationTermRequest.getStartDate().getYear())){
            throw new ConflictException(ErrorMessages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);
        }
        // !!! yil icine eklencek educationTerm, mevcuttakilerin tarihleri ile cakismamali
        if(educationTermRepository.findByYear(educationTermRequest.getStartDate().getYear())
                .stream()
                .anyMatch(educationTerm ->
                        (         educationTerm.getStartDate().equals(educationTermRequest.getStartDate()) //!!! 1. kontrol : baslama tarihleri ayni ise --> et1(10 kasim 2023) / YeniEt(10 kasim 2023)
                                ||  (educationTerm.getStartDate().isBefore(educationTermRequest.getStartDate())//!!! 2. kontrol : baslama tarihi mevcuttun baslama ve bitis tarihi ortasinda ise -->
                                && educationTerm.getEndDate().isAfter(educationTermRequest.getStartDate())) // Ornek : et1 ( baslama 10 kasim 2023 - bitme 20 kasim 2023)  - YeniEt ( baslama 15 kasim 2023 bitme 25 kasim 2023)
                                ||  (educationTerm.getStartDate().isBefore(educationTermRequest.getEndDate())//!!! 3. kontrol bitis tarihi mevcuttun baslama ve bitis tarihi ortasinda ise
                                && educationTerm.getEndDate().isAfter(educationTermRequest.getEndDate())) // Ornek : et1 ( baslama 10 kasim 2023 - bitme 20 kasim 2023)  - YeniEt ( baslama 09 kasim 2023 bitme 15 kasim 2023)
                                ||  (educationTerm.getStartDate().isAfter(educationTermRequest.getStartDate()) //!!!4.kontrol : yeni eklenecek eskiyi tamamen kapsiyorsa
                                && educationTerm.getEndDate().isBefore(educationTermRequest.getEndDate()))//et1 ( baslama 10 kasim 2023 - bitme 20 kasim 2023)  - YeniEt ( baslama 09 kasim 2023 bitme 25 kasim 2023)

                        ))
        ){
            throw new BadRequestException(ErrorMessages.EDUCATION_TERM_CONFLICT_MESSAGE);
        }
    }
}