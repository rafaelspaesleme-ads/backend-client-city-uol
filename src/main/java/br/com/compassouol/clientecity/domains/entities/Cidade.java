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
    private String nome;
    private String estado;
    @Column(length = 2)
    private String uf;
    private Boolean ativo;
}