package io.github.lucasalmeida.cafeconnection.service.datacoffe;

import io.github.lucasalmeida.cafeconnection.dto.DataProximoCoffeeDTO;
import io.github.lucasalmeida.cafeconnection.exception.DataProximoCoffeeDuplicadaException;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDataProximoCoffeeService {
    ResponseEntity<DataProximoCoffeeDTO> cadastrarData(DataProximoCoffeeDTO dataDTO) throws DataProximoCoffeeDuplicadaException;

    List<DataProximoCoffeeDTO> listarDatas();
}
