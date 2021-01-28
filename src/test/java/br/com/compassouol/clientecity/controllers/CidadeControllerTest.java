package br.com.compassouol.clientecity.controllers;

import br.com.compassouol.clientecity.resources.v1.controllers.CidadeController;
import br.com.compassouol.clientecity.resources.v1.dtos.CidadeDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ResponseDTO;
import com.github.javafaker.Faker;
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

import java.util.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CidadeControllerTest {

    @Autowired
    private CidadeController cidadeController;

    @Test
    public void saveSuccess() {

        ResponseEntity<ResponseDTO> save = cidadeController.save(CidadeDTO.builder()
                .withAtivo(true)
                .withEstado("Rio de Janeiro").withNome("Três Rios").build());

        Assert.assertEquals("Era para retornar CREATED Status 201", 201, (int) Objects.requireNonNull(save.getBody()).getHttpStatus());
    }

    @Test
    public void saveAllSuccess() {

        Faker faker = new Faker();

        List<ResponseEntity<ResponseDTO>> saves = new ArrayList<>();

        for (int i = 0; i <= 9; i++) {
            ResponseEntity<ResponseDTO> save = cidadeController.save(CidadeDTO.builder()
                    .withAtivo(true)
                    .withEstado(faker.address().state())
                    .withNome(faker.address().city())
                    .build());
            if (save.getStatusCode() == HttpStatus.CREATED) {
                saves.add(save);
            }
        }

        Assert.assertEquals("Era para retornar 10 cadastros", 10, saves.size());

    }

    @Test
    public void notSaveNameNull() {
        ResponseEntity<ResponseDTO> save = cidadeController.save(CidadeDTO.builder()
                .withAtivo(true)
                .withEstado("Rio de Janeiro")
                .withNome(null).build());

        Assert.assertNotSame("Não era para salvar", save.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void findByName() {
        cidadeController.save(CidadeDTO.builder()
                .withAtivo(true)
                .withEstado("Rio de Janeiro")
                .withNome("Três Rios").build());

        ResponseEntity<ResponseDTO> city = cidadeController.findByNameOrState("Três Rios", null);

        Assert.assertSame("Era para retornar uma ou mais cidades", city.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void findByState() {
        cidadeController.save(CidadeDTO.builder()
                .withAtivo(true)
                .withEstado("Rio de Janeiro")
                .withNome("Três Rios").build());

        ResponseEntity<ResponseDTO> shortState = cidadeController.findByNameOrState(null, "RJ");
        ResponseEntity<ResponseDTO> state = cidadeController.findByNameOrState(null, "Rio de Janeiro");

        Assert.assertSame("Era para retornar uma ou mais cidades deste estado", shortState.getStatusCode(), HttpStatus.OK);
        Assert.assertSame("Era para retornar uma ou mais cidades deste estado", state.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void findAll() {
        cidadeController.save(CidadeDTO.builder()
                .withAtivo(true)
                .withEstado("Rio de Janeiro")
                .withNome("Três Rios").build());

        ResponseEntity<ResponseDTO> state = cidadeController.findByNameOrState(null, null);

        Assert.assertSame("Era para retornar todos os estados", state.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void findNotExists() {
        cidadeController.save(CidadeDTO.builder()
                .withAtivo(true)
                .withEstado("Rio de Janeiro")
                .withNome("Três Rios").build());

        ResponseEntity<ResponseDTO> city = cidadeController.findByNameOrState("Paraiba do Sul", null);
        ResponseEntity<ResponseDTO> state = cidadeController.findByNameOrState(null, "Minas Gerais");

        Assert.assertNotSame("Era para retornar todos os estados", city.getStatusCode(), HttpStatus.OK);
        Assert.assertNotSame("Era para retornar todos os estados", state.getStatusCode(), HttpStatus.OK);

    }
}
