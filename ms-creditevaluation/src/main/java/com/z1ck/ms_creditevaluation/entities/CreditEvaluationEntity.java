package com.z1ck.ms_creditevaluation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credits_evaluations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditEvaluationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private Long creditRequestId;  // Relación con la solicitud evaluada

    @Column(name = "client_id", nullable = false)
    private Long clientId;  // Guardamos solo el ID del cliente

    private Double quotaToIncomeRatio;  // Relación cuota/ingreso
    private Double debtToIncomeRatio;  // Relación deuda/ingreso
    private Boolean stableEmployment;  // Indica si tiene empleo estable
    private Boolean goodCreditHistory;  // Indica si tiene buen historial crediticio
    private Boolean sufficientSavings;  // Indica si tiene capacidad de ahorro suficiente
    private String evaluationResult;  // Resultado de la evaluación (Aprobado, Rechazado) ejecutivo

    public Boolean isStableEmployment() {
        return stableEmployment;  // Asegúrate de tener este método
    }

    public Boolean isGoodCreditHistory() {
        return goodCreditHistory;  // Asegúrate de tener este método
    }

    public Boolean isSufficientSavings() {
        return sufficientSavings;  // Asegúrate de tener este método
    }
}
