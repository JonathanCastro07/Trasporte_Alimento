package com.LoginBasico.TransporteAlimentos.Security;

import com.LoginBasico.TransporteAlimentos.Modelo.Usuario;
import com.LoginBasico.TransporteAlimentos.Service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AutenticacionFilter extends HttpFilter {

    private final UsuarioService usuarioService;

    public AutenticacionFilter(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader("Authorization");


        if (header == null || !header.startsWith("Basic ")) {
            rechazar(response, "Debes enviar usuario y contraseña (Basic Auth)");
            return;
        }

        try {

            String base64Credenciales = header.substring("Basic ".length());
            String credenciales = new String(Base64.getDecoder().decode(base64Credenciales), StandardCharsets.UTF_8);
            String[] partes = credenciales.split(":", 2);

            if (partes.length != 2) {
                rechazar(response, "Formato de credenciales invalido");
                return;
            }

            String username = partes[0];
            String password = partes[1];

            Usuario usuario = usuarioService.validarCredenciales(username, password);

            if (usuario == null) {
                rechazar(response, "Usuario o contraseña incorrectos");
                return;
            }

            request.setAttribute("usuarioAutenticado", usuario);

            chain.doFilter(request, response);

        } catch (IllegalArgumentException e) {
            rechazar(response, "El header Authorization esta mal formado");
        }
    }

    private void rechazar(HttpServletResponse response, String mensaje) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + mensaje + "\"}");
    }
}
