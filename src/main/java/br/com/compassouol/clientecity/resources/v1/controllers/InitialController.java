package br.com.compassouol.clientecity.resources.v1.controllers;

import br.com.compassouol.clientecity.utils.messages.HtmlMessage;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Pagina inicial apresentada com sucesso!")
})
public class InitialController {
    private final HtmlMessage htmlMessage;

    public InitialController(HtmlMessage htmlMessage) {
        this.htmlMessage = htmlMessage;
    }

    @ApiIgnore
    @CrossOrigin
    @GetMapping(value = "/")
    public ResponseEntity<?> initialPage() {
        return ResponseEntity.ok(htmlMessage.initialPage());
    }
}
