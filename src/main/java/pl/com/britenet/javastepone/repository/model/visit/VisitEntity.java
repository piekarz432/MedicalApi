package pl.com.britenet.javastepone.repository.model.visit;

import pl.com.britenet.javastepone.repository.model.AbstractEntity;
import pl.com.britenet.javastepone.repository.model.Status;
import pl.com.britenet.javastepone.repository.model.medical_procedure.MedicalProcedureEntity;
import pl.com.britenet.javastepone.repository.model.patient.PatientEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "visit")
public class VisitEntity extends AbstractEntity {

    @Column(name = "visit_date")
    private LocalDate date;
    @Column(name = "visit_time")
    private LocalTime time;
    @Enumerated(EnumType.STRING)
    private Payment payment;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    private PatientEntity patient;
    @OneToOne
    @JoinColumn(name = "medical_procedure_id", referencedColumnName = "id")
    private MedicalProcedureEntity medicalProcedure;

    public VisitEntity() {
    }

    public LocalDate getVisitDate() {
        return date;
    }

    public void setVisitDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getVisitTime() {
        return time;
    }

    public void setVisitTime(LocalTime time) {
        this.time = time;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public MedicalProcedureEntity getMedicalProcedure() {
        return medicalProcedure;
    }

    public void setMedicalProcedure(MedicalProcedureEntity medicalProcedure) {
        this.medicalProcedure = medicalProcedure;
    }
}
