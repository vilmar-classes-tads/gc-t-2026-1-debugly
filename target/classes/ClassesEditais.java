main 
// apagar depois
public class Main {
    public static void main(String[] args) {
        UsuarioService service = new UsuarioService();
        
        try {
         
            Usuario admin = service.cadastrarUsuario("Carlos Silva", "12345678901", "carlos@email.com", Perfil.ADMINISTRADOR);
            Usuario coord = service.cadastrarUsuario("Marina Souza", "98765432100", "marina@email.com", Perfil.COORDENADOR);
            
   service.cadastrarUsuario("Teste Duplicado", "12345678901", "outro@email.com", Perfil.PESQUISADOR);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
       
        System.out.println("\n--- Todos os usuários ---");
        service.listarTodos().forEach(System.out::println);
        
      
        System.out.println("\n--- Busca por CPF ---");
        service.buscarPorCpf("98765432100").ifPresent(System.out::println);
    }
}