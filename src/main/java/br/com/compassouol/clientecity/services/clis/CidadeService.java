package br.com.compassouol.clientecity.services.clis;

import br.com.compassouol.clientecity.resources.v1.dtos.CidadeDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ServiceDTO;

import java.util.Optional;

public interface CidadeService {
    Optional<ServiceDTO> save(CidadeDTO cidadeDTO);

    Optional<ServiceDTO> findByNameOrState(String nomeCidade, String nomeEstado);
}
