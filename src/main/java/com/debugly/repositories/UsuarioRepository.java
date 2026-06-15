package com.debugly.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.debugly.entities.Usuario;

public class UsuarioRepository {

    private final List<Usuario> usuarios = new ArrayList<>();

    public Usuario salvar(Usuario usuario) {
        usuarios.add(usuario);
        return usuario;
    }

    public Optional<Usuario> buscarPorCpf(String cpf) {
        return usuarios.stream()
                .filter(u -> u.getCpf().equals(cpf))
                .findFirst();
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios); // cópia defensiva
    }

    public boolean existePorCpf(String cpf) {
        return buscarPorCpf(cpf).isPresent();
    }

    public boolean existePorEmail(String email) {
        return buscarPorEmail(email).isPresent();
    }
}