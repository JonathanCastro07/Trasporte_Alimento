package com.LoginBasico.TransporteAlimentos.Controller;

import com.LoginBasico.TransporteAlimentos.Modelo.Rol;
import com.LoginBasico.TransporteAlimentos.Modelo.Usuario;
import com.LoginBasico.TransporteAlimentos.Service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    private Usuario getUsuarioAutenticado(HttpServletRequest request) {
        return (Usuario) request.getAttribute("usuarioAutenticado");
    }

    // Solo el ADMINISTRADOR puede registrar usuarios nuevos
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario, HttpServletRequest request) {
        Usuario usuarioAutenticado = getUsuarioAutenticado(request);

        if (usuarioAutenticado.getRol() != Rol.ADMINISTRADOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Solo el administrador puede registrar usuarios");
        }

        Usuario guardado = usuarioService.registrarUsuario(usuario);

        if (guardado == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El username ya existe");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
}
