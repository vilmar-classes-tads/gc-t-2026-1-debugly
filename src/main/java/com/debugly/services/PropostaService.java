package com.debugly.services;

import com.debugly.entities.Proposta;
import com.debugly.entities.StatusProposta;
import com.debugly.repositories.PropostaRepository;
import java.util.List;

public class PropostaService {

    private final PropostaRepository propostaRepository;

    public PropostaService(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    public Proposta cadastrar(Proposta proposta) {
        return propostaRepository.salvar(proposta);
    }

    public Proposta editarProposta(Long id, String titulo, String resumo,
                                    List<String> palavrasChave, String publicoAlvo,
                                    String areaTematica, String campus,
                                    List<Integer> ods, boolean aceiteTermoCompromisso) {

        Proposta proposta = propostaRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Proposta não encontrada."));

        validarStatusEditavel(proposta);

        proposta.setTitulo(titulo);
        proposta.setResumo(resumo);
        proposta.setPalavrasChave(palavrasChave);
        proposta.setPublicoAlvo(publicoAlvo);
        proposta.setAreaTematica(areaTematica);
        proposta.setCampus(campus);
        proposta.setOds(ods);
        proposta.setAceiteTermoCompromisso(aceiteTermoCompromisso);

        return propostaRepository.atualizar(proposta);
    }

    public List<Proposta> listarTodos() {
        return propostaRepository.listarTodos();
    }

    private void validarStatusEditavel(Proposta proposta) {
        StatusProposta status = proposta.getStatus();

        if (status != StatusProposta.RASCUNHO && status != StatusProposta.EM_CORRECAO) {
            throw new IllegalStateException(
                    "Não é possível editar uma proposta com status " + status + "."
            );
        }
    }
}