package pl.com.britenet.javastepone.service.dtos.visit;

import pl.com.britenet.javastepone.repository.model.visit.Payment;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class VisitDto {

    private LocalDate date;
    private LocalTime time;
    private Payment payment;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
