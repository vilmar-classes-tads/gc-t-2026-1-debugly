package com.debugly;

import com.debugly.controllers.UsuarioController;
import com.debugly.repositories.UsuarioRepository;
import com.debugly.services.UsuarioService;

public class App {
    public static void main(String[] args) {
        UsuarioRepository repository = new UsuarioRepository();
        UsuarioService service = new UsuarioService(repository);
        UsuarioController controller = new UsuarioController(service);

        controller.exibirFormularioCadastro();
    }
}