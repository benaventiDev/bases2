package com.example.bases2.repository.mongo_repository;

import com.example.bases2.entity.mongo_entity.LogActividad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogActividadMongoRepository extends MongoRepository<LogActividad, String> {
    @Query(value = "{}", fields = "{ '_id' : 0, idPaciente : 1 }")
    List<PacienteId> findAllIdPacientes();
}

