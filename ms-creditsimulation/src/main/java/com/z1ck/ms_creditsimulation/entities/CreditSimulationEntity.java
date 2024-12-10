package com.z1ck.ms_creditsimulation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credit-simulations")
public class CreditSimulationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID de la simulación, auto-generado

    private long amount;           // Monto del crédito
    private int term;              // Plazo en años
    private double annualInterestRate; // Tasa de interés anual

    private double monthlyPayment; //resultado
}
