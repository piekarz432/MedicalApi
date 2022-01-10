package pl.com.medicalApi.repository.model.patient;

import pl.com.medicalApi.repository.model.AbstractEntity;
import pl.com.medicalApi.repository.model.visit.VisitEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "patient")
public class PatientEntity extends AbstractEntity {

    private String name;
    private String surname;
    private LocalDate date;
    private Gender gender;
    private String personalIdentityNumber;
    @OneToMany(mappedBy = "patient")
    private List<VisitEntity> visits;

    public PatientEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPersonalIdentityNumber() {
        return personalIdentityNumber;
    }

    public void setPersonalIdentityNumber(String personalIdentifyNumber) {
        this.personalIdentityNumber = personalIdentifyNumber;
    }

    public List<VisitEntity> getVisits() {
        return visits;
    }

    public void setVisits(List<VisitEntity> visits) {
        this.visits = visits;
    }

    public void addVisit(VisitEntity visit) {
        visits.add(visit);
        visit.setPatient(this);
    }
}
