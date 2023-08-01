package io.github.lucasalmeida.cafeconnection.controller;

import io.github.lucasalmeida.cafeconnection.dto.ParticipanteDTO;
import io.github.lucasalmeida.cafeconnection.exception.OpcaoJaSelecionadaException;
import io.github.lucasalmeida.cafeconnection.model.Participante;
import io.github.lucasalmeida.cafeconnection.service.relacionamentos.IParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ParticipanteController {

    private final IParticipanteService participanteService;

    @Autowired
    public ParticipanteController(IParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    @PostMapping("/participantes")
    public ResponseEntity<?> cadastrarParticipante(@RequestBody ParticipanteDTO participanteDTO) {
        try {
            Participante novoParticipante = participanteService.cadastrarParticipante(participanteDTO);
            return new ResponseEntity<>(novoParticipante, HttpStatus.CREATED);
        } catch (OpcaoJaSelecionadaException ex) {
            String mensagemErro = "Opção já selecionada por outro colaborador para esta data";
            return new ResponseEntity<>(mensagemErro, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lista-de-participantes")
    public ResponseEntity<List<Participante>> listarParticipantesPorData(@RequestParam("data") String data) {
        List<Participante> listaParticipantes = participanteService.listarParticipantesPorData(data);
        return new ResponseEntity<>(listaParticipantes, HttpStatus.OK);
    }

}
