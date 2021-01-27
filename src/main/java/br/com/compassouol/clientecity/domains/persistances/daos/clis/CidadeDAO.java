package br.com.compassouol.clientecity.domains.persistances.daos.clis;

import br.com.compassouol.clientecity.domains.entities.Cidade;

import java.util.List;
import java.util.Optional;

public interface CidadeDAO {
    Optional<Cidade> save(Cidade cidade);

    List<Cidade> findAll();

    Optional<Cidade> findById(Long id);
}
