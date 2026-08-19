package com.LoginBasico.TransporteAlimentos.Repository;

import com.LoginBasico.TransporteAlimentos.Modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepopsitory extends JpaRepository <Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
