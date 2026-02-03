package com.devsenior.cdiaz.bibliokeep.scheduled;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.devsenior.cdiaz.bibliokeep.model.entity.Loan;
import com.devsenior.cdiaz.bibliokeep.repository.LoanRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OverdueLoanNotificationScheduler {

    private final LoanRepository loanRepository;

    @Scheduled(cron = "0 0 9 * * *") // Ejecuta todos los días a las 9:00 AM
    public void checkAndNotifyOverdueLoans() {
        log.info("Iniciando verificación de préstamos en mora...");
        
        var currentDate = LocalDate.now();
        var overdueLoans = loanRepository.findOverdueLoans(currentDate);
        
        if (overdueLoans.isEmpty()) {
            log.info("No se encontraron préstamos en mora.");
            return;
        }
        
        log.warn("Se encontraron {} préstamo(s) en mora:", overdueLoans.size());
        
        for (Loan loan : overdueLoans) {
            var daysOverdue = currentDate.toEpochDay() - loan.getDueDate().toEpochDay();
            log.warn(
                    "Préstamo ID: {} - Libro: {} - Contacto: {} - Días en mora: {}",
                    loan.getId(),
                    loan.getBook().getTitle(),
                    loan.getContactName(),
                    daysOverdue
            );
            
            // TODO: Enviar notificación al usuario cuando se implemente el servicio de notificaciones
            // notificationService.sendOverdueNotification(loan);
        }
        
        log.info("Verificación de préstamos en mora completada.");
    }
}
