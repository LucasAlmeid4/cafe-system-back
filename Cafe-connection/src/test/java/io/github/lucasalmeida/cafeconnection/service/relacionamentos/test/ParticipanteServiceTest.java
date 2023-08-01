package io.github.lucasalmeida.cafeconnection.service.relacionamentos.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.github.lucasalmeida.cafeconnection.dto.ParticipanteDTO;
import io.github.lucasalmeida.cafeconnection.exception.OpcaoJaSelecionadaException;
import io.github.lucasalmeida.cafeconnection.model.DataProximoCoffee;
import io.github.lucasalmeida.cafeconnection.model.OpcaoComidaBebida;
import io.github.lucasalmeida.cafeconnection.model.Participante;
import io.github.lucasalmeida.cafeconnection.model.Usuario;
import io.github.lucasalmeida.cafeconnection.repository.DataProximoCoffeeRepository;
import io.github.lucasalmeida.cafeconnection.repository.OpcaoComidaBebidaRepository;
import io.github.lucasalmeida.cafeconnection.repository.ParticipanteRepository;
import io.github.lucasalmeida.cafeconnection.repository.UsuarioRepository;
import io.github.lucasalmeida.cafeconnection.service.relacionamentos.ParticipanteService;

public class ParticipanteServiceTest {

    @Mock
    private ParticipanteRepository participanteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private OpcaoComidaBebidaRepository opcaoComidaBebidaRepository;

    @Mock
    private DataProximoCoffeeRepository dataProximoCoffeeRepository;

    @InjectMocks
    private ParticipanteService participanteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarParticipante_ComSucesso() {

        ParticipanteDTO participanteDTO = new ParticipanteDTO();
        participanteDTO.setUsuario_id(1L);
        participanteDTO.setOpcao_id(2L);
        participanteDTO.setData_id(3L);

        Usuario usuario = new Usuario();
        OpcaoComidaBebida opcao = new OpcaoComidaBebida();
        DataProximoCoffee data = new DataProximoCoffee();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(opcaoComidaBebidaRepository.findById(2L)).thenReturn(Optional.of(opcao));
        when(dataProximoCoffeeRepository.findById(3L)).thenReturn(Optional.of(data));
        when(participanteRepository.existsByOpcaoAndData(opcao, data)).thenReturn(false);
        when(participanteRepository.save(any(Participante.class))).thenReturn(new Participante());

        Participante result = participanteService.cadastrarParticipante(participanteDTO);

        assertNotNull(result);
    }

    @Test
    public void testCadastrarParticipante_OpcaoJaSelecionadaException() {

        ParticipanteDTO participanteDTO = new ParticipanteDTO();
        participanteDTO.setUsuario_id(1L);
        participanteDTO.setOpcao_id(2L);
        participanteDTO.setData_id(3L);

        Usuario usuario = new Usuario();
        OpcaoComidaBebida opcao = new OpcaoComidaBebida();
        DataProximoCoffee data = new DataProximoCoffee();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(opcaoComidaBebidaRepository.findById(2L)).thenReturn(Optional.of(opcao));
        when(dataProximoCoffeeRepository.findById(3L)).thenReturn(Optional.of(data));
        when(participanteRepository.existsByOpcaoAndData(opcao, data)).thenReturn(true); // Simula que a opção já foi selecionada

        assertThrows(OpcaoJaSelecionadaException.class, () -> participanteService.cadastrarParticipante(participanteDTO));
    }

    @Test
    public void testListarParticipantesPorData() {

        String dataString = "31-07-2023";
        LocalDate formattedDate = LocalDate.of(2023, 7, 31);

        List<Participante> participanteList = new ArrayList<>();
        participanteList.add(new Participante());

        when(participanteRepository.findByData_Date(formattedDate)).thenReturn(participanteList);

        List<Participante> result = participanteService.listarParticipantesPorData(dataString);

        assertNotNull(result);
    }

}
