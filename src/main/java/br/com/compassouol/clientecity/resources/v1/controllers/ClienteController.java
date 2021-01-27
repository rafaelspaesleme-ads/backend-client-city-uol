package br.com.compassouol.clientecity.resources.v1.controllers;

import br.com.compassouol.clientecity.exceptions.ResponseHttpExceptions;
import br.com.compassouol.clientecity.resources.v1.dtos.ClienteDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ResponseDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ServiceDTO;
import br.com.compassouol.clientecity.resources.v1.responses.clis.SendHttpResponse;
import br.com.compassouol.clientecity.services.clis.ClienteService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static br.com.compassouol.clientecity.enums.MethodHttpEnum.*;
import static br.com.compassouol.clientecity.utils.messages.ResponseMessage.statusMessageHttpPtBr;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/cliente")
@PropertySource(value = "classpath:messages/messages.properties", encoding = "UTF-8")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Consulta de cliente realizada com sucesso!"),
        @ApiResponse(code = 201, message = "Cliente cadastrado com sucesso!"),
        @ApiResponse(code = 202, message = "Ação de exclusão de cliente foi aceita."),
        @ApiResponse(code = 404, message = "Informações de cliente para consulta, alterações de dados ou exclusão de dados não encontrado!"),
        @ApiResponse(code = 406, message = "Ação de alteração ou exclusão de cliente não foi aceita."),
        @ApiResponse(code = 500, message = "O Servidor não conseguiu tratar os dados enviados pela requisição."),
        @ApiResponse(code = 501, message = "Cadastro de cliente não pode ser implementado.")
})
public class ClienteController {

    @Value(value = "${messages.http.post.success.cliente}")
    private String messagePostSuccess;

    @Value(value = "${messages.http.post.error.cliente}")
    private String messagePostError;

    @Value(value = "${messages.http.get.success.cliente}")
    private String messageGetSuccess;

    @Value(value = "${messages.http.get.error.cliente}")
    private String messageGetError;

    @Value(value = "${messages.http.get.success.cliente_id}")
    private String messageGetWithParamSuccess;

    @Value(value = "${messages.http.get.error.cliente_id}")
    private String messageGetWithParamError;

    @Value(value = "${messages.http.delete.success.cliente}")
    private String messageDeleteSuccess;

    @Value(value = "${messages.http.delete.error.cliente}")
    private String messageDeleteError;

    @Value(value = "${messages.http.put_or_patch.success.cliente}")
    private String messagePutOrPatchSuccess;

    @Value(value = "${messages.http.put_or_patch.error.cliente}")
    private String messagePutOrPatchError;

    private final SendHttpResponse sendHttpResponse;
    private final ClienteService clienteService;

    public ClienteController(SendHttpResponse sendHttpResponse, ClienteService clienteService) {
        this.sendHttpResponse = sendHttpResponse;
        this.clienteService = clienteService;
    }

    @CrossOrigin
    @PostMapping(value = "/cadastrar")
    public ResponseEntity<ResponseDTO> save(@RequestBody ClienteDTO clienteDTO) {
        Optional<ServiceDTO> service = clienteService.save(clienteDTO);

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
    public ResponseEntity<ResponseDTO> findByNameOrId(@RequestParam(required = false) String nomeCliente, @RequestParam(required = false) Long idCliente) {
        Optional<ServiceDTO> service = clienteService.findByNameOrId(nomeCliente, idCliente);

        return service.map(serviceDTO -> serviceDTO.getData().isPresent()
                ? ResponseEntity.ok(sendHttpResponse.sendSuccess(
                    serviceDTO.getData().get(),
                    nomeCliente == null && idCliente != null ? messageGetWithParamSuccess : messageGetSuccess,
                    OK.value(),
                    OK.getReasonPhrase(),
                    GET))

                : ResponseEntity.status(INTERNAL_SERVER_ERROR).body(sendHttpResponse.sendError(
                    nomeCliente == null && idCliente != null ? messageGetWithParamError : messageGetError,
                    INTERNAL_SERVER_ERROR.value(),
                    INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    serviceDTO.getError().orElse(new ResponseHttpExceptions(statusMessageHttpPtBr(INTERNAL_SERVER_ERROR, GET)).getMessage()),
                    GET)))
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND)
                        .body(sendHttpResponse.sendError(
                                nomeCliente == null && idCliente != null ? messageGetWithParamError : messageGetError,
                                NOT_FOUND.value(),
                                NOT_FOUND.getReasonPhrase(),
                                new ResponseHttpExceptions(statusMessageHttpPtBr(NOT_FOUND, GET)).getMessage(),
                                GET)));
    }

    @CrossOrigin
    @DeleteMapping(value = "/remover")
    public ResponseEntity<ResponseDTO> deleteById(@RequestParam Long idCliente) {
        Optional<ServiceDTO> service = clienteService.deleteById(idCliente);

        return service.map(serviceDTO -> serviceDTO.getData().isPresent()
                ? ResponseEntity.status(ACCEPTED)
                .body(sendHttpResponse.sendSuccess(
                        serviceDTO.getData().get(),
                        messageDeleteSuccess,
                        ACCEPTED.value(),
                        ACCEPTED.getReasonPhrase(),
                        DELETE))

                : ResponseEntity.status(NOT_ACCEPTABLE)
                .body(sendHttpResponse.sendError(
                        messageDeleteError,
                        NOT_ACCEPTABLE.value(),
                        NOT_ACCEPTABLE.getReasonPhrase(),
                        serviceDTO.getError().orElse(new ResponseHttpExceptions(statusMessageHttpPtBr(NOT_ACCEPTABLE, DELETE)).getMessage()),
                        DELETE)))
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND)
                        .body(sendHttpResponse.sendError(
                                messageDeleteError,
                                NOT_FOUND.value(),
                                NOT_FOUND.getReasonPhrase(),
                                new ResponseHttpExceptions(statusMessageHttpPtBr(NOT_FOUND, DELETE)).getMessage(),
                                DELETE)));
    }

    @CrossOrigin
    @PatchMapping(value = "/alterar/{idCliente}")
    public ResponseEntity<ResponseDTO> updateName(@PathVariable Long idCliente, @RequestParam String nomeCliente) {
        Optional<ServiceDTO> service = clienteService.updateName(idCliente, nomeCliente);

        return service.map(serviceDTO -> serviceDTO.getData().isPresent()
                ? ResponseEntity.ok(sendHttpResponse.sendSuccess(
                        serviceDTO.getData().get(),
                        messagePutOrPatchSuccess,
                        OK.value(),
                        OK.getReasonPhrase(),
                        PATCH))

                : ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(sendHttpResponse.sendError(
                        messageGetWithParamError,
                        INTERNAL_SERVER_ERROR.value(),
                        INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        serviceDTO.getError().orElse(new ResponseHttpExceptions(statusMessageHttpPtBr(INTERNAL_SERVER_ERROR, PATCH)).getMessage()),
                        PATCH)))
                .orElseGet(() -> ResponseEntity.status(NOT_ACCEPTABLE)
                        .body(sendHttpResponse.sendError(
                                messagePutOrPatchError,
                                NOT_ACCEPTABLE.value(),
                                NOT_ACCEPTABLE.getReasonPhrase(),
                                new ResponseHttpExceptions(statusMessageHttpPtBr(NOT_ACCEPTABLE, PATCH)).getMessage(),
                                PATCH)));

    }
}
