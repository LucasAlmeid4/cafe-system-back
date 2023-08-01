package io.github.lucasalmeida.cafeconnection.repository;

import io.github.lucasalmeida.cafeconnection.model.OpcaoComidaBebida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OpcaoComidaBebidaRepository extends JpaRepository<OpcaoComidaBebida, Long> {
    Optional<OpcaoComidaBebida> findByDescricao(String descricao);

    boolean existsByDescricao(String descricao);
}

