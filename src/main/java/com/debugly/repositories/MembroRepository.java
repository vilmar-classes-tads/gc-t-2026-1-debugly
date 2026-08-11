package com.debugly.repositories;

import com.debugly.entities.Membro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MembroRepository {

    private final List<Membro> membros = new ArrayList<>();

    public Membro salvar(Membro membro) {
        membros.add(membro);
        return membro;
    }

    public Optional<Membro> buscarPorCpf(String cpf) {
        return membros.stream()
                .filter(m -> m.getCpf().equals(cpf))
                .findFirst();
    }

    public List<Membro> listarTodos() {
        return new ArrayList<>(membros); // cópia defensiva
    }

    public boolean existePorCpf(String cpf) {
        return buscarPorCpf(cpf).isPresent();
    }

    public Optional<Membro> buscarPorId(Long id) {
        return membros.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    public void removerPorId(Long id) {
        membros.removeIf(m -> m.getId().equals(id));
    }
}