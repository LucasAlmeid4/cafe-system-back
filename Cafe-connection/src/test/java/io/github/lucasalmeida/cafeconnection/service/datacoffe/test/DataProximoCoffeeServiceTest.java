package io.github.lucasalmeida.cafeconnection.service.datacoffe.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.github.lucasalmeida.cafeconnection.dto.DataProximoCoffeeDTO;
import io.github.lucasalmeida.cafeconnection.exception.DataProximoCoffeeDuplicadaException;
import io.github.lucasalmeida.cafeconnection.exception.DataProximoCoffeeFormatException;
import io.github.lucasalmeida.cafeconnection.model.DataProximoCoffee;
import io.github.lucasalmeida.cafeconnection.repository.DataProximoCoffeeRepository;
import io.github.lucasalmeida.cafeconnection.service.datacoffe.DataProximoCoffeeService;

public class DataProximoCoffeeServiceTest {

    @Mock
    private DataProximoCoffeeRepository dataProximoCoffeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DataProximoCoffeeService dataProximoCoffeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCadastrarData_DataDuplicadaException() {

        DataProximoCoffeeDTO dataDTO = new DataProximoCoffeeDTO();
        dataDTO.setDate("31/07/2023");

        LocalDate date = LocalDate.of(2023, 7, 31);
        when(dataProximoCoffeeRepository.existsByDate(date)).thenReturn(true);

        assertThrows(DataProximoCoffeeDuplicadaException.class, () -> dataProximoCoffeeService.cadastrarData(dataDTO));
    }

    @Test
    public void testCadastrarData_DataFormatException() {

        DataProximoCoffeeDTO dataDTO = new DataProximoCoffeeDTO();
        dataDTO.setDate("31-07-2023");

        assertThrows(DataProximoCoffeeFormatException.class, () -> dataProximoCoffeeService.cadastrarData(dataDTO));
    }

    @Test
    public void testListarDatas() {

        List<DataProximoCoffee> datas = new ArrayList<>();
        DataProximoCoffee data1 = new DataProximoCoffee();
        data1.setDate(LocalDate.of(2023, 7, 31));
        datas.add(data1);

        when(dataProximoCoffeeRepository.findAll()).thenReturn(datas);

        DataProximoCoffeeDTO dataDTO1 = new DataProximoCoffeeDTO();
        dataDTO1.setDate("31/07/2023");
        when(modelMapper.map(data1, DataProximoCoffeeDTO.class)).thenReturn(dataDTO1);

        List<DataProximoCoffeeDTO> result = dataProximoCoffeeService.listarDatas();

        assertNotNull(result);
    }

}
