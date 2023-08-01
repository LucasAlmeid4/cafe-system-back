package io.github.lucasalmeida.cafeconnection.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.github.lucasalmeida.cafeconnection.dto.DataProximoCoffeeDTO;
import io.github.lucasalmeida.cafeconnection.exception.DataProximoCoffeeDuplicadaException;
import io.github.lucasalmeida.cafeconnection.exception.DataProximoCoffeeFormatException;
import io.github.lucasalmeida.cafeconnection.exception.ErrorResponse;
import io.github.lucasalmeida.cafeconnection.service.datacoffe.IDataProximoCoffeeService;

public class DataProximoCoffeeControllerTest {

    @Mock
    private IDataProximoCoffeeService dataProximoCoffeeService;

    @InjectMocks
    private DataProximoCoffeeController dataProximoCoffeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarData_ComSucesso() {

        DataProximoCoffeeDTO dataDTO = new DataProximoCoffeeDTO();
        dataDTO.setDate("20/12/2023"); // Data válida

        ResponseEntity<DataProximoCoffeeDTO> responseEntity = new ResponseEntity<>(dataDTO, HttpStatus.CREATED);
        when(dataProximoCoffeeService.cadastrarData(dataDTO)).thenReturn(responseEntity);

        ResponseEntity<?> result = dataProximoCoffeeController.cadastrarData(dataDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertTrue(result.hasBody());
        assertTrue(result.getBody() instanceof ResponseEntity);
        ResponseEntity<?> responseBody = (ResponseEntity<?>) result.getBody();
        assertNotNull(responseBody.getBody());
        assertTrue(responseBody.getBody() instanceof DataProximoCoffeeDTO);
        DataProximoCoffeeDTO responseBodyDTO = (DataProximoCoffeeDTO) responseBody.getBody();
        assertEquals(dataDTO, responseBodyDTO);
    }

    @Test
    public void testCadastrarData_DataDuplicada() {

        DataProximoCoffeeDTO dataDTO = new DataProximoCoffeeDTO();
        dataDTO.setDate("20/12/2023"); // Data válida

        when(dataProximoCoffeeService.cadastrarData(dataDTO))
                .thenThrow(new DataProximoCoffeeDuplicadaException("Data já cadastrada"));

        ResponseEntity<?> result = dataProximoCoffeeController.cadastrarData(dataDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertTrue(result.hasBody());
        assertTrue(result.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals("Data já cadastrada", errorResponse.getMessage());
    }

    @Test
    public void testCadastrarData_FormatInvalido() {

        DataProximoCoffeeDTO dataDTO = new DataProximoCoffeeDTO();
        dataDTO.setDate("2023/12/20"); // Formato inválido

        when(dataProximoCoffeeService.cadastrarData(dataDTO))
                .thenThrow(new DataProximoCoffeeFormatException("Formato de data inválido"));

        ResponseEntity<?> result = dataProximoCoffeeController.cadastrarData(dataDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertTrue(result.hasBody());
        assertTrue(result.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals("Formato de data inválido", errorResponse.getMessage());
    }

    @Test
    public void testListarDatas_ComSucesso() {

        List<DataProximoCoffeeDTO> datas = new ArrayList<>();
        DataProximoCoffeeDTO data1 = new DataProximoCoffeeDTO();
        data1.setDate("20/12/2023");
        datas.add(data1);

        when(dataProximoCoffeeService.listarDatas()).thenReturn(datas);

        ResponseEntity<List<DataProximoCoffeeDTO>> result = dataProximoCoffeeController.listarDatas();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.hasBody());
        assertEquals(datas, result.getBody());
    }

    @Test
    public void testListarDatas_SemDatas() {

        List<DataProximoCoffeeDTO> datas = new ArrayList<>();

        when(dataProximoCoffeeService.listarDatas()).thenReturn(datas);

        ResponseEntity<List<DataProximoCoffeeDTO>> result = dataProximoCoffeeController.listarDatas();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.hasBody());
        assertTrue(result.getBody().isEmpty());
    }

}
