package io.github.lucasalmeida.cafeconnection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @Size(min = 11, max = 11, message = "O CPF deve ter exatamente 11 dígitos")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas números")
    private String cpf;

}

