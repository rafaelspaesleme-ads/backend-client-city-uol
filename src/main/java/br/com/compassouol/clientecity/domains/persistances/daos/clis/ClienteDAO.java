package br.com.compassouol.clientecity.domains.persistances.daos.clis;

import br.com.compassouol.clientecity.domains.entities.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteDAO {
    Optional<Cliente> save(Cliente cliente);

    List<Cliente> findAll();

    Optional<Cliente> findById(Long idCliente);

    Boolean deleteCliente(Cliente cliente);
}
