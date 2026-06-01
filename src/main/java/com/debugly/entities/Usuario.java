import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
    private static Long contadorId = 1L;

    // Obrigatórios
    private Long id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String senha;
    private String campus;
    private String areaFormacao;
    private String titulacao;
    private List<Perfil> perfis;

    // Opcionais
    private String nomeSocial;
    private String sexo;
    private String linkLattes;
    private String telefone;

    public Usuario(String nomeCompleto, String cpf, String email, String senha,
                   String campus, String areaFormacao, String titulacao) {
        this.id = contadorId++;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.campus = campus;
        this.areaFormacao = areaFormacao;
        this.titulacao = titulacao;
        this.perfis = new ArrayList<>();
        // Issue 5 — roles padrão atribuídas na criação
        this.perfis.add(Perfil.ROLE_COORDENADOR);
        this.perfis.add(Perfil.ROLE_AVALIADOR);
    }

    // Getters obrigatórios
    public Long getId() { return id; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }

    public String getAreaFormacao() { return areaFormacao; }
    public void setAreaFormacao(String areaFormacao) { this.areaFormacao = areaFormacao; }

    public String getTitulacao() { return titulacao; }
    public void setTitulacao(String titulacao) { this.titulacao = titulacao; }

    public List<Perfil> getPerfis() { return perfis; }
    public void setPerfis(List<Perfil> perfis) { this.perfis = perfis; }

    // Getters opcionais
    public String getNomeSocial() { return nomeSocial; }
    public void setNomeSocial(String nomeSocial) { this.nomeSocial = nomeSocial; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getLinkLattes() { return linkLattes; }
    public void setLinkLattes(String linkLattes) { this.linkLattes = linkLattes; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(cpf, usuario.cpf) || Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, email);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", campus='" + campus + '\'' +
                ", areaFormacao='" + areaFormacao + '\'' +
                ", titulacao='" + titulacao + '\'' +
                ", perfis=" + perfis +
                '}';
    }
}