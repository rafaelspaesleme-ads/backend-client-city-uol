package br.com.compassouol.clientecity.services.impls;

import br.com.compassouol.clientecity.domains.entities.Cidade;
import br.com.compassouol.clientecity.domains.persistances.daos.clis.CidadeDAO;
import br.com.compassouol.clientecity.resources.v1.dtos.CidadeDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ServiceDTO;
import br.com.compassouol.clientecity.services.clis.CidadeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.compassouol.clientecity.services.builders.CidadeBuilder.*;
import static br.com.compassouol.clientecity.services.builders.ServiceDTOBuilder.*;
import static br.com.compassouol.clientecity.utils.messages.CidadeMessage.messageSave;

@Service
public class CidadeServiceImpl implements CidadeService {
    private final CidadeDAO cidadeDAO;

    public CidadeServiceImpl(CidadeDAO cidadeDAO) {
        this.cidadeDAO = cidadeDAO;
    }

    @Override
    public Optional<ServiceDTO> save(CidadeDTO cidadeDTO) {
        try {
            Optional<Cidade> cidade = cidadeDAO.save(buildCidade(cidadeDTO));

            return cidade.map(value -> buildServiceDTO(messageSave(value.getNome()), null));

        } catch (Exception e) {
            return Optional.of(buildServiceDTO(null, e.getCause()));
        }
    }

    @Override
    public Optional<ServiceDTO> findByNameOrState(String nomeCidade, String nomeEstado) {
        try {
            List<CidadeDTO> cidadeDTOList = new ArrayList<>();

            if (nomeCidade != null && nomeEstado == null) {
                List<Cidade> cidades = cidadeDAO.findAll()
                        .stream()
                        .filter(cidade -> cidade.getNome().contains(nomeCidade))
                        .collect(Collectors.toList());

                cidades.forEach(cidade -> {
                    cidadeDTOList.add(buildCidadeDTO(cidade));
                });

                return cidadeDTOList.size() > 0
                        ? Optional.of(buildServiceDTO(cidadeDTOList, null))
                        : Optional.empty();

            } else {
                List<Cidade> cidades = cidadeDAO.findAll()
                        .stream()
                        .filter(cidade -> nomeEstado != null
                                ? (nomeEstado.length() > 2 ? cidade.getEstado().contains(nomeEstado) : cidade.getUf().contains(nomeEstado))
                                : cidade.getAtivo().equals(true))
                        .collect(Collectors.toList());

                cidades.forEach(cidade -> {
                    cidadeDTOList.add(buildCidadeDTO(cidade));
                });

                return cidadeDTOList.size() > 0
                        ? Optional.of(buildServiceDTO(cidadeDTOList, null))
                        : Optional.empty();

            }

        } catch (Exception e) {
            return Optional.of(buildServiceDTO(null, e.getCause()));
        }
    }

}
