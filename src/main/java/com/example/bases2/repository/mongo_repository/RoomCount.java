package com.example.bases2.repository.mongo_repository;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor

public class RoomCount {
    private Long _id;
    @Getter @Setter
    private String habitacion;
    @Getter
    private Long total;

    public Long getIdHabitacion(){
        return _id;
    }


    // getters, setters, and other necessary methods...
}
