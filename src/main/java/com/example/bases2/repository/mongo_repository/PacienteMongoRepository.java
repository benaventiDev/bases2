package com.example.bases2.repository.mongo_repository;


import com.example.bases2.entity.mongo_entity.LogActividad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.bases2.entity.mongo_entity.Paciente;
import java.util.List;

@Repository
public interface PacienteMongoRepository extends MongoRepository<Paciente, String>{
    List<Paciente> findAllBy();
}
