package com.example.bases2.dto;

import lombok.Getter;

@Getter
public class HabitacionMaxDate extends Dto{
    private String date;
    private Long count;
    public HabitacionMaxDate(String date, Long count){
        this.date = date;
        this.count = count;
    }
}
