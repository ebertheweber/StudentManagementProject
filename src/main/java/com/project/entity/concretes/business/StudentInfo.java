package com.project.entity.concretes.business;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.entity.concretes.user.User;
import com.project.entity.enums.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StudentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer absentee; //yoklama

    private Double midtermExam;

    private Double finalExam;

    private Double examAverage;

    private String infoNote;

    @Enumerated(EnumType.STRING)
    private Note letterGrade; //AA , Aa , aa

    @ManyToOne // 1 ogretmen 1den fazla student infoya atanabilir
    @JsonIgnore
    private User teacher;

    @ManyToOne //1 ogrenci aldığı ders sayısı kadar studentInfosu vardır
    @JsonIgnore
    private User student;

    @ManyToOne
    private Lesson lesson;



}
