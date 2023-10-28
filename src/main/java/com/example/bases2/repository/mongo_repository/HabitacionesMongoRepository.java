package com.example.bases2.repository.mongo_repository;
import com.example.bases2.entity.mongo_entity.Habitacion;
import com.example.bases2.entity.mongo_entity.LogActividad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface HabitacionesMongoRepository extends MongoRepository<Habitacion, String> {
    Optional<Habitacion> findByIdHabitacion(int idHabitacion);
}
