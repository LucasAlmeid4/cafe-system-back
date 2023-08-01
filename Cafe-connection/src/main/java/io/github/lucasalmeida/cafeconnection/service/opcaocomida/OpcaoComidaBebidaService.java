package io.github.lucasalmeida.cafeconnection.service.opcaocomida;

import io.github.lucasalmeida.cafeconnection.dto.OpcaoComidaBebidaDTO;
import io.github.lucasalmeida.cafeconnection.exception.OpcaoComidaBebidaDuplicadaException;
import io.github.lucasalmeida.cafeconnection.exception.OpcaoJaCadastradaException;
import io.github.lucasalmeida.cafeconnection.model.OpcaoComidaBebida;
import io.github.lucasalmeida.cafeconnection.repository.OpcaoComidaBebidaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpcaoComidaBebidaService implements IOpcaoComidaBebidaService{
    private final OpcaoComidaBebidaRepository opcaoComidaBebidaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OpcaoComidaBebidaService(OpcaoComidaBebidaRepository opcaoComidaBebidaRepository, ModelMapper modelMapper) {
        this.opcaoComidaBebidaRepository = opcaoComidaBebidaRepository;
        this.modelMapper = modelMapper;
    }

    public OpcaoComidaBebidaDTO cadastrarOpcao(OpcaoComidaBebidaDTO opcaoComidaBebidaDTO) {
        String descricao = opcaoComidaBebidaDTO.getDescricao();

        // Verificar se a descrição da opção já existe na base de dados
        if (opcaoComidaBebidaRepository.existsByDescricao(descricao)) {
            throw new OpcaoComidaBebidaDuplicadaException("Opção de comida/bebida já cadastrada");
        }

        // Mapear o DTO para a entidade de domínio
        OpcaoComidaBebida novaOpcao = modelMapper.map(opcaoComidaBebidaDTO, OpcaoComidaBebida.class);

        // Salvar a entidade de domínio no banco de dados
        novaOpcao = opcaoComidaBebidaRepository.save(novaOpcao);

        // Retornar o DTO mapeado a partir da entidade de domínio
        return modelMapper.map(novaOpcao, OpcaoComidaBebidaDTO.class);
    }

    public List<OpcaoComidaBebidaDTO> listarOpcoesComidaBebida() {
        List<OpcaoComidaBebida> listaOpcoes = opcaoComidaBebidaRepository.findAll();

        // Mapear a lista de entidades de domínio para uma lista de DTOs
        return listaOpcoes.stream()
                .map(opcao -> modelMapper.map(opcao, OpcaoComidaBebidaDTO.class))
                .collect(Collectors.toList());
    }
}

