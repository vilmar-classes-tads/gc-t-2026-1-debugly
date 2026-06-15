package com.debugly.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.debugly.entities.Edital;

public class EditalRepository {

    private final List<Edital> editais = new ArrayList<>();

    public Edital salvar(Edital edital) {
        editais.add(edital);
        return edital;
    }

    public Optional<Edital> buscarPorId(Long id) {
        return editais.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public boolean existePorNumeroEAno(Integer numero, Integer ano) {
        return editais.stream()
                .anyMatch(e -> e.getNumero().equals(numero) && e.getAno().equals(ano));
    }

    public List<Edital> listarTodos() {
        return new ArrayList<>(editais);
    }

    public Edital atualizar(Edital edital) {
        for (int i = 0; i < editais.size(); i++) {
            if (editais.get(i).getId().equals(edital.getId())) {
                editais.set(i, edital);
                return edital;
            }
        }
        throw new IllegalArgumentException("Edital não encontrado.");
    }
}