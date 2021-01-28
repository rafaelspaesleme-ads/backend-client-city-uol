package br.com.compassouol.clientecity.controllers;

import br.com.compassouol.clientecity.enums.SexoEnum;
import br.com.compassouol.clientecity.resources.v1.controllers.CidadeController;
import br.com.compassouol.clientecity.resources.v1.controllers.ClienteController;
import br.com.compassouol.clientecity.resources.v1.dtos.CidadeDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ClienteDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ResponseDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClienteControllerTest {
    @Autowired
    private ClienteController clienteController;
    @Autowired
    private CidadeController cidadeController;

    @Test
    public void saveClientWithoutCity() {
        ResponseEntity<ResponseDTO> save = clienteController.save(ClienteDTO.builder()
                .withSexo(SexoEnum.MASCULINO)
                .withNomeCompleto("João da Silva")
                .withDataNascimento(LocalDate.of(1990, 5, 5))
                .withCidade(CidadeDTO.builder()
                        .withEstado("RJ")
                        .withAtivo(true)
                        .withNome("Três Rios")
                        .build())
                .withAtivo(true)
                .build());

        Assert.assertSame("Era para cadastrar cliente e cidade", HttpStatus.CREATED, save.getStatusCode());
    }

    @Test
    public void save() {
        cidadeController.save(CidadeDTO.builder()
                .withId(1L)
                .withEstado("RJ")
                .withAtivo(true)
                .withNome("Três Rios")
                .build());

        ResponseEntity<ResponseDTO> save = clienteController.save(ClienteDTO.builder()
                .withSexo(SexoEnum.MASCULINO)
                .withNomeCompleto("João da Silva")
                .withDataNascimento(LocalDate.of(1990, 5, 5))
                .withCidade(CidadeDTO.builder()
                        .withId(1L)
                        .build())
                .withAtivo(true)
                .build());

        Assert.assertSame("Era para cadastrar o cliente", HttpStatus.CREATED, save.getStatusCode());
    }

    @Test
    public void notSave() {

        ResponseEntity<ResponseDTO> notSaveCityNull = clienteController.save(ClienteDTO.builder()
                .withSexo(SexoEnum.MASCULINO)
                .withNomeCompleto("João da Silva")
                .withDataNascimento(LocalDate.of(1990, 5, 5))
                .withCidade(null)
                .withAtivo(true)
                .build());

        ResponseEntity<ResponseDTO> notSave = clienteController.save(ClienteDTO.builder()
                .withSexo(SexoEnum.MASCULINO)
                .withNomeCompleto("João da Silva")
                .withDataNascimento(LocalDate.of(1990, 5, 5))
                .withCidade(CidadeDTO.builder()
                        .withId(1L)
                        .build())
                .withAtivo(true)
                .build());

        Assert.assertSame("Era para não cadastrar o cliente", HttpStatus.NOT_IMPLEMENTED, notSaveCityNull.getStatusCode());
        Assert.assertSame("Era para não cadastrar o cliente", HttpStatus.INTERNAL_SERVER_ERROR, notSave.getStatusCode());

    }

    @Test
    public void findByNameOrId() {

        clienteController.save(ClienteDTO.builder()
                .withId(2L)
                .withSexo(SexoEnum.MASCULINO)
                .withNomeCompleto("João da Silva")
                .withDataNascimento(LocalDate.of(1990, 5, 5))
                .withCidade(CidadeDTO.builder()
                        .withId(1L)
                        .withEstado("RJ")
                        .withAtivo(true)
                        .withNome("Três Rios")
                        .build())
                .withAtivo(true)
                .build());

        ResponseEntity<ResponseDTO> clientId = clienteController.findByNameOrId(null, 2L);
        ResponseEntity<ResponseDTO> clientNotId = clienteController.findByNameOrId(null, 1L);
        ResponseEntity<ResponseDTO> clientNameContains = clienteController.findByNameOrId("João", null);
        ResponseEntity<ResponseDTO> clientNotName = clienteController.findByNameOrId("Diego", null);
        ResponseEntity<ResponseDTO> clientNameSpecific = clienteController.findByNameOrId("João da Silva", null);

        Assert.assertSame("Era para retornar clientes deste id", HttpStatus.OK, clientId.getStatusCode());
        Assert.assertSame("Era para não retornar clientes deste id", HttpStatus.NOT_FOUND, clientNotId.getStatusCode());
        Assert.assertSame("Era para retornar clientes que contem o nome escrito", HttpStatus.OK, clientNameContains.getStatusCode());
        Assert.assertSame("Era para não retornar clientes que contem o nome escrito", HttpStatus.NOT_FOUND, clientNotName.getStatusCode());
        Assert.assertSame("Era para retornar clientes que contem o nome escrito", HttpStatus.OK, clientNameSpecific.getStatusCode());
    }

    @Test
    public void deleteById() {

        clienteController.save(ClienteDTO.builder()
                .withId(2L)
                .withSexo(SexoEnum.MASCULINO)
                .withNomeCompleto("João da Silva")
                .withDataNascimento(LocalDate.of(1990, 5, 5))
                .withCidade(CidadeDTO.builder()
                        .withId(1L)
                        .withEstado("RJ")
                        .withAtivo(true)
                        .withNome("Três Rios")
                        .build())
                .withAtivo(true)
                .build());

        ResponseEntity<ResponseDTO> delete = clienteController.deleteById(2L);
        ResponseEntity<ResponseDTO> notDelete = clienteController.deleteById(10L);
        ResponseEntity<ResponseDTO> notDeleteIdNull = clienteController.deleteById(null);

        Assert.assertSame("Era para ter deletado", HttpStatus.ACCEPTED, delete.getStatusCode());
        Assert.assertSame("Era para não ter deletado", HttpStatus.NOT_FOUND, notDelete.getStatusCode());
        Assert.assertSame("Era para não ter deletado", HttpStatus.NOT_ACCEPTABLE, notDeleteIdNull.getStatusCode());
    }

    @Test
    public void updateName() {
        clienteController.save(ClienteDTO.builder()
                .withId(2L)
                .withSexo(SexoEnum.MASCULINO)
                .withNomeCompleto("João da Silva")
                .withDataNascimento(LocalDate.of(1990, 5, 5))
                .withCidade(CidadeDTO.builder()
                        .withId(1L)
                        .withEstado("RJ")
                        .withAtivo(true)
                        .withNome("Três Rios")
                        .build())
                .withAtivo(true)
                .build());

        ResponseEntity<ResponseDTO> updateName = clienteController.updateName(2L, "Bernardo Silva");
        ResponseEntity<ResponseDTO> notUpdateNameNull = clienteController.updateName(2L, null);
        ResponseEntity<ResponseDTO> notUpdateName = clienteController.updateName(7L, "Bernardo Silva");

        Assert.assertSame("Era para ter atualizado", HttpStatus.OK, updateName.getStatusCode());
        Assert.assertSame("Era para não ter atualizado", HttpStatus.NOT_ACCEPTABLE, notUpdateName.getStatusCode());
        Assert.assertSame("Era para não ter atualizado", HttpStatus.INTERNAL_SERVER_ERROR, notUpdateNameNull.getStatusCode());

    }

}
