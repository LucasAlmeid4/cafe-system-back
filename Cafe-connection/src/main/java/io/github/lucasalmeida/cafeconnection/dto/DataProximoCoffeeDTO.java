package io.github.lucasalmeida.cafeconnection.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
@Data
public class DataProximoCoffeeDTO {
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String date;

}