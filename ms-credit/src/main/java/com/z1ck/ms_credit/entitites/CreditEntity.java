package com.z1ck.ms_credit.entitites;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "credits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;  // Guardamos solo el ID del cliente

    private Double amount;  // Monto del crédito aprobado
    private Double interestRate;  // Tasa de interés aplicada
    private Double monthlyPayment;  // Cuota mensual a pagar
    private Integer term;  // Plazo del crédito en años

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate approvalDate;  // Fecha de aprobación del crédito
}
