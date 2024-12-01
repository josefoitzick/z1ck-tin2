package com.z1ck.ms_credittotalcost.services;

import com.z1ck.ms_credittotalcost.clients.CreditRequestFeignClient;
import com.z1ck.ms_credittotalcost.entities.CreditRequestEntity;
import com.z1ck.ms_credittotalcost.entities.CreditTotalCostEntity;
import com.z1ck.ms_credittotalcost.repositories.CreditTotalCostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditTotalCostService {

    @Autowired
    private CreditRequestFeignClient creditRequestFeignClient; // Feign client para solicitudes de crédito

    @Autowired
    private CreditTotalCostRepository creditTotalCostRepository;

    public CreditTotalCostEntity calculateAndSaveTotalCost(Long creditRequestId) {
        // Verificar si ya existe un costo total para esta solicitud de crédito
        List<CreditTotalCostEntity> existingCosts = creditTotalCostRepository.findByCreditRequestId(creditRequestId);
        if (!existingCosts.isEmpty()) {
            return existingCosts.get(0); // Devolver el primer costo encontrado
        }

        // Obtener la solicitud de crédito a través del Feign client
        CreditRequestEntity creditRequest = creditRequestFeignClient.getCreditRequestById(creditRequestId);

        if (creditRequest == null) {
            throw new IllegalArgumentException("Solicitud de crédito no encontrada");
        }

        // Determinar la tasa de interés dependiendo del tipo de préstamo
        double interestRate;
        switch (creditRequest.getCreditType()) {
            case "Primera vivienda":
                interestRate = 4.5;
                break;
            case "Segunda vivienda":
                interestRate = 5.0;
                break;
            case "Propiedades Comerciales":
                interestRate = 6.0;
                break;
            case "Remodelación":
                interestRate = 5.25;
                break;
            default:
                throw new IllegalArgumentException("Tipo de crédito no reconocido: " + creditRequest.getCreditType());
        }

        // Convertir la tasa de interés anual a tasa mensual
        double monthlyInterestRate = interestRate / 12 / 100;

        // Obtener el monto del préstamo y el número total de pagos (número de meses)
        double loanAmount = creditRequest.getAmount();
        int termInYears = creditRequest.getTerm();
        int totalPayments = termInYears * 12;

        // Calcular la cuota mensual usando la fórmula de anualidad
        double monthlyPayment = loanAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalPayments)) /
                (Math.pow(1 + monthlyInterestRate, totalPayments) - 1);

        // Calcular los seguros
        double insuranceCostFire = 20000; // Seguro de incendio: constante de 20000 CLP por mes
        double insuranceCostLife = loanAmount * 0.0003; // Seguro de desgravamen: 0.03% del monto del préstamo por mes

        // Calcular la comisión por administración
        double commissionCost = loanAmount * 0.01; // Ejemplo: 1% de comisión

        // Calcular el costo mensual total incluyendo seguros
        double totalMonthlyCost = monthlyPayment + insuranceCostFire + insuranceCostLife;

        // Calcular el costo total del crédito (costo mensual total * número de pagos + comisión)
        double totalCost = (totalMonthlyCost * totalPayments) + commissionCost;

        // Crear y guardar la entidad de costo total
        CreditTotalCostEntity totalCostEntity = new CreditTotalCostEntity();
        totalCostEntity.setCreditRequestId(creditRequestId);
        totalCostEntity.setMonthlyPayment(monthlyPayment); // Cuota mensual
        totalCostEntity.setInsuranceCostLife(insuranceCostLife); // Seguro de desgravamen
        totalCostEntity.setInsuranceCostFire(insuranceCostFire); // Seguro de incendio
        totalCostEntity.setComissionCost(commissionCost); // Comisión administrativa
        totalCostEntity.setMonthlyTotalCost(totalMonthlyCost); // Costo mensual total (cuota + seguros)
        totalCostEntity.setTotalCost(totalCost); // Costo total del crédito

        return creditTotalCostRepository.save(totalCostEntity);
    }
}
