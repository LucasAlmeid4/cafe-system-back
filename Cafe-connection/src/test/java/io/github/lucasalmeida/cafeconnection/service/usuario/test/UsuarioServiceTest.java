package io.github.lucasalmeida.cafeconnection.service.usuario.test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import io.github.lucasalmeida.cafeconnection.dto.UsuarioDTO;
import io.github.lucasalmeida.cafeconnection.exception.CpfDuplicadoException;
import io.github.lucasalmeida.cafeconnection.model.Usuario;
import io.github.lucasalmeida.cafeconnection.repository.UsuarioRepository;
import io.github.lucasalmeida.cafeconnection.service.usuario.UsuarioService;

import java.util.ArrayList;
import java.util.List;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarUsuario_ComSucesso() {

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678901"); // CPF válido

        Usuario novoUsuario = new Usuario();
        novoUsuario.setCpf("12345678901");
        when(usuarioRepository.existsByCpf("12345678901")).thenReturn(false); // Simula que o CPF não está duplicado
        when(modelMapper.map(usuarioDTO, Usuario.class)).thenReturn(novoUsuario);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(new Usuario());
        when(modelMapper.map(any(Usuario.class), eq(UsuarioDTO.class))).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.cadastrarUsuario(usuarioDTO);

        assertNotNull(result);
    }

    @Test
    public void testCadastrarUsuario_CpfDuplicadoException() {

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678901"); // CPF válido

        when(usuarioRepository.existsByCpf("12345678901")).thenReturn(true); // Simula que o CPF está duplicado

        assertThrows(CpfDuplicadoException.class, () -> usuarioService.cadastrarUsuario(usuarioDTO));
    }

    @Test
    public void testCadastrarUsuario_CpfMenorQue11Caracteres() {

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("1234567890"); // CPF inválido, menos de 11 caracteres

        assertThrows(IllegalArgumentException.class, () -> usuarioService.cadastrarUsuario(usuarioDTO));
    }

    @Test
    public void testCadastrarUsuario_CpfNulo() {

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf(null); // CPF nulo

        assertThrows(IllegalArgumentException.class, () -> usuarioService.cadastrarUsuario(usuarioDTO));
    }

    @Test
    public void testListarUsuarios_UsuariosExistentes() {

        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario1 = new Usuario();
        usuario1.setCpf("12345678901");
        usuarios.add(usuario1);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        UsuarioDTO usuarioDTO1 = new UsuarioDTO();
        usuarioDTO1.setCpf("12345678901");
        when(modelMapper.map(usuario1, UsuarioDTO.class)).thenReturn(usuarioDTO1);

        List<UsuarioDTO> result = usuarioService.listarUsuarios();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testListarUsuarios_SemUsuarios() {

        List<Usuario> usuarios = new ArrayList<>();

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioDTO> result = usuarioService.listarUsuarios();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}
