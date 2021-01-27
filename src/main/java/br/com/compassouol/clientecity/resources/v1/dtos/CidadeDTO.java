package br.com.compassouol.clientecity.resources.v1.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class CidadeDTO {
    private Long id;
    private String nome;
    private String estado;
    private Boolean ativo;
}
