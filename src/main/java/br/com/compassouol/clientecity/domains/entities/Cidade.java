package br.com.compassouol.clientecity.domains.entities;

import lombok.*;

import javax.persistence.*;

@Entity(name = "tb_cidade")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with", toBuilder = true)
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String estado;
    @Column(length = 2, nullable = false)
    private String uf;
    private Boolean ativo;
}
