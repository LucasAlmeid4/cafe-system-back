package io.github.lucasalmeida.cafeconnection.service.relacionamentos;

import io.github.lucasalmeida.cafeconnection.dto.ParticipanteDTO;
import io.github.lucasalmeida.cafeconnection.exception.OpcaoJaSelecionadaException;
import io.github.lucasalmeida.cafeconnection.model.Participante;

import java.util.List;

public interface IParticipanteService {
    Participante cadastrarParticipante(ParticipanteDTO participanteDTO) throws OpcaoJaSelecionadaException;

    List<Participante> listarParticipantesPorData(String data);

}
