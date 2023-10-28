package com.example.bases2.dto;

import com.example.bases2.repository.sql_repository.GenderCount;
import lombok.Getter;

import java.util.List;

@Getter
public class PacientesPorGenero extends Dto{
    private List<GenderCount> Generos;
    public PacientesPorGenero(List<GenderCount> list){
        this.Generos = list;
    }

}
