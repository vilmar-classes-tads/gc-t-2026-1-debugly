package com.debugly.services;

import com.debugly.entities.Membro;
import com.debugly.repositories.MembroRepository;

import java.util.List;

public class MembroService {

    private static final List<String> FUNCOES_VALIDAS =
            List.of("Coordenador", "Bolsista", "Voluntário", "Colaborador");
    private static final int CARGA_HORARIA_MAXIMA = 40;

    private final MembroRepository membroRepository;

    public MembroService(MembroRepository membroRepository) {
        this.membroRepository = membroRepository;
    }

    public Membro adicionar(String nome, String cpf, String funcao, int cargaHoraria) {
        validarCpf(cpf);
        validarCpfDuplicado(cpf);
        validarFuncao(funcao);
        validarCargaHoraria(cargaHoraria);

        Membro membro = new Membro(nome, cpf, funcao, cargaHoraria);
        return membroRepository.salvar(membro);
    }

    public void remover(Long id) {
        Membro membro = membroRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Membro não encontrado."));

        if (membro.isPossuiPlanoDeTrabalhoVinculado()) {
            throw new IllegalStateException(
                    "Não é possível remover o membro: existem planos de trabalho vinculados.");
        }

        membroRepository.removerPorId(id);
    }

    public List<Membro> listarTodos() {
        return membroRepository.listarTodos();
    }

 
    private void validarCpf(String cpf) {
        String cpfLimpo = cpf.replaceAll("[.\\-]", "");

        if (!cpfLimpo.matches("\\d{11}") || cpfLimpo.chars().distinct().count() == 1) {
            throw new IllegalArgumentException("CPF inválido.");
        }
    }

    private void validarCpfDuplicado(String cpf) {
        String cpfLimpo = cpf.replaceAll("[.\\-]", "");
        if (membroRepository.existePorCpf(cpfLimpo)) {
            throw new IllegalArgumentException("CPF já está associado a outro membro.");
        }
    }

    private void validarFuncao(String funcao) {
        if (funcao == null || !FUNCOES_VALIDAS.contains(funcao)) {
            throw new IllegalArgumentException("Função inválida.");
        }
    }

    private void validarCargaHoraria(int cargaHoraria) {
        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException("Carga Horária deve ser maior que zero.");
        }
        if (cargaHoraria > CARGA_HORARIA_MAXIMA) {
            throw new IllegalArgumentException(
                    "Carga Horária não pode ultrapassar " + CARGA_HORARIA_MAXIMA + "h semanais.");
        }
    }
}