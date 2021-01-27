package br.com.compassouol.clientecity.utils.messages;

import br.com.compassouol.clientecity.enums.MethodHttpEnum;

public class ClienteMessage {
    public static String messageSaveOrDeleteOrUpdate(String fullName, MethodHttpEnum method) {

        String[] name = fullName.split(" ");

        switch (method) {
            case POST:
                return name.length > 1 ? "Cliente " + name[0] + " " + name[1] + " cadastrado com sucesso!" : "Cliente " + name[0] + " cadastrado com sucesso!";
            case DELETE:
                return name.length > 1 ? "Cliente " + name[0] + " " + name[1] + " deletado com sucesso!" : "Cliente " + name[0] + " deletado com sucesso!";
            case PATCH:
                return name.length > 1 ? "Nome do cliente " + name[0] + " " + name[1] + " atualizado com sucesso!" : "Nome do cliente " + name[0] + " atualizado com sucesso!";
            default:
                return null;
        }
    }
}
