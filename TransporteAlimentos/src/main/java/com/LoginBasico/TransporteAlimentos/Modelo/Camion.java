package com.LoginBasico.TransporteAlimentos.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "camion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Camion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String placa;


    @Column(nullable = false)
    private String tipoVehiculo;

    // Un camion puede tener un conductor asociado (o ninguno todavia)
    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;
}
