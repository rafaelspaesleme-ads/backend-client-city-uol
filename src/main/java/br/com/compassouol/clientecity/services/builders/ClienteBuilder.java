package br.com.compassouol.clientecity.services.builders;

import br.com.compassouol.clientecity.domains.entities.Cliente;
import br.com.compassouol.clientecity.resources.v1.dtos.ClienteDTO;

import static br.com.compassouol.clientecity.services.builders.CidadeBuilder.*;
import static br.com.compassouol.clientecity.utils.functions.ClienteFunction.*;

public class ClienteBuilder {
    public static Cliente buildCliente(ClienteDTO clienteDTO) {


        return Cliente.builder()
                .withId(clienteDTO.getId())
                .withAtivo(clienteDTO.getAtivo())
                .withCidade(buildCidade(clienteDTO.getCidade()))
                .withDataNascimento(clienteDTO.getDataNascimento())
                .withIdade(clienteDTO.getIdade() != null
                        ? checkAge(clienteDTO.getIdade(), convertDateInAge(clienteDTO.getDataNascimento()))
                        : convertDateInAge(clienteDTO.getDataNascimento()))
                .withNomeCompleto(clienteDTO.getNomeCompleto())
                .withSexo(clienteDTO.getSexo())
                .build();
    }

    public static ClienteDTO buildClienteDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .withId(cliente.getId())
                .withAtivo(cliente.getAtivo())
                .withCidade(buildCidadeDTO(cliente.getCidade()))
                .withDataNascimento(cliente.getDataNascimento())
                .withIdade(cliente.getIdade())
                .withNomeCompleto(cliente.getNomeCompleto())
                .withSexo(cliente.getSexo())
                .build();
    }
}
