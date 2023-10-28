package com.example.bases2.entity.sql_entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Habitacion")
@Getter
@Setter
public class Habitacion {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHabitacion")
    @Basic(optional = false)
    private Integer idHabitacion;

    @Column(name = "habitacion")
    private String habitacion;

    // getters, setters, and other necessary methods...
}

