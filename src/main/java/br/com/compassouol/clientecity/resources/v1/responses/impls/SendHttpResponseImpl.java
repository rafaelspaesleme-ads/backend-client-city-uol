package br.com.compassouol.clientecity.resources.v1.responses.impls;

import br.com.compassouol.clientecity.enums.MethodHttpEnum;
import br.com.compassouol.clientecity.resources.v1.dtos.ResponseDTO;
import br.com.compassouol.clientecity.resources.v1.responses.clis.SendHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class SendHttpResponseImpl implements SendHttpResponse {
    @Override
    public ResponseDTO sendSuccess(Object data, String message, Integer httpStatus, String httpMessage, MethodHttpEnum methodHttp) {
        return ResponseDTO.builder()
                .withData(data)
                .withError(null)
                .withHttpStatus(httpStatus)
                .withMessage(message)
                .withHttpMessage(httpMessage)
                .withMethodHttp(methodHttp)
                .build();
    }

    @Override
    public ResponseDTO sendError(String message, Integer httpStatus, String httpMessage, Object error, MethodHttpEnum methodHttp) {
        return ResponseDTO.builder()
                .withData(Boolean.FALSE)
                .withMessage(message)
                .withHttpStatus(httpStatus)
                .withHttpMessage(httpMessage)
                .withError(error)
                .withMethodHttp(methodHttp)
                .build();
    }
}
