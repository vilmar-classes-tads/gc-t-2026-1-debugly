package com.debugly.controllers

import java.util.Scanner;

import com.debugly.entities.Usuario;
import com.debugly.services.UsuarioService;

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final Scanner scanner;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.scanner = new Scanner(System.in);
    }

    public void exibirFormularioCadastro() {
        System.out.println("=== Cadastro de Usuário ===\n");

        // Campos obrigatórios
        System.out.print("Nome completo *: ");
        String nomeCompleto = scanner.nextLine();

        System.out.print("CPF *: ");
        String cpf = scanner.nextLine();

        System.out.print("E-mail *: ");
        String email = scanner.nextLine();

        System.out.print("Senha * (mínimo 6 caracteres): ");
        String senha = scanner.nextLine();

        System.out.print("Campus *: ");
        String campus = scanner.nextLine();

        System.out.print("Área de formação *: ");
        String areaFormacao = scanner.nextLine();

        System.out.print("Titulação *: ");
        String titulacao = scanner.nextLine();

        // Campos opcionais
        System.out.print("Nome social (opcional): ");
        String nomeSocial = scanner.nextLine();

        System.out.print("Sexo (opcional - M/F/Outro): ");
        String sexo = scanner.nextLine();

        System.out.print("Link Lattes (opcional): ");
        String linkLattes = scanner.nextLine();

        System.out.print("Telefone (opcional): ");
        String telefone = scanner.nextLine();

        try {
            Usuario usuario = usuarioService.cadastrar(
                    nomeCompleto, cpf, email, senha, campus, areaFormacao, titulacao
            );

            // Campos opcionais — só atribui se preenchidos
            if (!nomeSocial.isBlank()) usuario.setNomeSocial(nomeSocial);
            if (!sexo.isBlank()) usuario.setSexo(sexo);
            if (!linkLattes.isBlank()) usuario.setLinkLattes(linkLattes);
            if (!telefone.isBlank()) usuario.setTelefone(telefone);

            System.out.println("\nUsuário cadastrado com sucesso!");
            System.out.println(usuario);

        } catch (IllegalArgumentException e) {
            System.out.println("\nErro no cadastro: " + e.getMessage());
        }
    }
}