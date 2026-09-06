package com.LoginBasico.TransporteAlimentos.Controller;

import com.LoginBasico.TransporteAlimentos.Modelo.Rol;
import com.LoginBasico.TransporteAlimentos.Modelo.Usuario;
import com.LoginBasico.TransporteAlimentos.Security.CustomUserDetails;
import com.LoginBasico.TransporteAlimentos.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    private Usuario getUsuarioAutenticado() {
        CustomUserDetails userDetails = (CustomUserDetails)
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsuario();
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioAutenticado = getUsuarioAutenticado();

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
