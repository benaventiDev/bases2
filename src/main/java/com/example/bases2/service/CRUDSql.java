package com.example.bases2.service;

import com.example.bases2.dto.Dto;
import com.example.bases2.dto.InsertionSql;
import com.example.bases2.entity.sql_entity.Habitacion;
import com.example.bases2.entity.sql_entity.LogActividad;
import com.example.bases2.entity.sql_entity.LogHabitacion;
import com.example.bases2.entity.sql_entity.Paciente;
import com.example.bases2.repository.sql_repository.HabitacionRepository;
import com.example.bases2.repository.sql_repository.LogActividadRepository;
import com.example.bases2.repository.sql_repository.LogHabitacionRepository;
import com.example.bases2.repository.sql_repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CRUDSql {
    private final PacienteRepository patientRepository;
    private final HabitacionRepository habitacionRepository;
    private final LogActividadRepository logActividadRepository;
    private final LogHabitacionRepository logHabitacionRepository;

    @Autowired
    public CRUDSql(HabitacionRepository habitacionRepository,
                              LogActividadRepository logActividadRepository,
                              LogHabitacionRepository logHabitacionRepository,
                              PacienteRepository pacienteRepository) {
        this.habitacionRepository = habitacionRepository;
        this.logActividadRepository = logActividadRepository;
        this.logHabitacionRepository = logHabitacionRepository;
        this.patientRepository = pacienteRepository;
    }
    public Dto saveHabitacion(Habitacion habitacion) {
        long startTime = System.nanoTime();
        habitacionRepository.save(habitacion);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        InsertionSql insertionSql = new InsertionSql();
        insertionSql.setDuration(duration);
        return insertionSql;
    }
    public Dto saveLogActividad(LogActividad logActividad) {
        long startTime = System.nanoTime();
        logActividadRepository.save(logActividad);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        InsertionSql insertionSql = new InsertionSql();
        insertionSql.setDuration(duration);
        return insertionSql;
    }

    public Dto saveLogHabitacion(LogHabitacion logHabitacion) {
        long startTime = System.nanoTime();
        logHabitacionRepository.save(logHabitacion);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        InsertionSql insertionSql = new InsertionSql();
        insertionSql.setDuration(duration);
        return insertionSql;
    }

    public Dto savePaciente(Paciente paciente) {
        long startTime = System.nanoTime();
        patientRepository.save(paciente);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        InsertionSql insertionSql = new InsertionSql();
        insertionSql.setDuration(duration);
        return insertionSql;
    }

}
