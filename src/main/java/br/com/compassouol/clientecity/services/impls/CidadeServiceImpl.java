package br.com.compassouol.clientecity.services.impls;

import br.com.compassouol.clientecity.domains.entities.Cidade;
import br.com.compassouol.clientecity.domains.persistances.daos.clis.CidadeDAO;
import br.com.compassouol.clientecity.resources.v1.dtos.CidadeDTO;
import br.com.compassouol.clientecity.resources.v1.dtos.ServiceDTO;
import br.com.compassouol.clientecity.services.clis.CidadeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.compassouol.clientecity.services.builders.CidadeBuilder.*;
import static br.com.compassouol.clientecity.services.builders.ServiceDTOBuilder.*;
import static br.com.compassouol.clientecity.utils.messages.CidadeMessage.messageSave;

@Service
@PropertySource(value = "classpath:messages/messages.properties", encoding = "UTF-8")
public class CidadeServiceImpl implements CidadeService {

    @Value(value = "${messages.save.service.error.name-null}")
    private String errorNameNull;

    @Value(value = "${messages.save.service.error.state-null}")
    private String errorStateNull;

    private final CidadeDAO cidadeDAO;

    public CidadeServiceImpl(CidadeDAO cidadeDAO) {
        this.cidadeDAO = cidadeDAO;
    }

    @Override
    public Optional<ServiceDTO> save(CidadeDTO cidadeDTO) {
        try {
            if (cidadeDTO.getNome() == null) {
                return Optional.of(buildServiceDTO(null, errorNameNull));
            } else if (cidadeDTO.getEstado() == null) {
                return Optional.of(buildServiceDTO(null, errorStateNull));
            } else {
                Optional<Cidade> cidade = cidadeDAO.save(buildCidade(cidadeDTO));
                return cidade.map(value -> buildServiceDTO(messageSave(value.getNome()), null));
            }
        } catch (Exception e) {
            return Optional.of(buildServiceDTO(null, e.getCause()));
        }
    }

    @Override
    public Optional<ServiceDTO> findByNameOrState(String nomeCidade, String nomeEstado) {
        try {
            List<CidadeDTO> cidadeDTOList = new ArrayList<>();

            if (nomeCidade != null && nomeEstado == null) {
                return findAllByNameContains(nomeCidade, cidadeDTOList);
            }

            if (nomeCidade == null && nomeEstado != null) {
                return findAllByStateContains(nomeEstado, cidadeDTOList);
            }

            if (nomeCidade == null) {
                return findAllByActive(cidadeDTOList);
            }

            return findAllByNameContainsAndStateContains(nomeCidade, nomeEstado, cidadeDTOList);

        } catch (Exception e) {
            return Optional.of(buildServiceDTO(null, e.getCause()));
        }
    }

    private Optional<ServiceDTO> findAllByNameContains(String nomeCidade, List<CidadeDTO> cidadeDTOList) {

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
    }

    private Optional<ServiceDTO> findAllByStateContains(String nomeEstado, List<CidadeDTO> cidadeDTOList) {
        List<Cidade> cidades = cidadeDAO.findAll()
                .stream()
                .filter(cidade -> nomeEstado.length() > 2
                        ? cidade.getEstado().contains(nomeEstado)
                        : cidade.getUf().contains(nomeEstado))
                .collect(Collectors.toList());

        cidades.forEach(cidade -> {
            cidadeDTOList.add(buildCidadeDTO(cidade));
        });

        return cidadeDTOList.size() > 0
                ? Optional.of(buildServiceDTO(cidadeDTOList, null))
                : Optional.empty();
    }

    private Optional<ServiceDTO> findAllByActive(List<CidadeDTO> cidadeDTOList) {
        List<Cidade> cidades = cidadeDAO.findAll()
                .stream()
                .filter(cidade -> cidade.getAtivo().equals(true))
                .collect(Collectors.toList());

        cidades.forEach(cidade -> {
            cidadeDTOList.add(buildCidadeDTO(cidade));
        });

        return cidadeDTOList.size() > 0
                ? Optional.of(buildServiceDTO(cidadeDTOList, null))
                : Optional.empty();
    }

    private Optional<ServiceDTO> findAllByNameContainsAndStateContains(String nomeCidade, String nomeEstado, List<CidadeDTO> cidadeDTOList) {
        List<Cidade> cidades = cidadeDAO.findAllByNomeContainsAndEstadoContains(nomeCidade, nomeEstado);

        cidades.forEach(cidade -> {
            cidadeDTOList.add(buildCidadeDTO(cidade));
        });

        return cidadeDTOList.size() > 0
                ? Optional.of(buildServiceDTO(cidadeDTOList, null))
                : Optional.empty();
    }

}
