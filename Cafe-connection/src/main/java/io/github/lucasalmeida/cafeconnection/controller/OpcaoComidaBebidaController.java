package io.github.lucasalmeida.cafeconnection.controller;

import io.github.lucasalmeida.cafeconnection.dto.OpcaoComidaBebidaDTO;
import io.github.lucasalmeida.cafeconnection.exception.ErrorResponse;
import io.github.lucasalmeida.cafeconnection.exception.OpcaoComidaBebidaDuplicadaException;
import io.github.lucasalmeida.cafeconnection.service.opcaocomida.IOpcaoComidaBebidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opcoes")
public class OpcaoComidaBebidaController {

    private final IOpcaoComidaBebidaService opcaoComidaBebidaService;

    @Autowired
    public OpcaoComidaBebidaController(IOpcaoComidaBebidaService opcaoComidaBebidaService) {
        this.opcaoComidaBebidaService = opcaoComidaBebidaService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarOpcaoComidaBebida(@RequestBody OpcaoComidaBebidaDTO opcaoComidaBebidaDTO) {
        try {
            OpcaoComidaBebidaDTO novaOpcao = opcaoComidaBebidaService.cadastrarOpcao(opcaoComidaBebidaDTO);
            return new ResponseEntity<>(novaOpcao, HttpStatus.CREATED);
        } catch (OpcaoComidaBebidaDuplicadaException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<OpcaoComidaBebidaDTO>> listarOpcoesComidaBebida() {
        List<OpcaoComidaBebidaDTO> listaOpcoes = opcaoComidaBebidaService.listarOpcoesComidaBebida();
        return new ResponseEntity<>(listaOpcoes, HttpStatus.OK);
    }
}
