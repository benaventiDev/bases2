package com.example.bases2.service;


import com.example.bases2.dto.*;
import com.example.bases2.repository.sql_repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class ReportServiceMySql {
    private final PacienteRepository patientRepository;
    private final HabitacionRepository habitacionRepository;
    private final LogActividadRepository logActividadRepository;
    private final LogHabitacionRepository logHabitacionRepository;

    @Autowired
    public ReportServiceMySql(HabitacionRepository habitacionRepository,
                       LogActividadRepository logActividadRepository,
                       LogHabitacionRepository logHabitacionRepository,
                       PacienteRepository pacienteRepository) {
        this.habitacionRepository = habitacionRepository;
        this.logActividadRepository = logActividadRepository;
        this.logHabitacionRepository = logHabitacionRepository;
        this.patientRepository = pacienteRepository;
    }


    public Dto getReport(int reportId){
        long startTime = System.nanoTime();
        Dto dto = null;
        switch (reportId){
            case 1:
                dto = getReportOne();
                break;
            case 2:
                dto = getReportTwo();
                break;
            case 3:
                dto = getReportThree();
                break;
            case 4:
                dto = getReportFour();
                break;
            case 5:
                dto = getReportFive();
                break;
            case 6:
                dto = getReportSix();
                break;
            case 7:
                dto = getReportSeven();
                break;
            case 8:
                dto = getReportEight();
                break;
            default:
                return  null;
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        dto.setDuration(duration);
        return dto;
    }
    public PatientAgeCategoryDto getReportOne() {
        long below18 = patientRepository.countPediatrico();
        long between18to60 = patientRepository.countMedianaEdad();
        long above60 = patientRepository.countGeriatico();
        return new PatientAgeCategoryDto(below18, between18to60, above60);
    }
    public LogHabitacionCount getReportTwo() {
        return new LogHabitacionCount(logActividadRepository.countRoomOccurrences());
    }
    public PacientesPorGenero getReportThree() {
        return new PacientesPorGenero(patientRepository.countPatientsByGender());
    }
    public PacientesPorEdad getReportFour() {
        return new PacientesPorEdad(patientRepository.findTop5MostAttendedAges());
    }
    public PacientesPorEdad getReportFive() {
        return new PacientesPorEdad(patientRepository.findTop5LeastAttendedAges());
    }
    public LogHabitacionCount getReportSix() {
        return new LogHabitacionCount(logActividadRepository.top5RoomOccurrences());
    }
    public LogHabitacionCount getReportSeven() {
        return new LogHabitacionCount(logActividadRepository.bottom5RoomOccurrences());
    }
    public HabitacionMaxDate getReportEight(){
        DateWithCount dateWithCount = logActividadRepository.findDayWithMostActivities().get(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(dateWithCount.getDate());
        return new HabitacionMaxDate(formattedDate, dateWithCount.getTotal());
    }
    /*
    findDayWithMostPatients
            bottom5RoomOccurrences
    public Dto getReport(int reportId){

    * */
}


