package com.gestao.matricula.service;

import com.gestao.matricula.dto.DiscenteRequest;
import com.gestao.matricula.dto.DiscenteResponse;
import com.gestao.matricula.dto.MatriculaDto;
import com.gestao.matricula.mapper.DiscenteMapper;
import com.gestao.matricula.model.Discente;
import com.gestao.matricula.model.Matricula;
import com.gestao.matricula.repository.DiscenteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiscenteServiceTest {

    @Mock
    private DiscenteRepository discenteRepository;

    @Mock
    private DiscenteMapper discenteMapper;

    @Autowired
    @InjectMocks
    private DiscenteService discenteService;

    private Discente discente;
    private DiscenteRequest request;
    private DiscenteResponse response;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        discente = new Discente();
        discente.setId(1L);
        discente.setNome("João");
        discente.setTelefone("99999-9999");
        discente.setDataNascimento(LocalDate.of(1990, 1, 1));
        discente.setMatriculas(new ArrayList<>());

        request = new DiscenteRequest("João", LocalDate.of(1990, 1, 1),"99999-9999", new ArrayList<>());
        response = new DiscenteResponse(1L, "João", "99999-9999", LocalDate.of(1990, 1, 1), new ArrayList<>());

    }

    @Test
    void listarTodosAlunos_existemAlunos_listaRetornada() {
        List<Discente> discentes = List.of(discente);
        List<DiscenteResponse> respostas = List.of(response);

        when(discenteRepository.findAll()).thenReturn(discentes);
        when(discenteMapper.paraResponseList(discentes)).thenReturn(respostas);

        List<DiscenteResponse> resultado = discenteService.listarTodosAlunos();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).nome()).isEqualTo("João");
        verify(discenteRepository).findAll();
    }

    @Test
    void salvar_dadosValidos_alunoSalvoComSucesso() {
        when(discenteMapper.toEntity(request)).thenReturn(discente);
        when(discenteRepository.save(discente)).thenReturn(discente);
        when(discenteMapper.toResponse(discente)).thenReturn(response);

        DiscenteResponse resultado = discenteService.salvar(request);

        assertThat(resultado.nome()).isEqualTo("João");
        verify(discenteRepository).save(discente);
    }

    @Test
    void listarmatriculas_idExistente_matriculasRetornadas() {
        Matricula matricula = new Matricula();
        matricula.setCodigoMatricula("MAT123");
        matricula.setNomeCurso("Java");
        matricula.setDataInicio(LocalDate.of(2023, 1, 1));
        matricula.setDiscente(discente);

        discente.setMatriculas(List.of(matricula));

        when(discenteRepository.findById(1L)).thenReturn(Optional.of(discente));

        List<MatriculaDto> resultado = discenteService.listarmatriculas(1L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).CodigoMatricula()).isEqualTo("MAT123");
    }

    @Test
    void listarmatriculas_idInexistente_lancarExcecao() {
        when(discenteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> discenteService.listarmatriculas(99L));
    }

    void remover_idExistente_alunoRemovidoComSucesso() {
        when(discenteRepository.findById(1L)).thenReturn(Optional.of(discente));

        discenteService.remover(1L);

        verify(discenteRepository).delete(discente);
    }

    @Test
    void remover_idInexistente_lancarExcecao() {
        when(discenteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> discenteService.remover(99L));
    }

    @Test
    void atualizar_idExistente_dadosAtualizados() {
        when(discenteRepository.findById(1L)).thenReturn(Optional.of(discente));
        when(discenteRepository.save(any())).thenReturn(discente);
        when(discenteMapper.toResponse(any())).thenReturn(response);

        DiscenteResponse resultado = discenteService.atualizar(1L, request);

        assertThat(resultado.nome()).isEqualTo("João");
        verify(discenteRepository).save(discente);
    }

    @Test
    void atualizar_idInexistente_lancarExcecao() {
        when(discenteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> discenteService.atualizar(99L, request));
    }


}