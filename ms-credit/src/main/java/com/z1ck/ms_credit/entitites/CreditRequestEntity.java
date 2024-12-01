package com.z1ck.ms_credit.entitites;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;  // Guardamos solo el ID del cliente

    private String creditType;  // Tipo de crédito (Primera vivienda, Segunda vivienda, etc.)
    private Double amount;  // Monto del préstamo solicitado
    private Integer term;  // Plazo en años
    private String status;  // Estado de la solicitud (En revisión, Pre-aprobada, Aprobada, etc.) ejecutivo
    private String autoStatus; //Resultado por el backend

    private Double propertyValue; //valor de la propiedad
    private String savingsCapacity; //capacidad de ahorro R7

    @Lob
    @Column(name = "document")
    private byte[] document;  // Campo para almacenar el archivo como un BLOB
}
