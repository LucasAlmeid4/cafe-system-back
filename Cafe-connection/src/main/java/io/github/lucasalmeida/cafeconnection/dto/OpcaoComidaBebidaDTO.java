package io.github.lucasalmeida.cafeconnection.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OpcaoComidaBebidaDTO {
    @NotBlank
    private String descricao;
}
