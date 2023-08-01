package io.github.lucasalmeida.cafeconnection.controller;

import io.github.lucasalmeida.cafeconnection.exception.CpfDuplicadoException;
import io.github.lucasalmeida.cafeconnection.exception.ErrorResponse;
import io.github.lucasalmeida.cafeconnection.model.Usuario;
import io.github.lucasalmeida.cafeconnection.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @PersistenceContext
    private EntityManager entityManager;

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private boolean isValidCpf(String cpf) {
        return cpf != null && cpf.length() == 11;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid Usuario usuario) {
        try {
            if (!isValidCpf(usuario.getCpf())) {
                throw new IllegalArgumentException("O CPF deve ter exatamente 11 dígitos");
            }

            if (usuarioRepository.existsByCpf(usuario.getCpf())) {
                throw new CpfDuplicadoException("CPF já cadastrado");
            }

            String insertSql = "INSERT INTO usuario (nome, cpf) VALUES (?, ?)";
            entityManager.createNativeQuery(insertSql)
                    .setParameter(1, usuario.getNome())
                    .setParameter(2, usuario.getCpf())
                    .executeUpdate();

            return new ResponseEntity<>(usuario, HttpStatus.CREATED);
        } catch (CpfDuplicadoException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid Usuario usuario) {
        try {
            if (!isValidCpf(usuario.getCpf())) {
                throw new IllegalArgumentException("O CPF deve ter exatamente 11 dígitos");
            }

            String updateSql = "UPDATE usuario SET nome = ?, cpf = ? WHERE id = ?";
            int rowsAffected = entityManager.createNativeQuery(updateSql)
                    .setParameter(1, usuario.getNome())
                    .setParameter(2, usuario.getCpf())
                    .setParameter(3, id)
                    .executeUpdate();

            if (rowsAffected == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirUsuario(@PathVariable Long id) {
        try {
            String deleteSql = "DELETE FROM usuario WHERE id = ?";
            int rowsAffected = entityManager.createNativeQuery(deleteSql)
                    .setParameter(1, id)
                    .executeUpdate();

            if (rowsAffected == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        String selectSql = "SELECT * FROM usuario";
        List<Usuario> usuarios = entityManager.createNativeQuery(selectSql, Usuario.class).getResultList();

        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}
