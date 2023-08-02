package io.github.lucasalmeida.cafeconnection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataProximoCoffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DATE") // Especifica o tipo de dado como DATE
    @DateTimeFormat(pattern = "dd/MM/yyyy") // Formato da data no banco de dados
    private LocalDate date;

}
