package com.gestao.matricula.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestao.matricula.dto.DiscenteRequest;
import com.gestao.matricula.dto.DiscenteResponse;
import com.gestao.matricula.dto.MatriculaDto;
import com.gestao.matricula.service.DiscenteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DiscenteControllerTest {

    @Mock
    private DiscenteService discenteService;


    @InjectMocks
    private DiscenteController discenteController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;


    private DiscenteResponse discenteResponse;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(discenteController).build();
        discenteResponse = new DiscenteResponse(1L, "João", "99999-9999", LocalDate.of(1990, 1, 1), new ArrayList<>());
    }

    @Test
    void listarTodosAlunos_dadosExistem_retornaLista200() throws Exception {
        List<DiscenteResponse> alunos = List.of(discenteResponse);
        when(discenteService.listarTodosAlunos()).thenReturn(alunos);

        mockMvc.perform(get("/aluno/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"));
    }

    @Test
    void criar_dadosValidos_alunoCriado201() throws Exception {

        String json = """
                {
                     "nome": "João",
                     "dataNascimento": "1990-01-01",
                     "telefone": "99999-9999",
                     "matriculas": [
                       {
                         "CodigoMatricula": "123456",
                         "nomeCurso": "matematica",
                         "dataInicio": "2080-02-10"
                       }
                     ]
                   }
                """;

        when(discenteService.salvar(any())).thenReturn(discenteResponse);

        mockMvc.perform(post("/aluno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void criar_dadosInvalidos_retorna400() throws Exception {
        DiscenteRequest invalido = new DiscenteRequest("", null, null, new ArrayList<>());

        mockMvc.perform(post("/aluno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listarMatriculas_idExistente_retornaMatriculas200() throws Exception {
        List<MatriculaDto> matriculas = List.of(new MatriculaDto("MAT001", "Java", LocalDate.of(2023, 1, 1)));
        when(discenteService.listarmatriculas(1L)).thenReturn(matriculas);

        mockMvc.perform(get("/aluno/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].CodigoMatricula").value("MAT001"));
    }

    @Test
    void listarMatriculas_idInexistente_retorna404() throws Exception {
        when(discenteService.listarmatriculas(999L)).thenThrow(new EntityNotFoundException());

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(get("/aluno/999"))
                    .andExpect(status().isNotFound());

        });
    }


    @Test
    void atualizar_idExistente_dadosValidos_retorna200() throws Exception {

        String json = """
                {
                     "nome": "João",
                     "dataNascimento": "1990-01-01",
                     "telefone": "99999-9999",
                     "matriculas": [
                       {
                         "CodigoMatricula": "123456",
                         "nomeCurso": "matematica",
                         "dataInicio": "2080-02-10"
                       }
                     ]
                   }
                """;
        when(discenteService.atualizar(eq(1L), any())).thenReturn(discenteResponse);

        mockMvc.perform(put("/aluno/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void atualizar_idInexistente_retorna404() throws Exception {

        String json = """
                {
                     "nome": "naoExistente",
                     "dataNascimento": "3026-02-10",
                     "telefone": "00000-9999",
                     "matriculas": [
                       {
                         "CodigoMatricula": "naoExistente",
                         "nomeCurso": "naoExistente",
                         "dataInicio": "3026-02-10"
                       }
                     ]
                   }
                """;


        when(discenteService.atualizar(eq(999L), any())).thenThrow(new EntityNotFoundException());

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(put("/aluno/999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isNotFound());
        });

    }

    @Test
    void remover_idExistente_remocaoComSucesso204() throws Exception {
        mockMvc.perform(delete("/aluno/1"))
                .andExpect(status().isNoContent());

        verify(discenteService).remover(1L);
    }

    @Test
    void remover_idInexistente_retorna404() throws Exception {
        doThrow(new EntityNotFoundException()).when(discenteService).remover(999L);

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(delete("/aluno/999")).andExpect(status().isNotFound());

        });
    }


}