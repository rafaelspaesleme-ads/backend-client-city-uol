package br.com.compassouol.clientecity.resources.v1.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class ServiceDTO {
    private Optional<Object> data;
    private Optional<Object> error;
}
