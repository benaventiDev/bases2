package com.example.bases2.entity.mongo_entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "habitaciones")
@Getter
@Setter

public class LogHabitacion {
    @Id
    private String _id;
    private Integer idHabitacion;
    private String timestamp;
    private String status;
    public LogHabitacion(Integer idHabitacion, String timestamp, String status){
         this.idHabitacion =  idHabitacion;
         this.timestamp =  timestamp;
         this.status = status;
    }
}
