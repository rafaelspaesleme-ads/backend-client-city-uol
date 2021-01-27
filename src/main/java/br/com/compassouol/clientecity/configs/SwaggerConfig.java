package br.com.compassouol.clientecity.configs;

import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${app.name.api}")
    private String NAME_API;
    @Value(value = "${app.description.api}")
    private String DESCRIPTION_API;
    @Value(value = "${app.version.api}")
    private String VERSION_API;
    @Value(value = "${app.license.api}")
    private String LICENSE_API;
    @Value(value = "${app.url-license.api}")
    private String LICENSE_URL_API;
    @Value(value = "${app.author.api}")
    private String AUTHOR;
    @Value(value = "${app.url-portfolio-author.api}")
    private String PORTFOLIO;
    @Value(value = "${app.mail-author.api}")
    private String MAIL_AUTHOR;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.compassouol.clientecity.resources.v1.controllers"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(NAME_API)
                .description(DESCRIPTION_API)
                .version(VERSION_API)
                .license(LICENSE_API)
                .licenseUrl(LICENSE_URL_API)
                .contact(new Contact(AUTHOR, PORTFOLIO, MAIL_AUTHOR))
                .build();
    }

    private List<ResponseMessage> responseMessageForGET()
    {
        return new ArrayList<ResponseMessage>() {{
            add(new ResponseMessageBuilder()
                    .code(200)
                    .message("Dados foram retornados com sucesso!")
                    .build());
            add(new ResponseMessageBuilder()
                    .code(201)
                    .message("Informações cadastrados na base de dados.")
                    .build());
            add(new ResponseMessageBuilder()
                    .code(202)
                    .message("Ações de exclusão de dados foram aceitas.")
                    .build());
            add(new ResponseMessageBuilder()
                    .code(404)
                    .message("Dados não encontrados")
                    .responseModel(new ModelRef("Error"))
                    .build());
            add(new ResponseMessageBuilder()
                    .code(406)
                    .message("Ações de alterações ou exclusão de dados não foram aceitas.")
                    .responseModel(new ModelRef("Error"))
                    .build());
            add(new ResponseMessageBuilder()
                    .code(500)
                    .message("Erro ao requerer tratamentos do servidor.")
                    .responseModel(new ModelRef("Error"))
                    .build());
            add(new ResponseMessageBuilder()
                    .code(501)
                    .message("O servidor não aceitou implementar os dados enviados nessa requisição.")
                    .responseModel(new ModelRef("Error"))
                    .build());
        }};
    }

}
