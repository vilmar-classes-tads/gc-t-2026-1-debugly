public class App {
    public static void main(String[] args) {
        UsuarioRepository repository = new UsuarioRepository();
        UsuarioService service = new UsuarioService(repository);
        UsuarioController controller = new UsuarioController(service);

        controller.exibirFormularioCadastro();
    }
}