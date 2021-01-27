package br.com.compassouol.clientecity.resources.v1.responses.clis;

import br.com.compassouol.clientecity.enums.MethodHttpEnum;
import br.com.compassouol.clientecity.resources.v1.dtos.ResponseDTO;

public interface SendHttpResponse {
    ResponseDTO sendSuccess(Object data, String message, Integer httpStatus, String httpMessage, MethodHttpEnum methodHttp);

    ResponseDTO sendError(String message, Integer httpStatus, String httpMessage, Object error, MethodHttpEnum methodHttp);
}
