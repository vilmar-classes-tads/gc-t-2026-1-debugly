package com.debugly.entities;

public class Projeto {
    private static Long contadorId = 1L;
    
    private Long id;
    private String titulo;
    private String resumo;
    private Usuario coordenador;  // Usuário com perfil COORDENADOR
    //private Edital edital;
    private String status; // EX: SUBMETIDO, EM_ANALISE, APROVADO, REPROVADO
    
    public Projeto(String titulo, String resumo, Usuario coordenador) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.resumo = resumo;
        this.coordenador = coordenador;
       // this.edital = edital;
        this.status = "SUBMETIDO";
    }

    // Getters e Setters
    public Long getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }

    public Usuario getCoordenador() { return coordenador; }
    public void setCoordenador(Usuario coordenador) { this.coordenador = coordenador; }

   // public Edital getEdital() { return edital; }
    //public void setEdital(Edital edital) { this.edital = edital; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}