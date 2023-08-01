package io.github.lucasalmeida.cafeconnection.repository;

import io.github.lucasalmeida.cafeconnection.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM usuario WHERE cpf = :cpf")
    BigInteger countByCpf(@Param("cpf") String cpf);

    default boolean existsByCpf(String cpf) {
        BigInteger count = countByCpf(cpf);
        return count != null && count.compareTo(BigInteger.ZERO) > 0;
    }

}
