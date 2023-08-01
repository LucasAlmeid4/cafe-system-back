package io.github.lucasalmeida.cafeconnection.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.lucasalmeida.cafeconnection.dto.OpcaoComidaBebidaDTO;
import io.github.lucasalmeida.cafeconnection.exception.ErrorResponse;
import io.github.lucasalmeida.cafeconnection.exception.OpcaoComidaBebidaDuplicadaException;
import io.github.lucasalmeida.cafeconnection.service.opcaocomida.IOpcaoComidaBebidaService;

public class OpcaoComidaBebidaControllerTest {

    @Mock
    private IOpcaoComidaBebidaService opcaoComidaBebidaService;

    @InjectMocks
    private OpcaoComidaBebidaController opcaoComidaBebidaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarOpcaoComidaBebida_ComSucesso() {

        OpcaoComidaBebidaDTO opcaoDTO = new OpcaoComidaBebidaDTO();
        opcaoDTO.setDescricao("Bolo de Chocolate"); // Descrição da opção

        OpcaoComidaBebidaDTO novaOpcao = new OpcaoComidaBebidaDTO();
        novaOpcao.setDescricao("Bolo de Chocolate");
        when(opcaoComidaBebidaService.cadastrarOpcao(opcaoDTO)).thenReturn(novaOpcao);

        ResponseEntity<?> result = opcaoComidaBebidaController.cadastrarOpcaoComidaBebida(opcaoDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertTrue(result.hasBody());
        assertTrue(result.getBody() instanceof OpcaoComidaBebidaDTO);
        OpcaoComidaBebidaDTO responseBody = (OpcaoComidaBebidaDTO) result.getBody();
        assertEquals(novaOpcao, responseBody);
    }

    @Test
    public void testCadastrarOpcaoComidaBebida_ComOpcaoDuplicada() {

        OpcaoComidaBebidaDTO opcaoDTO = new OpcaoComidaBebidaDTO();
        opcaoDTO.setDescricao("Bolo de Chocolate"); // Descrição da opção

        when(opcaoComidaBebidaService.cadastrarOpcao(opcaoDTO)).thenThrow(new OpcaoComidaBebidaDuplicadaException("Opção de comida/bebida já cadastrada"));

        ResponseEntity<?> result = opcaoComidaBebidaController.cadastrarOpcaoComidaBebida(opcaoDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertTrue(result.hasBody());
        assertTrue(result.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals("Opção de comida/bebida já cadastrada", errorResponse.getMessage());
    }

    @Test
    public void testListarOpcoesComidaBebida() {

        List<OpcaoComidaBebidaDTO> listaOpcoes = new ArrayList<>();
        OpcaoComidaBebidaDTO opcaoDTO1 = new OpcaoComidaBebidaDTO();
        opcaoDTO1.setDescricao("Bolo de Chocolate");
        listaOpcoes.add(opcaoDTO1);

        OpcaoComidaBebidaDTO opcaoDTO2 = new OpcaoComidaBebidaDTO();
        opcaoDTO2.setDescricao("Café com Leite");
        listaOpcoes.add(opcaoDTO2);

        when(opcaoComidaBebidaService.listarOpcoesComidaBebida()).thenReturn(listaOpcoes);

        ResponseEntity<List<OpcaoComidaBebidaDTO>> result = opcaoComidaBebidaController.listarOpcoesComidaBebida();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.hasBody());
        assertEquals(listaOpcoes, result.getBody());
    }
}
