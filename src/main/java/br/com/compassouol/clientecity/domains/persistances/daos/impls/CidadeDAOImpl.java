package br.com.compassouol.clientecity.domains.persistances.daos.impls;

import br.com.compassouol.clientecity.domains.entities.Cidade;
import br.com.compassouol.clientecity.domains.persistances.daos.clis.CidadeDAO;
import br.com.compassouol.clientecity.domains.persistances.repositories.CidadeRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class CidadeDAOImpl implements CidadeDAO {
    private final CidadeRepository cidadeRepository;

    public CidadeDAOImpl(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    @Override
    public Optional<Cidade> save(Cidade cidade) {
        try {
            return Optional.of(cidadeRepository.save(cidade));
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Cidade> findAll() {
        try {
            return cidadeRepository.findAll();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Cidade> findById(Long id) {
        try {
            return cidadeRepository.findById(id);
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return Optional.empty();
        }
    }
}
