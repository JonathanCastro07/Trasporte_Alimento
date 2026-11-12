package com.LoginBasico.TransporteAlimentos.Controller;


import com.LoginBasico.TransporteAlimentos.Modelo.Conductor;
import com.LoginBasico.TransporteAlimentos.Modelo.Rol;
import com.LoginBasico.TransporteAlimentos.Modelo.Usuario;
import com.LoginBasico.TransporteAlimentos.Repository.ConductorRepository;
import com.LoginBasico.TransporteAlimentos.Security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conductores")
public class ConductorController {

    private final ConductorRepository conductorRepository;

    public ConductorController(ConductorRepository conductorRepository) {
        this.conductorRepository = conductorRepository;
    }

    private Usuario getUsuarioAutenticado() {
    CustomUserDetails userDetails = (CustomUserDetails)
            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userDetails.getUsuario();
}

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Conductor conductor) {
        Usuario usuario = getUsuarioAutenticado();

        if (usuario.getRol() != Rol.ADMINISTRADOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Solo el administrador puede registrar conductores");
        }

        Conductor guardado = conductorRepository.save(conductor);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @GetMapping
    public ResponseEntity<List<Conductor>> listar() {
        return ResponseEntity.ok(conductorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conductor> obtener(@PathVariable Long id) {
        return conductorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
