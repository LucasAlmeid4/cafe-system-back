package io.github.lucasalmeida.cafeconnection.controller;

import io.github.lucasalmeida.cafeconnection.dto.ParticipanteDTO;
import io.github.lucasalmeida.cafeconnection.exception.OpcaoJaSelecionadaException;
import io.github.lucasalmeida.cafeconnection.model.Participante;
import io.github.lucasalmeida.cafeconnection.service.relacionamentos.IParticipanteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ParticipanteControllerTest {

    private ParticipanteController participanteController;
    private IParticipanteService participanteService;

    @BeforeEach
    public void setUp() {
        participanteService = mock(IParticipanteService.class);
        participanteController = new ParticipanteController(participanteService);
    }

    @Test
    public void testCadastrarParticipante_ComOpcaoJaSelecionada() {

        ParticipanteDTO participanteDTO = new ParticipanteDTO(1L, 2L, 3L); // Valores dos IDs dos campos

        when(participanteService.cadastrarParticipante(participanteDTO))
                .thenThrow(new OpcaoJaSelecionadaException("Opção já selecionada por outro colaborador para esta data"));

        ResponseEntity<?> result = participanteController.cadastrarParticipante(participanteDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertTrue(result.hasBody());
        assertTrue(result.getBody() instanceof String);
        String mensagemErro = (String) result.getBody();
        assertEquals("Opção já selecionada por outro colaborador para esta data", mensagemErro);

        verify(participanteService, times(1)).cadastrarParticipante(participanteDTO);
    }

    @Test
    public void testListarParticipantesPorData_ComDataValida() {

        String data = "2023-07-31";

        when(participanteService.listarParticipantesPorData(data)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Participante>> result = participanteController.listarParticipantesPorData(data);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.hasBody());
        List<Participante> participantesRetornados = result.getBody();
        assertTrue(participantesRetornados.isEmpty());

        verify(participanteService, times(1)).listarParticipantesPorData(data);
    }
}
