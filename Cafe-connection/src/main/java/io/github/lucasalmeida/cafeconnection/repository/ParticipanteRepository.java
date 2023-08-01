package io.github.lucasalmeida.cafeconnection.repository;

import io.github.lucasalmeida.cafeconnection.model.DataProximoCoffee;
import io.github.lucasalmeida.cafeconnection.model.OpcaoComidaBebida;
import io.github.lucasalmeida.cafeconnection.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

    @Query("SELECT p FROM Participante p WHERE p.data.date = :data")
    List<Participante> findByData_Date(@Param("data") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data);


    boolean existsByOpcaoAndData(OpcaoComidaBebida opcao, DataProximoCoffee data);
}
