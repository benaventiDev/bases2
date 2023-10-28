package com.example.bases2.repository.sql_repository;

import com.example.bases2.entity.sql_entity.LogActividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LogActividadRepository extends JpaRepository<LogActividad, Integer> {
    @Query(value = "SELECT h.idHabitacion as idHabitacion, h.habitacion as habitacion, COUNT( l.idPaciente)  as total " +
            "FROM Habitacion h " +
            "LEFT JOIN Log_Actividad l ON h.idHabitacion = l.idHabitacion " +
            "GROUP BY h.idHabitacion, h.habitacion", nativeQuery = true)
    List<RoomCount> countRoomOccurrences();

    @Query(value =
            "SELECT h.idHabitacion as idHabitacion, h.habitacion as habitacion, COUNT( l.idPaciente)  as total " +
                    "FROM Habitacion h " +
                    "LEFT JOIN Log_Actividad l ON h.idHabitacion = l.idHabitacion " +
                    "GROUP BY h.idHabitacion, h.habitacion " +
                    "ORDER BY total DESC " +
                    "LIMIT 5",
            nativeQuery = true)
    List<RoomCount> top5RoomOccurrences();

    @Query(value =
            "SELECT h.idHabitacion as idHabitacion, h.habitacion as habitacion, COUNT( l.idPaciente)  as total " +
                    "FROM Habitacion h " +
                    "LEFT JOIN Log_Actividad l ON h.idHabitacion = l.idHabitacion " +
                    "GROUP BY h.idHabitacion, h.habitacion " +
                    "ORDER BY total ASC " +
                    "LIMIT 5",
            nativeQuery = true)
    List<RoomCount> bottom5RoomOccurrences();

    @Query(value = "SELECT DATE(STR_TO_DATE(timestampx, '%c/%e/%Y %H:%i')) as date, COUNT(*) as total \n" +
            "FROM Log_Actividad \n" +
            "WHERE timestampx IS NOT NULL \n" +
            "GROUP BY DATE(STR_TO_DATE(timestampx, '%c/%e/%Y %H:%i')) \n" +
            "ORDER BY total DESC \n" +
            "LIMIT 1;", nativeQuery = true)
    List<DateWithCount> findDayWithMostActivities();


}
