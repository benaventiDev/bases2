package com.example.bases2.service;
import com.example.bases2.dto.*;
import com.example.bases2.entity.mongo_entity.*;
import com.example.bases2.repository.mongo_repository.*;
import com.example.bases2.repository.sql_repository.GenderCount;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;



import java.util.*;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ReportServiceMongo {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private LogActividadMongoRepository logActividadMongoRepository;
    @Autowired
    private PacienteMongoRepository pacienteMongoRepository;
    @Autowired
    private HabitacionesMongoRepository habitacionesRepository;
    @Autowired
    private LogHabitacionMongoRepository logHabitacionMongoRepository;
    public Dto getReport(int reportId){
        long startTime = System.nanoTime();
        Dto dto = null;
        switch (reportId){
            case 1:
                dto =  getPatientsByAgeCategory();
            break;
            case 2:
                dto = countRoomOccurrences();
                break;
            case 3:
                dto = countGenderWithActivity();
                break;
            case 4:
                dto = getTop5MostAttendedAges();
                break;
            case 5:
                dto = getTop5LeastAttendedAges();
                break;
            case 6:
                dto = getTop5Rooms();
                break;
            case 7:
                dto = getBottom5Rooms();
                break;
            case 8:
                dto = getHighestOccuranceDate();
                break;
            default:
                return  null;
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        assert dto != null;
        dto.setDuration(duration);
        return dto;
    }
    public Dto getPatientsByAgeCategory() {
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("edad").exists(true)),
                bucket("edad").withBoundaries(0, 18, 60)
                        .withDefaultBucket("Unknown")
                        .andOutputCount().as("total"),
                project("total")
                        .and(ConditionalOperators.Cond.when(Criteria.where("_id").is(0))
                                .then("Pediátrico")
                                .otherwise(
                                        ConditionalOperators.Cond.when(Criteria.where("_id").is(18))
                                                .then("Mediana edad")
                                                .otherwise(
                                                        ConditionalOperators.Cond.when(Criteria.where("_id").is(60))
                                                                .then("Geriátrico")
                                                                .otherwise("Unknown")
                                                )
                                )
                        ).as("category")
        );

        AggregationResults<AgeCategoryCount> results = mongoTemplate.aggregate(aggregation, "pacientes", AgeCategoryCount.class);
        return new AgeCategoryList(results.getMappedResults());
    }




    public Dto countRoomOccurrences() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("idHabitacion")
                        .count().as("total")
                        .first("idHabitacion").as("idHabitacion"),
                Aggregation.lookup("habitaciones", "idHabitacion", "idHabitacion", "roomInfo"),
                Aggregation.unwind("roomInfo"),
                Aggregation.project("idHabitacion", "total")
                        .and("roomInfo.habitacion").as("habitacion")
        );

        AggregationResults<RoomCount> results = mongoTemplate.aggregate(aggregation, "logActividad", RoomCount.class);

        return new RoomCountList(results.getMappedResults());
    }




    public Dto countGenderWithActivity() {

        List<Paciente> allPatients = pacienteMongoRepository.findAllBy();
        // Step 2: Fetch distinct idPaciente values from logActividad
        Query query = new Query();
        query.fields().include("idPaciente");
        List<PacienteId> activePatientIds = logActividadMongoRepository.findAllIdPacientes();
        Map<Integer, Long> idCountMap = activePatientIds.stream()
                .map(PacienteId::getIdPaciente)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Create a map to store aggregated counts by gender
        Map<String, Long> genderCountMap = new HashMap<>();
        for (Paciente patient : allPatients) {
            Long count = idCountMap.get(patient.getIdPaciente());  // get the count from idCountMap
            if (count != null) {  // if the patient has an activity count
                String gender = patient.getGenero();
                genderCountMap.put(gender, genderCountMap.getOrDefault(gender, 0L) + count);
            }
        }
        return new GeneroCountList(genderCountMap);
    }

    public Dto getTop5MostAttendedAges() {
        List<Paciente> allPatients = pacienteMongoRepository.findAllBy();
        // Step 2: Fetch distinct idPaciente values from logActividad
        Query query = new Query();
        query.fields().include("idPaciente");
        List<PacienteId> activePatientIds = logActividadMongoRepository.findAllIdPacientes();
        Map<Integer, Long> idCountMap = activePatientIds.stream()
                .map(PacienteId::getIdPaciente)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Create a map to store aggregated counts by gender
        Map<Integer, Long> ageCountMap = new HashMap<>();
        for (Paciente patient : allPatients) {
            Long count = idCountMap.get(patient.getIdPaciente());  // get the count from idCountMap
            if (count != null) {  // if the patient has an activity count
                int age = patient.getEdad();
                ageCountMap.put(age, ageCountMap.getOrDefault(age, 0L) + count);
            }
        }

        Map<Integer, Long> top5Ages = ageCountMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())  // sort by value in descending order
                .limit(5)  // limit to top 5 entries
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        return  new EdadesList(top5Ages);
    }

    public Dto getTop5LeastAttendedAges() {
        List<Paciente> allPatients = pacienteMongoRepository.findAllBy();
        // Step 2: Fetch distinct idPaciente values from logActividad
        Query query = new Query();
        query.fields().include("idPaciente");
        List<PacienteId> activePatientIds = logActividadMongoRepository.findAllIdPacientes();
        Map<Integer, Long> idCountMap = activePatientIds.stream()
                .map(PacienteId::getIdPaciente)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Create a map to store aggregated counts by gender
        Map<Integer, Long> ageCountMap = new HashMap<>();
        for (Paciente patient : allPatients) {
            Long count = idCountMap.get(patient.getIdPaciente());  // get the count from idCountMap
            if (count != null) {  // if the patient has an activity count
                int age = patient.getEdad();
                ageCountMap.put(age, ageCountMap.getOrDefault(age, 0L) + count);
            }
        }

        Map<Integer, Long> bottom5Ages = ageCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())  // sort by value in ascending order
                .limit(5)  // limit to bottom 5 entries
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        return  new EdadesList(bottom5Ages);
    }

    private Dto getTop5Rooms(){
        // Group by idHabitacion and count
        GroupOperation groupByHabitacion = Aggregation.group("idHabitacion").count().as("total");
        ProjectionOperation projectToRename = Aggregation.project("total").and("_id").as("idHabitacion");

        // Sort by count descending for top 5
        SortOperation sortByCountDesc = Aggregation.sort(Sort.Direction.DESC, "total");

        // Aggregation pipeline
        Aggregation aggregationTop5 = Aggregation.newAggregation(groupByHabitacion, projectToRename, sortByCountDesc);
        AggregationResults<RoomCount> top5Results = mongoTemplate.aggregate(aggregationTop5, "logActividad", RoomCount.class);
        // Execute the aggregation
        List<RoomCount> top5Rooms =  top5Results.getMappedResults()
                .stream()
                .limit(5)
                .collect(Collectors.toList());
        for (RoomCount roomCount : top5Rooms) {
            String habitacionName = getHabitacionNameById(roomCount.getIdHabitacion().intValue());
            roomCount.setHabitacion(habitacionName);
        }
        return new RoomCountList(top5Rooms);
    }

    public String getHabitacionNameById(int idHabitacion) {
        Optional<Habitacion> habitacion = habitacionesRepository.findByIdHabitacion(idHabitacion);
        return habitacion.map(Habitacion::getHabitacion).orElse(null);
    }
    private Dto getBottom5Rooms(){
        // Group by idHabitacion and count
        GroupOperation groupByHabitacion = Aggregation.group("idHabitacion").count().as("total");
        ProjectionOperation projectToRename = Aggregation.project("total").and("_id").as("idHabitacion");

        // Sort by count descending for top 5
        SortOperation sortByCountDesc = Aggregation.sort(Sort.Direction.ASC, "total");

        // Aggregation pipeline
        Aggregation aggregationTop5 = Aggregation.newAggregation(groupByHabitacion, projectToRename, sortByCountDesc);
        AggregationResults<RoomCount> top5Results = mongoTemplate.aggregate(aggregationTop5, "logActividad", RoomCount.class);
        // Execute the aggregation
        List<RoomCount> top5Rooms =  top5Results.getMappedResults()
                .stream()
                .limit(5)
                .collect(Collectors.toList());
        for (RoomCount roomCount : top5Rooms) {
            String habitacionName = getHabitacionNameById(roomCount.getIdHabitacion().intValue());
            roomCount.setHabitacion(habitacionName);
        }
        return new RoomCountList(top5Rooms);
    }


    private Dto getHighestOccuranceDate(){
        AggregationOperation projection = new AggregationOperation() {
            @Override
            public Document toDocument(AggregationOperationContext context) {
                return new Document("$project",
                        new Document("formattedDate",
                                new Document("$dateToString",
                                        new Document("format", "%Y-%m-%d")
                                                .append("date", "$timestamp"))));
            }
        };

        Aggregation aggregation = Aggregation.newAggregation(
                projection,
                // Group by the extracted date
                Aggregation.group("formattedDate").count().as("count"),

                // Sort by count in descending order
                Aggregation.sort(Sort.by(Sort.Order.desc("count"))),

                // Limit to top 1
                Aggregation.limit(1)
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "logActividad", Document.class);
        Document topDateDocument = results.getUniqueMappedResult();

        String topDate = (String) topDateDocument.get("formattedDate");
        Long occurrences = (Long) topDateDocument.get("count");
        return new HabitacionMaxDate(topDate,occurrences);
    }


    public Dto saveLogActividad(LogActividad logActividad) {
        long startTime = System.nanoTime();
        logActividadMongoRepository.save(logActividad);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        InsertionSql insertionSql = new InsertionSql();
        insertionSql.setDuration(duration);
        return insertionSql;
    }
    public Dto saveLogHabitacion(LogHabitacion logHabitacion) {
        long startTime = System.nanoTime();
        logHabitacionMongoRepository.save(logHabitacion);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        InsertionSql insertionSql = new InsertionSql();
        insertionSql.setDuration(duration);
        return insertionSql;
    }
    public Dto saveHabitacion(Habitacion habitacion) {
        long startTime = System.nanoTime();
        habitacionesRepository.save(habitacion);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        InsertionSql insertionSql = new InsertionSql();
        insertionSql.setDuration(duration);
        return insertionSql;
    }
    public Dto savePaciente(Paciente paciente) {
        long startTime = System.nanoTime();
        pacienteMongoRepository.save(paciente);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        InsertionSql insertionSql = new InsertionSql();
        insertionSql.setDuration(duration);
        return insertionSql;
    }

}
