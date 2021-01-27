package br.com.compassouol.clientecity.resources.v1.controllers;

import br.com.compassouol.clientecity.exceptions.ResponseHttpExceptions;
import br.com.compassouol.clientecity.resources.v1.dtos.CidadeDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ResponseDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ServiceDTO;
import br.com.compassouol.clientecity.resources.v1.responses.clis.SendHttpResponse;
import br.com.compassouol.clientecity.services.clis.CidadeService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static br.com.compassouol.clientecity.enums.MethodHttpEnum.*;
import static br.com.compassouol.clientecity.utils.messages.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/cidade")
@PropertySource(value = "classpath:messages/messages.properties", encoding = "UTF-8")
@ApiResponses(value = {
        @ApiResponse(code = 201, message = "Cidade cadastrada com sucesso!"),
        @ApiResponse(code = 200, message = "Consulta de cidade realizada com sucesso!"),
        @ApiResponse(code = 404, message = "Consulta de cidade não encontrada!"),
        @ApiResponse(code = 500, message = "O Servidor não conseguiu tratar os dados enviados pela requisição."),
        @ApiResponse(code = 501, message = "Cadastro de cidade não pode ser implementado.")
})
public class CidadeController {


    @Value(value = "${messages.http.post.success.cidade}")
    private String messagePostSuccess;

    @Value(value = "${messages.http.post.error.cidade}")
    private String messagePostError;

    @Value(value = "${messages.http.get.success.cidade}")
    private String messageGetSuccess;

    @Value(value = "${messages.http.get.error.cidade}")
    private String messageGetError;

    @Value(value = "${messages.http.get.success.cidade_uf}")
    private String messageGetWithParamSuccess;

    @Value(value = "${messages.http.get.error.cidade_uf}")
    private String messageGetWithParamError;

    private final SendHttpResponse sendHttpResponse;
    private final CidadeService cidadeService;

    public CidadeController(SendHttpResponse sendHttpResponse, CidadeService cidadeService) {
        this.sendHttpResponse = sendHttpResponse;
        this.cidadeService = cidadeService;
    }

    @CrossOrigin
    @PostMapping(value = "/cadastrar")
    public ResponseEntity<ResponseDTO> save(@RequestBody CidadeDTO cidadeDTO) {
        Optional<ServiceDTO> service = cidadeService.save(cidadeDTO);

        return service.map(serviceDTO -> serviceDTO.getData().isPresent()
                ? ResponseEntity.status(CREATED)
                .body(sendHttpResponse.sendSuccess(
                        serviceDTO.getData().get(),
                        messagePostSuccess,
                        CREATED.value(),
                        CREATED.getReasonPhrase(),
                        POST))

                : ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(sendHttpResponse.sendError(
                        messagePostError,
                        INTERNAL_SERVER_ERROR.value(),
                        INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        serviceDTO.getError().orElse(new ResponseHttpExceptions(statusMessageHttpPtBr(INTERNAL_SERVER_ERROR, POST)).getMessage()),
                        POST)))
                .orElseGet(() -> ResponseEntity.status(NOT_IMPLEMENTED)
                        .body(sendHttpResponse.sendError(
                                messagePostError,
                                NOT_IMPLEMENTED.value(),
                                NOT_IMPLEMENTED.getReasonPhrase(),
                                new ResponseHttpExceptions(statusMessageHttpPtBr(NOT_IMPLEMENTED, POST)).getMessage(),
                                POST)));

    }

    @CrossOrigin
    @GetMapping(value = "/consultar")
    public ResponseEntity<ResponseDTO> findByNameOrState(@RequestParam(required = false) String nomeCidade, @RequestParam(required = false) String nomeEstado) {
        Optional<ServiceDTO> service = cidadeService.findByNameOrState(nomeCidade, nomeEstado);

        return service.map(serviceDTO -> serviceDTO.getData().isPresent()
                ? ResponseEntity.ok(sendHttpResponse.sendSuccess(
                    serviceDTO.getData().get(),
                    nomeEstado == null && nomeCidade != null ? messageGetSuccess : messageGetWithParamSuccess,
                    OK.value(),
                    OK.getReasonPhrase(), GET))

                : ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(sendHttpResponse.sendError(
                        nomeEstado == null && nomeCidade != null ? messageGetError : messageGetWithParamError,
                        INTERNAL_SERVER_ERROR.value(),
                        INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        serviceDTO.getError().orElse(new ResponseHttpExceptions(statusMessageHttpPtBr(INTERNAL_SERVER_ERROR, GET)).getMessage()), GET)))
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND)
                        .body(sendHttpResponse.sendError(
                                nomeEstado == null && nomeCidade != null ? messageGetError : messageGetWithParamError,
                                NOT_FOUND.value(),
                                NOT_FOUND.getReasonPhrase(),
                                new ResponseHttpExceptions(statusMessageHttpPtBr(NOT_FOUND, GET)).getMessage(),
                                GET)));

    }
}
