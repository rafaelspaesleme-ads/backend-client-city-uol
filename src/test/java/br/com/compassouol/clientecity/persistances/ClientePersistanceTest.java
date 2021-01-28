package br.com.compassouol.clientecity.persistances;

import br.com.compassouol.clientecity.domains.entities.Cidade;
import br.com.compassouol.clientecity.domains.entities.Cliente;
import br.com.compassouol.clientecity.domains.persistances.daos.clis.CidadeDAO;
import br.com.compassouol.clientecity.domains.persistances.daos.clis.ClienteDAO;
import br.com.compassouol.clientecity.enums.SexoEnum;
import br.com.compassouol.clientecity.utils.functions.ClienteFunction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.compassouol.clientecity.utils.functions.ClienteFunction.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientePersistanceTest {

    @Autowired
    private ClienteDAO clienteDAO;
    @Autowired
    private CidadeDAO cidadeDAO;

    @Test
    public void save() {
        Optional<Cidade> cidade = cidadeDAO.save(Cidade.builder()
                .withNome("Três Rios")
                .withEstado("Rio de Janeiro")
                .withUf("RJ")
                .withAtivo(true)
                .build());

        Optional<Cliente> cliente = clienteDAO.save(Cliente.builder()
                .withNomeCompleto("Rafael Paes Leme")
                .withSexo(SexoEnum.MASCULINO)
                .withDataNascimento(LocalDate.of(1989, 8, 5))
                .withIdade(convertDateInAge(LocalDate.of(1989, 8, 5)))
                .withCidade(cidade.get())
                .withAtivo(true)
                .build());

        Optional<Cliente> clienteCityNull = clienteDAO.save(Cliente.builder()
                .withNomeCompleto("Rafael Paes Leme")
                .withSexo(SexoEnum.MASCULINO)
                .withDataNascimento(LocalDate.of(1989, 8, 5))
                .withIdade(convertDateInAge(LocalDate.of(1989, 8, 5)))
                .withCidade(null)
                .withAtivo(true)
                .build());

        Optional<Cliente> clienteNameNull = clienteDAO.save(Cliente.builder()
                .withNomeCompleto(null)
                .withSexo(SexoEnum.MASCULINO)
                .withDataNascimento(LocalDate.of(1989, 8, 5))
                .withIdade(convertDateInAge(LocalDate.of(1989, 8, 5)))
                .withCidade(cidade.get())
                .withAtivo(true)
                .build());

        Assert.assertTrue("Era para salvar.", cliente.isPresent());
        Assert.assertFalse("Era para não salvar.", clienteCityNull.isPresent());
        Assert.assertFalse("Era para não salvar.", clienteNameNull.isPresent());
    }

    @Test
    public void findAll() {

        List<Cliente> clientesEmpty = clienteDAO.findAll();

        Optional<Cidade> cidade = cidadeDAO.save(Cidade.builder()
                .withNome("Três Rios")
                .withEstado("Rio de Janeiro")
                .withUf("RJ")
                .withAtivo(true)
                .build());

        clienteDAO.save(Cliente.builder()
                .withNomeCompleto("Rafael Paes Leme")
                .withSexo(SexoEnum.MASCULINO)
                .withDataNascimento(LocalDate.of(1989, 8, 5))
                .withIdade(convertDateInAge(LocalDate.of(1989, 8, 5)))
                .withCidade(cidade.get())
                .withAtivo(true)
                .build());

        clienteDAO.save(Cliente.builder()
                .withNomeCompleto("Paulo Ricardo")
                .withSexo(SexoEnum.MASCULINO)
                .withDataNascimento(LocalDate.of(1942, 10, 5))
                .withIdade(convertDateInAge(LocalDate.of(1942, 10, 5)))
                .withCidade(cidade.get())
                .withAtivo(true)
                .build());

        clienteDAO.save(Cliente.builder()
                .withNomeCompleto("Caroline")
                .withSexo(SexoEnum.MASCULINO)
                .withDataNascimento(LocalDate.of(1987, 6, 8))
                .withIdade(convertDateInAge(LocalDate.of(1987, 6, 8)))
                .withCidade(cidade.get())
                .withAtivo(true)
                .build());

        List<Cliente> clientes = clienteDAO.findAll();

        Assert.assertSame("Era para retornar vazio", 0, clientesEmpty.size());
        Assert.assertSame("Era para não retornar vazio", 3, clientes.size());
    }

    @Test
    public void findById() {

        Optional<Cidade> cidade = cidadeDAO.save(Cidade.builder()
                .withNome("Três Rios")
                .withEstado("Rio de Janeiro")
                .withUf("RJ")
                .withAtivo(true)
                .build());

        Optional<Cliente> cliente1 = clienteDAO.save(Cliente.builder()
                .withNomeCompleto("Rafael Paes Leme")
                .withSexo(SexoEnum.MASCULINO)
                .withDataNascimento(LocalDate.of(1989, 8, 5))
                .withIdade(convertDateInAge(LocalDate.of(1989, 8, 5)))
                .withCidade(cidade.get())
                .withAtivo(true)
                .build());

        Optional<Cliente> cliente2 = clienteDAO.save(Cliente.builder()
                .withNomeCompleto("Paulo Ricardo")
                .withSexo(SexoEnum.MASCULINO)
                .withDataNascimento(LocalDate.of(1942, 10, 5))
                .withIdade(convertDateInAge(LocalDate.of(1942, 10, 5)))
                .withCidade(cidade.get())
                .withAtivo(true)
                .build());

        Optional<Cliente> cliente3 = clienteDAO.save(Cliente.builder()
                .withNomeCompleto("Caroline")
                .withSexo(SexoEnum.MASCULINO)
                .withDataNascimento(LocalDate.of(1987, 6, 8))
                .withIdade(convertDateInAge(LocalDate.of(1987, 6, 8)))
                .withCidade(cidade.get())
                .withAtivo(true)
                .build());

        Optional<Cliente> cliente = clienteDAO.findById(cliente2.get().getId());
        Optional<Cliente> clienteNull = clienteDAO.findById(null);
        Optional<Cliente> clienteIdInvalid = clienteDAO.findById(18L);

        Assert.assertSame("Era para retornar um cliente.", cliente2.get().getId(), cliente.get().getId());
        Assert.assertFalse("Era para não retornar um cliente.", clienteNull.isPresent());
        Assert.assertFalse("Era para não retornar um cliente.", clienteIdInvalid.isPresent());
    }

    @Test
    public void deleteCliente() {

        Optional<Cidade> cidade = cidadeDAO.save(Cidade.builder()
                .withNome("Três Rios")
                .withEstado("Rio de Janeiro")
                .withUf("RJ")
                .withAtivo(true)
                .build());

        Optional<Cliente> cliente = clienteDAO.save(Cliente.builder()
                .withNomeCompleto("Caroline")
                .withSexo(SexoEnum.MASCULINO)
                .withDataNascimento(LocalDate.of(1987, 6, 8))
                .withIdade(convertDateInAge(LocalDate.of(1987, 6, 8)))
                .withCidade(cidade.get())
                .withAtivo(true)
                .build());

        Boolean deleteCliente = clienteDAO.deleteCliente(cliente.get());
        Boolean deleteClienteNull = clienteDAO.deleteCliente(null);
        Boolean clienteNotExiste = clienteDAO.deleteCliente(Cliente.builder()
                .withNomeCompleto("Caroline")
                .withSexo(SexoEnum.MASCULINO)
                .withDataNascimento(LocalDate.of(1987, 6, 8))
                .withIdade(convertDateInAge(LocalDate.of(1987, 6, 8)))
                .withCidade(cidade.get())
                .withAtivo(true)
                .build());

        Assert.assertTrue("Era pra deletar cliente", deleteCliente);
        Assert.assertFalse("Era pra não deletar cliente", deleteClienteNull);
        Assert.assertFalse("Era pra não deletar cliente", clienteNotExiste);
    }
}
