package com.gestao.matricula.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MatriculaDto(@NotBlank String CodigoMatricula, @NotBlank String nomeCurso, @NotNull LocalDate dataInicio) {
}
