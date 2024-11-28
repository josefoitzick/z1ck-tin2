package com.z1ck.ms_creditsimulation.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditSimulationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID de la simulación, auto-generado

    private long amount;           // Monto del crédito
    private int term;              // Plazo en años
    private double annualInterestRate; // Tasa de interés anual

    private double monthlyPayment; //resultado
}
