package com.example.bases2.entity.mongo_entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logActividad")
@Getter
@Setter

public class LogActividad {
    @Id
    private String id;
    private String timestamp;
    private String actividad;
    @Indexed
    private Integer idHabitacion;
    @Indexed
    private Integer idPaciente;

    public LogActividad(String timestamp, String actividad, Integer idHabitacion, Integer idPaciente){
         this.timestamp = timestamp;
         this.actividad = actividad;
         this.idHabitacion = idHabitacion;
         this.idPaciente =  idPaciente;
    }
    // getters, setters, and other necessary methods...
}