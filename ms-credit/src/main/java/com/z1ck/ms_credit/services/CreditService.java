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
        CreditEntity credit = new CreditEntity();
        credit.setClientId(request.getClientId());  // Guardamos el ID del cliente
        credit.setAmount(request.getAmount());
        credit.setTerm(request.getTerm());
        credit.setApprovalDate(LocalDate.now());

        // Asignar la tasa de interés basada en el tipo de crédito
        double interestRate;
        switch (request.getCreditType()) {
            case "Primera vivienda":
                interestRate = 4.5;  // Tasa promedio para Primera Vivienda
                break;
            case "Segunda vivienda":
                interestRate = 5.0;   // Tasa promedio para Segunda Vivienda
                break;
            case "Propiedades Comerciales":
                interestRate = 6.0;   // Tasa promedio para Propiedades Comerciales
                break;
            case "Remodelación":
                interestRate = 5.25;  // Tasa promedio para Remodelación
                break;
            default:
                throw new IllegalArgumentException("Tipo de crédito no reconocido: " + request.getCreditType());
        }

        // Asignar la tasa de interés calculada
        credit.setInterestRate(interestRate);

        // Convertir la tasa de interés anual a tasa mensual
        double monthlyInterestRate = interestRate / 12 / 100;

        // Obtener el monto del préstamo y el número total de pagos (número de meses)
        double loanAmount = request.getAmount();
        int termInYears = request.getTerm();
        int totalPayments = termInYears * 12;

        // Calcular la cuota mensual usando la fórmula de anualidad
        double monthlyPayment = loanAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalPayments)) /
                (Math.pow(1 + monthlyInterestRate, totalPayments) - 1);

        // Asignar la cuota mensual calculada al crédito
        credit.setMonthlyPayment(monthlyPayment);

        return creditRepository.save(credit);
    }

    // Obtener todos los créditos de un cliente por su clientId
    public List<CreditEntity> getCreditsByClientId(Long clientId) {
        return creditRepository.findByClientId(clientId);
    }
}
