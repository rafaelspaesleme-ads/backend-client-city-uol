package br.com.compassouol.clientecity.resources.v1.dtos;

import br.com.compassouol.clientecity.enums.SexoEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class ClienteDTO {
    private Long id;
    private String nomeCompleto;
    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;
    private LocalDate dataNascimento;
    private Integer idade;
    private CidadeDTO cidade;
    private Boolean ativo;
}
