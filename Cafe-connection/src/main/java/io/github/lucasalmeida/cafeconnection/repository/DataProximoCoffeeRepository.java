package io.github.lucasalmeida.cafeconnection.repository;

import io.github.lucasalmeida.cafeconnection.model.DataProximoCoffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DataProximoCoffeeRepository extends JpaRepository<DataProximoCoffee, Long> {
    boolean existsByDate(LocalDate date);
}