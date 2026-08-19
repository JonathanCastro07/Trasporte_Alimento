package com.LoginBasico.TransporteAlimentos.Repository;

import com.LoginBasico.TransporteAlimentos.Modelo.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConductorRepository extends JpaRepository <Conductor, Long> {
}
