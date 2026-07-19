package com.gestao.matricula.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record DiscenteRequest(@NotBlank String nome, @NotNull LocalDate dataNascimento, @NotBlank String telefone, @NotNull List<MatriculaDto> matriculas) {
}
