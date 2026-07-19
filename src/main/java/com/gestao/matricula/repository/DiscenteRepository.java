package com.gestao.matricula.repository;

import com.gestao.matricula.model.Discente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DiscenteRepository extends JpaRepository<Discente, Long> {
}
