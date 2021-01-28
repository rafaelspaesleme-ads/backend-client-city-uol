package br.com.compassouol.clientecity.services;

import br.com.compassouol.clientecity.resources.v1.dtos.CidadeDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ServiceDTO;
import br.com.compassouol.clientecity.services.clis.CidadeService;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CidadeServiceTest {
    @Autowired
    private CidadeService cidadeService;

    @Test
    public void saveSuccess() {
        Optional<ServiceDTO> save = cidadeService.save(CidadeDTO.builder()
                .withNome("Três Rios")
                .withEstado("Rio de Janeiro")
                .withAtivo(true)
                .build());

        Assert.assertTrue("Cadastro presente", save.isPresent());
        Assert.assertTrue("Era para ter cadastrado de fato", String.valueOf(save.get().getData().get()).contains("Três Rios cadastrada com sucesso!"));
    }

    @Test
    public void saveAllSuccess() {
        Faker faker = new Faker();

        List<ServiceDTO> serviceDTOList = new ArrayList<>();

        for (int i = 0; i <= 9; i++) {
            serviceDTOList.add(cidadeService.save(CidadeDTO.builder()
                    .withNome(faker.address().city())
                    .withEstado(faker.address().state())
                    .withAtivo(true)
                    .build()).get());
        }

        Assert.assertSame("Era para retornar uma lista com 10 cadastros", 10, serviceDTOList.size());
    }

    @Test
    public void notSave() {
        Faker faker = new Faker();

        Optional<ServiceDTO> notSaveCityNull = cidadeService.save(CidadeDTO.builder()
                .withNome(null)
                .withEstado(faker.address().state())
                .withAtivo(true)
                .build());

        Optional<ServiceDTO> notSaveStateNull = cidadeService.save(CidadeDTO.builder()
                .withNome(faker.address().city())
                .withEstado(null)
                .withAtivo(true)
                .build());

        Assert.assertTrue("Não era para salvar", notSaveCityNull.get().getError().get().toString().contains("Nome não pode esta nulo."));
        Assert.assertTrue("Não era para salvar", notSaveStateNull.get().getError().toString().contains("Estado não pode esta nulo."));
    }

    @Test
    public void findByNameOrState() {

        cidadeService.save(CidadeDTO.builder()
                .withNome("Três Rios")
                .withEstado("Rio de Janeiro")
                .withAtivo(true)
                .build());

        cidadeService.save(CidadeDTO.builder()
                .withNome("Itaguai")
                .withEstado("Rio de Janeiro")
                .withAtivo(true)
                .build());

        cidadeService.save(CidadeDTO.builder()
                .withNome("Juiz de Fora")
                .withEstado("Minas Gerais")
                .withAtivo(true)
                .build());

        Optional<ServiceDTO> service = cidadeService.findByNameOrState("Três Rios", null);
        Optional<ServiceDTO> service1 = cidadeService.findByNameOrState(null, "Minas Gerais");
        Optional<ServiceDTO> service2 = cidadeService.findByNameOrState("Itaguai", "Rio de Janeiro");
        Optional<ServiceDTO> service3 = cidadeService.findByNameOrState("Itaguai", "Minas Gerais");
        Optional<ServiceDTO> service4 = cidadeService.findByNameOrState("Itaguai", "Rio");
        Optional<ServiceDTO> service5 = cidadeService.findByNameOrState(null, null);

        Assert.assertTrue("Era para retornar como presente esta consulta", service.get().getData().isPresent());
        Assert.assertTrue("Era para retornar como presente esta consulta", service1.get().getData().isPresent());
        Assert.assertTrue("Era para retornar como presente esta consulta", service2.get().getData().isPresent());
        Assert.assertSame("Era para retornar como ausente esta consulta", Optional.empty(), service3);
        Assert.assertTrue("Era para retornar como presente esta consulta", service4.get().getData().isPresent());
        Assert.assertTrue("Era para retornar como presente esta consulta", service5.get().getData().isPresent());
    }
}
