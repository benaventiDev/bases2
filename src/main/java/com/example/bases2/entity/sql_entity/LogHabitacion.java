package com.example.bases2.entity.sql_entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Log_Habitacion")
@Getter
@Setter
public class LogHabitacion {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLogHabitacion")
    @Basic(optional = false)
    private Integer idLogHabitacion;

    @Column(name = "timestampx")
    private String timestampx;

    @Column(name = "statusx")
    private String statusx;

    @ManyToOne
    @JoinColumn(name = "idHabitacion")
    private Habitacion habitacion;

    // getters, setters, and other necessary methods...
}
