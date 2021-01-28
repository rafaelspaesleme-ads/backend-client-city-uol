package br.com.compassouol.clientecity.persistances;

import br.com.compassouol.clientecity.domains.entities.Cidade;
import br.com.compassouol.clientecity.domains.persistances.daos.clis.CidadeDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CidadePersistanceTest {
    @Autowired
    private CidadeDAO cidadeDAO;

    @Test
    public void save() {
        Optional<Cidade> save = cidadeDAO.save(Cidade.builder()
                .withNome("Três Rios")
                .withEstado("Rio de Janeiro")
                .withUf("RJ")
                .withAtivo(true)
                .build());

        Optional<Cidade> saveUfLenght3 = cidadeDAO.save(Cidade.builder()
                .withNome("Três Rios")
                .withEstado("Rio de Janeiro")
                .withUf("RJJ")
                .withAtivo(true)
                .build());

        Optional<Cidade> saveCityNull = cidadeDAO.save(Cidade.builder()
                .withNome(null)
                .withEstado("Rio de Janeiro")
                .withUf("RJ")
                .withAtivo(true)
                .build());

        Optional<Cidade> saveStateNull = cidadeDAO.save(Cidade.builder()
                .withNome("Três Rios")
                .withEstado(null)
                .withUf("RJ")
                .withAtivo(true)
                .build());

        Optional<Cidade> saveUfNull = cidadeDAO.save(Cidade.builder()
                .withNome("Três Rios")
                .withEstado("Rio de Janeiro")
                .withUf(null)
                .withAtivo(true)
                .build());

        Assert.assertTrue("Era para salvar.", save.isPresent());
        Assert.assertFalse("Era para não salvar.", saveUfLenght3.isPresent());
        Assert.assertFalse("Era para não salvar.", saveCityNull.isPresent());
        Assert.assertFalse("Era para não salvar.", saveStateNull.isPresent());
        Assert.assertFalse("Era para não salvar.", saveUfNull.isPresent());
    }

    @Test
    public void findById() {
        Optional<Cidade> save = cidadeDAO.save(Cidade.builder()
                .withNome("Três Rios")
                .withEstado("Rio de Janeiro")
                .withUf("RJ")
                .withAtivo(true)
                .build());

        Optional<Cidade> cidade = cidadeDAO.findById(save.get().getId());
        Optional<Cidade> cidadeNotExist = cidadeDAO.findById(10L);
        Optional<Cidade> cidadeNull = cidadeDAO.findById(null);

        Assert.assertSame("Era para retornar cidade", save.get().getId(), cidade.get().getId());
        Assert.assertFalse("Era para não retornar cidade", cidadeNotExist.isPresent());
        Assert.assertFalse("Era para não retornar cidade", cidadeNull.isPresent());
    }

    @Test
    public void findAll() {
        List<Cidade> cidadesEmpty = cidadeDAO.findAll();

        cidadeDAO.save(Cidade.builder()
                .withNome("Três Rios")
                .withEstado("Rio de Janeiro")
                .withUf("RJ")
                .withAtivo(true)
                .build());

        cidadeDAO.save(Cidade.builder()
                .withNome("Juiz de Fora")
                .withEstado("Minas Gerais")
                .withUf("MG")
                .withAtivo(true)
                .build());

        List<Cidade> cidades = cidadeDAO.findAll();

        Assert.assertSame("Era para vir vazio", 0, cidadesEmpty.size());
        Assert.assertSame("Era para vir uma lista", 2, cidades.size());
    }

    @Test
    public void findAllByNomeContainsAndEstadoContains() {

        cidadeDAO.save(Cidade.builder()
                .withNome("Três Rios")
                .withEstado("Rio de Janeiro")
                .withUf("RJ")
                .withAtivo(true)
                .build());

        cidadeDAO.save(Cidade.builder()
                .withNome("Petropolis")
                .withEstado("Rio de Janeiro")
                .withUf("RJ")
                .withAtivo(true)
                .build());

        cidadeDAO.save(Cidade.builder()
                .withNome("Juiz de Fora")
                .withEstado("Minas Gerais")
                .withUf("MG")
                .withAtivo(true)
                .build());

        cidadeDAO.save(Cidade.builder()
                .withNome("Três Passos")
                .withEstado("Rio Grande do Sul")
                .withUf("MG")
                .withAtivo(true)
                .build());

        List<Cidade> cidadesNull = cidadeDAO.findAllByNomeContainsAndEstadoContains(null, null);
        List<Cidade> cidadesConflit = cidadeDAO.findAllByNomeContainsAndEstadoContains("Juiz de Fora", "Rio de Janeiro");
        List<Cidade> cidadesNameNull = cidadeDAO.findAllByNomeContainsAndEstadoContains(null, "Rio de Janeiro");
        List<Cidade> cidadesStateNull = cidadeDAO.findAllByNomeContainsAndEstadoContains("Três Rios", null);
        List<Cidade> cidadesContains = cidadeDAO.findAllByNomeContainsAndEstadoContains("Três", "Rio");
        List<Cidade> cidadesStateContains = cidadeDAO.findAllByNomeContainsAndEstadoContains("", "Rio");
        List<Cidade> cidadesContainsAll = cidadeDAO.findAllByNomeContainsAndEstadoContains("r", "r");
        List<Cidade> cidadesSpecification = cidadeDAO.findAllByNomeContainsAndEstadoContains("Três Rios", "Rio de Janeiro");

        Assert.assertSame("Era para vir vazio", 0, cidadesNull.size());
        Assert.assertSame("Era para vir vazio", 0, cidadesConflit.size());
        Assert.assertSame("Era para vir vazio", 0, cidadesNameNull.size());
        Assert.assertSame("Era para vir vazio", 0, cidadesStateNull.size());
        Assert.assertSame("Era para vir vazio", 0, cidadesStateNull.size());
        Assert.assertSame("Era para vir vazio", 2, cidadesContains.size());
        Assert.assertSame("Era para vir vazio", 3, cidadesStateContains.size());
        Assert.assertSame("Era para vir vazio", 3, cidadesStateContains.size());
        Assert.assertSame("Era para vir vazio", 4, cidadesContainsAll.size());
        Assert.assertSame("Era para vir vazio", 1, cidadesSpecification.size());

    }
}
