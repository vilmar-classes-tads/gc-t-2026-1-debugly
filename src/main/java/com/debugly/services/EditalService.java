package com.debugly.services;

import java.time.LocalDate;
import java.util.List;
import com.debugly.entities.Edital;
import com.debugly.repositories.EditalRepository;

public class EditalService {

    private final EditalRepository editalRepository;
    private final EditalValidator editalValidator;

    public EditalService(EditalRepository editalRepository, EditalValidator editalValidator) {
        this.editalRepository = editalRepository;
        this.editalValidator = editalValidator;
    }

    public Edital cadastrar(String titulo, Integer numero, Integer ano,
                            LocalDate dataInicioSubmissao, LocalDate dataFimSubmissao,
                            LocalDate dataInicioAvaliacao, LocalDate dataFimAvaliacao) {

        validarCamposObrigatorios(titulo, numero, ano);
        editalValidator.validar(dataInicioSubmissao, dataFimSubmissao,
                                dataInicioAvaliacao, dataFimAvaliacao);
        validarUnicidade(numero, ano);

        Edital edital = new Edital(titulo, numero, ano,
                dataInicioSubmissao, dataFimSubmissao,
                dataInicioAvaliacao, dataFimAvaliacao);

        return editalRepository.salvar(edital);
    }

    public Edital editar(Long id, String titulo, Integer numero, Integer ano,
                         LocalDate dataInicioSubmissao, LocalDate dataFimSubmissao,
                         LocalDate dataInicioAvaliacao, LocalDate dataFimAvaliacao) {

        Edital edital = editalRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Edital não encontrado."));

        validarCamposObrigatorios(titulo, numero, ano);
        editalValidator.validar(dataInicioSubmissao, dataFimSubmissao,
                                dataInicioAvaliacao, dataFimAvaliacao);

        edital.setTitulo(titulo);
        edital.setNumero(numero);
        edital.setAno(ano);
        edital.setDataInicioSubmissao(dataInicioSubmissao);
        edital.setDataFimSubmissao(dataFimSubmissao);
        edital.setDataInicioAvaliacao(dataInicioAvaliacao);
        edital.setDataFimAvaliacao(dataFimAvaliacao);

        return editalRepository.atualizar(edital);
    }

    public List<Edital> listarTodos() {
        return editalRepository.listarTodos();
    }

    // ---- Validações privadas ----

    private void validarCamposObrigatorios(String titulo, Integer numero, Integer ano) {
        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("Título é obrigatório.");
        if (numero == null)
            throw new IllegalArgumentException("Número é obrigatório.");
        if (ano == null)
            throw new IllegalArgumentException("Ano é obrigatório.");
    }

    private void validarUnicidade(Integer numero, Integer ano) {
        if (editalRepository.existePorNumeroEAno(numero, ano))
            throw new IllegalArgumentException("Já existe um edital com esse número e ano.");
    }
}