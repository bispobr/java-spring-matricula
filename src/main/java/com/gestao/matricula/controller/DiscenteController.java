package com.gestao.matricula.controller;

import com.gestao.matricula.dto.DiscenteRequest;
import com.gestao.matricula.dto.DiscenteResponse;
import com.gestao.matricula.dto.MatriculaDto;
import com.gestao.matricula.service.DiscenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/aluno")
public class DiscenteController {

    @Autowired
    DiscenteService discenteService;

    @GetMapping("/alunos")
    @Operation(description = "Endpoint responsável Listar todos os alunos")
    @ApiResponse(responseCode = "200", description = "Listagem de alunos bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<List<DiscenteResponse>> listarTodosAlunos(){
        return ResponseEntity.ok().body(discenteService.listarTodosAlunos());
    }

    @PostMapping
    @Operation(description = "Endpoint responsável por cadastrar novo alunos")
    @ApiResponse(responseCode = "201", description = "entidade aluno criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<DiscenteResponse> criar (@Valid @RequestBody DiscenteRequest request){
        log.info("requisição de criação de entidade recebida");
        return ResponseEntity.status(HttpStatus.CREATED).body(discenteService.salvar(request));
    }

    @GetMapping("/{id}")
    @Operation(description = "Endpoint responsável por listar matriculas vinculadas a um aluno identificado pelo id")
    @ApiResponse(responseCode = "200", description = "Listagem de matriculas bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "404", description = "id não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<List<MatriculaDto>> listarMatriculas(@PathVariable Long id){
        log.info("requisição de listagem de matricula recebida");
        return ResponseEntity.ok().body(discenteService.listarmatriculas(id));
    }

    @PutMapping("/{id}")
    @Operation(description = "Endpoint responsável por atualizar dados da entidade aluno ")
    @ApiResponse(responseCode = "200", description = "Alualização bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "404", description = "id não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<DiscenteResponse> atualizar (@PathVariable Long id, @Valid @RequestBody DiscenteRequest request ){
        log.info("requisição de atualização de entidade recebida");
        return ResponseEntity.ok(discenteService.atualizar(id,request));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Endpoint responsável por remover aluno cadastrado ")
    @ApiResponse(responseCode = "204", description = "Remoção bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "404", description = "id não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<Void> remover (@PathVariable Long id){
        log.info("requisição de remoção de entidade recebida");
        discenteService.remover(id);
        return ResponseEntity.noContent().build();
    }

}
