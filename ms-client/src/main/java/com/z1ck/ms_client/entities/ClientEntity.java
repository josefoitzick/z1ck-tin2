package com.z1ck.ms_client.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut;
    private String name;
    private String email;
    private String numberPhone;
    private String address;
    private String historyCredit; //historial crediticio
    private Double savingsBalance; //saldo ahorros
    private int age; //edad del cliente
    private int monthsOfWork; //anios de de trabajo

    private Double monthlyIncome; //ingreso mensual
    private Double currentDebts; //deudas
    private int accountAgeYears; //cuenta de ahorros antiguedad para R74



    //R7 Capacidad de ahorro
    @ElementCollection
    @CollectionTable(name = "client_savings", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "balance")
    private List<Double> savingsHistory = new ArrayList<>();  // Historial de ahorros mensuales

    @ElementCollection
    @CollectionTable(name = "client_deposits", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "deposit")
    private List<Double> depositHistory = new ArrayList<>();  // Historial de depósitos mensuales
}
