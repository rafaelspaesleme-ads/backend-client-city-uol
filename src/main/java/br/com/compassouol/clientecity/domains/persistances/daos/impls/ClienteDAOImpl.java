package br.com.compassouol.clientecity.domains.persistances.daos.impls;

import br.com.compassouol.clientecity.domains.entities.Cliente;
import br.com.compassouol.clientecity.domains.persistances.daos.clis.ClienteDAO;
import br.com.compassouol.clientecity.domains.persistances.repositories.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ClienteDAOImpl implements ClienteDAO {
    private final ClienteRepository clienteRepository;

    public ClienteDAOImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Optional<Cliente> save(Cliente cliente) {
        return Optional.of(clienteRepository.save(cliente));
    }

    @Override
    public List<Cliente> findAll() {
        try {
            return clienteRepository.findAll();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Cliente> findById(Long idCliente) {
        try {
            return clienteRepository.findById(idCliente);
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Boolean deleteCliente(Cliente cliente) {
        try {
            clienteRepository.delete(cliente);
            Optional<Cliente> clienteExist = clienteRepository.findById(cliente.getId());
            return !clienteExist.isPresent();

        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return false;
        }
    }
}
