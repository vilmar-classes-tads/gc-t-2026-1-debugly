package com.debugly.services;

import com.debugly.entities.Membro;
import com.debugly.repositories.MembroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes da Issue 3 - Gestão de Membros da Equipe, escritos em TDD.
 * Casos de teste: CT01 a CT09.
 */
class MembroServiceTest {

    private MembroRepository membroRepository;
    private MembroService membroService;

    @BeforeEach
    void setUp() {
        membroRepository = new MembroRepository();
        membroService = new MembroService(membroRepository);
    }

    @Test
    @DisplayName("CT01 - Adicionar membro com dados válidos")
    void deveCadastrarMembroComDadosValidos() {
        // Arrange
        String nome = "Maria Souza";
        String cpf = "52998224725"; // CPF válido (dígitos verificadores corretos)
        String funcao = "Bolsista";
        int cargaHoraria = 20;

        // Act
        Membro membro = membroService.adicionar(nome, cpf, funcao, cargaHoraria);

        // Assert
        assertNotNull(membro);
        assertEquals(nome, membro.getNome());
        assertEquals(1, membroRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT02 - Adicionar membro com CPF inválido")
    void deveRejeitarMembroComCpfInvalido() {
        // Arrange: CPF com todos os dígitos iguais -> inválido
        String cpfInvalido = "11111111111";

        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                membroService.adicionar("João Silva", cpfInvalido, "Voluntário", 10)
        );

        assertEquals("CPF inválido.", exception.getMessage());
        assertEquals(0, membroRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT03 - Adicionar membro com CPF já cadastrado (duplicidade)")
    void deveRejeitarMembroComCpfDuplicado() {
        // Arrange: já existe um membro com esse CPF
        String cpf = "52998224725";
        membroService.adicionar("Primeiro Membro", cpf, "Bolsista", 20);

        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                membroService.adicionar("Segundo Membro", cpf, "Voluntário", 10)
        );

        assertEquals("CPF já está associado a outro membro.", exception.getMessage());
        assertEquals(1, membroRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT04 - Adicionar membro com Função inválida/não permitida")
    void deveRejeitarMembroComFuncaoInvalida() {
        // Arrange: função fora da lista permitida
        String funcaoInvalida = "Estagiário Fantasma";

        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                membroService.adicionar("Carlos Lima", "52998224725", funcaoInvalida, 15)
        );

        assertEquals("Função inválida.", exception.getMessage());
        assertEquals(0, membroRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT05 - Adicionar membro com Carga Horária inválida (zero ou negativa)")
    void deveRejeitarMembroComCargaHorariaZeroOuNegativa() {
        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                membroService.adicionar("Ana Paula", "52998224725", "Bolsista", 0)
        );

        assertEquals("Carga Horária deve ser maior que zero.", exception.getMessage());
        assertEquals(0, membroRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT06 - Adicionar membro com Carga Horária acima do limite permitido")
    void deveRejeitarMembroComCargaHorariaAcimaDoLimite() {
        // Arrange: limite é 40h semanais
        int cargaHorariaAcimaDoLimite = 41;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () ->
                membroService.adicionar("Pedro Santos", "52998224725", "Bolsista", cargaHorariaAcimaDoLimite)
        );
        assertEquals(0, membroRepository.listarTodos().size());
    }

    @Test
    @DisplayName("CT07 - Remover membro existente da equipe")
    void deveRemoverMembroExistente() {
        // Arrange: existe um membro cadastrado (sem plano de trabalho vinculado)
        Membro membro = membroService.adicionar("Fernanda Costa", "52998224725", "Bolsista", 20);

        // Act
        membroService.remover(membro.getId());

        // Assert: membro não aparece mais na listagem
        assertTrue(membroService.listarTodos().isEmpty());
    }

    @Test
    @DisplayName("CT08 - Cancelar remoção de membro")
    void deveManterMembroQuandoRemocaoForCancelada() {
        // Arrange: existe um membro cadastrado
        membroService.adicionar("Rafael Alves", "52998224725", "Bolsista", 20);

        // Act: "cancelar" equivale, no backend, a nunca chamar remover() -
        // não há ação a executar aqui, propositalmente.

        // Assert: membro permanece na listagem, sem alterações
        assertEquals(1, membroService.listarTodos().size());
    }

    @Test
    @DisplayName("CT09 - Remover membro que possui planos de trabalho vinculados")
    void deveImpedirRemocaoDeMembroComPlanoDeTrabalhoVinculado() {
        // Arrange: membro com plano de trabalho ativo vinculado
        Membro membro = membroService.adicionar("Juliana Rocha", "52998224725", "Bolsista", 20);
        membro.setPossuiPlanoDeTrabalhoVinculado(true);

        // Act + Assert: sistema impede a remoção e avisa sobre o vínculo
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                membroService.remover(membro.getId())
        );

        assertEquals("Não é possível remover o membro: existem planos de trabalho vinculados.",
                exception.getMessage());
        assertEquals(1, membroService.listarTodos().size());
    }
}