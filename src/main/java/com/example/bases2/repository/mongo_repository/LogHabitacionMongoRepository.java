package com.example.bases2.repository.mongo_repository;

import com.example.bases2.entity.mongo_entity.LogHabitacion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogHabitacionMongoRepository extends MongoRepository<LogHabitacion, String> {
}
