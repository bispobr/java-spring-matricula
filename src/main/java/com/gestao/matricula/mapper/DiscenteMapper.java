package com.gestao.matricula.mapper;

import com.gestao.matricula.dto.DiscenteRequest;
import com.gestao.matricula.dto.DiscenteResponse;
import com.gestao.matricula.dto.MatriculaDto;
import com.gestao.matricula.model.Discente;
import com.gestao.matricula.model.Matricula;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiscenteMapper {

    public Discente toEntity(DiscenteRequest dto){
        Discente discente = new Discente();
        discente.setNome(dto.nome());
        discente.setTelefone(dto.telefone());
        discente.setDataNascimento(dto.dataNascimento());

        List<Matricula> matriculas = dto.matriculas().stream().map(m->{
            Matricula matricula = new Matricula();
            matricula.setCodigoMatricula(m.CodigoMatricula());
            matricula.setDataInicio(m.dataInicio());
            matricula.setNomeCurso(m.nomeCurso());
            matricula.setDiscente(discente);
            return matricula;

        }).toList();

        discente.setMatriculas(matriculas);
        return discente;
    }



    public DiscenteResponse toResponse(Discente discente){

        List<MatriculaDto>matriculaDtos = discente.getMatriculas().stream().map(matricula ->
                new MatriculaDto(matricula.getCodigoMatricula(), matricula.getNomeCurso(), matricula.getDataInicio())).toList();
        return new DiscenteResponse(discente.getId(), discente.getNome(), discente.getTelefone(), discente.getDataNascimento(), matriculaDtos);
    }

    public List<DiscenteResponse> paraResponseList(List<Discente> lista){
        return lista.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

    }
}
