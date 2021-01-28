package br.com.compassouol.clientecity.utils.messages;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HtmlMessage {

    @Value(value = "${access.base-url}")
    private String BASE_URL;
    @Value(value = "${app.url-logo.api}")
    private String URL_IMG;
    @Value(value = "${app.name.api}")
    private String NAME_API;
    @Value(value = "${app.description.api}")
    private String DESCRIPTION_API;
    @Value(value = "${app.version.api}")
    private String VERSION_API;
    @Value(value = "${app.script-url-docs.api}")
    private String SCRIPT_URL_DOCS;

    public String initialPage() {
        return "<html>" +
                "<body>" +
                "<div style='padding=50px;text-align=justify;font-family: Tahoma, sans-serif;'>" +
                "<h1>" +
                "BEM VINDO AO " + NAME_API.toUpperCase() + " - v" + VERSION_API + "" +
                "</h1>" +
                "<br/>" +
                "<p>" + DESCRIPTION_API + "</p>" +
                "<br/>" +
                "<strong>Para ter acesso a nossa documentação de endpoints do swagger, <a href='" + BASE_URL + "/swagger-ui.html'>clique aqui</a></strong>" +
                checkAccessH2(BASE_URL) +
                "<br/>" +
                "<br/>" +
                "<hr/>" +
                "<br/>" +
                "<br/>" +
                "<center>" +
                "<img style='width: 300px; height: 150px;' src='" + URL_IMG + "' alt='logo'/>" +
                "<br/>" +
                "<br/>" +
                "<hr/>" +
                "<br/>" +
                "<br/>" +
                "</center>" +
                "</div>" +
                "</body>" +
                "<script src=\"" + SCRIPT_URL_DOCS + "\"></script>" +
                "</html>";

    }


    private String checkAccessH2(String baseUrl) {
        return baseUrl.contains("localhost") ? "<br/>-<br/><strong>Para ter acesso ao banco de dados embarcado, <a href='" + baseUrl + "/h2-console'>clique aqui</a></strong>" : "";
    }

}
