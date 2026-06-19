package com.debugly.repositories;

import com.debugly.entities.Proposta;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PropostaRepository {

    private final List<Proposta> propostas = new ArrayList<>();

    public Proposta salvar(Proposta proposta) {
        propostas.add(proposta);
        return proposta;
    }

    public Optional<Proposta> buscarPorId(Long id) {
        return propostas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public List<Proposta> listarTodos() {
        return new ArrayList<>(propostas);
    }

    public Proposta atualizar(Proposta proposta) {
        for (int i = 0; i < propostas.size(); i++) {
            if (propostas.get(i).getId().equals(proposta.getId())) {
                propostas.set(i, proposta);
                return proposta;
            }
        }
        throw new IllegalArgumentException("Proposta não encontrada.");
    }
}