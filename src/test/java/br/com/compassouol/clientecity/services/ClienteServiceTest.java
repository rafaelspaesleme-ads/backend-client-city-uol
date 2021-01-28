package br.com.compassouol.clientecity.services;

import br.com.compassouol.clientecity.enums.SexoEnum;
import br.com.compassouol.clientecity.resources.v1.dtos.CidadeDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ClienteDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ServiceDTO;
import br.com.compassouol.clientecity.services.clis.CidadeService;
import br.com.compassouol.clientecity.services.clis.ClienteService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Optional;

import static br.com.compassouol.clientecity.enums.SexoEnum.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClienteServiceTest {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private CidadeService cidadeService;

    @Test
    public void save() {
        cidadeService.save(CidadeDTO.builder()
                .withId(2L)
                .withNome("Três Rios")
                .withEstado("Rio de Janeiro")
                .withAtivo(true)
                .build());

        Optional<ServiceDTO> save = clienteService.save(ClienteDTO.builder()
                .withNomeCompleto("Rafael Paes Leme")
                .withDataNascimento(LocalDate.of(1989, 8, 5))
                .withSexo(MASCULINO)
                .withCidade(CidadeDTO.builder()
                        .withNome("Três Rios")
                        .withEstado("Rio de Janeiro")
                        .withAtivo(true)
                        .build())
                .withAtivo(true)
                .build());

        Optional<ServiceDTO> notSave = clienteService.save(ClienteDTO.builder()
                .withNomeCompleto(null)
                .withDataNascimento(LocalDate.of(1989, 8, 5))
                .withSexo(MASCULINO)
                .withCidade(CidadeDTO.builder()
                        .withNome("Três Rios")
                        .withEstado("Rio de Janeiro")
                        .withAtivo(true)
                        .build())
                .withAtivo(true)
                .build());

        Optional<ServiceDTO> saveCityExist = clienteService.save(ClienteDTO.builder()
                .withNomeCompleto("Rafael Paes Leme")
                .withDataNascimento(LocalDate.of(1989, 8, 5))
                .withSexo(MASCULINO)
                .withCidade(CidadeDTO.builder()
                        .withId(2L)
                        .build())
                .withAtivo(true)
                .build());

        Optional<ServiceDTO> saveCityNull = clienteService.save(ClienteDTO.builder()
                .withNomeCompleto("Rafael Paes Leme")
                .withDataNascimento(LocalDate.of(1989, 8, 5))
                .withSexo(MASCULINO)
                .withCidade(null)
                .withAtivo(true)
                .build());

        Assert.assertTrue("Era para cadastrar cliente", save.get().getData().isPresent());
        Assert.assertTrue("Era para cadastrar cliente", save.get().getData().isPresent());
        Assert.assertTrue("Era para não cadastrar cliente", notSave.get().getError().isPresent());
        Assert.assertFalse("Era para não cadastrar cliente", saveCityNull.isPresent());
        Assert.assertTrue("Era para cadastrar cliente", save.get().getData().get().toString().contains("Rafael Paes cadastrado com sucesso!"));
        Assert.assertTrue("Era para cadastrar cliente", saveCityExist.get().getData().get().toString().contains("Rafael Paes cadastrado com sucesso!"));
    }

    @Test
    public void findByNameOrId() {
        clienteService.save(ClienteDTO.builder()
                .withId(2L)
                .withNomeCompleto("Rafael Paes Leme")
                .withDataNascimento(LocalDate.of(1989, 8, 5))
                .withSexo(MASCULINO)
                .withCidade(CidadeDTO.builder()
                        .withNome("Três Rios")
                        .withEstado("Rio de Janeiro")
                        .withAtivo(true)
                        .build())
                .withAtivo(true)
                .build());

        Optional<ServiceDTO> serviceNameSpecification = clienteService.findByNameOrId("Rafael Paes Leme", null);
        Optional<ServiceDTO> serviceNameContains = clienteService.findByNameOrId("Rafael", null);
        Optional<ServiceDTO> serviceId = clienteService.findByNameOrId(null, 2L);
        Optional<ServiceDTO> serviceNull = clienteService.findByNameOrId(null, null);
        Optional<ServiceDTO> service = clienteService.findByNameOrId("Rafael", 2L);
        Optional<ServiceDTO> serviceNameIdInvalid = clienteService.findByNameOrId("Rafael", 8L);
        Optional<ServiceDTO> serviceNameInvalid = clienteService.findByNameOrId("Joaquim", null);
        Optional<ServiceDTO> serviceIdInvalid = clienteService.findByNameOrId(null, 10L);

        Assert.assertTrue("Era para retornar a consulta!", serviceNameSpecification.get().getData().isPresent());
        Assert.assertTrue("Era para retornar a consulta!", serviceNameContains.get().getData().isPresent());
        Assert.assertTrue("Era para retornar a consulta!", serviceId.get().getData().isPresent());
        Assert.assertTrue("Era para retornar a consulta!", serviceNull.get().getData().isPresent());
        Assert.assertTrue("Era para retornar a consulta!", service.get().getData().isPresent());
        Assert.assertTrue("Era para retornar a consulta!", service.get().getData().isPresent());
        Assert.assertFalse("Era para não retornar a consulta!", serviceNameIdInvalid.isPresent());
        Assert.assertFalse("Era para não retornar a consulta!", serviceNameInvalid.isPresent());
        Assert.assertFalse("Era para não retornar a consulta!", serviceIdInvalid.isPresent());
    }

    @Test
    public void deleteById() {
        clienteService.save(ClienteDTO.builder()
                .withId(2L)
                .withNomeCompleto("Rafael Paes Leme")
                .withDataNascimento(LocalDate.of(1989, 8, 5))
                .withSexo(MASCULINO)
                .withCidade(CidadeDTO.builder()
                        .withNome("Três Rios")
                        .withEstado("Rio de Janeiro")
                        .withAtivo(true)
                        .build())
                .withAtivo(true)
                .build());

        Optional<ServiceDTO> deleteNull = clienteService.deleteById(null);
        Optional<ServiceDTO> deleteClientNotExist = clienteService.deleteById(10L);
        Optional<ServiceDTO> delete = clienteService.deleteById(2L);

        Assert.assertTrue("Era para retornar uma mensagem de erro.", deleteNull.get().getError().isPresent());
        Assert.assertFalse("Era para retornar vazio.", deleteClientNotExist.isPresent());
        Assert.assertTrue("Era para deletar.", delete.get().getData().isPresent());
    }

    @Test
    public void updateName() {
        clienteService.save(ClienteDTO.builder()
                .withId(2L)
                .withNomeCompleto("Rafael Paes Leme")
                .withDataNascimento(LocalDate.of(1989, 8, 5))
                .withSexo(MASCULINO)
                .withCidade(CidadeDTO.builder()
                        .withNome("Três Rios")
                        .withEstado("Rio de Janeiro")
                        .withAtivo(true)
                        .build())
                .withAtivo(true)
                .build());

        Optional<ServiceDTO> updateIdNull = clienteService.updateName(null, "Rafael Serdeiro Paes Leme");
        Optional<ServiceDTO> updateNameEquals = clienteService.updateName(2L, "Rafael Paes Leme");
        Optional<ServiceDTO> updateIdNotPresent = clienteService.updateName(3L, "Rafael Serdeiro Paes Leme");
        Optional<ServiceDTO> updateName = clienteService.updateName(2L, "Rafael Serdeiro Paes Leme");

        Assert.assertTrue("Era para retornar um erro.", updateIdNull.get().getError().isPresent());
        Assert.assertTrue("Não era para atualizar.", updateNameEquals.get().getData().get().toString().contains("Nome esta igual"));
        Assert.assertFalse("Era para retornar vazio.", updateIdNotPresent.isPresent());
        Assert.assertTrue("Era para atualizar.", updateName.get().getData().get().toString().contains("Rafael Serdeiro atualizado com sucesso!"));
    }
}
