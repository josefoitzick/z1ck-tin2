package com.z1ck.ms_creditsimulation.services;

import com.z1ck.ms_creditsimulation.entities.CreditSimulationEntity;
import com.z1ck.ms_creditsimulation.repositories.CreditSimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditSimulationService {
    @Autowired
    private CreditSimulationRepository creditSimulationRepository;

    public double simulateMonthlyPayment(CreditSimulationEntity simulation) {
        double principal = simulation.getAmount();
        double monthlyInterestRate = simulation.getAnnualInterestRate() / 12 / 100;
        int numberOfPayments = simulation.getTerm() * 12;

        double monthlyPayment;
        if (monthlyInterestRate == 0) {
            monthlyPayment = principal / numberOfPayments;
        } else {
            // Fórmula para el cálculo de cuota mensual (cuota fija)
            monthlyPayment = (principal * monthlyInterestRate) /
                    (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
        }

        // Guardar la simulación en la base de datos
        simulation.setMonthlyPayment(monthlyPayment);
        creditSimulationRepository.save(simulation);

        return monthlyPayment;
    }
}
