package com.example.bases2.controller;

import com.example.bases2.dto.Dto;
import com.example.bases2.entity.sql_entity.Habitacion;
import com.example.bases2.entity.sql_entity.LogActividad;
import com.example.bases2.entity.sql_entity.LogHabitacion;
import com.example.bases2.entity.sql_entity.Paciente;
import com.example.bases2.service.CRUDSql;
import com.example.bases2.service.RedisService;
import com.example.bases2.service.ReportServiceMongo;
import com.example.bases2.service.ReportServiceMySql;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class ReportController {
    private final ReportServiceMySql reportServiceMySql;
    private final CRUDSql crudSql;
    @Autowired
    private RedisService redisService;
    private final ReportServiceMongo reportServiceMongo;


    @Autowired
    public ReportController(CRUDSql crudSql, ReportServiceMySql reportServiceMySql, ReportServiceMongo reportServiceMongo) {
        this.crudSql = crudSql;
        this.reportServiceMySql = reportServiceMySql;
        this.reportServiceMongo = reportServiceMongo;
    }

    @GetMapping("/report")
    public ResponseEntity<String> getPatientsByAgeCategories(@RequestParam("database") int type, @RequestParam("reportId") int reportId) throws JsonProcessingException {
        String redisMessage = "";
        switch (type){
            case 0:
                Dto result = reportServiceMySql.getReport(reportId);
                redisMessage = "On MySql " + reportId;
                redisService.saveTransactionMessage(redisMessage);
                return ResponseEntity.ok(new ObjectMapper().writeValueAsString(result));
            case 1:
                Dto mongoResult = reportServiceMongo.getReport(reportId);
                redisMessage = "On Mongo runned report " + reportId;
                redisService.saveTransactionMessage(redisMessage);
                return ResponseEntity.ok(new ObjectMapper().writeValueAsString(mongoResult));
            case 2:
                break;
            default:
        }
        return null;
    }

    @PostMapping("/habitacion")
    public ResponseEntity<String> createHabitacion(@RequestBody Habitacion habitacion, @RequestParam("database") int type) throws JsonProcessingException {
        String redisMessage = "";
        if(type == 0) {
            Dto result = crudSql.saveHabitacion(habitacion);
            redisMessage = "On MySql " + " created Habitacion";
            return  ResponseEntity.ok(new ObjectMapper().writeValueAsString(result));
        }else if(type == 1){
            com.example.bases2.entity.mongo_entity.Habitacion hab = new com.example.bases2.entity.mongo_entity.Habitacion(habitacion.getIdHabitacion(), habitacion.getHabitacion());
            Dto result2 = reportServiceMongo.saveHabitacion(hab);
            redisMessage = "On Mongo " + " created Habitacion";
            return  ResponseEntity.ok(new ObjectMapper().writeValueAsString(result2));
        }
        return null;
    }

    @PostMapping("/log-habitacion")
    public ResponseEntity<String> createLogHabitacion(@RequestBody LogHabitacion logHabitacion, @RequestParam("database") int type) throws JsonProcessingException {
        String redisMessage = "";
        if(type == 0) {
            Dto result = crudSql.saveLogHabitacion(logHabitacion);
            redisMessage = "On MySql " + " created LogHabitacion";
            return ResponseEntity.ok(new ObjectMapper().writeValueAsString(result));
        }else if(type == 1){
            com.example.bases2.entity.mongo_entity.LogHabitacion logHab =
                    new com.example.bases2.entity.mongo_entity.LogHabitacion(logHabitacion.getHabitacion().getIdHabitacion(), logHabitacion.getTimestampx(),
                    logHabitacion.getStatusx());
            Dto result2 = reportServiceMongo.saveLogHabitacion(logHab);
            redisMessage = "On Mongo " + " created LogHabitacion";
            return  ResponseEntity.ok(new ObjectMapper().writeValueAsString(result2));
        }
        return null;
    }
    @PostMapping("/log-actividad")
    public ResponseEntity<String> createLogActividad(@RequestBody LogActividad logActividad, @RequestParam("database") int type) throws JsonProcessingException {
        String redisMessage = "";
        if(type == 0) {
            Dto result = crudSql.saveLogActividad(logActividad);
            redisMessage = "On MySql " + " created LogActividad";
            return ResponseEntity.ok(new ObjectMapper().writeValueAsString(result));
        }else if(type == 1){
            com.example.bases2.entity.mongo_entity.LogActividad logAct =
                    new com.example.bases2.entity.mongo_entity.LogActividad(logActividad.getTimestampx(), logActividad.getActividad(),
                            logActividad.getHabitacion().getIdHabitacion(), logActividad.getPaciente().getIdPaciente());
            Dto result2 = reportServiceMongo.saveLogActividad(logAct);
            redisMessage = "On Mongo " + " created LogActividad";
            return  ResponseEntity.ok(new ObjectMapper().writeValueAsString(result2));
        }
        return null;
    }

    @PostMapping("/paciente")
    public ResponseEntity<String> createPaciente(@RequestBody Paciente paciente, @RequestParam("database") int type) throws JsonProcessingException {
        String redisMessage = "";
        if(type == 0) {
            Dto result = crudSql.savePaciente(paciente);
            redisMessage = "On MySql " + " created Paciente";
            return ResponseEntity.ok(new ObjectMapper().writeValueAsString(result));
        }else if(type == 1){
            com.example.bases2.entity.mongo_entity.Paciente pac =
                    new com.example.bases2.entity.mongo_entity.Paciente(paciente.getIdPaciente(), paciente.getEdad(), paciente.getGenero());
            Dto result2 = reportServiceMongo.savePaciente(pac);
            redisMessage = "On Mongo " + " created Paciente";
            return  ResponseEntity.ok(new ObjectMapper().writeValueAsString(result2));
        }
        return null;
    }





}
