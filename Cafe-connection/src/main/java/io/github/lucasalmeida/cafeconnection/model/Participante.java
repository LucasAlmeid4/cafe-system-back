package io.github.lucasalmeida.cafeconnection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "opcao_id", "data_id"})})
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "opcao_id", nullable = false)
    private OpcaoComidaBebida opcao;

    @ManyToOne
    @JoinColumn(name = "data_id", nullable = false)
    private DataProximoCoffee data;


    public Participante(Usuario usuario, OpcaoComidaBebida opcao, DataProximoCoffee data) {
        this.usuario = usuario;
        this.opcao = opcao;
        this.data = data;
    }

}
