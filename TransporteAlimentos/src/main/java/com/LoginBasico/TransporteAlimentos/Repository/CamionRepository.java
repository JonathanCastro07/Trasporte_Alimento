package com.LoginBasico.TransporteAlimentos.Repository;

import com.LoginBasico.TransporteAlimentos.Modelo.Camion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CamionRepository extends JpaRepository <Camion, Long> {
}
