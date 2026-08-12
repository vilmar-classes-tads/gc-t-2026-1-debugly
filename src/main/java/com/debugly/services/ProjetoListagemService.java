
package com.debugly.services;

import com.debugly.entities.Edital;
import com.debugly.entities.Perfil;
import com.debugly.entities.Projeto;
import com.debugly.entities.Usuario;

import java.util.List;
import java.util.stream.Collectors;


public class ProjetoListagemService {

    public List<Projeto> filtrarPorEdital(List<Projeto> projetos, Edital edital) {
        return projetos.stream()
                .filter(p -> p.getEdital() != null && p.getEdital().equals(edital))
                .collect(Collectors.toList());
    }

    public List<Projeto> filtrarPorCampus(List<Projeto> projetos, String campus) {
        return projetos.stream()
                .filter(p -> campus.equals(p.getCampus()))
                .collect(Collectors.toList());
    }

    public List<Projeto> filtrarPorStatus(List<Projeto> projetos, String status) {
        return projetos.stream()
                .filter(p -> status.equals(p.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Projeto> listarParaUsuario(Usuario usuario, List<Projeto> projetos) {
        if (usuario.getPerfis().contains(Perfil.ROLE_ADMINISTRADOR)) {
            return List.copyOf(projetos);
        }

        if (usuario.getPerfis().contains(Perfil.ROLE_GESTOR_DIRETOR)) {
            return filtrarPorCampus(projetos, usuario.getCampus());
        }

        throw new SecurityException("Acesso não autorizado à listagem de projetos.");
    }
}