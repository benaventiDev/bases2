package com.example.bases2.entity.sql_entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Log_Actividad")
@Getter
@Setter
public class LogActividad {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log_actividad")
    @Basic(optional = false)
    private Integer idLogActividad;

    @Column(name = "timestampx")
    private String timestampx;

    @Column(name = "actividad")
    private String actividad;

    @ManyToOne
    @JoinColumn(name = "idPaciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "idHabitacion")
    private Habitacion habitacion;

    // getters, setters, and other necessary methods...
}
