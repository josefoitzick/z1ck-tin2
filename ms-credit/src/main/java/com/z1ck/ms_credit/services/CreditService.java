package com.z1ck.ms_credit.services;


import com.z1ck.ms_credit.entitites.CreditEntity;
import com.z1ck.ms_credit.entitites.CreditRequestEntity;
import com.z1ck.ms_credit.repositories.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CreditService {
    @Autowired
    private CreditRepository creditRepository;

    // Obtener todos los créditos
    public List<CreditEntity> getCredits() {
        return creditRepository.findAll();
    }

    // Obtener un crédito por ID
    public CreditEntity getCreditById(Long id) {
        Optional<CreditEntity> credit = creditRepository.findById(id);
        return credit.orElse(null);  // Devuelve null si no se encuentra
    }

    // Guardar un nuevo crédito
    public CreditEntity saveCredit(CreditEntity credit) {
        return creditRepository.save(credit);
    }

    // Actualizar un crédito existente
    public CreditEntity updateCredit(CreditEntity credit) {
        return creditRepository.save(credit);  // JPA detecta si el crédito ya existe y lo actualiza
    }

    // Eliminar un crédito por ID
    public boolean deleteCredit(Long id) {
        Optional<CreditEntity> credit = creditRepository.findById(id);
        if (credit.isPresent()) {
            creditRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //################################################################################################
    //LOGICA DE NEGOCIOS
    //################################################################################################

    // Crear crédito a partir de una solicitud aprobada
    public CreditEntity createCreditFromRequest(CreditRequestEntity request) {
        System.out.println("Inicio de creación del crédito desde la solicitud.");

        // Validar que la solicitud no sea nula
        if (request == null) {
            System.out.println("Error: La solicitud de crédito es nula.");
            throw new IllegalArgumentException("La solicitud de crédito no puede ser nula.");
        }

        // Imprimir detalles iniciales de la solicitud
        System.out.println("Detalles de la solicitud recibida:");
        System.out.println("ClientId: " + request.getClientId());
        System.out.println("Amount: " + request.getAmount());
        System.out.println("Term: " + request.getTerm());
        System.out.println("CreditType: " + request.getCreditType());

        CreditEntity credit = new CreditEntity();
        credit.setClientId(request.getClientId());
        credit.setAmount(request.getAmount());
        credit.setTerm(request.getTerm());
        credit.setApprovalDate(LocalDate.now());

        System.out.println("Fecha de aprobación asignada: " + credit.getApprovalDate());

        // Asignar la tasa de interés basada en el tipo de crédito
        double interestRate;
        try {
            switch (request.getCreditType()) {
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
                    System.out.println("Error: Tipo de crédito no reconocido: " + request.getCreditType());
                    throw new IllegalArgumentException("Tipo de crédito no reconocido: " + request.getCreditType());
            }
        } catch (Exception e) {
            System.out.println("Error al asignar la tasa de interés: " + e.getMessage());
            throw e;
        }

        // Asignar la tasa de interés calculada
        credit.setInterestRate(interestRate);
        System.out.println("Tasa de interés asignada: " + interestRate);

        // Convertir la tasa de interés anual a tasa mensual
        double monthlyInterestRate = interestRate / 12 / 100;
        System.out.println("Tasa de interés mensual calculada: " + monthlyInterestRate);

        // Obtener el monto del préstamo y el número total de pagos
        double loanAmount = request.getAmount();
        int termInYears = request.getTerm();
        int totalPayments = termInYears * 12;

        System.out.println("Monto del préstamo: " + loanAmount);
        System.out.println("Plazo en años: " + termInYears);
        System.out.println("Número total de pagos: " + totalPayments);

        // Calcular la cuota mensual usando la fórmula de anualidad
        try {
            double monthlyPayment = loanAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalPayments)) /
                    (Math.pow(1 + monthlyInterestRate, totalPayments) - 1);

            // Asignar la cuota mensual calculada al crédito
            credit.setMonthlyPayment(monthlyPayment);
            System.out.println("Cuota mensual calculada: " + monthlyPayment);
        } catch (Exception e) {
            System.out.println("Error al calcular la cuota mensual: " + e.getMessage());
            throw e;
        }

        System.out.println("Crédito creado exitosamente: " + credit);
        return credit;
    }


    // Obtener todos los créditos de un cliente por su clientId
    public List<CreditEntity> getCreditsByClientId(Long clientId) {
        return creditRepository.findByClientId(clientId);
    }
}
