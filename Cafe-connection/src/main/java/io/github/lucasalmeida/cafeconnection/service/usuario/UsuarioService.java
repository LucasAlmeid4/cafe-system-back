package io.github.lucasalmeida.cafeconnection.service.usuario;

import io.github.lucasalmeida.cafeconnection.dto.UsuarioDTO;
import io.github.lucasalmeida.cafeconnection.exception.CpfDuplicadoException;
import io.github.lucasalmeida.cafeconnection.model.Usuario;
import io.github.lucasalmeida.cafeconnection.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsuarioService implements IUsuarioService{
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    public UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) {
        String cpf = usuarioDTO.getCpf();
        if (cpf == null || cpf.length() != 11) {
            if (cpf == null) {
                throw new IllegalArgumentException("CPF deve ser informado");
            } else if (cpf.length() < 11) {
                throw new IllegalArgumentException("CPF deve ter exatamente 11 caracteres (CPF atual possui " + cpf.length() + " caracteres)");
            } else {
                throw new IllegalArgumentException("CPF deve ter exatamente 11 caracteres (CPF atual possui " + cpf.length() + " caracteres)");
            }
        }

        // Verificar se o CPF já existe na base de dados
        if (usuarioRepository.existsByCpf(cpf)) {
            throw new CpfDuplicadoException("CPF já cadastrado");
        }

        // Cria uma instância de Usuario e define os atributos com base na DTO usando o ModelMapper
        Usuario novoUsuario = modelMapper.map(usuarioDTO, Usuario.class);

        // Salva o novo usuário no banco de dados
        novoUsuario = usuarioRepository.save(novoUsuario);

        // Retorne a DTO mapeada para a classe de domínio usando o ModelMapper
        return modelMapper.map(novoUsuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> listarUsuarios() {
        Iterable<Usuario> usuarios = usuarioRepository.findAll();
        return StreamSupport.stream(usuarios.spliterator(), false)
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }
}
