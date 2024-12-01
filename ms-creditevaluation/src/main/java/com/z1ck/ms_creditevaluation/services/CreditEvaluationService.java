package com.z1ck.ms_creditevaluation.services;

import com.z1ck.ms_creditevaluation.clients.ClientFeignClient;
import com.z1ck.ms_creditevaluation.clients.CreditRequestFeignClient;
import com.z1ck.ms_creditevaluation.entities.ClientEntity;
import com.z1ck.ms_creditevaluation.entities.CreditEvaluationEntity;
import com.z1ck.ms_creditevaluation.entities.CreditRequestEntity;
import com.z1ck.ms_creditevaluation.repositories.CreditEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditEvaluationService {
    @Autowired
    private CreditEvaluationRepository creditEvaluationRepository;

    @Autowired
    private CreditRequestFeignClient creditRequestFeignClient; // Feign client para solicitudes de crédito

    @Autowired
    private ClientFeignClient clientFeignClient; // Feign client para clientes

    // Obtener todas las evaluaciones de crédito
    public List<CreditEvaluationEntity> getEvaluations() {
        return creditEvaluationRepository.findAll();
    }

    // Obtener evaluación de crédito por ID
    public CreditEvaluationEntity getEvaluationById(Long id) {
        Optional<CreditEvaluationEntity> evaluation = creditEvaluationRepository.findById(id);
        return evaluation.orElse(null);
    }

    // Guardar una nueva evaluación
    public CreditEvaluationEntity saveEvaluation(CreditEvaluationEntity evaluation) {
        return creditEvaluationRepository.save(evaluation);
    }

    // Actualizar una evaluación existente
    public CreditEvaluationEntity updateEvaluation(CreditEvaluationEntity evaluation) {
        return creditEvaluationRepository.save(evaluation);
    }

    // Eliminar una evaluación de crédito por ID
    public boolean deleteEvaluation(Long id) {
        if (creditEvaluationRepository.existsById(id)) {
            creditEvaluationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Evaluar solicitud de crédito manualmente
    public CreditEvaluationEntity evaluateCreditRequestManually(Long requestId) {
        CreditRequestEntity request = creditRequestFeignClient.getCreditRequestById(requestId);
        return evaluateCreditRequest(request);
    }

    public CreditEvaluationEntity evaluateCreditRequest(CreditRequestEntity request) {
        CreditEvaluationEntity evaluation = new CreditEvaluationEntity();
        evaluation.setCreditRequestId(request.getId());
        evaluation.setClientId(request.getClientId());

        ClientEntity client = clientFeignClient.getClientById(request.getClientId());

        double quotaToIncomeRatio = calculateQuotaToIncomeRatio(request, client);
        double debtToIncomeRatio = calculateDebtToIncomeRatio(client);

        evaluation.setQuotaToIncomeRatio(quotaToIncomeRatio);
        evaluation.setDebtToIncomeRatio(debtToIncomeRatio);

        boolean goodCreditHistory = "BUENO".equals(client.getHistoryCredit());
        boolean stableEmployment = client.getMonthsOfWork() >= 12;
        boolean sufficientSavings = client.getSavingsBalance() >= (client.getMonthlyIncome() * 0.1);
        double propertyValue = request.getPropertyValue();
        double financingLimit = 0.8 * propertyValue;
        boolean withinLoanLimit = request.getAmount() <= financingLimit;
        int remainingYears = 75 - client.getAge();
        boolean meetsAgeRequirement = remainingYears >= request.getTerm();

        evaluation.setGoodCreditHistory(goodCreditHistory);
        evaluation.setStableEmployment(stableEmployment);
        evaluation.setSufficientSavings(sufficientSavings);

        boolean approved = (quotaToIncomeRatio <= 0.35 && debtToIncomeRatio <= 0.50 &&
                goodCreditHistory && stableEmployment &&
                sufficientSavings && withinLoanLimit && meetsAgeRequirement);

        evaluation.setEvaluationResult(approved ? "Aprobado" : "Rechazado");

        creditEvaluationRepository.save(evaluation);

        String savingsCapacity = evaluateSavingsCapacity(client, request.getAmount());
        request.setSavingsCapacity(savingsCapacity);

        request.setAutoStatus(approved ? "Aprobado" : "Rechazado");
        creditRequestFeignClient.updateCreditRequest(request);

        return evaluation;
    }

    private double calculateQuotaToIncomeRatio(CreditRequestEntity request, ClientEntity client) {
        double monthlyIncome = client.getMonthlyIncome();
        double annualInterestRate;
        switch (request.getCreditType()) {
            case "Primera vivienda":
                annualInterestRate = 4.5;
                break;
            case "Segunda vivienda":
                annualInterestRate = 5.0;
                break;
            case "Propiedades Comerciales":
                annualInterestRate = 6.0;
                break;
            case "Remodelación":
                annualInterestRate = 5.25;
                break;
            default:
                throw new IllegalArgumentException("Tipo de crédito no reconocido: " + request.getCreditType());
        }
        double monthlyInterestRate = (annualInterestRate / 100) / 12;
        int totalPayments = request.getTerm() * 12;
        double amount = request.getAmount();
        double monthlyPayment = (amount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalPayments)) /
                (Math.pow(1 + monthlyInterestRate, totalPayments) - 1);

        return monthlyPayment / monthlyIncome;
    }

    private double calculateDebtToIncomeRatio(ClientEntity client) {
        return client.getCurrentDebts() / client.getMonthlyIncome();
    }

    // Evaluación de la capacidad de ahorro (R7)
    public String evaluateSavingsCapacity(ClientEntity client, double loanAmount) {
        boolean rule71 = checkMinimumBalance(client, loanAmount);
        boolean rule72 = checkConsistentSavings(client);
        boolean rule73 = checkRegularDeposits(client);
        boolean rule74 = checkBalanceBasedOnAccountAge(client, loanAmount);
        boolean rule75 = checkRecentWithdrawals(client);

        int rulesPassed = (rule71 ? 1 : 0) + (rule72 ? 1 : 0) + (rule73 ? 1 : 0) + (rule74 ? 1 : 0) + (rule75 ? 1 : 0);

        if (rulesPassed == 5) {
            return "sólida";
        } else if (rulesPassed >= 3) {
            return "moderada";
        } else {
            return "insuficiente";
        }
    }

    private boolean checkMinimumBalance(ClientEntity client, double loanAmount) {
        return client.getSavingsBalance() >= (loanAmount * 0.1);
    }

    private boolean checkConsistentSavings(ClientEntity client) {
        List<Double> savingsHistory = client.getSavingsHistory();
        if (savingsHistory.size() < 12) {
            return false;
        }
        for (int i = 1; i < savingsHistory.size(); i++) {
            double previousBalance = savingsHistory.get(i - 1);
            double currentBalance = savingsHistory.get(i);
            if (currentBalance < previousBalance * 0.5 || currentBalance < 0) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRegularDeposits(ClientEntity client) {
        List<Double> depositHistory = client.getDepositHistory();
        double totalDeposits = depositHistory.stream().mapToDouble(Double::doubleValue).sum();
        return totalDeposits >= (client.getMonthlyIncome() * 0.05 * 12);
    }

    private boolean checkBalanceBasedOnAccountAge(ClientEntity client, double loanAmount) {
        double requiredPercentage = client.getAccountAgeYears() >= 2 ? 0.1 : 0.2;
        return client.getSavingsBalance() >= (loanAmount * requiredPercentage);
    }

    private boolean checkRecentWithdrawals(ClientEntity client) {
        List<Double> depositHistory = client.getDepositHistory();
        double threshold = client.getSavingsBalance() * 0.3;

        if (depositHistory.size() < 6) {
            return false;
        }

        for (int i = depositHistory.size() - 6; i < depositHistory.size(); i++) {
            double deposit = depositHistory.get(i);
            if (deposit > threshold) {
                return false;
            }
        }

        return true;
    }

}
