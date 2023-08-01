package io.github.lucasalmeida.cafeconnection.service.opcaocomida;

import io.github.lucasalmeida.cafeconnection.dto.OpcaoComidaBebidaDTO;
import io.github.lucasalmeida.cafeconnection.exception.OpcaoComidaBebidaDuplicadaException;

import java.util.List;

public interface IOpcaoComidaBebidaService {
    OpcaoComidaBebidaDTO cadastrarOpcao(OpcaoComidaBebidaDTO opcaoComidaBebidaDTO) throws OpcaoComidaBebidaDuplicadaException;

    List<OpcaoComidaBebidaDTO> listarOpcoesComidaBebida();
}
