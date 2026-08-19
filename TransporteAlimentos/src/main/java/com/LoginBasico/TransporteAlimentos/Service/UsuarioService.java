package com.LoginBasico.TransporteAlimentos.Service;

import com.LoginBasico.TransporteAlimentos.Modelo.Usuario;
import com.LoginBasico.TransporteAlimentos.Repository.UsuarioRepopsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepopsitory usuarioRepopsitory;

    public Usuario validarCredenciales(String username, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepopsitory.findByUsername(username);

        if (usuarioOpt.isEmpty()) {
            return null;
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.getPassword().equals(password)) {
            return null;
        }

        return usuario;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        Optional<Usuario> existente = usuarioRepopsitory.findByUsername(usuario.getUsername());

        if (existente.isPresent()) {
            return null;
        }

        return usuarioRepopsitory.save(usuario);
    }
}
