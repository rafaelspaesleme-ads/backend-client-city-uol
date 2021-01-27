package br.com.compassouol.clientecity.services.clis;

import br.com.compassouol.clientecity.resources.v1.dtos.ClienteDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ServiceDTO;

import java.util.Optional;

public interface ClienteService {
    Optional<ServiceDTO> save(ClienteDTO clienteDTO);

    Optional<ServiceDTO> findByNameOrId(String nomeCliente, Long idCliente);

    Optional<ServiceDTO> deleteById(Long idCliente);

    Optional<ServiceDTO> updateName(Long idCliente, String nomeCliente);
}
