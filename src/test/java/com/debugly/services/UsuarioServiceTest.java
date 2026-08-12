package com.debugly.services;

import com.debugly.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testes automatizados da Issue 0 - Cadastro de Usuário.
 * Casos de teste (wiki): CT02, CT03, CT04, CT05.
 */
class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        // Arrange (comum a todos os testes): repositório em memória "limpo" a cada teste
        usuarioRepository = new UsuarioRepository();
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    @DisplayName("CT02 - Validação de unicidade de CPF")
    void deveRejeitarCadastroComCpfJaExistente() {
        // Arrange: já existe um usuário cadastrado com o CPF 12345678912
        usuarioService.cadastrar(
                "Usuário Original", "12345678912", "original@email.com",
                "senha123", "Campus A", "Tecnologia", "Graduação"
        );

        // Act + Assert: tenta cadastrar outro usuário com o mesmo CPF
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(
                        "Outro Usuário", "12345678912", "outro@email.com",
                        "senha123", "Campus B", "Tecnologia", "Graduação"
                )
        );

        assertEquals("CPF já cadastrado.", exception.getMessage());
        // O "usuário permanece na tela de cadastro" -> no backend, isso equivale a
        // nenhum novo registro ter sido persistido no repositório
        assertEquals(1, usuarioRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT03 - Validação de unicidade do e-mail")
    void deveRejeitarCadastroComEmailJaExistente() {
        // Arrange: já existe um usuário cadastrado com o e-mail informado
        usuarioService.cadastrar(
                "Albert Einstein", "11122233344", "albert.einstein@gmail.com",
                "senha123", "Campus A", "Física", "Doutorado"
        );

        // Act + Assert: tenta cadastrar outro usuário com o mesmo e-mail
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(
                        "Outro Cientista", "99988877766", "albert.einstein@gmail.com",
                        "senha123", "Campus B", "Química", "Mestrado"
                )
        );

        assertEquals("E-mail já cadastrado.", exception.getMessage());
        assertEquals(1, usuarioRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT04 - Validação de tamanho mínimo da senha")
    void deveRejeitarCadastroComSenhaMenorQueOMinimo() {
        // Arrange: dados válidos, exceto a senha com 7 caracteres ("1234567" - abaixo do mínimo de 8)

        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(
                        "Novo Usuário", "12345678900", "novo@email.com",
                        "1234567", "Campus A", "Tecnologia", "Graduação"
                )
        );

        assertEquals("Insira uma senha com pelo menos 8 caracteres.", exception.getMessage());
        assertEquals(0, usuarioRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT05 - Campos obrigatórios em branco")
    void deveRejeitarCadastroComCamposObrigatoriosEmBranco() {
        // Arrange: todos os campos obrigatórios em branco, apenas opcionais preenchidos
        // (o service valida em sequência; o primeiro campo obrigatório vazio -
        // nomeCompleto - é o que dispara a exceção)

        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(
                        "", "", "", "", "", "", ""
                )
        );

        assertEquals("Nome completo é obrigatório.", exception.getMessage());
        assertEquals(0, usuarioRepository.listarTodos().size());
    }
}
