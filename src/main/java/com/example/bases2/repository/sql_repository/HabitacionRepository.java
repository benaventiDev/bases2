package com.example.bases2.repository.sql_repository;

import com.example.bases2.entity.sql_entity.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {
}