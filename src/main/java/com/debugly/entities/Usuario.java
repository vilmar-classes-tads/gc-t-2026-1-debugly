import java.util.Objects;

public class Usuario {
    private static Long contadorId = 1L;

    private Long id;
    private String nomeCompleto;
    private String senha;
    private String cpf;
    private String email;
    private String campus;
    private String areaFormacao;
    private List<Perfil> perfis;

    public Usuario(String nomeCompleto, String senha, String cpf, String email, String campus, String areaFormacao, Perfil perfil) {
        this.id = contadorId++;
        this.nomeCompleto = nomeCompleto;
        this.senha = senha;
        this.cpf = cpf;
        this.email = email;
        this.campus = campus;
        this.areaFormacao = areaFormacao;
        this.perfil = perfil;
    }

    // Getters e Setters
    public Long getId() { return id; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }

    public String getAreaFormacao() { return areaFormacao; }
    public void setAreaFormacao(String areaFormacao) { this.areaFormacao = areaFormacao; }

    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }

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
                ", perfil=" + perfil +
                '}';
    }
}