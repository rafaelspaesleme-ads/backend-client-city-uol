package br.com.compassouol.clientecity.resources.v1.dtos;

import br.com.compassouol.clientecity.enums.MethodHttpEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Builder(toBuilder = true, setterPrefix = "with")
public class ResponseDTO {
    private Object data;
    private String message;
    private Integer httpStatus;
    private String httpMessage;
    @Enumerated(EnumType.STRING)
    private MethodHttpEnum methodHttp;
    private Object error;
}
