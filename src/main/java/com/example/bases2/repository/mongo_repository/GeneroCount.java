package com.example.bases2.repository.mongo_repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GeneroCount {

    private String genero;
    private long count;
    private String idPaciente;

    // getters, setters, and constructors...
}
