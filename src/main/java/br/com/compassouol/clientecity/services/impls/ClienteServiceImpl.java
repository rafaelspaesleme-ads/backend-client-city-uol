package br.com.compassouol.clientecity.services.impls;

import br.com.compassouol.clientecity.domains.entities.Cidade;
import br.com.compassouol.clientecity.domains.entities.Cliente;
import br.com.compassouol.clientecity.domains.persistances.daos.clis.CidadeDAO;
import br.com.compassouol.clientecity.domains.persistances.daos.clis.ClienteDAO;
import br.com.compassouol.clientecity.enums.MethodHttpEnum;
import br.com.compassouol.clientecity.resources.v1.dtos.ClienteDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ServiceDTO;
import br.com.compassouol.clientecity.services.clis.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.compassouol.clientecity.enums.MethodHttpEnum.*;
import static br.com.compassouol.clientecity.services.builders.CidadeBuilder.*;
import static br.com.compassouol.clientecity.services.builders.ClienteBuilder.*;
import static br.com.compassouol.clientecity.services.builders.ServiceDTOBuilder.*;
import static br.com.compassouol.clientecity.utils.messages.ClienteMessage.messageSaveOrDeleteOrUpdate;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteDAO clienteDAO;
    private final CidadeDAO cidadeDAO;

    public ClienteServiceImpl(ClienteDAO clienteDAO, CidadeDAO cidadeDAO) {
        this.clienteDAO = clienteDAO;
        this.cidadeDAO = cidadeDAO;
    }

    @Override
    public Optional<ServiceDTO> save(ClienteDTO clienteDTO) {
        try {
            Optional<Cidade> cidade = clienteDTO.getCidade() != null
                    ? cidadeDAO.findById(clienteDTO.getCidade().getId())
                    : Optional.empty();

            if (cidade.isPresent()) {
                clienteDTO.setCidade(buildCidadeDTO(cidade.get()));
                Optional<Cliente> cliente = clienteDAO.save(buildCliente(clienteDTO));

                return cliente.map(value -> buildServiceDTO(messageSaveOrDeleteOrUpdate(value.getNomeCompleto(), POST), null));

            } else {
                Optional<Cidade> cidadeSave = cidadeDAO.save(clienteDTO.getCidade() != null
                        ? buildCidade(clienteDTO.getCidade())
                        : null);

                if (cidadeSave.isPresent()) {
                    clienteDTO.setCidade(buildCidadeDTO(cidadeSave.get()));
                    Optional<Cliente> cliente = clienteDAO.save(buildCliente(clienteDTO));

                    return cliente.map(value -> buildServiceDTO(messageSaveOrDeleteOrUpdate(value.getNomeCompleto(), POST), null));
                } else {
                    return Optional.empty();
                }
            }

        } catch (Exception e) {
            return Optional.of(buildServiceDTO(null, e.getCause()));
        }
    }

    @Override
    public Optional<ServiceDTO> findByNameOrId(String nomeCliente, Long idCliente) {
        try {
            List<ClienteDTO> clienteDTOList = new ArrayList<>();

            if (nomeCliente != null && idCliente == null) {

                List<Cliente> clientes = clienteDAO.findAll()
                        .stream()
                        .filter(cliente -> cliente.getNomeCompleto().contains(nomeCliente))
                        .collect(Collectors.toList());

                clientes.forEach(cliente -> {
                    clienteDTOList.add(buildClienteDTO(cliente));
                });

                return clienteDTOList.size() > 0 ? Optional.of(buildServiceDTO(clienteDTOList, null)) : Optional.empty();

            } else if (nomeCliente == null && idCliente == null) {
                List<Cliente> clientes = clienteDAO.findAll()
                        .stream()
                        .filter(cliente -> cliente.getAtivo().equals(true))
                        .collect(Collectors.toList());

                clientes.forEach(cliente -> {
                    clienteDTOList.add(buildClienteDTO(cliente));
                });

                return clienteDTOList.size() > 0 ? Optional.of(buildServiceDTO(clienteDTOList, null)) : Optional.empty();
            } else {
                Optional<Cliente> cliente = clienteDAO.findById(idCliente);

                return cliente.map(value -> buildServiceDTO(buildClienteDTO(value), null));
            }

        } catch (Exception e) {
            return Optional.of(buildServiceDTO(null, e.getCause()));
        }
    }

    @Override
    public Optional<ServiceDTO> deleteById(Long idCliente) {
        try {
            if (idCliente != null) {
                Optional<Cliente> cliente = clienteDAO.findById(idCliente);

                if (cliente.isPresent()) {
                    Boolean deleteCliente = clienteDAO.deleteCliente(cliente.get());

                    if (deleteCliente) {
                        return Optional.of(buildServiceDTO(messageSaveOrDeleteOrUpdate(cliente.get().getNomeCompleto(), DELETE), null));
                    } else {
                        return Optional.of(buildServiceDTO(null, new Throwable(new HttpClientErrorException(HttpStatus.UNAUTHORIZED)).getCause()));
                    }
                } else {
                    return Optional.empty();
                }
            } else {
                return Optional.of(buildServiceDTO(null, new NullPointerException()));
            }

        } catch (Exception e) {
            return Optional.of(buildServiceDTO(null, e.getCause()));
        }
    }

    @Override
    public Optional<ServiceDTO> updateName(Long idCliente, String nomeCliente) {
        try {
            Optional<Cliente> cliente = clienteDAO.findById(idCliente);
            if (cliente.isPresent()) {
                Cliente getCliente = cliente.get();
                getCliente.setNomeCompleto(nomeCliente);
                Optional<Cliente> clienteUpdate = clienteDAO.save(getCliente);

                return clienteUpdate.map(value -> buildServiceDTO(messageSaveOrDeleteOrUpdate(value.getNomeCompleto(), PATCH), null));
            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            return Optional.of(buildServiceDTO(null, e.getCause()));
        }
    }
}
