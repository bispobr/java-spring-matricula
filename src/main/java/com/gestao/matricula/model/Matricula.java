package com.gestao.matricula.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String CodigoMatricula;
    private String nomeCurso;
    private LocalDate dataInicio;

    @ManyToOne
    @JoinColumn(name = "discente_id")
    private Discente discente;
}
