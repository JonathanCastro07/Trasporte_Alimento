package com.LoginBasico.TransporteAlimentos.Controller;

import com.LoginBasico.TransporteAlimentos.Modelo.Usuario;
import com.LoginBasico.TransporteAlimentos.Security.CustomUserDetails;
import com.LoginBasico.TransporteAlimentos.Security.JwtUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LoginBasico.TransporteAlimentos.Service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil){
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }
    public record LoginRequest(String username, String password){

    }
    
    public record LoginResponse(String token){

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioService.validarCredenciales(request.username(), request.password());
        
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuariop o contraseña incorrecta");
        }

        String token = jwtUtil.generarToken(new CustomUserDetails(usuario));
        return ResponseEntity.ok(new LoginResponse(token));
    }
    
}
