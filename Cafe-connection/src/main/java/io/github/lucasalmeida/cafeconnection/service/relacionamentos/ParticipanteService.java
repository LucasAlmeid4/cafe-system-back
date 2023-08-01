package io.github.lucasalmeida.cafeconnection.service.relacionamentos;

import io.github.lucasalmeida.cafeconnection.dto.ParticipanteDTO;
import io.github.lucasalmeida.cafeconnection.exception.NenhumParicipanteException;
import io.github.lucasalmeida.cafeconnection.exception.OpcaoJaSelecionadaException;
import io.github.lucasalmeida.cafeconnection.model.DataProximoCoffee;
import io.github.lucasalmeida.cafeconnection.model.OpcaoComidaBebida;
import io.github.lucasalmeida.cafeconnection.model.Participante;
import io.github.lucasalmeida.cafeconnection.model.Usuario;
import io.github.lucasalmeida.cafeconnection.repository.DataProximoCoffeeRepository;
import io.github.lucasalmeida.cafeconnection.repository.OpcaoComidaBebidaRepository;
import io.github.lucasalmeida.cafeconnection.repository.ParticipanteRepository;
import io.github.lucasalmeida.cafeconnection.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ParticipanteService implements IParticipanteService{
    private final ParticipanteRepository participanteRepository;
    private final UsuarioRepository usuarioRepository;
    private final OpcaoComidaBebidaRepository opcaoComidaBebidaRepository;
    private final DataProximoCoffeeRepository dataProximoCoffeeRepository;

    public ParticipanteService(ParticipanteRepository participanteRepository, UsuarioRepository usuarioRepository, OpcaoComidaBebidaRepository opcaoComidaBebidaRepository, DataProximoCoffeeRepository dataProximoCoffeeRepository) {
        this.participanteRepository = participanteRepository;
        this.usuarioRepository = usuarioRepository;
        this.opcaoComidaBebidaRepository = opcaoComidaBebidaRepository;
        this.dataProximoCoffeeRepository = dataProximoCoffeeRepository;
    }

    public Participante cadastrarParticipante(ParticipanteDTO participanteDTO) {
        Usuario usuario = usuarioRepository.findById(participanteDTO.getUsuario_id()).orElseThrow();
        OpcaoComidaBebida opcao = opcaoComidaBebidaRepository.findById(participanteDTO.getOpcao_id()).orElseThrow();
        DataProximoCoffee data = dataProximoCoffeeRepository.findById(participanteDTO.getData_id()).orElseThrow();

        if (opcaoJaEscolhidaParaData(opcao, data)) {
            throw new OpcaoJaSelecionadaException("Opção já selecionada por outro colaborador.");
        }

        return participanteRepository.save(new Participante(usuario, opcao, data));
    }

    private boolean opcaoJaEscolhidaParaData(OpcaoComidaBebida opcao, DataProximoCoffee data) {
        return participanteRepository.existsByOpcaoAndData(opcao, data);
    }


    public List<Participante> listarParticipantesPorData(String data) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate formattedDate = LocalDate.parse(data, formatter);

        return participanteRepository.findByData_Date(formattedDate);
    }



}
