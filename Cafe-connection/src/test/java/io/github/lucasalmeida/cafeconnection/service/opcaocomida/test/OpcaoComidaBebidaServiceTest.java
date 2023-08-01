package io.github.lucasalmeida.cafeconnection.service.opcaocomida.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import io.github.lucasalmeida.cafeconnection.dto.OpcaoComidaBebidaDTO;
import io.github.lucasalmeida.cafeconnection.exception.OpcaoComidaBebidaDuplicadaException;
import io.github.lucasalmeida.cafeconnection.model.OpcaoComidaBebida;
import io.github.lucasalmeida.cafeconnection.repository.OpcaoComidaBebidaRepository;
import io.github.lucasalmeida.cafeconnection.service.opcaocomida.OpcaoComidaBebidaService;

public class OpcaoComidaBebidaServiceTest {

    @Mock
    private OpcaoComidaBebidaRepository opcaoComidaBebidaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OpcaoComidaBebidaService opcaoComidaBebidaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarOpcao_ComSucesso() {

        OpcaoComidaBebidaDTO opcaoDTO = new OpcaoComidaBebidaDTO();
        opcaoDTO.setDescricao("Bolo de Chocolate"); // Descrição válida

        OpcaoComidaBebida novaOpcao = new OpcaoComidaBebida();
        novaOpcao.setDescricao("Bolo de Chocolate");
        when(opcaoComidaBebidaRepository.existsByDescricao("Bolo de Chocolate")).thenReturn(false); // Simula que a opção não está duplicada
        when(modelMapper.map(opcaoDTO, OpcaoComidaBebida.class)).thenReturn(novaOpcao);
        when(opcaoComidaBebidaRepository.save(any(OpcaoComidaBebida.class))).thenReturn(new OpcaoComidaBebida());
        when(modelMapper.map(any(OpcaoComidaBebida.class), eq(OpcaoComidaBebidaDTO.class))).thenReturn(opcaoDTO);

        OpcaoComidaBebidaDTO result = opcaoComidaBebidaService.cadastrarOpcao(opcaoDTO);

        assertNotNull(result);
    }

    @Test
    public void testCadastrarOpcao_OpcaoComidaBebidaDuplicadaException() {

        OpcaoComidaBebidaDTO opcaoDTO = new OpcaoComidaBebidaDTO();
        opcaoDTO.setDescricao("Bolo de Chocolate"); // Descrição válida

        when(opcaoComidaBebidaRepository.existsByDescricao("Bolo de Chocolate")).thenReturn(true); // Simula que a opção está duplicada

        assertThrows(OpcaoComidaBebidaDuplicadaException.class, () -> opcaoComidaBebidaService.cadastrarOpcao(opcaoDTO));
    }

    @Test
    public void testListarOpcoesComidaBebida() {

        List<OpcaoComidaBebida> listaOpcoes = new ArrayList<>();
        OpcaoComidaBebida opcao1 = new OpcaoComidaBebida();
        opcao1.setDescricao("Bolo de Chocolate");
        listaOpcoes.add(opcao1);

        when(opcaoComidaBebidaRepository.findAll()).thenReturn(listaOpcoes);

        OpcaoComidaBebidaDTO opcaoDTO1 = new OpcaoComidaBebidaDTO();
        opcaoDTO1.setDescricao("Bolo de Chocolate");
        when(modelMapper.map(opcao1, OpcaoComidaBebidaDTO.class)).thenReturn(opcaoDTO1);

        List<OpcaoComidaBebidaDTO> result = opcaoComidaBebidaService.listarOpcoesComidaBebida();

        assertNotNull(result);
    }

}
