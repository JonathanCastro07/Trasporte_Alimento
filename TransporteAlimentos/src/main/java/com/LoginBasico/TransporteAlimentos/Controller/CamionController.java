package com.LoginBasico.TransporteAlimentos.Controller;

import com.LoginBasico.TransporteAlimentos.Modelo.Camion;
import com.LoginBasico.TransporteAlimentos.Modelo.Conductor;
import com.LoginBasico.TransporteAlimentos.Modelo.Rol;
import com.LoginBasico.TransporteAlimentos.Modelo.Usuario;
import com.LoginBasico.TransporteAlimentos.Repository.CamionRepository;
import com.LoginBasico.TransporteAlimentos.Repository.ConductorRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class CamionController {

    private final CamionRepository camionRepository;
    private final ConductorRepository conductorRepository;

    public CamionController(CamionRepository camionRepository, ConductorRepository conductorRepository) {
        this.camionRepository = camionRepository;
        this.conductorRepository = conductorRepository;
    }

    private Usuario getUsuarioAutenticado(HttpServletRequest request) {
        return (Usuario) request.getAttribute("usuarioAutenticado");
    }


    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Camion camion, HttpServletRequest request) {
        Usuario usuario = getUsuarioAutenticado(request);

        if (usuario.getRol() != Rol.ADMINISTRADOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Solo el administrador puede registrar camiones");
        }

        Camion guardado = camionRepository.save(camion);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }


    @GetMapping
    public ResponseEntity<List<Camion>> listar() {
        return ResponseEntity.ok(camionRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Camion> obtener(@PathVariable Long id) {
        return camionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{camionId}/conductor/{conductorId}")
    public ResponseEntity<?> asociarConductor(@PathVariable Long camionId,
                                              @PathVariable Long conductorId,
                                              HttpServletRequest request) {

        Usuario usuario = getUsuarioAutenticado(request);

        if (usuario.getRol() != Rol.SUPERVISOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Solo el supervisor puede asociar conductores a camiones");
        }

        Camion camion = camionRepository.findById(camionId).orElse(null);
        if (camion == null) {
            return ResponseEntity.notFound().build();
        }

        Conductor conductor = conductorRepository.findById(conductorId).orElse(null);
        if (conductor == null) {
            return ResponseEntity.notFound().build();
        }

        camion.setConductor(conductor);
        Camion actualizado = camionRepository.save(camion);
        return ResponseEntity.ok(actualizado);
    }
}
