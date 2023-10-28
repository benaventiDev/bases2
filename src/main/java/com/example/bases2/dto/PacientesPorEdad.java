package com.example.bases2.dto;

import com.example.bases2.repository.sql_repository.AgeCount;
import lombok.Getter;

import java.util.List;

@Getter
public class PacientesPorEdad extends Dto{
    private List<AgeCount> edades;
    public PacientesPorEdad(List<AgeCount> list){
        this.edades = list;
    }
}
