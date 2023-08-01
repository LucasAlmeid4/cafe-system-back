package io.github.lucasalmeida.cafeconnection.controller;

import io.github.lucasalmeida.cafeconnection.service.datacoffe.IDataProximoCoffeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.github.lucasalmeida.cafeconnection.dto.DataProximoCoffeeDTO;
import io.github.lucasalmeida.cafeconnection.exception.DataProximoCoffeeDuplicadaException;
import io.github.lucasalmeida.cafeconnection.exception.DataProximoCoffeeFormatException;
import io.github.lucasalmeida.cafeconnection.exception.ErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/api/datas")
public class DataProximoCoffeeController {

    @Autowired
    private IDataProximoCoffeeService dataProximoCoffeeService;

    @PostMapping("/datas-proximo-coffee")
    public ResponseEntity<?> cadastrarData(@RequestBody DataProximoCoffeeDTO dataDTO) {
        try {
            ResponseEntity<DataProximoCoffeeDTO> novaData = dataProximoCoffeeService.cadastrarData(dataDTO);
            return new ResponseEntity<>(novaData, HttpStatus.CREATED);
        } catch (DataProximoCoffeeDuplicadaException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (DataProximoCoffeeFormatException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<DataProximoCoffeeDTO>> listarDatas() {
        List<DataProximoCoffeeDTO> datas = dataProximoCoffeeService.listarDatas();
        return new ResponseEntity<>(datas, HttpStatus.OK);
    }
}