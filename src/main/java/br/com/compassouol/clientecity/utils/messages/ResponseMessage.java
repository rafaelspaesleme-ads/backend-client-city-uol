package br.com.compassouol.clientecity.utils.messages;

import br.com.compassouol.clientecity.enums.MethodHttpEnum;
import org.springframework.http.HttpStatus;

import static br.com.compassouol.clientecity.enums.MethodHttpEnum.*;

public class ResponseMessage {
    public static String statusMessageHttpPtBr(HttpStatus httpStatus, MethodHttpEnum methodHttp) {
        if (methodHttp.equals(GET)) {
            switch (httpStatus) {
                case NOT_FOUND:
                    return "Informação não encontrada.";
                case INTERNAL_SERVER_ERROR:
                    return "Servidor não conseguiu tratar a busca por informações.";
                case OK:
                    return "Informação encontrada com sucesso!";
                default:
                    return new Throwable(httpStatus.getReasonPhrase()).getMessage();
            }
        } else if (methodHttp.equals(POST)) {
            switch (httpStatus) {
                case CREATED:
                    return "Dados cadastrados com sucesso!";
                case NOT_IMPLEMENTED:
                    return "Não foi possivel cadastrar informações.";
                case INTERNAL_SERVER_ERROR:
                    return "Servidor não conseguiu tratar os dados para cadastrar em nossa base de dados.";
                default:
                    return new Throwable(httpStatus.getReasonPhrase()).getMessage();
            }
        } else if (methodHttp.equals(PATCH)) {
            switch (httpStatus) {
                case OK:
                    return "Dados alterados com sucesso!";
                case INTERNAL_SERVER_ERROR:
                    return "Servidor não conseguiu tratar os dados para serem alterados em nossa base de dados.";
                case NOT_ACCEPTABLE:
                    return "Alteração não foi aceita em nossa base de dados.";
                default:
                    return new Throwable(httpStatus.getReasonPhrase()).getMessage();
            }
        } else if (methodHttp.equals(DELETE)) {
            switch (httpStatus) {
                case ACCEPTED:
                    return "Dado enviado foi aceito para exclusão.";
                case NOT_ACCEPTABLE:
                    return "Dado enviado não foi aceito para exclusão.";
                case NOT_FOUND:
                    return "Dado enviado para exclusão não foi encontrada em nossa base de dados.";
                default:
                    return new Throwable(httpStatus.getReasonPhrase()).getMessage();
            }
        } else {
            return new Throwable(httpStatus.getReasonPhrase()).getMessage();
        }
    }
}
