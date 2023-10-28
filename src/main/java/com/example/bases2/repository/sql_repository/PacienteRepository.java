package com.example.bases2.repository.sql_repository;

import com.example.bases2.entity.sql_entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    //@Query("SELECT COUNT(DISTINCT p.idPaciente) FROM Paciente p JOIN LogActividad la ON p.idPaciente = la.paciente.idPaciente WHERE p.edad < 18")
    @Query("SELECT COUNT(p.idPaciente) FROM Paciente p WHERE p.edad < 18")
    long countPediatrico();


    // Count for Mediana edad category
    @Query("SELECT COUNT(p.idPaciente) FROM Paciente p WHERE p.edad BETWEEN 18 AND 60")
    long countMedianaEdad();

    @Query("SELECT COUNT(p.idPaciente) FROM Paciente p WHERE p.edad > 60")
    long countGeriatico();

    @Query(value =
            "SELECT p.genero as genero, " +
                    "COUNT(DISTINCT l.idPaciente) as total " +
                    "FROM Paciente p " +
                    "JOIN Log_Actividad l ON p.idPaciente = l.idPaciente " +
                    "GROUP BY p.genero",
            nativeQuery = true)
    List<GenderCount> countPatientsByGender();

    @Query(value = "SELECT p.edad as age, COUNT( l.idPaciente) as total " +
            "FROM Paciente p " +
            "JOIN Log_Actividad l ON p.idPaciente = l.idPaciente " +
            "GROUP BY p.edad " +
            "ORDER BY total DESC " +
            "LIMIT 5", nativeQuery = true)
    List<AgeCount> findTop5MostAttendedAges();


    @Query(value = "SELECT p.edad as age, COUNT( l.idPaciente) as total " +
            "FROM Paciente p " +
            "JOIN Log_Actividad l ON p.idPaciente = l.idPaciente " +
            "GROUP BY p.edad " +
            "ORDER BY total ASC " +
            "LIMIT 5", nativeQuery = true)
    List<AgeCount> findTop5LeastAttendedAges();

}
