package com.example.bases2.entity.mongo_entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "habitaciones")
@Getter @Setter

public class Habitacion {
    @Id
    private String id;
    @Indexed
    private Integer idHabitacion;
    private String habitacion;
    public Habitacion(Integer idHabitacion,
    String habitacion){
        this.habitacion = habitacion;
        this.idHabitacion = idHabitacion;
    }
    // getters, setters, and other necessary methods...
}