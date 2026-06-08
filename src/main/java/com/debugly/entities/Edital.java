package com.debugly.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Edital {
    private static Long contadorId = 1L;

    private Long id;
    private String titulo;
    private Integer numero;
    private Integer ano;
    private LocalDate dataInicioSubmissao;
    private LocalDate dataFimSubmissao;
    private LocalDate dataInicioAvaliacao;
    private LocalDate dataFimAvaliacao;

    public Edital(String titulo, Integer numero, Integer ano,
                  LocalDate dataInicioSubmissao, LocalDate dataFimSubmissao,
                  LocalDate dataInicioAvaliacao, LocalDate dataFimAvaliacao) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.numero = numero;
        this.ano = ano;
        this.dataInicioSubmissao = dataInicioSubmissao;
        this.dataFimSubmissao = dataFimSubmissao;
        this.dataInicioAvaliacao = dataInicioAvaliacao;
        this.dataFimAvaliacao = dataFimAvaliacao;
    }

    public Long getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public LocalDate getDataInicioSubmissao() { return dataInicioSubmissao; }
    public void setDataInicioSubmissao(LocalDate dataInicioSubmissao) { this.dataInicioSubmissao = dataInicioSubmissao; }

    public LocalDate getDataFimSubmissao() { return dataFimSubmissao; }
    public void setDataFimSubmissao(LocalDate dataFimSubmissao) { this.dataFimSubmissao = dataFimSubmissao; }

    public LocalDate getDataInicioAvaliacao() { return dataInicioAvaliacao; }
    public void setDataInicioAvaliacao(LocalDate dataInicioAvaliacao) { this.dataInicioAvaliacao = dataInicioAvaliacao; }

    public LocalDate getDataFimAvaliacao() { return dataFimAvaliacao; }
    public void setDataFimAvaliacao(LocalDate dataFimAvaliacao) { this.dataFimAvaliacao = dataFimAvaliacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edital edital = (Edital) o;
        return Objects.equals(numero, edital.numero) && Objects.equals(ano, edital.ano);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, ano);
    }

    @Override
    public String toString() {
        return "Edital{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", numero=" + numero +
                ", ano=" + ano +
                ", dataInicioSubmissao=" + dataInicioSubmissao +
                ", dataFimSubmissao=" + dataFimSubmissao +
                ", dataInicioAvaliacao=" + dataInicioAvaliacao +
                ", dataFimAvaliacao=" + dataFimAvaliacao +
                '}';
    }
}