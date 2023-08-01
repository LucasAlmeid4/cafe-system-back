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

import io.github.lucasalmeida.cafeconnection.controller.UsuarioController;
import io.github.lucasalmeida.cafeconnection.dto.UsuarioDTO;
import io.github.lucasalmeida.cafeconnection.exception.CpfDuplicadoException;
import io.github.lucasalmeida.cafeconnection.exception.ErrorResponse;
import io.github.lucasalmeida.cafeconnection.service.usuario.IUsuarioService;

public class UsuarioControllerTest {

    @Mock
    private IUsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   /* @Test
    public void testCadastrarUsuario_ComSucesso() {
        // Cenário de teste: cadastro de usuário com sucesso

        // Dados simulados para o DTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678901"); // CPF válido
        usuarioDTO.setNome("João da Silva");

        // Mock do serviço para retornar o novo usuário cadastrado
        UsuarioDTO novoUsuarioDTO = new UsuarioDTO();
        novoUsuarioDTO.setCpf("12345678901");
        novoUsuarioDTO.setNome("João da Silva");
        when(usuarioService.cadastrarUsuario(usuarioDTO)).thenReturn(novoUsuarioDTO);

        // Executa o método a ser testado
        ResponseEntity<?> result = usuarioController.cadastrarUsuario(usuarioDTO);

        // Verificações
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertTrue(result.hasBody());
        assertTrue(result.getBody() instanceof UsuarioDTO);
        UsuarioDTO responseBody = (UsuarioDTO) result.getBody();
        assertEquals(novoUsuarioDTO, responseBody);
    }

    @Test
    public void testCadastrarUsuario_ComCpfDuplicado() {
        // Cenário de teste: cadastro de usuário com CPF duplicado

        // Dados simulados para o DTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678901"); // CPF duplicado
        usuarioDTO.setNome("João da Silva");

        // Mock do serviço para lançar uma exceção de CPF duplicado
        when(usuarioService.cadastrarUsuario(usuarioDTO)).thenThrow(new CpfDuplicadoException("CPF já cadastrado"));

        // Executa o método a ser testado
        ResponseEntity<?> result = usuarioController.cadastrarUsuario(usuarioDTO);

        // Verificações
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertTrue(result.hasBody());
        assertTrue(result.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals("CPF já cadastrado", errorResponse.getMessage());
    }

    @Test
    public void testCadastrarUsuario_ComCpfInvalido() {
        // Cenário de teste: cadastro de usuário com CPF inválido

        // Dados simulados para o DTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("123"); // CPF inválido
        usuarioDTO.setNome("João da Silva");

        // Mock do serviço para lançar a exceção CpfDuplicadoException
        when(usuarioService.cadastrarUsuario(usuarioDTO)).thenThrow(new CpfDuplicadoException("CPF já cadastrado"));

        // Executa o método a ser testado
        ResponseEntity<?> result = usuarioController.cadastrarUsuario(usuarioDTO);

        // Verificações
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertTrue(result.hasBody());
        assertTrue(result.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals("CPF já cadastrado", errorResponse.getMessage());

        // Verifica se o serviço foi chamado corretamente
        verify(usuarioService, times(1)).cadastrarUsuario(usuarioDTO);
    }




    @Test
    public void testListarUsuarios() {
        // Cenário de teste: listagem de usuários

        // Dados simulados para a lista de DTOs
        List<UsuarioDTO> usuarios = new ArrayList<>();
        UsuarioDTO usuarioDTO1 = new UsuarioDTO();
        usuarioDTO1.setCpf("12345678901");
        usuarioDTO1.setNome("João da Silva");
        usuarios.add(usuarioDTO1);

        UsuarioDTO usuarioDTO2 = new UsuarioDTO();
        usuarioDTO2.setCpf("98765432101");
        usuarioDTO2.setNome("Maria Oliveira");
        usuarios.add(usuarioDTO2);

        // Mock do serviço para retornar a lista de usuários
        when(usuarioService.listarUsuarios()).thenReturn(usuarios);

        // Executa o método a ser testado
        ResponseEntity<List<UsuarioDTO>> result = usuarioController.listarUsuarios();

        // Verificações
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.hasBody());
        assertEquals(usuarios, result.getBody());
    }*/
}
