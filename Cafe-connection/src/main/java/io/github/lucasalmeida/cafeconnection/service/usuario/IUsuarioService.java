package io.github.lucasalmeida.cafeconnection.service.usuario;

import io.github.lucasalmeida.cafeconnection.dto.UsuarioDTO;
import io.github.lucasalmeida.cafeconnection.exception.CpfDuplicadoException;

import java.util.List;

public interface IUsuarioService {
    UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) throws CpfDuplicadoException;

    List<UsuarioDTO> listarUsuarios();
}
