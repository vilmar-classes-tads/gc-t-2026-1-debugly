package com.debugly.services;

import java.util.List;

import com.debugly.entities.Usuario;
import com.debugly.repositories.UsuarioRepository;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrar(String nomeCompleto, String cpf, String email, String senha,
                             String campus, String areaFormacao, String titulacao) {

        validarCamposObrigatorios(nomeCompleto, cpf, email, senha, campus, areaFormacao, titulacao);
        validarFormatoCpf(cpf);
        validarFormatoEmail(email);
        validarSenha(senha);
        validarUnicidadeCpf(cpf);
        validarUnicidadeEmail(email);

        Usuario usuario = new Usuario(nomeCompleto, cpf, email, senha, campus, areaFormacao, titulacao);

        return usuarioRepository.salvar(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    // ---- Validações privadas ----

    private void validarCamposObrigatorios(String nomeCompleto, String cpf, String email,
                                           String senha, String campus,
                                           String areaFormacao, String titulacao) {
        if (nomeCompleto == null || nomeCompleto.isBlank())
            throw new IllegalArgumentException("Nome completo é obrigatório.");
        if (cpf == null || cpf.isBlank())
            throw new IllegalArgumentException("CPF é obrigatório.");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("E-mail é obrigatório.");
        if (senha == null || senha.isBlank())
            throw new IllegalArgumentException("Senha é obrigatória.");
        if (campus == null || campus.isBlank())
            throw new IllegalArgumentException("Campus é obrigatório.");
        if (areaFormacao == null || areaFormacao.isBlank())
            throw new IllegalArgumentException("Área de formação é obrigatória.");
        if (titulacao == null || titulacao.isBlank())
            throw new IllegalArgumentException("Titulação é obrigatória.");
    }

    private void validarFormatoCpf(String cpf) {
        // Remove pontuação antes de validar
        String cpfLimpo = cpf.replaceAll("[.\\-]", "");

        if (!cpfLimpo.matches("\\d{11}"))
            throw new IllegalArgumentException("CPF inválido.");
    }

    private void validarFormatoEmail(String email) {
        if (!email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$"))
            throw new IllegalArgumentException("E-mail inválido.");
    }

    private void validarSenha(String senha) {
        if (senha.length() < 8)
            throw new IllegalArgumentException("Insira uma senha com pelo menos 8 caracteres.");
    }

    private void validarUnicidadeCpf(String cpf) {
        String cpfLimpo = cpf.replaceAll("[.\\-]", "");
        if (usuarioRepository.existePorCpf(cpfLimpo))
            throw new IllegalArgumentException("CPF já cadastrado.");
    }

    private void validarUnicidadeEmail(String email) {
        if (usuarioRepository.existePorEmail(email))
            throw new IllegalArgumentException("E-mail já cadastrado.");
    }
}