package io.github.lucasalmeida.cafeconnection.service.datacoffe;

import io.github.lucasalmeida.cafeconnection.dto.DataProximoCoffeeDTO;
import io.github.lucasalmeida.cafeconnection.exception.DataProximoCoffeeDuplicadaException;
import io.github.lucasalmeida.cafeconnection.exception.DataProximoCoffeeFormatException;
import io.github.lucasalmeida.cafeconnection.model.DataProximoCoffee;
import io.github.lucasalmeida.cafeconnection.repository.DataProximoCoffeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataProximoCoffeeService implements IDataProximoCoffeeService{
    private final DataProximoCoffeeRepository dataProximoCoffeeRepository;
    private final ModelMapper modelMapper;

    public DataProximoCoffeeService(DataProximoCoffeeRepository dataProximoCoffeeRepository, ModelMapper modelMapper) {
        this.dataProximoCoffeeRepository = dataProximoCoffeeRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<DataProximoCoffeeDTO> cadastrarData(DataProximoCoffeeDTO dataDTO) {
        String dateString = dataDTO.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate date = LocalDate.parse(dateString, formatter);

            // Verificar se a data já existe na base de dados
            if (dataProximoCoffeeRepository.existsByDate(date)) {
                throw new DataProximoCoffeeDuplicadaException("Já há um café marcado para essa data");
            }

            // Verificar se a data é válida (ou seja, se é uma data futura em relação à data atual)
            LocalDate currentDate = LocalDate.now();
            if (date.isBefore(currentDate)) {
                throw new DataProximoCoffeeFormatException("Não é possível cadastrar uma data que já passou");
            }

            DataProximoCoffee dataProximoCoffee = new DataProximoCoffee();
            dataProximoCoffee.setDate(date);

            dataProximoCoffee = dataProximoCoffeeRepository.save(dataProximoCoffee);

            DataProximoCoffeeDTO responseDTO = modelMapper.map(dataProximoCoffee, DataProximoCoffeeDTO.class);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (DateTimeParseException e) {
            throw new DataProximoCoffeeFormatException("Data deve estar no formato dd/MM/yyyy");
        }
    }

    public List<DataProximoCoffeeDTO> listarDatas() {
        List<DataProximoCoffee> datas = dataProximoCoffeeRepository.findAll();

        // Converter as datas para o formato "dd/MM/yyyy"
        List<DataProximoCoffeeDTO> datasDTO = datas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return datasDTO;
    }

    private DataProximoCoffeeDTO convertToDTO(DataProximoCoffee data) {
        DataProximoCoffeeDTO dataDTO = new DataProximoCoffeeDTO();
        dataDTO.setDate(data.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return dataDTO;
    }
}

