package io.github.lucasalmeida.cafeconnection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    @NotBlank
    private String nome;

    @NotNull
    @Size(min = 11, max = 11, message = "O CPF deve ter exatamente 11 dígitos")
    @CPF(message = "CPF inválido")
    private String cpf;


}

