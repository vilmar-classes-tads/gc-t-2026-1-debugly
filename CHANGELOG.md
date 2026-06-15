# Changelog

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

---

## Issue-0 — Gerenciamento de Usuários

### Criar entidade de usuário
- Criada classe `Usuario` com atributos obrigatórios: `id` (autogerado), `nomeCompleto`, `cpf`, `email`, `senha`, `campus`, `areaFormacao`, `titulacao` e `perfis`
- Adicionados atributos opcionais: `nomeSocial`, `sexo`, `linkLattes` e `telefone`
- Implementados `equals` e `hashCode` com base em `cpf` e `email` para garantir unicidade da entidade

### Formulário de cadastro de usuário
- Criado `UsuarioController` com método `exibirFormularioCadastro()` que coleta os dados do usuário via console (`Scanner`)
- Formulário exibe campos obrigatórios (nome, CPF, e-mail, senha, campus, área de formação, titulação) e campos opcionais (nome social, sexo, link Lattes, telefone)
- Atualizado `App.java` para invocar o controller na inicialização da aplicação

### Validação de formulário
- Criado `UsuarioService` com as seguintes validações antes do cadastro:
  - Verificação de preenchimento de todos os campos obrigatórios
  - Validação de formato do CPF (11 dígitos numéricos, ignora pontuação)
  - Validação de formato do e-mail via expressão regular
  - Validação de senha com mínimo de 6 caracteres

### Validação de unicidade
- `UsuarioRepository` implementa busca por CPF e por e-mail com verificação de existência (`existePorCpf`, `existePorEmail`)
- `UsuarioService` chama ambas as verificações antes de persistir, lançando `IllegalArgumentException` em caso de duplicata

### Definir perfis padrão do usuário
- Criado enum `Perfil` com os valores: `ROLE_ADMINISTRADOR`, `ROLE_COORDENADOR`, `ROLE_PESQUISADOR` e `ROLE_AVALIADOR`
- Todo novo `Usuario` recebe automaticamente os perfis `ROLE_COORDENADOR` e `ROLE_AVALIADOR` no momento da criação

---

## Issue-1 — Gerenciamento de Editais

### Classe Edital
- Criada classe `Edital` com atributos: `id` (autogerado), `titulo`, `numero`, `ano`, `dataInicioSubmissao`, `dataFimSubmissao`, `dataInicioAvaliacao` e `dataFimAvaliacao`
- Implementados `equals` e `hashCode` com base em `numero` e `ano` para garantir unicidade do edital

### Método de validação de datas
- Criado `EditalValidator` com método `validar()` que aplica as seguintes regras:
  - Período de submissão: datas obrigatórias e `dataInicio` deve ser anterior a `dataFim`
  - Período de avaliação: datas obrigatórias e `dataInicio` deve ser anterior a `dataFim`
  - Ordem entre períodos: o período de avaliação deve começar após o fim da submissão

### Classe de serviço e repositório em memória
- Criado `EditalRepository` com persistência em memória (`ArrayList`) e operações: `salvar`, `buscarPorId`, `existePorNumeroEAno`, `listarTodos` e `atualizar`
- Criado `EditalService` com os métodos:
  - `cadastrar()` — valida campos obrigatórios, valida datas, verifica unicidade por número e ano, e persiste o edital
  - `editar()` — localiza o edital por id e aplica as mesmas validações antes de atualizar
  - `listarTodos()` — retorna todos os editais cadastrados
