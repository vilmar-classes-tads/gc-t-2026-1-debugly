package com.debugly.services;

import com.debugly.entities.Edital;
import com.debugly.entities.Perfil;
import com.debugly.entities.Projeto;
import com.debugly.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes da Issue 4 - Filtros de Listagem de Projetos, escritos em TDD.
 * Casos de teste: CT01 a CT05.
 */
class ProjetoListagemServiceTest {

    private ProjetoListagemService projetoListagemService;
    private Edital editalA;
    private Edital editalB;
    private Usuario coordenador;
    private Projeto projetoRecife1;
    private Projeto projetoRecife2;
    private Projeto projetoOlinda;
    private List<Projeto> todosOsProjetos;

    @BeforeEach
    void setUp() {
        projetoListagemService = new ProjetoListagemService();

        editalA = new Edital("Edital A", 1, 2026,
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 31),
                LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 15));
        editalB = new Edital("Edital B", 2, 2026,
                LocalDate.of(2026, 3, 1), LocalDate.of(2026, 3, 31),
                LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 15));

        coordenador = new Usuario("Coordenador Teste", "52998224725", "coord@email.com",
                "senha123", "Recife", "Tecnologia", "Mestrado");

        projetoRecife1 = new Projeto("Projeto Recife 1", "Resumo", coordenador);
        projetoRecife1.setCampus("Recife");
        projetoRecife1.setEdital(editalA);
        projetoRecife1.setStatus("EM_ANALISE");

        projetoRecife2 = new Projeto("Projeto Recife 2", "Resumo", coordenador);
        projetoRecife2.setCampus("Recife");
        projetoRecife2.setEdital(editalB);
        projetoRecife2.setStatus("APROVADO");

        projetoOlinda = new Projeto("Projeto Olinda", "Resumo", coordenador);
        projetoOlinda.setCampus("Olinda");
        projetoOlinda.setEdital(editalA);
        projetoOlinda.setStatus("EM_ANALISE");

        todosOsProjetos = List.of(projetoRecife1, projetoRecife2, projetoOlinda);
    }

    @Test
    @DisplayName("CT01 - Admin Geral filtra projetos por Edital")
    void deveFiltrarProjetosPorEdital() {
        // Act
        List<Projeto> resultado = projetoListagemService.filtrarPorEdital(todosOsProjetos, editalA);

        // Assert: apenas os projetos vinculados ao Edital A
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(projetoRecife1));
        assertTrue(resultado.contains(projetoOlinda));
    }

    @Test
    @DisplayName("CT02 - Admin Geral filtra projetos por Campus")
    void deveFiltrarProjetosPorCampus() {
        // Act
        List<Projeto> resultado = projetoListagemService.filtrarPorCampus(todosOsProjetos, "Recife");

        // Assert: apenas os projetos do campus Recife
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(projetoRecife1));
        assertTrue(resultado.contains(projetoRecife2));
    }

    @Test
    @DisplayName("CT03 - Admin Geral filtra projetos por Status")
    void deveFiltrarProjetosPorStatus() {
        // Act
        List<Projeto> resultado = projetoListagemService.filtrarPorStatus(todosOsProjetos, "EM_ANALISE");

        // Assert: apenas os projetos com status EM_ANALISE
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(projetoRecife1));
        assertTrue(resultado.contains(projetoOlinda));
    }

    @Test
    @DisplayName("CT04 - Gestor/Diretor visualiza apenas projetos do próprio Campus")
    void gestorDeveVisualizarApenasProjetosDoProprioCampus() {
        // Arrange: usuário Gestor/Diretor vinculado ao campus Recife
        Usuario gestor = new Usuario("Gestor Teste", "11122233344", "gestor@email.com",
                "senha123", "Recife", "Gestão", "Especialização");
        gestor.setPerfis(List.of(Perfil.ROLE_GESTOR_DIRETOR));

        // Act
        List<Projeto> resultado = projetoListagemService.listarParaUsuario(gestor, todosOsProjetos);

        // Assert: só projetos do campus do gestor (Recife); Olinda não aparece
        assertEquals(2, resultado.size());
        assertFalse(resultado.contains(projetoOlinda));
    }

    @Test
    @DisplayName("CT05 - Usuário sem perfil administrativo tenta acessar a listagem de projetos")
    void deveBloquearAcessoDeUsuarioSemPerfilAdministrativo() {
        // Arrange: usuário comum, sem ROLE_ADMINISTRADOR nem ROLE_GESTOR_DIRETOR
        Usuario usuarioComum = new Usuario("Bolsista Teste", "99988877766", "bolsista@email.com",
                "senha123", "Recife", "Tecnologia", "Graduação");
        usuarioComum.setPerfis(List.of(Perfil.ROLE_PESQUISADOR));

        // Act + Assert: sistema bloqueia o acesso
        assertThrows(SecurityException.class, () ->
                projetoListagemService.listarParaUsuario(usuarioComum, todosOsProjetos)
        );
    }
}
