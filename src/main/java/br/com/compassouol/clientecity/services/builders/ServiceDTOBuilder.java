package br.com.compassouol.clientecity.services.builders;

import br.com.compassouol.clientecity.resources.v1.dtos.ServiceDTO;

import java.util.Optional;

public class ServiceDTOBuilder {
    public static ServiceDTO buildServiceDTO(Object data, Object error) {
        return ServiceDTO.builder()
                .withData(data != null ? Optional.of(data) : Optional.empty())
                .withError(error != null ? Optional.of(error) : Optional.empty())
                .build();
    }
}
