package br.com.compassouol.clientecity.domains.entities;

import br.com.compassouol.clientecity.enums.SexoEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "tb_cliente")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with", toBuilder = true)
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String nomeCompleto;
    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;
    private LocalDate dataNascimento;
    private Integer idade;
    @ManyToOne
    @JoinColumn(name = "fk_cidade", nullable = false)
    private Cidade cidade;
    private Boolean ativo;
}
