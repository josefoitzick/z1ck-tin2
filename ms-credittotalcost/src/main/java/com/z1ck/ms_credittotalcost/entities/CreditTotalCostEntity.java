package com.z1ck.ms_credittotalcost.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credit_total_costs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditTotalCostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "credit_request_id", unique = true, nullable = false)
    private Long creditRequestId;

    @Column(name = "monthly_payment", nullable = false)
    private Double monthlyPayment; // Cuota mensual del préstamo (sin seguros)

    @Column(name = "insurance_cost_life", nullable = false)
    private Double insuranceCostLife; // Seguro de desgravamen

    @Column(name = "insurance_cost_fire", nullable = false)
    private Double insuranceCostFire; // Seguro de incendio

    @Column(name = "comission_cost", nullable = false)
    private Double comissionCost; // Comisión administrativa

    @Column(name = "monthly_total_cost", nullable = false)
    private Double monthlyTotalCost; // Costo mensual total (cuota + seguros)

    @Column(name = "total_cost", nullable = false)
    private Double totalCost; // Costo total del crédito
}
