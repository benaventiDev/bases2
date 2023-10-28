package com.example.bases2.repository.mongo_repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PatientActivityCount {
    private int idPaciente;
    private long count;
}
