package com.gestao.matricula.service;

import com.gestao.matricula.dto.DiscenteRequest;
import com.gestao.matricula.dto.DiscenteResponse;
import com.gestao.matricula.dto.MatriculaDto;
import com.gestao.matricula.mapper.DiscenteMapper;
import com.gestao.matricula.model.Discente;
import com.gestao.matricula.model.Matricula;
import com.gestao.matricula.repository.DiscenteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DiscenteService {

    @Autowired
    DiscenteRepository discenteRepository;

    @Autowired
    DiscenteMapper discenteMapper;

    public DiscenteResponse salvar (DiscenteRequest dto){
        Discente discente = discenteMapper.toEntity(dto);
        discenteRepository.save(discente);
        log.info("Aluno salvo com sucesso");
        return discenteMapper.toResponse(discente); }

    public List<DiscenteResponse> listarTodosAlunos (){
        log.info("Listando todos os alunos");
        return discenteMapper.paraResponseList(discenteRepository.findAll());
    }



    public List<MatriculaDto> listarmatriculas (Long id){
        Discente discente = discenteRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        log.info("Listado matriculas");
        return discente.getMatriculas().stream().map(m -> new MatriculaDto(m.getCodigoMatricula(), m.getNomeCurso(),m.getDataInicio())).toList();
    }

    @Transactional
    public DiscenteResponse atualizar (Long id, DiscenteRequest dto){
        Discente discente = discenteRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        discente.setNome(dto.nome());
        discente.setTelefone(dto.telefone());
        discente.setDataNascimento(dto.dataNascimento());
        discente.getMatriculas().clear();

        for (MatriculaDto m : dto.matriculas()){
            Matricula matricula = new Matricula();
            matricula.setCodigoMatricula(m.CodigoMatricula());
            matricula.setDataInicio(m.dataInicio());
            matricula.setNomeCurso(m.nomeCurso());
            matricula.setDiscente(discente);
            discente.getMatriculas().add(matricula);


        }
        log.info("aluno atualizado");
        return discenteMapper.toResponse(discenteRepository.save(discente));
    }


    @Transactional
    public void remover (Long id){
        discenteRepository.delete(discenteRepository.findById(id).orElseThrow(EntityNotFoundException::new));

    }




}
