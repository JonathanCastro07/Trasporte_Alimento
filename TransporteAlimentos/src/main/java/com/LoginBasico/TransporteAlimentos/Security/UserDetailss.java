package com.LoginBasico.TransporteAlimentos.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.LoginBasico.TransporteAlimentos.Modelo.Usuario;
import com.LoginBasico.TransporteAlimentos.Repository.UsuarioRepopsitory;

@Service
public class UserDetailss implements UserDetailsService {
    
    private final UsuarioRepopsitory usuarioRepopsitory;

    public UserDetailss(UsuarioRepopsitory usuarioRepopsitory){
        this.usuarioRepopsitory = usuarioRepopsitory;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepopsitory.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        return new CustomUserDetails(usuario);
    }
} 
