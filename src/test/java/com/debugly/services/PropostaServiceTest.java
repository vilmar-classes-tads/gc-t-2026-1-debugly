package com.debugly.services;

import com.debugly.entities.Proposta;
import com.debugly.entities.StatusProposta;
import com.debugly.repositories.PropostaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes da Issue 2 - Cadastro/Edição de Projeto (entidade Proposta), em TDD.
 * Casos de teste: CT01 a CT06.
 */
class PropostaServiceTest {

    private PropostaRepository propostaRepository;
    private PropostaService propostaService;

    @BeforeEach
    void setUp() {
        propostaRepository = new PropostaRepository();
        propostaService = new PropostaService(propostaRepository);
    }

    @Test
    @DisplayName("CT01 - Cadastro de Projeto com Dados Válidos")
    void deveCadastrarProjetoComDadosValidos() {
        // Arrange
        String titulo = "Projeto Recicla Campus";
        String resumo = "Projeto sobre reciclagem.";
        List<String> palavrasChave = List.of("reciclagem", "sustentabilidade");
        String publicoAlvo = "Comunidade Acadêmica";
        String areaTematica = "Meio Ambiente";
        String campus = "Recife";
        List<Integer> ods = List.of(12);

        // Act
        Proposta proposta = propostaService.cadastrar(titulo, resumo, palavrasChave,
                publicoAlvo, areaTematica, campus, ods, true);

        // Assert
        assertNotNull(proposta);
        assertEquals(titulo, proposta.getTitulo());
        assertEquals(1, propostaRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT02 - Validação dos Campos Obrigatórios")
    void deveRejeitarCadastroComCampoObrigatorioVazio() {
        // Arrange: título em branco (representa qualquer campo obrigatório vazio)

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () ->
                propostaService.cadastrar("", "Resumo válido", List.of("chave"),
                        "Público", "Área", "Recife", List.of(1), true)
        );
        assertEquals(0, propostaRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT03 - Validação da Seleção de ODS")
    void deveRejeitarCadastroSemNenhumOdsSelecionado() {
        // Arrange: lista de ODS vazia

        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                propostaService.cadastrar("Título", "Resumo", List.of("chave"),
                        "Público", "Área", "Recife", List.of(), true)
        );
        assertEquals("É obrigatório selecionar pelo menos um ODS.", exception.getMessage());
        assertEquals(0, propostaRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT04 - Validação do Aceite do Termo de Compromisso")
    void deveRejeitarCadastroSemAceiteDoTermo() {
        // Arrange: aceiteTermoCompromisso = false

        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                propostaService.cadastrar("Título", "Resumo", List.of("chave"),
                        "Público", "Área", "Recife", List.of(1), false)
        );
        assertEquals("O aceite do Termo de Compromisso é obrigatório.", exception.getMessage());
        assertEquals(0, propostaRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT05 - Bloqueio de Edição de Projeto Submetido")
    void deveBloquearEdicaoDeProjetoSubmetido() {
        // Arrange: projeto cadastrado e depois marcado como SUBMETIDO
        Proposta proposta = propostaService.cadastrar("Título", "Resumo", List.of("chave"),
                "Público", "Área", "Recife", List.of(1), true);
        proposta.setStatus(StatusProposta.SUBMETIDO);

        // Act + Assert: sistema bloqueia a edição
        assertThrows(IllegalStateException.class, () ->
                propostaService.editarProposta(proposta.getId(), "Novo Título", "Resumo",
                        List.of("chave"), "Público", "Área", "Recife", List.of(1), true)
        );
    }

    @Test
    @DisplayName("CT06 - Edição de Projeto em Status de Correção")
    void devePermitirEdicaoDeProjetoEmCorrecao() {
        // Arrange: projeto cadastrado e marcado como EM_CORRECAO
        Proposta proposta = propostaService.cadastrar("Título", "Resumo", List.of("chave"),
                "Público", "Área", "Recife", List.of(1), true);
        proposta.setStatus(StatusProposta.EM_CORRECAO);
        String tituloCorrigido = "Título Corrigido";

        // Act
        Proposta editada = propostaService.editarProposta(proposta.getId(), tituloCorrigido,
                "Resumo revisado", List.of("chave"), "Público", "Área", "Recife", List.of(1), true);

        // Assert: sistema salva as alterações com sucesso
        assertEquals(tituloCorrigido, editada.getTitulo());
        assertEquals(tituloCorrigido, propostaService.listarTodos().get(0).getTitulo());
    }
}
