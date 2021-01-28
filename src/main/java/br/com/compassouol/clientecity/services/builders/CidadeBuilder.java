package br.com.compassouol.clientecity.services.builders;

import br.com.compassouol.clientecity.domains.entities.Cidade;
import br.com.compassouol.clientecity.enums.EstadosEnum;
import br.com.compassouol.clientecity.resources.v1.dtos.CidadeDTO;

public class CidadeBuilder {
    public static Cidade buildCidade(CidadeDTO cidadeDTO) {

        EstadosEnum estado = null;

        EstadosEnum[] estados = EstadosEnum.values();

        if (cidadeDTO.getEstado().length() > 2) {
            for (EstadosEnum e : estados) {
                if (cidadeDTO.getEstado().contains(e.getNome())) {
                    estado = e;
                }
            }
        } else {
            for (EstadosEnum e : estados) {
                if (cidadeDTO.getEstado().contains(e.getSigla())) {
                    estado = e;
                }
            }
        }

        return Cidade.builder()
                .withId(cidadeDTO.getId())
                .withNome(cidadeDTO.getNome())
                .withAtivo(cidadeDTO.getAtivo())
                .withEstado(cidadeDTO.getEstado())
                .withUf(estado != null ? estado.getSigla() : cidadeDTO.getEstado().split("")[0]+cidadeDTO.getEstado().split("")[1])
                .build();
    }

    public static CidadeDTO buildCidadeDTO(Cidade cidade) {
        return CidadeDTO.builder()
                .withId(cidade.getId())
                .withNome(cidade.getNome())
                .withAtivo(cidade.getAtivo())
                .withEstado(cidade.getEstado())
                .build();
    }
}
