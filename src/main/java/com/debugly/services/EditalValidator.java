package com.debugly.services;

import java.time.LocalDate;

public class EditalValidator {

    public void validar(LocalDate dataInicioSubmissao, LocalDate dataFimSubmissao,
                        LocalDate dataInicioAvaliacao, LocalDate dataFimAvaliacao) {

        validarPeriodoSubmissao(dataInicioSubmissao, dataFimSubmissao);
        validarPeriodoAvaliacao(dataInicioAvaliacao, dataFimAvaliacao);
        validarOrdemEntrePeriodos(dataFimSubmissao, dataInicioAvaliacao);
    }

    private void validarPeriodoSubmissao(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null)
            throw new IllegalArgumentException("Datas de submissão são obrigatórias.");
        if (!inicio.isBefore(fim))
            throw new IllegalArgumentException("Data de início de submissão deve ser anterior à data de fim.");
    }

    private void validarPeriodoAvaliacao(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null)
            throw new IllegalArgumentException("Datas de avaliação são obrigatórias.");
        if (!inicio.isBefore(fim))
            throw new IllegalArgumentException("Data de início de avaliação deve ser anterior à data de fim.");
    }

    private void validarOrdemEntrePeriodos(LocalDate fimSubmissao, LocalDate inicioAvaliacao) {
        if (!fimSubmissao.isBefore(inicioAvaliacao))
            throw new IllegalArgumentException("O período de avaliação deve começar após o fim da submissão.");
    }
}