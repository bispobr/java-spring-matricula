package com.gestao.matricula.dto;

import java.time.LocalDate;
import java.util.List;

public record DiscenteResponse(Long id, String nome, String telefone, LocalDate dataNascimento, List<MatriculaDto> matriculas) {
}
