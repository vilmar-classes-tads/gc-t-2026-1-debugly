package com.debugly.entities;

public class Membro {
    private static Long contadorId = 1L;

    private Long id;
    private String nome;
    private String cpf;
    private String funcao;
    private int cargaHoraria;
    private boolean possuiPlanoDeTrabalhoVinculado;

    public Membro(String nome, String cpf, String funcao, int cargaHoraria) {
        this.id = contadorId++;
        this.nome = nome;
        this.cpf = cpf;
        this.funcao = funcao;
        this.cargaHoraria = cargaHoraria;
        this.possuiPlanoDeTrabalhoVinculado = false;
    }

    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }

    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public boolean isPossuiPlanoDeTrabalhoVinculado() { return possuiPlanoDeTrabalhoVinculado; }
    public void setPossuiPlanoDeTrabalhoVinculado(boolean possuiPlanoDeTrabalhoVinculado) {
        this.possuiPlanoDeTrabalhoVinculado = possuiPlanoDeTrabalhoVinculado;
    }
}
