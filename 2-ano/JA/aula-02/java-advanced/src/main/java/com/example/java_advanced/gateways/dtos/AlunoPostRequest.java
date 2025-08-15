package com.example.java_advanced.gateways.dtos;

import lombok.Data;

@Data
public class AlunoPostRequest {
    private String nomeCompleto;
    private String matricula;
    private int idade;
}
