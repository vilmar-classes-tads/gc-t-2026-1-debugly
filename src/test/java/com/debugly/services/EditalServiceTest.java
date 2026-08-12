package com.debugly.services;

import com.debugly.entities.Edital;
import com.debugly.repositories.EditalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes automatizados da Issue 1 - Cadastro/Edição/Listagem de Edital.
 * Casos de teste (wiki): CT01, CT02, CT03, CT04, CT05.
 */
class EditalServiceTest {

    private EditalRepository editalRepository;
    private EditalValidator editalValidator;
    private EditalService editalService;

    @BeforeEach
    void setUp() {
        // Arrange (comum): repositório em memória "limpo" a cada teste
        editalRepository = new EditalRepository();
        editalValidator = new EditalValidator();
        editalService = new EditalService(editalRepository, editalValidator);
    }

    @Test
    @DisplayName("CT01 - Cadastro de um novo edital com dados válidos")
    void deveCadastrarEditalComDadosValidos() {
        // Arrange
        String titulo = "Edital de Apoio a Projetos de Extensão Tecnológica";
        Integer numero = 43;
        Integer ano = 2026;
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 3);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 28);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 9, 1);
        LocalDate fimAvaliacao = LocalDate.of(2026, 9, 18);

        // Act
        Edital edital = editalService.cadastrar(titulo, numero, ano,
                inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);

        // Assert: edital cadastrado com sucesso e presente na listagem
        assertNotNull(edital);
        assertEquals(titulo, edital.getTitulo());
        assertEquals(1, editalRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT02 - Edição de dados de um edital existente")
    void deveEditarDadosDeEditalExistente() {
        // Arrange: já existe pelo menos 1 edital cadastrado
        Edital editalOriginal = editalService.cadastrar(
                "Edital de Apoio a Projetos de Extensão Tecnológica", 43, 2026,
                LocalDate.of(2026, 8, 3), LocalDate.of(2026, 8, 28),
                LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 18)
        );
        String tituloAlterado = "Edital de Apoio a Projetos de Extensão Tecnológica (Alterado)";

        // Act
        Edital editalEditado = editalService.editar(
                editalOriginal.getId(), tituloAlterado, 43, 2026,
                LocalDate.of(2026, 8, 3), LocalDate.of(2026, 8, 28),
                LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 18)
        );

        // Assert: alterações salvas e refletidas na listagem
        assertEquals(tituloAlterado, editalEditado.getTitulo());
        List<Edital> editais = editalService.listarTodos();
        assertEquals(1, editais.size());
        assertEquals(tituloAlterado, editais.get(0).getTitulo());
    }

    @Test
    @DisplayName("CT03 - Listagem de editais cadastrados")
    void deveListarTodosOsEditaisCadastrados() {
        // Arrange: sistema possui pelo menos 2 editais cadastrados
        editalService.cadastrar("Edital 1", 1, 2026,
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 31),
                LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 15));
        editalService.cadastrar("Edital 2", 2, 2026,
                LocalDate.of(2026, 3, 1), LocalDate.of(2026, 3, 31),
                LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 15));

        // Act
        List<Edital> editais = editalService.listarTodos();

        // Assert: lista com todos os editais cadastrados
        assertEquals(2, editais.size());
    }

    @Test
    @DisplayName("CT04 - Validação de datas de submissão (início > fim)")
    void deveImpedirCadastroComDataInicioSubmissaoMaiorQueFim() {
        // Arrange: data de início de submissão (16/09) posterior à data de fim (01/09)
        LocalDate inicioSubmissao = LocalDate.of(2026, 9, 16);
        LocalDate fimSubmissao = LocalDate.of(2026, 9, 1);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 10, 19);
        LocalDate fimAvaliacao = LocalDate.of(2026, 11, 13);

        // Act + Assert: sistema impede o salvamento
        assertThrows(IllegalArgumentException.class, () ->
                editalService.cadastrar(
                        "Programa de Estímulo à Extensão Universitária e Desenvolvimento Social",
                        45, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao
                )
        );
        assertEquals(0, editalRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT05 - Validação de datas de avaliação (início > fim)")
    void deveImpedirCadastroComDataInicioAvaliacaoMaiorQueFim() {
        // Arrange: período de submissão correto, mas avaliação com início (30/11) após o fim (09/11)
        LocalDate inicioSubmissao = LocalDate.of(2026, 10, 5);
        LocalDate fimSubmissao = LocalDate.of(2026, 11, 6);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 11, 30);
        LocalDate fimAvaliacao = LocalDate.of(2026, 11, 9);

        // Act + Assert: sistema impede o salvamento
        assertThrows(IllegalArgumentException.class, () ->
                editalService.cadastrar(
                        "Edital de Seleção de Projetos para Concessão de Bolsas de Extensão",
                        49, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao
                )
        );
        assertEquals(0, editalRepository.listarTodos().size());
    }
}