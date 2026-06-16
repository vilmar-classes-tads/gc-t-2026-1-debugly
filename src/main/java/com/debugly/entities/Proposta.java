package com.debugly.entities;

import java.util.List;
import java.util.Objects;

public class Proposta {
    private static Long contadorId = 1L;

    private Long id;
    private String titulo;
    private String resumo;
    private List<String> palavrasChave;
    private String publicoAlvo;
    private String areaTematica;
    private String campus;
    private List<Integer> ods;
    private boolean aceiteTermoCompromisso;
    private StatusProposta status;

    public Proposta(String titulo, String resumo, List<String> palavrasChave,
                   String publicoAlvo, String areaTematica, String campus,
                   List<Integer> ods, boolean aceiteTermoCompromisso) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.resumo = resumo;
        this.palavrasChave = palavrasChave;
        this.publicoAlvo = publicoAlvo;
        this.areaTematica = areaTematica;
        this.campus = campus;
        this.ods = ods;
        this.aceiteTermoCompromisso = aceiteTermoCompromisso;
        this.status = StatusProposta.RASCUNHO;
    }

    public Long getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }

    public List<String> getPalavrasChave() { return palavrasChave; }
    public void setPalavrasChave(List<String> palavrasChave) { this.palavrasChave = palavrasChave; }

    public String getPublicoAlvo() { return publicoAlvo; }
    public void setPublicoAlvo(String publicoAlvo) { this.publicoAlvo = publicoAlvo; }

    public String getAreaTematica() { return areaTematica; }
    public void setAreaTematica(String areaTematica) { this.areaTematica = areaTematica; }

    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }

    public List<Integer> getOds() { return ods; }
    public void setOds(List<Integer> ods) { this.ods = ods; }

    public boolean isAceiteTermoCompromisso() { return aceiteTermoCompromisso; }
    public void setAceiteTermoCompromisso(boolean aceiteTermoCompromisso) { this.aceiteTermoCompromisso = aceiteTermoCompromisso; }

    public StatusProposta getStatus() { return status; }
    public void setStatus(StatusProposta status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposta proposta = (Proposta) o;
        return Objects.equals(id, proposta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", status=" + status +
                ", campus='" + campus + '\'' +
                ", areaTematica='" + areaTematica + '\'' +
                ", ods=" + ods +
                '}';
    }
}