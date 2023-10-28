package com.example.bases2.dto;

import com.example.bases2.repository.sql_repository.RoomCount;
import lombok.Getter;

import java.util.List;

@Getter
public class LogHabitacionCount extends Dto {
    private List<RoomCount> habitaciones;
    public LogHabitacionCount(List<RoomCount> list){
        this.habitaciones = list;
    }

}
