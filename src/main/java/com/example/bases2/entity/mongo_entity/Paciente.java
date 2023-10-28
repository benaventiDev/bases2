package com.example.bases2.entity.mongo_entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pacientes")
@Getter
@Setter

public class Paciente {

    @Id
    private String _id;
    @Indexed
    private Integer idPaciente;
    private Integer edad;
    private String genero;
    public Paciente(Integer idPaciente, Integer edad, String genero){
          this.idPaciente = idPaciente;
          this.edad = edad;
          this.genero = genero;
    }

    // getters, setters and other necessary methods...
}
