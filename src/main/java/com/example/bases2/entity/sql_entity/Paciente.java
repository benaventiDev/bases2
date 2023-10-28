package com.example.bases2.entity.sql_entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Paciente")
@Getter @Setter
public class Paciente {
    @Id
    @Basic(optional = false)
    @Column(name = "idPaciente")
    private Integer idPaciente;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "genero")
    private String genero;

    // getters, setters, and other necessary methods...
}

