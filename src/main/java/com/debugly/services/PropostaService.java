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

    public Proposta cadastrar(String titulo, String resumo, List<String> palavrasChave,
                               String publicoAlvo, String areaTematica, String campus,
                               List<Integer> ods, boolean aceiteTermoCompromisso) {

        validarCamposObrigatorios(titulo, resumo, palavrasChave, publicoAlvo, areaTematica, campus);
        validarOds(ods);
        validarAceiteTermo(aceiteTermoCompromisso);

        Proposta proposta = new Proposta(titulo, resumo, palavrasChave, publicoAlvo,
                areaTematica, campus, ods, aceiteTermoCompromisso);

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

    // ---- Validações privadas (Issue 2) ----

    private void validarCamposObrigatorios(String titulo, String resumo, List<String> palavrasChave,
                                            String publicoAlvo, String areaTematica, String campus) {
        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("Título é obrigatório.");
        if (resumo == null || resumo.isBlank())
            throw new IllegalArgumentException("Resumo é obrigatório.");
        if (palavrasChave == null || palavrasChave.isEmpty())
            throw new IllegalArgumentException("Palavras-chave são obrigatórias.");
        if (publicoAlvo == null || publicoAlvo.isBlank())
            throw new IllegalArgumentException("Público-alvo é obrigatório.");
        if (areaTematica == null || areaTematica.isBlank())
            throw new IllegalArgumentException("Área Temática é obrigatória.");
        if (campus == null || campus.isBlank())
            throw new IllegalArgumentException("Campus é obrigatório.");
    }

    private void validarOds(List<Integer> ods) {
        if (ods == null || ods.isEmpty())
            throw new IllegalArgumentException("É obrigatório selecionar pelo menos um ODS.");
    }

    private void validarAceiteTermo(boolean aceiteTermoCompromisso) {
        if (!aceiteTermoCompromisso)
            throw new IllegalArgumentException("O aceite do Termo de Compromisso é obrigatório.");
    }
}