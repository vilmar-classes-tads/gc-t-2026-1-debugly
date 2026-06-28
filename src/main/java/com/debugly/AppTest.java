package com.debugly;

import com.debugly.repositories.UsuarioRepository;
import com.debugly.services.UsuarioService;
import com.debugly.entities.Usuario;

public class AppTest {
    public static void main(String[] args) {
        System.out.println("✓ Iniciando teste da aplicação...");
        
        UsuarioRepository repository = new UsuarioRepository();
        UsuarioService service = new UsuarioService(repository);
        
        try {
            Usuario usuario = service.cadastrar(
                "João Silva", "123.456.789-00", "joao@email.com", 
                "senha123", "Campus A", "Tecnologia", "Graduação"
            );
            System.out.println("✓ Usuário criado: " + usuario.getNomeCompleto());
            System.out.println("✓ Aplicação funcionando corretamente!");
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
            System.exit(1);
        }
    }
}